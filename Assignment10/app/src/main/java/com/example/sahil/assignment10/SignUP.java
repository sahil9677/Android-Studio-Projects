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
import android.webkit.MimeTypeMap;
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

import static com.example.sahil.assignment10.LogIN.MyFAVORITES;

public class SignUP extends AppCompatActivity {
    SharedPreferences sharedpreferences;
    EditText emailField;
    EditText passwordField;
    EditText cPasswordField;
    EditText firstName;
    EditText lastName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        emailField = findViewById(R.id.email);
        passwordField = findViewById(R.id.password);
        cPasswordField = findViewById(R.id.confirm_password);
        firstName = findViewById(R.id.first_name);
        lastName = findViewById(R.id.last_name);
        Button signUp = findViewById(R.id.signup_button);
        sharedpreferences = getSharedPreferences(MyFAVORITES, Context.MODE_PRIVATE);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("button clicked","done");
                boolean validate = validate(firstName.getText().toString(),lastName.getText().toString(), emailField.getText().toString(), passwordField.getText().toString(), cPasswordField.getText().toString());
                if(validate){
                    Log.d("after Validate","done");
                    if(passwordField.getText().toString().matches(cPasswordField.getText().toString())) {
                        Log.d("passwords match","done");
                        OkHttpClient client = new OkHttpClient();
                        RequestBody formBody = new FormBody.Builder()
                                .add("name", firstName.getText().toString())
                                .add("email", emailField.getText().toString())
                                .add("password", passwordField.getText().toString())
                                .build();
                        Log.d("Request build","done");
                        Request request = new Request.Builder().url("http://ec2-3-91-77-16.compute-1.amazonaws.com:3000/api/auth/register").post(formBody).build();
                        client.newCall(request).enqueue(new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {

                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                try{

                                    Log.d("response built","done");
                                    final LogInDetails logInDetails = SignUpJSONParser.JSONParser.parseTracks(response.body().string());
                                    SharedPreferences.Editor editor = sharedpreferences.edit();
                                    editor.putBoolean("auth",true);
                                    editor.putString("token", logInDetails.getToken());
                                    editor.commit();
                                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(SignUP.this,"User has been created",Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                    Log.d("before intent","done");
                                    Intent i = new Intent(SignUP.this, MainActivity.class);
                                    startActivity(i);
                                    finishAffinity();
                                }catch (JSONException e){
                                    e.printStackTrace();
                                }
                            }
                        });

                    }else{
                        Toast.makeText(SignUP.this,"password & repeatpassword are not matching",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
    public boolean validate(String firstName,String lastName,String email,String password,String repeatpassword){
        boolean flag = true;
        StringBuilder error= new StringBuilder();
        if("".equalsIgnoreCase(firstName)){
            error.append("firstName");
            flag = false;
        } if("".equalsIgnoreCase(lastName)){
            error.append(", lastName");
            flag = false;
        } if("".equalsIgnoreCase(email)){
            error.append(", email");
            flag = false;
        }
        if("".equalsIgnoreCase(password)){
            error.append(", password");
            flag = false;
        } if("".equalsIgnoreCase(repeatpassword)){
            error.append(", repeatpassword");
            flag = false;
        }
        if(!flag)
            Toast.makeText(SignUP.this,error+" cannot be empty",Toast.LENGTH_SHORT).show();
        return flag;
    }
}
