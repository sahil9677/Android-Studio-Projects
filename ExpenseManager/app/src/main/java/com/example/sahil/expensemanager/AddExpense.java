package com.example.sahil.expensemanager;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AddExpense extends AppCompatActivity {
    String selectedDate;
    TextView textView;
    EditText edit1, edit2;
    String imageString;
    DatePickerDialog datePickerDialog;
    File file;
    private final int PICK_IMAGE_CAMERA = 1, PICK_IMAGE_GALLERY = 2;
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);
        textView = (TextView) findViewById(R.id.date);
        Button button = (Button)findViewById(R.id.button);
    button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(AddExpense.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        selectedDate = String.valueOf(month) + "," + dayOfMonth + "," + year;
                        textView.setText(selectedDate);
                    }
                },mYear,mMonth,mDay);
                datePickerDialog.show();
            }
        });
        Button button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageSelection();
            }
        });
        edit1 = (EditText) findViewById(R.id.editText);
        edit2 = (EditText) findViewById(R.id.editText2);
        Button button1 = findViewById(R.id.button3);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edit1.getText().toString().equals("")){
                    edit1.setError("Enter a value");
                }else if( edit2.getText().toString().equals("")){
                    edit2.setError("Enter a value");
                }else{
                    Expense expense1 = new Expense();
                    expense1.name = edit1.getText().toString();
                    expense1.cost = Double.parseDouble(edit2.getText().toString());
                    expense1.date = selectedDate;
                    expense1.image = imageString;
                    DatabaseReference ref2 = mDatabase.child("users");
                    String key = ref2.push().getKey();
                    expense1.expenseID = key;
                    Log.d("expense add", expense1.toString());
                    ref2.child(key).setValue(expense1);
                    Intent intent = new Intent();
                    intent.putExtra(MainActivity.VALUE_KEY, "value");
                    setResult(RESULT_OK, intent);
                }
                finish();
            }
        });
    }
    public void imageSelection(){
        PackageManager pm = getPackageManager();
        final CharSequence[] options = {"Take Photo", "Choose From Gallery","Cancel"};
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
        builder.setTitle("Select Option");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo")) {
                    dialog.dismiss();
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, PICK_IMAGE_CAMERA);
                } else if (options[item].equals("Choose From Gallery")) {
                    dialog.dismiss();
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    pickPhoto.setAction(Intent.ACTION_GET_CONTENT);
                    pickPhoto.setType("image/*");
                    startActivityForResult(pickPhoto, PICK_IMAGE_GALLERY);
                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_CAMERA) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            ImageView imageView = (ImageView) findViewById(R.id.imageView);
            imageView.setImageBitmap(imageBitmap);
            encodeBitmapAndSaveToFirebase(imageBitmap);
        } else if (requestCode == PICK_IMAGE_GALLERY) {
            Uri imageUri = data.getData();
            Bitmap imageBitmap = null;
            try {
                imageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            ImageView imageView = (ImageView) findViewById(R.id.imageView);
            imageView.setImageBitmap(imageBitmap);
            encodeBitmapAndSaveToFirebase(imageBitmap);
        }
    }
    public void encodeBitmapAndSaveToFirebase(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        imageString = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);
        Log.d("demo", imageString);
    }
}
