package com.example.rhodium;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.room.Room;

import com.example.rhodium.data.AppDataBase;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 1;
    public static AppDataBase appDatabase;
    public static int strength_level;
    private Button readdata;
    public Button b;
    public  Button QOE;
    private Context context;


    private void setupRoomDatabase() {
        appDatabase = Room.databaseBuilder(getApplicationContext(),
                AppDataBase.class, "Parameter")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();

    }

    @Override
    protected void onCreate(Bundle bundle) {

        super.onCreate(bundle);
        this.context = getApplicationContext();

        setContentView(R.layout.activity_main);
        setupRoomDatabase();
        setContentView(R.layout.activity_main);

        requestPermissionsIfNecessary(new String[]{

                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.INTERNET,
                Manifest.permission.ACCESS_COARSE_LOCATION,
        });
//        appDatabase.clearAllTables();
        b = (Button) findViewById(R.id.map);
        b.setOnClickListener(this);

        QOE = (Button) findViewById(R.id.qoe);
        QOE.setOnClickListener(this);


        readdata = (Button) findViewById(R.id.button_read);
        readdata.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.map:
                startActivity(new Intent(MainActivity.this, MapActivity.class));
                break;
            case R.id.button_read:
                startActivity(new Intent(MainActivity.this, ReadDataActivity.class));
                break;
            case  R.id.qoe:
                startActivity(new Intent(MainActivity.this, qoe.class));
                break;

        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        ArrayList<String> permissionsToRequest = new ArrayList<>();
        for (int i = 0; i < grantResults.length; i++) {
            permissionsToRequest.add(permissions[i]);
        }
        if (permissionsToRequest.size() > 0) {
            ActivityCompat.requestPermissions(
                    this,
                    permissionsToRequest.toArray(new String[0]),
                    REQUEST_PERMISSIONS_REQUEST_CODE);
        }
    }


    private void requestPermissionsIfNecessary(String[] permissions) {
        ArrayList<String> permissionsToRequest = new ArrayList<>();

        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission)
                    != PackageManager.PERMISSION_GRANTED) {
                // Permission is not granted
                permissionsToRequest.add(permission);
            }
        }
        if (permissionsToRequest.size() > 0) {
            ActivityCompat.requestPermissions(
                    this,
                    permissionsToRequest.toArray(new String[0]),
                    REQUEST_PERMISSIONS_REQUEST_CODE);
        }
    }
}
