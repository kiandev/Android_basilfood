package com.basilgroup.basilfood.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.basilgroup.basilfood.R;
import com.basilgroup.basilfood.activity.FoodActivity;
import com.basilgroup.basilfood.model.Category;
import com.basilgroup.basilfood.model.Notification;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {


    private ArrayList<Notification> os_version;
    private Context context;

    public NotificationAdapter(ArrayList<Notification> arrayList) {
        os_version = arrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification, null);
        ViewHolder viewHolder = new ViewHolder(view);
        context = parent.getContext();
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Notification notification = os_version.get(position);
        holder.title.setText(notification.getTitle());
        holder.text.setText(notification.getText());
        holder.date.setText(notification.getDate());


    }

    @Override
    public int getItemCount() {
        return os_version.size();
    }


    public void setFilter(ArrayList<Notification> arrayList) {
        os_version = new ArrayList<>();
        os_version.addAll(arrayList);
        notifyDataSetChanged();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView title, text, date;


        public ViewHolder(View view) {
            super(view);
            title = itemView.findViewById(R.id.title);
            text = itemView.findViewById(R.id.text);
            date = itemView.findViewById(R.id.date);
        }
    }

}
