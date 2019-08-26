package com.example.sahil.passwordgenerator;
//Group 11
// Sahil Deshmukh(801100363) Aashwin Patki(801079127)
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    public static final int REQ_CODE = 100;
    public int lengthT, countT, lengthA, countA;
    public ExecutorService threadPool;
    Handler handler;
    public static ProgressDialog progressDialog;
    public static ArrayList passwords = new ArrayList<String>();
    public static ArrayList passwords1 = new ArrayList<String>();
    public Util u = new Util();
    public static TextView textView3;
    public static TextView textView;
    public static TextView textView1;
    public static TextView textView2;
    public static SeekBar seekBar1;
    public static SeekBar seekBar2;
    public static SeekBar seekBar3;
    public static SeekBar seekBar4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Updating Progress");

        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setCancelable(false);
        setContentView(R.layout.activity_main);
        seekBar1 = (SeekBar) findViewById(R.id.seekBar);
        seekBar2 = (SeekBar) findViewById(R.id.seekBar2);
        seekBar3 = (SeekBar) findViewById(R.id.seekBar3);
        seekBar4 = (SeekBar) findViewById(R.id.seekBar4);
        textView = (TextView) findViewById(R.id.textView7);
        textView1 = (TextView) findViewById(R.id.textView10);
        textView2 = (TextView) findViewById(R.id.textView9);
        textView3 = (TextView) findViewById(R.id.textView11);
        seekBar1.setProgress(1);
        seekBar2.setProgress(0);
        seekBar3.setProgress(1);
        seekBar4.setProgress(0);
        countA = 1;
        countT = 1;
        lengthA = 7;
        lengthT = 7;
        threadPool = Executors.newFixedThreadPool(2);
        seekBar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int current = progress;
                String prog = String.valueOf(current);
                textView.setText(prog);
                countT = current;

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        seekBar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int current = progress;
                lengthT = current + 7;
                String prog = String.valueOf(lengthT);
                textView1.setText(prog);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        seekBar3.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int current = progress;
                String prog = String.valueOf(current);
                textView2.setText(prog);
                countA = current;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        seekBar4.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int current = progress;
                lengthA = current + 7;
                String prog = String.valueOf(lengthA);
                textView3.setText(prog);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        Button button = (Button) findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AsyncTask asyncTask = new DoWorkAsync().execute(countA);
                handler = new Handler(new Handler.Callback() {
                    @Override
                    public boolean handleMessage(Message msg) {
                        switch (msg.what){
                            case DoWork.STATUS_START:
                                progressDialog.setMax(countT+countA);
                                progressDialog.setProgress(0);
                                progressDialog.show();
                                Log.d("demo", "Starting.....");
                                break;
                            case DoWork.STATUS_PROGRESS:
                                progressDialog.setProgress(msg.getData().getInt(DoWork.PROGRESS_KEY)+1);
                                Log.d("demo", "Progress....." + msg.getData().getInt(DoWork.PROGRESS_KEY));
                                break;
                            case DoWork.STATUS_STOP:
                                while (passwords1.size()!=countA){
                                    Log.d("demo", "AsyncTask still running");
                                }
                                asyncTask.cancel(true);
                                progressDialog.dismiss();
                                Log.d("demo", "Stopping.....");
                                Intent intent = new Intent(MainActivity.this, GeneratedPasswords.class);
                                intent.putStringArrayListExtra("key",  passwords);
                                intent.putStringArrayListExtra("key1", passwords1);
                                startActivity(intent);
                                break;
                        }
                        return false;
                    }
                });
                threadPool.execute(new DoWork());

            }
        });
    }
    class DoWorkAsync extends AsyncTask<Integer, Integer, Void> {

        @Override
        protected Void doInBackground(Integer... integers) {
            for (int i=1; i<=countA; i++) {
                passwords1.add(u.getPassword(lengthA));
                Log.d("demo", (String) passwords1.get(i - 1));
            }
            return null;
        }
    }
    class DoWork implements Runnable {
        static final int STATUS_START = 0x00;
        static final int STATUS_PROGRESS = 0x01;
        static final int STATUS_STOP = 0x02;
        static final String PROGRESS_KEY = "PROGRESS";

        @Override
        public void run() {
            Message startMessage = new Message();
            startMessage.what = STATUS_START;
            handler.sendMessage(startMessage);
            //Log.d("demo", "Started Work");
            for (int i = 1; i <= countT; i++) {
                passwords.add(u.getPassword(lengthT));
                Log.d("demo", (String) passwords.get(i - 1));
                Message message = new Message();
                message.what = STATUS_PROGRESS;
                message.obj = (Integer) i;
                Bundle bundle = new Bundle();
                bundle.putInt(PROGRESS_KEY, (Integer) i);
                message.setData(bundle);
                handler.sendMessage(message);
            }
            Message stopMessage = new Message();
            stopMessage.what = STATUS_STOP;
            handler.sendMessage(stopMessage);
        }
    }
}
