package com.svalero.appCamisetas.adapter;

import static com.svalero.appCamisetas.db.Constants.DATABASE_NAME;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.svalero.appCamisetas.R;
import com.svalero.appCamisetas.ShirtDetailsActivity;
import com.svalero.appCamisetas.db.AppDatabase;
import com.svalero.appCamisetas.domain.Shirt;

import java.util.List;

public class ShirtAdapter extends RecyclerView.Adapter<ShirtAdapter.ShirtHolder> {

    private Context context;
    private List<Shirt> shirtList;

    public ShirtAdapter(Context context, List<Shirt> dataList) {
        this.context = context;
        this.shirtList = dataList;
    }

    @Override
    public ShirtHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_shirt_item, parent, false);
        return new ShirtHolder(view);
    }

    @Override
    public void onBindViewHolder(ShirtHolder holder, int position) {
        holder.shirtModel.setText(shirtList.get(position).getModel());
        holder.shirtDescription.setText(shirtList.get(position).getDescription());
    }


    @Override
    public int getItemCount() {
        return shirtList.size();
    }

    public class ShirtHolder extends RecyclerView.ViewHolder {
        public TextView shirtModel;
        public TextView shirtDescription;
        public Button seeDetailsButton;
        public Button deleteShirtButton;
        public View parentView;

        public ShirtHolder(View view){
            super(view);
            parentView = view;

            shirtModel = view.findViewById(R.id.tv_shirt_model);
            shirtDescription = view.findViewById(R.id.tv_shirt_description);
            seeDetailsButton = view.findViewById(R.id.see_details_button);
            deleteShirtButton = view.findViewById(R.id.delete_shirt_button);


            deleteShirtButton.setOnClickListener(v -> deleteShirt(getAdapterPosition()));
            seeDetailsButton.setOnClickListener(v -> seeDetails(getAdapterPosition()));
        }
    }

    private void deleteShirt (int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Estas seguro?")
                .setTitle("Eliminar Camisa")
                .setPositiveButton("Yes", (dialog, id) -> {
                    final AppDatabase db = Room.databaseBuilder(context, AppDatabase.class, DATABASE_NAME)
                            .allowMainThreadQueries().build();
                    Shirt shirt = shirtList.get(position);
                    db.shirtDao().delete(shirt);

                    shirtList.remove(position);
                    notifyItemRemoved(position);
                })
                .setNegativeButton("No", (dialog, id) -> dialog.dismiss());
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void seeDetails(int position) {
        Shirt shirt = shirtList.get(position);

        Intent intent = new Intent(context, ShirtDetailsActivity.class);
        intent.putExtra("model", shirt.getModel());
        context.startActivity(intent);
    }
}
