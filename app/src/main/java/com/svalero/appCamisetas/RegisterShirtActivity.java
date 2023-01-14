package com.svalero.appCamisetas;

import static com.svalero.appCamisetas.db.Constants.DATABASE_NAME;

import android.database.sqlite.SQLiteConstraintException;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.mapbox.geojson.Point;
import com.mapbox.maps.MapView;
import com.mapbox.maps.plugin.annotation.AnnotationConfig;
import com.mapbox.maps.plugin.annotation.AnnotationPlugin;
import com.mapbox.maps.plugin.annotation.AnnotationPluginImplKt;
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationManager;
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationManagerKt;
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions;
import com.mapbox.maps.plugin.gestures.GesturesPlugin;
import com.mapbox.maps.plugin.gestures.GesturesUtils;
import com.svalero.appCamisetas.domain.Shirt;
import com.svalero.appCamisetas.db.AppDatabase;
import com.svalero.appCamisetas.domain.Shirt;

public class RegisterShirtActivity extends AppCompatActivity {

    private MapView shirtMap;
    private Point point;
    private PointAnnotationManager pointAnnotationManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_shirt);

        shirtMap = findViewById(R.id.shirtMap);

        GesturesPlugin gesturesPlugin = GesturesUtils.getGestures(shirtMap);
        gesturesPlugin.addOnMapClickListener(point -> {
            removeAllMarkers();
            this.point = point;
            addMarker(point);
            return true;
        });

        initializePointManager();
    }

    public void saveButton(View view) {
        EditText etModel = findViewById(R.id.edit_text_model);
        EditText etDescription = findViewById(R.id.edit_text_description);
        EditText etBrand = findViewById(R.id.edit_text_brand);


        String model = etModel.getText().toString();
        String description = etDescription.getText().toString();
        String brand = etBrand.getText().toString();


        if (point == null) {
            Toast.makeText(this, R.string.select_location_message, Toast.LENGTH_LONG).show();
            return;
        }

        Shirt shirt = new Shirt(model, description, brand, point.latitude(), point.longitude());
        final AppDatabase db = Room.databaseBuilder(this, AppDatabase.class, DATABASE_NAME)
                .allowMainThreadQueries().build();
        try {
            db.shirtDao().insert(shirt);

            Toast.makeText(this, R.string.task_registered_message, Toast.LENGTH_LONG).show();
            etModel.setText("");
            etDescription.setText("");
            etBrand.setText("");
            etModel.requestFocus();
        } catch (SQLiteConstraintException sce) {
            Snackbar.make(etModel, R.string.task_registered_error, BaseTransientBottomBar.LENGTH_LONG).show();
            //Toast.makeText(this, R.string.task_registered_error, Toast.LENGTH_LONG).show();
        }
    }

    private void initializePointManager() {
        AnnotationPlugin annotationPlugin = AnnotationPluginImplKt.getAnnotations(shirtMap);
        AnnotationConfig annotationConfig = new AnnotationConfig();
        pointAnnotationManager = PointAnnotationManagerKt.createPointAnnotationManager(annotationPlugin, annotationConfig);
    }

    public void goBackButton(View view) {
        onBackPressed();
    }

    private void addMarker(Point point) {
        PointAnnotationOptions pointAnnotationOptions = new PointAnnotationOptions()
                .withPoint(point)
                .withIconImage(BitmapFactory.decodeResource(getResources(), R.mipmap.red_marker));
        pointAnnotationManager.create(pointAnnotationOptions);
    }

    private void removeAllMarkers() {
        pointAnnotationManager.deleteAll();
    }
}
