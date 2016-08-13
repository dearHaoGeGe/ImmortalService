package com.my.immortalservice.service;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.my.immortalservice.MyProcess;

/**
 * Created by YJH on 2016/8/13.
 */
public class RemoteService extends Service {

    private static final String TAG="哈哈哈—RemoteService";
    private MyBinder binder;
    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG,"RemoteService绑定成功~");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            //如果走这个方法代表远程服务和本地服务连接异常，这个时候就要启动本地服务

            Log.d(TAG,"RemoteService绑定失败~");
            RemoteService.this.startService(new Intent(RemoteService.this,LocalService.class));
            //启动了本地服务之后还要和本地服务建立连接
            RemoteService.this.bindService(new Intent(RemoteService.this,LocalService.class),conn,Context.BIND_IMPORTANT);
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();

        if (binder == null) {
            binder = new MyBinder();
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        /**连接本地服务*/
        bindService(new Intent(this, LocalService.class), conn, Context.BIND_IMPORTANT);
        return START_STICKY;    /**表示这个服务被异常干掉依然会重启*/
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private class MyBinder extends MyProcess.Stub {

        @Override
        public String getProcessName() throws RemoteException {
            return "RemoteService";
        }
    }
}
