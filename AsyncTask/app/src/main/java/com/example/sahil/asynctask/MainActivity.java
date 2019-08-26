package com.example.sahil.asynctask;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

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
        Log.d("demo","onCreate thread id is" + Thread.currentThread().getId());
        new DoWorkAsync().execute("Bob", "Alice");

    }
    class DoWorkAsync extends AsyncTask<String, Integer,Double>{

        @Override
        protected void onPreExecute() {
            Log.d("demo","onPreExecute thread id is" + Thread.currentThread().getId());
        }

        @Override
        protected void onPostExecute(Double aDouble) {
            Log.d("demo","onPostExecute thread id is" + Thread.currentThread().getId());
            Log.d("demo","onPostExecute aDouble is" + aDouble);

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            Log.d("demo","onProgressUpdate thread id is" + Thread.currentThread().getId());

        }

        @Override
        protected Double doInBackground(String... params) {
            for (String var:params) {
                Log.d("demo","doInBackground params is" + params);
            }
            Log.d("demo","doInBackground thread id is" + Thread.currentThread().getId());
            publishProgress(100);
            return 333.33;
        }
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
