package com.basilgroup.basilfood.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.basilgroup.basilfood.R;
import com.basilgroup.basilfood.model.Message;
import com.basilgroup.basilfood.model.Notification;

import java.util.ArrayList;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {


    private ArrayList<Message> os_version;
    private Context context;

    public MessageAdapter(ArrayList<Message> arrayList) {
        os_version = arrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message, null);
        ViewHolder viewHolder = new ViewHolder(view);
        context = parent.getContext();
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Message message = os_version.get(position);
        holder.title.setText(message.getTitle());
        holder.text_user.setText(message.getText_user());
        holder.date_user.setText("تاریخ ارسال پیام : " + message.getDate_user());
        if (!message.getText_admin().equals("0")){
            holder.line_answer.setVisibility(View.VISIBLE);
            holder.text_admin.setText(message.getText_admin());
            holder.date_admin.setText("تاریخ دریافت پیام : " + message.getDate_admin());
        }


    }

    @Override
    public int getItemCount() {
        return os_version.size();
    }

    public void wipeData() {
        if (os_version != null) {
            os_version.clear();
        }
        notifyDataSetChanged();
    }


    public void setFilter(ArrayList<Message> arrayList) {
        os_version = new ArrayList<>();
        os_version.addAll(arrayList);
        notifyDataSetChanged();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView title, text_user, date_user, text_admin, date_admin;
        RelativeLayout line_answer;

        public ViewHolder(View view) {
            super(view);
            title = itemView.findViewById(R.id.title);
            text_user = itemView.findViewById(R.id.text_user);
            date_user = itemView.findViewById(R.id.date_user);
            text_admin = itemView.findViewById(R.id.text_admin);
            date_admin = itemView.findViewById(R.id.date_admin);
            line_answer = itemView.findViewById(R.id.line_answer);
        }
    }

}
