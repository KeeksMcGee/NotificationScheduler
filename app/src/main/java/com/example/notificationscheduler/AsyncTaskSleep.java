package com.example.notificationscheduler;

import android.os.AsyncTask;
import android.widget.TextView;

import java.lang.ref.WeakReference;

public class AsyncTaskSleep extends AsyncTask<Void,Integer,String> {

    private WeakReference<TextView> mTextView;

    AsyncTaskSleep(TextView tv){
        mTextView = new WeakReference<>(tv);
    }

    @Override
    protected String doInBackground(Void... voids) {
        for (int i = 0; i<1; i++){
            try{
                Thread.sleep(5000);
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        return "Awake after sleeping for 5 seconds";
    }

    @Override
    protected void onPostExecute(String result){
        mTextView.get().setText(result);
    }
}
