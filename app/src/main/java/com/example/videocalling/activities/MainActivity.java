package com.example.videocalling.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

import com.example.videocalling.R;
import com.example.videocalling.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


          new Handler().postDelayed(new Runnable() {
              @Override
              public void run() {
                  startActivity(new Intent(MainActivity.this, WelcomeActivity.class));
              }
          },2000);
        }
    }
