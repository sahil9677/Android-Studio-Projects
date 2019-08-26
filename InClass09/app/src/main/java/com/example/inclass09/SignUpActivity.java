package com.example.inclass09;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
// Sahil Deshmukh (801100363) Aashwin Patki (801079127)
public class SignUpActivity extends AppCompatActivity {

    private static final String TAG = "SignUp";

    EditText emailField;
    EditText passwordField;
    EditText cPasswordField;
    EditText firstName;
    EditText lastName;

    FirebaseAuth firebaseAuth;

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        String userId = "";
        if (firebaseUser != null) {
            // intent to contact list
            userId = firebaseUser.getUid();
            Intent intent = new Intent(getBaseContext(), Contacts.class);
            intent.putExtra(MainActivity.KEY, userId);
            startActivity(intent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

//        if (getIntent() != null) {
            firebaseAuth = FirebaseAuth.getInstance();

            emailField = findViewById(R.id.email);
            passwordField = findViewById(R.id.password);
            cPasswordField = findViewById(R.id.confirm_password);
            firstName = findViewById(R.id.first_name);
            lastName = findViewById(R.id.last_name);

            Button signUp = findViewById(R.id.signup_button);

            signUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("Login","SigUp button click");
                    if (!firstName.getText().toString().isEmpty() && !lastName.getText().toString().isEmpty() && (passwordField.getText().toString().compareTo(cPasswordField.getText().toString()) == 0)) {
//                        createAccount(emailField.getText().toString(), passwordField.getText().toString());

                        firebaseAuth.createUserWithEmailAndPassword(emailField.getText().toString(), passwordField.getText().toString())
                                .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            Log.d("Login", "create user success");
                                            FirebaseUser user = firebaseAuth.getCurrentUser();
                                            String userId = user.getUid();
                                            Intent intent = new Intent(getBaseContext(), Contacts.class);
                                            intent.putExtra(MainActivity.KEY, userId);
                                            startActivity(intent);
                                            Toast.makeText(SignUpActivity.this, "Sign Up successful", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Log.w("", "failure", task.getException());
                                            Toast.makeText(SignUpActivity.this, "Sign Up unsuccessful", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });

//                        Intent intent = new Intent(SignUpActivity.this, Contacts.class);
//                        startActivity(intent);

                    }
                }
            });

            Button cancel = findViewById(R.id.cancel_button);
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
//        }
    }

//    private void createAccount(String email, String password) {
//
//        Log.d(TAG, "createAccount:" + email);
//        if (!validateForm()) {
//            return;
//        }
//
//
//        firebaseAuth.signInWithEmailAndPassword(email, password)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            // Sign in success, update UI with the signed-in user's information
//                            Log.d(TAG, "signInWithEmail:success");
//                            FirebaseUser user = firebaseAuth.getCurrentUser();
//
//                        } else {
//                            // If sign in fails, display a message to the user.
//                            Log.w(TAG, "signInWithEmail:failure", task.getException());
//                            Toast.makeText(SignUpActivity.this, "Authentication failed.",
//                                    Toast.LENGTH_SHORT).show();
//
//                        }
//
//                        // ...
//                    }
//                });
//    }

    private boolean validateForm() {
        boolean valid = true;

        String email = emailField.getText().toString();
        if (TextUtils.isEmpty(email)) {
            emailField.setError("Required.");
            valid = false;
        } else {
            emailField.setError(null);
        }

        String password = passwordField.getText().toString();
        if (TextUtils.isEmpty(password)) {
            passwordField.setError("Required.");
            valid = false;
        } else {
            passwordField.setError(null);
        }

        return valid;
    }
}
