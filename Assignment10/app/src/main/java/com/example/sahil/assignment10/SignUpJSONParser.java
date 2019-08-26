package com.example.sahil.assignment10;

import org.json.JSONException;
import org.json.JSONObject;

public class SignUpJSONParser {
    static public class JSONParser {
        static LogInDetails parseTracks(String in) throws JSONException {
            JSONObject root = new JSONObject(in);
            LogInDetails logInDetails = new LogInDetails();
            if(root.has("auth"))
                logInDetails.setAuth(root.getBoolean("auth"));
            if(root.has("token"))
                logInDetails.setToken(root.getString("token"));
            return logInDetails;
        }


    }
}
