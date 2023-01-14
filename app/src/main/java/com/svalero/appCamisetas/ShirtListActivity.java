package com.svalero.appCamisetas;
import static com.svalero.appCamisetas.db.Constants.DATABASE_NAME;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.svalero.appCamisetas.adapter.ShirtAdapter;
import com.svalero.appCamisetas.db.AppDatabase;
import com.svalero.appCamisetas.domain.Shirt;

import java.util.ArrayList;
import java.util.List;

public class ShirtListActivity extends AppCompatActivity  {

    private List<Shirt> shirtList;
    private ShirtAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shirt_list);

        shirtList = new ArrayList<>();

        RecyclerView recyclerView = findViewById(R.id.shirt_list);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ShirtAdapter(this,shirtList);
        recyclerView.setAdapter(adapter);

    }

    @Override
    protected void onResume() {
        super.onResume();

        final AppDatabase db = Room.databaseBuilder(this, AppDatabase.class, DATABASE_NAME)
                .allowMainThreadQueries().build();
        shirtList.clear();
        shirtList.addAll(db.shirtDao().getAll());
        adapter.notifyDataSetChanged();
    }
}