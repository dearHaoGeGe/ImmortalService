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
public class LocalService extends Service {

    private static final String TAG="哈哈哈—LocalService";
    private MyBinder binder;
    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG,"LocalService绑定成功~");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            //如果走这个方法代表本地服务和远程服务连接异常，这个时候就要启动远程服务

            Log.d(TAG,"LocalService绑定失败~");
            LocalService.this.startService(new Intent(LocalService.this,RemoteService.class));
            //启动了远程服务之后还要和远程服务建立连接
            LocalService.this.bindService(new Intent(LocalService.this,RemoteService.class),conn,Context.BIND_IMPORTANT);
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
        /**连接远程服务*/
        bindService(new Intent(this, RemoteService.class), conn, Context.BIND_IMPORTANT);
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
            return "LocalService";
        }
    }
}
