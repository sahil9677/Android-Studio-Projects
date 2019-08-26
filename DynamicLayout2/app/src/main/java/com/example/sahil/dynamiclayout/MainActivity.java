package com.example.sahil.dynamiclayout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RelativeLayout relativeLayout = new RelativeLayout(this);
        relativeLayout.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
        setContentView(relativeLayout);

        TextView textView = new TextView(this);
        textView.setText(R.string.hello_world);
        textView.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
        relativeLayout.addView(textView);
        textView.setId(View.generateViewId());

        Button button = new Button(this);
        button.setText(R.string.click_me);
        RelativeLayout.LayoutParams buttonLayoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        buttonLayoutParams.addRule(RelativeLayout.BELOW, textView.getId());
        button.setLayoutParams(buttonLayoutParams);
        relativeLayout.addView(button);
    }
}
