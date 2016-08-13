package com.my.immortalservice.service;

import android.annotation.TargetApi;
import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by YJH on 2016/8/14.
 */
@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class JobHandleServer extends JobService{

    private int jobId=0;
    private static final String TAG="哈哈哈—JobHandleServer";

    @Override
    public void onCreate() {
        super.onCreate();

        Log.d(TAG,"onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG,"onStartCommand");
        scheduleJob(getJobInfo());
        return START_NOT_STICKY;
    }

    @Override
    public boolean onStartJob(JobParameters params) {
        Toast.makeText(this,"process start",Toast.LENGTH_SHORT).show();
        Log.d(TAG,"onStartJob");
        scheduleJob(getJobInfo());
        startService(new Intent(this,LocalService.class));
        startService(new Intent(this,RemoteService.class));
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.d(TAG,"onStopJob");
        startService(new Intent(this,LocalService.class));
        startService(new Intent(this,RemoteService.class));
        return true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void scheduleJob(JobInfo j){
        Log.e(TAG,"scheduleJob");
        JobScheduler js= (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        js.schedule(j);
    }

    private JobInfo getJobInfo(){
        JobInfo.Builder builder=new JobInfo.Builder(jobId++,new ComponentName(this,JobHandleServer.class));
        builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY);
        return builder.build();
    }
}
