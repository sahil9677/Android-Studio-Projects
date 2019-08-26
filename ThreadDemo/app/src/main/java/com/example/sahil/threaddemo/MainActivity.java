package com.example.sahil.threaddemo;

import android.app.ProgressDialog;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    //ExecutorService threadPool;
    Handler handler;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Updating Progress");
        progressDialog.setMax(100);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setCancelable(false);
        //threadPool = Executors.newFixedThreadPool(4);
        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                switch (msg.what){
                    case DoWork.STATUS_START:
                        progressDialog.setProgress(0);
                            progressDialog.show();
                        Log.d("demo", "Starting.....");
                        break;
                    case DoWork.STATUS_PROGRESS:
                        progressDialog.setProgress(msg.getData().getInt(DoWork.PROGRESS_KEY));
                        Log.d("demo", "Progress....." + msg.getData().getInt(DoWork.PROGRESS_KEY));
                        break;
                    case DoWork.STATUS_STOP:
                        progressDialog.dismiss();
                        Log.d("demo", "Stopping.....");
                        break;
                }
                return false;
            }
        });
        new Thread(new DoWork()).start();
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Thread thread = new Thread(new DoWork(), "Worker 1");
                //thread.start();
                //threadPool.execute(new DoWork());
            }
        });
    }

    class DoWork implements Runnable{
        static final int STATUS_START = 0x00;
        static final int STATUS_PROGRESS = 0x01;
        static final int STATUS_STOP = 0x02;
        static final String PROGRESS_KEY = "PROGRESS";
        @Override
        public void run(){
            Message startMessage = new Message();
            startMessage.what = STATUS_START;
            handler.sendMessage(startMessage);
            //Log.d("demo", "Started Work");
            for(int i = 0; i<100; i++){
                for(int j = 0; j<10000000; j++){
                }
                    Message message = new Message();
                    message.what = STATUS_PROGRESS;
                    message.obj = (Integer)i;
                    Bundle bundle = new Bundle();
                    bundle.putInt(PROGRESS_KEY, (Integer)i);
                    message.setData(bundle);
                    handler.sendMessage(message);
            }
            Message stopMessage = new Message();
            stopMessage.what = STATUS_STOP;
            handler.sendMessage(stopMessage);
            //Log.d("demo","Ended Work");
        }
    }
}
