package com.example.sahil.assignment10;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LogIN extends AppCompatActivity {
    String token = "";
    SharedPreferences sharedpreferences;
    EditText emailField;
    EditText passwordField;
    public static final String MyFAVORITES = "MyToken" ;
    Button login;
    Button signUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_in);
        emailField = findViewById(R.id.l_email);
        passwordField = findViewById(R.id.l_password);
        sharedpreferences = getSharedPreferences(MyFAVORITES, Context.MODE_PRIVATE);
        login = findViewById(R.id.login_button);
        signUp = findViewById(R.id.l_signup_button);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LogIN.this, SignUP.class);
                startActivity(i);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OkHttpClient client = new OkHttpClient();
                RequestBody formBody = new FormBody.Builder()
                        .add("email", emailField.getText().toString())
                        .add("password", passwordField.getText().toString())
                        .build();
                Request request = new Request.Builder().url("http://ec2-54-164-74-55.compute-1.amazonaws.com/api/login").post(formBody).build();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        try{
                            final LogInDetails logInDetails = SignUpJSONParser.JSONParser.parseTracks(response.body().string());
                            Log.d("response built","done");
                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            editor.putBoolean("auth",true);
                            editor.putString("token", logInDetails.getToken());
                            editor.commit();
                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(LogIN.this,"User has been created",Toast.LENGTH_SHORT).show();
                                }
                            });
                            Log.d("before intent","done");
                            Intent i = new Intent(LogIN.this, MainActivity.class);
                            startActivity(i);
                            finishAffinity();
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }
}
