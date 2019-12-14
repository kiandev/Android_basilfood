package com.basilgroup.basilfood.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.basilgroup.basilfood.R;
import com.basilgroup.basilfood.adapter.MessageAdapter;
import com.basilgroup.basilfood.adapter.NewsAdapter;
import com.basilgroup.basilfood.model.Message;
import com.basilgroup.basilfood.model.News;
import com.basilgroup.basilfood.utils.HttpUrl;
import com.basilgroup.basilfood.utils.NetTest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MessageActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.TAG;
    RecyclerView recyclerView;
    ArrayList<Message> os_version = new ArrayList<>();
    MessageAdapter mAdapter;
    LinearLayoutManager layoutManager;
    ImageView btnBack, btnPlus;
    LinearLayout noData, progreesBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        progreesBar = findViewById(R.id.progreesBar);
        noData = findViewById(R.id.noData);
        recyclerView = findViewById(R.id.recycler_view);
        btnBack = findViewById(R.id.btnBack);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager( this));
        layoutManager = ((LinearLayoutManager)recyclerView.getLayoutManager());

        mAdapter = new MessageAdapter(os_version);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnPlus = findViewById(R.id.btnPlus);
        btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MessageActivity.this, SendMessageActivity.class));
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        mAdapter.wipeData();
        if (!NetTest.yes(getApplicationContext())){
            Toast.makeText(this, "لطفا ابتدا دستگاه خود را به اینترنت متصل نمایید", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            loadMessage();
        }
    }

    private void loadMessage() {
        String URL = HttpUrl.url + "message";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progreesBar.setVisibility(View.GONE);

                        try {
                            JSONArray array = new JSONArray(response);
                            Log.d(TAG, "onResponse: " + response);
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject message = array.getJSONObject(i);
                                os_version.add(new Message(
                                        message.getInt("id"),
                                        message.getString("title"),
                                        message.getString("text_user"),
                                        message.getString("date_user"),
                                        message.getString("code"),
                                        message.getString("token"),
                                        message.getString("text_admin"),
                                        message.getString("date_admin"),
                                        message.getString("created_at"),
                                        message.getString("updated_at")
                                ));
                            }
                            recyclerView.setAdapter(mAdapter);
                            if (mAdapter.getItemCount() == 0) {
                                recyclerView.setVisibility(View.GONE);
                                noData.setVisibility(View.VISIBLE);
                            } else {
                                recyclerView.setVisibility(View.VISIBLE);
                                noData.setVisibility(View.GONE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MessageActivity.this, "متاسفانه خطایی نامشخصی رخ داده است ، لطفا بعدا مجددا تلاش نمایید", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this.getApplicationContext());
        requestQueue.add(stringRequest);
    }
}
