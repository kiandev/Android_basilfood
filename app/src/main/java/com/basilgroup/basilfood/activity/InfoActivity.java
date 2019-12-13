package com.basilgroup.basilfood.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.basilgroup.basilfood.classes.GlideApp;
import com.basilgroup.basilfood.utils.NetTest;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.basilgroup.basilfood.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class InfoActivity extends AppCompatActivity {

    TextView title, text;
    ImageView imageView, btnBack;
    private String phone, site, email, instagram;
    LinearLayout btnWebsite, btnEmail, btnPhone, btnInstagram;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        title = findViewById(R.id.title);
        text = findViewById(R.id.text);
        imageView = findViewById(R.id.image);
        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnWebsite = findViewById(R.id.btnWebsite);
        btnWebsite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!site.equals(null)){
                    try {
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(site));
                        startActivity(i);
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(InfoActivity.this, "متاسفانه خطایی نامشخصی رخ داده است", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnEmail = findViewById(R.id.btnEmail);
        btnEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!email.equals(null)){
                    try {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("mailto:" + email));
                        intent.putExtra(Intent.EXTRA_SUBJECT, "عنوان پیام");
                        intent.putExtra(Intent.EXTRA_TEXT, "");
                        startActivity(intent);
                    } catch (Exception e) {
                        Toast.makeText(InfoActivity.this, "متاسفانه اپلیکیشن مناسب جهت ارسال ایمیل یافت نشد", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(InfoActivity.this, "متاسفانه خطایی نامشخصی رخ داده است", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnPhone = findViewById(R.id.btnPhone);
        btnPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!phone.equals(null)){
                    try {
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        intent.setData(Uri.parse("tel:" + phone));
                        startActivity(intent);
                    } catch (Exception e) {
                        Toast.makeText(InfoActivity.this, "متاسفانه خطایی نامشخصی رخ داده است", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(InfoActivity.this, "متاسفانه خطایی نامشخصی رخ داده است", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnInstagram = findViewById(R.id.btnInstagram);
        btnInstagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!instagram.equals(null)){
                    try {
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(instagram));
                        intent.setPackage("com.instagram.android");
                        startActivity(intent);
                    } catch (android.content.ActivityNotFoundException anfe) {
                        startActivity(new Intent(Intent.ACTION_VIEW,
                                Uri.parse(instagram)));
                    }
                } else {
                    Toast.makeText(InfoActivity.this, "متاسفانه خطایی نامشخصی رخ داده است", Toast.LENGTH_SHORT).show();
                }

            }
        });

        if (!NetTest.yes(getApplicationContext())){
            Toast.makeText(this, "لطفا ابتدا دستگاه خود را به اینترنت متصل نمایید", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            getdata();
        }


    }

    public void getdata() {
        String URL = "http://192.168.23.2:8000/api/info";
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonarray = new JSONArray(response);
                            for (int i = 0; i < jsonarray.length(); i++) {
                                JSONObject jsonobject = jsonarray.getJSONObject(i);
                                String txt_title = jsonobject.getString("title");
                                String txt_text = jsonobject.getString("text");
                                String txt_image = jsonobject.getString("image");
                                site = jsonobject.getString("site");
                                email = jsonobject.getString("email");
                                phone = jsonobject.getString("phone");
                                instagram = jsonobject.getString("instagram");
                                title.setText(txt_title);
                                text.setText(txt_text);

                                GlideApp
                                        .with(InfoActivity.this)
                                        .load(txt_image)
                                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                                        .skipMemoryCache(true)
                                        .transition(DrawableTransitionOptions.withCrossFade())
                                        .into(imageView);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(InfoActivity.this, "متاسفانه خطایی نامشخصی رخ داده است", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(InfoActivity.this);
        requestQueue.add(stringRequest);
    }

}
