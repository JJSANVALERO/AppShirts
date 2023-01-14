package com.svalero.appCamisetas;

import static com.svalero.appCamisetas.db.Constants.DATABASE_NAME;

import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.svalero.appCamisetas.db.AppDatabase;
import com.svalero.appCamisetas.domain.Shirt;


public class ShirtDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shirt_details);

        Intent intent = getIntent();
        String model = intent.getStringExtra("model");
        if (model == null)
            return;

        // Cargo los detalles del aviso
        final AppDatabase db = Room.databaseBuilder(this, AppDatabase.class, DATABASE_NAME)
                .allowMainThreadQueries().build();
        Shirt shirt = db.shirtDao().getByModel(model);
        fillData(shirt);

    }


    private void fillData(Shirt shirt) {
        EditText etModel = findViewById(R.id.et_shirt_model);
        EditText etDescription = findViewById(R.id.et_shirt_description);
        EditText etBrand = findViewById(R.id.et_shirt_brand);

        etModel.setText(shirt.getModel());
        etDescription.setText(shirt.getDescription());
        etBrand.setText(shirt.getBrand());
    }

//    public void modifyButtonClicked(View view) {
//        Intent intent = new Intent(this,ModifyShirtActivity.class);
//        startActivity(intent);
//    }

    public void modifyShirt(View view) {
        Intent intent = getIntent();

        String model = intent.getStringExtra("model");
        if (model == null)
            return;

        final AppDatabase db = Room.databaseBuilder(this,AppDatabase.class,DATABASE_NAME)
                .allowMainThreadQueries().build();

        Shirt newShirt = db.shirtDao().getByModel(model);

        EditText descriptionField = findViewById(R.id.et_shirt_description);
        EditText brandField = findViewById(R.id.et_shirt_brand);

        newShirt.setDescription(descriptionField.getText().toString());
        newShirt.setBrand(brandField.getText().toString());

        try {
            db.shirtDao().getByModel(newShirt.getModel());
            db.shirtDao().update(newShirt);
            Toast.makeText(this, R.string.shirt_modified_message, Toast.LENGTH_LONG).show();
            onBackPressed();
        }catch(SQLiteConstraintException sce) {
            Toast.makeText(this, "La camiseta no existe", Toast.LENGTH_LONG).show();

        }

    }


}