package com.mudtoperator;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class SplashActivity extends AppCompatActivity implements View.OnClickListener {

    private final int DURACION_SPLASH = 3000; // 3 segundos
    private final int MY_PERMISSION_ACCESS_FINE_LOCATION = 12, MY_WRITE_EXTERNAL_STORAGE = 13, MY_READ_EXTERNAL_STORAGE = 14,
            MY_READ_PHONE_STATE = 15;
    private LinearLayout splash_login_lay;
    private Button login_btn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        splash_login_lay = (LinearLayout)findViewById(R.id.splash_login_lay);
        login_btn = (Button)findViewById(R.id.login_btn);
        login_btn.setOnClickListener(this);

        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion >= Build.VERSION_CODES.M)
            checkGPSPermission();
            //checkWritePermission();
    }


    @TargetApi(Build.VERSION_CODES.M)
    private void checkWritePermission() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    MY_WRITE_EXTERNAL_STORAGE);
            return;
        } else {
            checkReadPermission();
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void checkReadPermission() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                    MY_READ_EXTERNAL_STORAGE);
            return;
        } else {
            //onUIThread();
            checkReadPhonePermission();
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void checkGPSPermission() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSION_ACCESS_FINE_LOCATION);
            return;
        } else {
            Singleton.getGpsConfig().configuracionLocationManager();
            checkWritePermission();
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void checkReadPhonePermission() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_PHONE_STATE)
                        != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_PHONE_STATE},
                    MY_READ_PHONE_STATE);
            return;
        } else {
            onUIThread();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSION_ACCESS_FINE_LOCATION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Singleton.getGpsConfig().configuracionLocationManager();
                    checkWritePermission();
                }
                break;
            case MY_WRITE_EXTERNAL_STORAGE:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    checkReadPermission();
                }
                break;
            case MY_READ_EXTERNAL_STORAGE:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    checkReadPhonePermission();
                }
                break;
            case MY_READ_PHONE_STATE:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    onUIThread();
                }
                break;
        }
    }

    private void mainIntent(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void onUIThread(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new Handler().postDelayed(new Runnable(){
                    public void run(){
                        splash_login_lay.setVisibility(View.VISIBLE);
                    };
                }, DURACION_SPLASH);
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.login_btn:
                mainIntent();
                break;
        }
    }
}