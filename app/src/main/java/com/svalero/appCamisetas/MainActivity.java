package com.svalero.appCamisetas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button viewShirtButton = findViewById(R.id.viewShirtsButton);
        viewShirtButton.setOnClickListener(this);
        Button viewMapButton = findViewById(R.id.viewMapButton);
        viewMapButton.setOnClickListener(this);
        Button openCameraButton = findViewById(R.id.openCameraButton);
        openCameraButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.viewShirtsButton) {
            Intent intent = new Intent(this, ShirtListActivity.class);
            startActivity(intent);
        } else if (view.getId() == R.id.viewMapButton) {
            Intent intent = new Intent(this, MapsActivity.class);
            startActivity(intent);
        }else if (view.getId() == R.id.openCameraButton){
            Intent intent = new Intent(this, CameraActivity.class);
            startActivity(intent);
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.register_shirt) {
            Intent intent = new Intent(this, RegisterShirtActivity.class);
            startActivity(intent);
            return true;
        }else if (item.getItemId() == R.id.view_map) {
            Intent intent = new Intent(this, MapsActivity.class);
            startActivity(intent);
        }else if (item.getItemId() == R.id.view_settings) {
            Intent intent = new Intent(this, PreferencesActivity.class);
            startActivity(intent);
        }
        return false;
    }
}