package com.my.immortalservice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.my.immortalservice.service.LocalService;
import com.my.immortalservice.service.RemoteService;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startService(new Intent(this, LocalService.class));
        startService(new Intent(this, RemoteService.class));
    }
}
