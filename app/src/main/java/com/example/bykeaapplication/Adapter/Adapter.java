package com.example.bykeaapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.bykeaapplication.Model.Model;
import com.example.bykeaapplication.R;
import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private ArrayList<Model> data;
    private Context context;

    // Constructor to initialize the adapter with data and context
    public Adapter(Context context, ArrayList<Model> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the card layout XML and create a ViewHolder for it
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.cardlayout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Bind data to the ViewHolder
        Model temp = data.get(position);
        holder.image_view.setImageResource(data.get(position).getImage_view());
        holder.txt_1.setText(data.get(position).getTxt_1());
    }

    @Override
    public int getItemCount() {
        // Return the number of items in the data list
        return data.size();
    }

    // ViewHolder class to hold and manage the views of each item
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image_view;
        TextView txt_1;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Initialize views from the card layout
            image_view = itemView.findViewById(R.id.img_food);
            txt_1 = itemView.findViewById(R.id.text_food);

            // Set up click listener or other view interactions if needed
            // For example, launching an activity when an item is clicked
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Handle item click here
                    Model item = data.get(getAdapterPosition());
                    // Intent intent = new Intent(context, YourActivity.class);
                    // Add extras to the intent if needed
                    // context.startActivity(intent);
                }
            });
        }
    }
}
