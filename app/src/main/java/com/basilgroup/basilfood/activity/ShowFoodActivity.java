package com.basilgroup.basilfood.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.basilgroup.basilfood.R;
import com.basilgroup.basilfood.classes.GlideApp;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

public class ShowFoodActivity extends AppCompatActivity {

    TextView txtName, txtText, txtPrice;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_food);

        txtName = findViewById(R.id.name);
        txtText = findViewById(R.id.text);
        txtPrice = findViewById(R.id.price);

        imageView = findViewById(R.id.image);

        String name = getIntent().getStringExtra("name");
        String text = getIntent().getStringExtra("text");
        String image = getIntent().getStringExtra("image");
        String price = getIntent().getStringExtra("price");

        txtName.setText(name);
        txtText.setText(text);
        txtPrice.setText(price + " تومان");
        GlideApp
                .with(ShowFoodActivity.this)
                .load(image)
                .placeholder(getResources().getDrawable(R.drawable.basifood_logo))
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imageView);
    }
}
