package com.example.inclass09;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;
// Sahil Deshmukh (801100363) Aashwin Patki (801079127)
public class NewContactActivity extends AppCompatActivity {

    Item contact = new Item();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_contact);

        if (getIntent() != null && getIntent().getExtras() != null) {
            final String item = (String) getIntent().getExtras().getSerializable(MainActivity.KEY);
            final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(item);

            Button submit = findViewById(R.id.submit);
            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EditText name = findViewById(R.id.name);
                    EditText email = findViewById(R.id.nc_email);
                    EditText phone = findViewById(R.id.phone);
                    ImageView imageView = findViewById(R.id.imageView);

                    if (!name.getText().toString().isEmpty() ||
                            !email.getText().toString().isEmpty() ||
                            !phone.getText().toString().isEmpty()) {
                        contact.setName(name.getText().toString());
                        contact.setEmail(email.getText().toString());
                        contact.setPhoneNumber(phone.getText().toString());

                        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
                        contact.setImage(encodeBitmapAndSaveToFirebase(bitmap));

                        String key = databaseReference.push().getKey();
                        contact.setId(key);
                        databaseReference.child(key).setValue(contact);
                        Toast.makeText(NewContactActivity.this, "Contact created", Toast.LENGTH_SHORT).show();
                        finish();
                    } else
                        Toast.makeText(v.getContext(), "Fields empty", Toast.LENGTH_SHORT).show();
                }
            });

            ImageView imageView = findViewById(R.id.imageView);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imageSelection();
                }
            });

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ImageView imageView = findViewById(R.id.imageView);
        if (requestCode == 0) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imageView.setImageBitmap(imageBitmap);
        }
    }

    public void imageSelection() {
        PackageManager packageManager = getPackageManager();
        final CharSequence[] options = {"Take a picture", "Cancel"};
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
        builder.setTitle("Select option");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (options[which].equals("Take a picture")) {
                    dialog.dismiss();
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, 0);
                } else if (options[which].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    public String encodeBitmapAndSaveToFirebase(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        String imageString = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);
        Log.d("demo", imageString);

        return imageString;
    }
}
