package com.basilgroup.basilfood.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.basilgroup.basilfood.R;
import com.basilgroup.basilfood.activity.ShowFoodActivity;
import com.basilgroup.basilfood.classes.GlideApp;
import com.basilgroup.basilfood.model.Food;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

import java.util.ArrayList;

public class FourAdapter extends RecyclerView.Adapter<FourAdapter.ViewHolder> {


    private ArrayList<Food> os_version;
    private Context context;

    public FourAdapter(ArrayList<Food> arrayList) {
        os_version = arrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_four, null);
        ViewHolder viewHolder = new ViewHolder(view);
        context = parent.getContext();
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Food food = os_version.get(position);
        holder.name.setText(food.getName());
        GlideApp
                .with(context)
                .load(food.getImage())
                .placeholder(context.getResources().getDrawable(R.drawable.basifood_logo_placeholder))
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(holder.image);

        holder.btnFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ShowFoodActivity.class);
                intent.putExtra("name" , food.getName());
                intent.putExtra("image" , food.getImage());
                intent.putExtra("text" , food.getText());
                intent.putExtra("price" , String.valueOf(food.getPrice()));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return os_version.size();
    }


    public void setFilter(ArrayList<Food> arrayList) {
        os_version = new ArrayList<>();
        os_version.addAll(arrayList);
        notifyDataSetChanged();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        ImageView image;
        LinearLayout btnFood;


        public ViewHolder(View view) {
            super(view);
            name = itemView.findViewById(R.id.name);
            image = itemView.findViewById(R.id.image);
            btnFood = itemView.findViewById(R.id.btnFood);
        }
    }

}
