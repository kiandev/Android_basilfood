package com.basilgroup.basilfood.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.basilgroup.basilfood.adapter.NewsAdapter;
import com.basilgroup.basilfood.model.News;
import com.basilgroup.basilfood.model.TypeFour;
import com.basilgroup.basilfood.utils.HttpUrl;
import com.basilgroup.basilfood.utils.NetTest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class NewsActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.TAG;
    RecyclerView recyclerView;
    ArrayList<News> os_version = new ArrayList<>();
    NewsAdapter mAdapter;
    LinearLayoutManager layoutManager;
    ImageView btnBack;
    LinearLayout noData, progreesBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        progreesBar = findViewById(R.id.progreesBar);
        noData = findViewById(R.id.noData);
        recyclerView = findViewById(R.id.recycler_view);
        btnBack = findViewById(R.id.btnBack);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager( this));
        layoutManager = ((LinearLayoutManager)recyclerView.getLayoutManager());

        mAdapter = new NewsAdapter(os_version);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if (!NetTest.yes(getApplicationContext())){
            Toast.makeText(this, "لطفا ابتدا دستگاه خود را به اینترنت متصل نمایید", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            loadNews();
        }

    }

    private void loadNews() {
        String URL = HttpUrl.url + "news";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progreesBar.setVisibility(View.GONE);

                        try {
                            JSONArray array = new JSONArray(response);
                            Log.d(TAG, "onResponse: " + response);
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject news = array.getJSONObject(i);
                                os_version.add(new News(
                                        news.getInt("id"),
                                        news.getString("title"),
                                        news.getString("text"),
                                        news.getString("image"),
                                        news.getString("date"),
                                        news.getString("created_at"),
                                        news.getString("updated_at")
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
                        Toast.makeText(NewsActivity.this, "متاسفانه خطایی نامشخصی رخ داده است ، لطفا بعدا مجددا تلاش نمایید", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this.getApplicationContext());
        requestQueue.add(stringRequest);
    }
}
