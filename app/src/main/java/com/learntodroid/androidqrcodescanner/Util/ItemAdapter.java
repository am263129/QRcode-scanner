package com.learntodroid.androidqrcodescanner.Util;

import android.content.Intent;
import android.database.Observable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.learntodroid.androidqrcodescanner.Activity.CategoryActivity;
import com.learntodroid.androidqrcodescanner.Activity.DetailActivity;
import com.learntodroid.androidqrcodescanner.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

private ArrayList<Item> localDataSet;

/**
 * Provide a reference to the type of views that you are using
 * (custom ViewHolder).
 */
public static class ViewHolder extends RecyclerView.ViewHolder {
    private TextView name,desc,price;
    private ImageView imageView;
    private ConstraintLayout root;

    public ViewHolder(View view) {
        super(view);
        // Define click listener for the ViewHolder's View
        name = (TextView) view.findViewById(R.id.name);
        desc = view.findViewById(R.id.desc);
        price = view.findViewById(R.id.price);
        imageView = view.findViewById(R.id.imageView);
        root = view.findViewById(R.id.root);
    }

    public TextView getTextView() {
        return name;
    }
}

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param dataSet String[] containing the data to populate views to be used
     * by RecyclerView.
     */
    public ItemAdapter(ArrayList<Item> dataSet) {
        localDataSet = dataSet;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.adapter_item, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.name.setText(localDataSet.get(position).getName());
        viewHolder.desc.setText(localDataSet.get(position).getDesc());
        viewHolder.price.setText(localDataSet.get(position).getPrice());
        Picasso.get().load("http://192.168.114.29/uploads/"+localDataSet.get(position).getImgUrl()).into(viewHolder.imageView);
        viewHolder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoryActivity.getInstance(), DetailActivity.class);
                intent.putExtra("id",localDataSet.get(position).getID());
                CategoryActivity.getInstance().startActivity(intent);
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return localDataSet.size();
    }

    public void update(ArrayList<Item> dataSet){
        localDataSet = dataSet;
        this.notifyDataSetChanged();
    }

}