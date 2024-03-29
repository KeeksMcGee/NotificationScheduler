package com.example.notificationscheduler;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

public class NotificationJobService extends JobService {

    private TextView mTextView;

    NotificationManager mNotifyManager;
    //Notification channel ID
    private static final String PRIMARY_CHANNEL_ID = "primary_notification_channel";



    //Create a Notification channel, for OREO and higher.
    public void createNotificationChannel(){

        //Define notification manager object.
        mNotifyManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);

        //Notification channels are only available in OREO and higher, so add a check on SDK version
        if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O){
            //Create the NotificationChannel with all the parameters.
            NotificationChannel notificationChannel = new NotificationChannel(PRIMARY_CHANNEL_ID,
                    "Job Service notification", NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setDescription("Notifications from Job Service");
            mNotifyManager.createNotificationChannel(notificationChannel);
        }
    }

    @Override
    public boolean onStartJob(JobParameters params) {
        //create the notification channel
        createNotificationChannel();
        //Set up the notification content intent to launch the app when clicked
        PendingIntent contentPendingIntent = PendingIntent.getActivity(this,0,
                new Intent(this,MainActivity.class),PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(
                this, PRIMARY_CHANNEL_ID)
                .setContentTitle("Job Service")
                .setContentText("Your Job is running!")
                .setContentIntent(contentPendingIntent)
                .setSmallIcon(R.drawable.ic_job_running)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setAutoCancel(true);

        mNotifyManager.notify(0,builder.build());

        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Toast.makeText(this,"Job was stopped",Toast.LENGTH_SHORT).show();
        return true;
    }

}
