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
import com.basilgroup.basilfood.activity.FourActivity;
import com.basilgroup.basilfood.model.TypeFour;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class TypeFourMainAdapter extends RecyclerView.Adapter<TypeFourMainAdapter.ViewHolder> {


    private ArrayList<TypeFour> os_version;
    private Context context;

    public TypeFourMainAdapter(ArrayList<TypeFour> arrayList) {
        os_version = arrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_typefour_main, null);
        ViewHolder viewHolder = new ViewHolder(view);
        context = parent.getContext();
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final TypeFour typeFour = os_version.get(position);
        holder.name.setText(typeFour.getName());
        Glide.with(context).load(typeFour.getImage()).into(holder.image);

        holder.btnItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, FourActivity.class);
                intent.putExtra("id" , String.valueOf(typeFour.getId()));
                intent.putExtra("title" , typeFour.getName());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return os_version.size();
    }


    public void setFilter(ArrayList<TypeFour> arrayList) {
        os_version = new ArrayList<>();
        os_version.addAll(arrayList);
        notifyDataSetChanged();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        ImageView image;
        LinearLayout btnItem;


        public ViewHolder(View view) {
            super(view);
            name = itemView.findViewById(R.id.name);
            image = itemView.findViewById(R.id.image);
            btnItem = itemView.findViewById(R.id.btnItem);
        }
    }

}
