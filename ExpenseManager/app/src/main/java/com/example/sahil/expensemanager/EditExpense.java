package com.example.sahil.expensemanager;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;

public class EditExpense extends AppCompatActivity {

    String selectedDate;
    TextView textView;
    EditText edit1, edit2;
    String imageString;
    File file;
    DatePickerDialog datePickerDialog;
    Expense expense = new Expense();
    ImageView imageView1;
    private final int PICK_IMAGE_CAMERA = 1, PICK_IMAGE_GALLERY = 2;
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_expense);

        edit1 = (EditText) findViewById(R.id.EeditText);
        edit2 = (EditText) findViewById(R.id.EeditText2);
        expense = (Expense) getIntent().getSerializableExtra("Expense");
        textView = (TextView) findViewById(R.id.Edate);
        imageView1 = (ImageView) findViewById(R.id.imageView2);
        textView.setText(expense.date);
        try {
            if(!expense.image.equals("")){
                Bitmap imageBitmap = decodeFromFirebaseBase64(expense.image);
                imageView1.setImageBitmap(imageBitmap);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        edit1.setText(expense.name);
        edit2.setText(String.valueOf(expense.cost));
        Button button = (Button)findViewById(R.id.Ebutton);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(EditExpense.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        selectedDate = String.valueOf(month) + "," + dayOfMonth + "," + year;
                        textView.setText(selectedDate);
                    }
                },mYear,mMonth,mDay);
                datePickerDialog.updateDate(mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
        Button button2 = (Button) findViewById(R.id.Ebutton2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageSelection();
            }
        });

        Button button1 = findViewById(R.id.Ebutton3);
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
                    String key = expense.expenseID;
                    Log.d("expense id", expense.expenseID);
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
    public static Bitmap decodeFromFirebaseBase64(String image) throws IOException {
        byte[] decodedByteArray = android.util.Base64.decode(image, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedByteArray, 0, decodedByteArray.length);
    }
}
