package com.example.sahil.chatroom;

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

public class LogIn extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;

    EditText emailField;
    EditText passwordField;

    Button login;
    Button signUp;

    static final String TAG = "Login";
    static final String KEY = "ITEM";

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        String userId = "";

        if (currentUser != null) {
            userId = currentUser.getUid();
            Intent intent = new Intent(getBaseContext(), MainActivity.class);
            intent.putExtra(MainActivity.KEY, userId);
            startActivity(intent);
        }

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        firebaseAuth = FirebaseAuth.getInstance();
        emailField = findViewById(R.id.l_email);
        passwordField = findViewById(R.id.l_password);

        login = findViewById(R.id.login_button);
        signUp = findViewById(R.id.l_signup_button);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!emailField.getText().toString().isEmpty() || !passwordField.getText().toString().isEmpty()) {

                    firebaseAuth.signInWithEmailAndPassword(emailField.getText().toString(), passwordField.getText().toString())
                            .addOnCompleteListener(LogIn.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Log.d("Login", "success");
                                        FirebaseUser user = firebaseAuth.getCurrentUser();
                                        String userId = user.getUid();
                                        Intent intent = new Intent(getBaseContext(), MainActivity.class);
                                        intent.putExtra(MainActivity.KEY, userId);
                                        startActivity(intent);
                                        Toast.makeText(LogIn.this, "Login successful", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Log.w("Login", "failure", task.getException());
                                        Toast.makeText(LogIn.this, "Authentication failed", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                } else
                    Toast.makeText(v.getContext(), "Fields are empty", Toast.LENGTH_SHORT).show();
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LogIn.this, SignUp.class);
                startActivity(intent);
            }
        });
    }
    private void createAccount(String email, String password) {

        Log.d(TAG, "createAccount:" + email);
        if (!validateForm()) {
            return;
        }


        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = firebaseAuth.getCurrentUser();

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LogIn.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }
                });

    }

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
