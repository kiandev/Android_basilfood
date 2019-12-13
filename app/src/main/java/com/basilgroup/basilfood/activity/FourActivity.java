package com.basilgroup.basilfood.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.basilgroup.basilfood.R;
import com.basilgroup.basilfood.adapter.FourAdapter;
import com.basilgroup.basilfood.model.Food;
import com.basilgroup.basilfood.utils.NetTest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FourActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.TAG;
    RecyclerView recyclerView;
    ArrayList<Food> os_version = new ArrayList<>();
    FourAdapter mAdapter;
    LinearLayoutManager layoutManager;
    ImageView btnBack;
    TextView txt_title;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_four);

        txt_title = findViewById(R.id.title);
        recyclerView = findViewById(R.id.recycler_view);
        btnBack = findViewById(R.id.btnBack);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager( this,2));
        layoutManager = ((LinearLayoutManager)recyclerView.getLayoutManager());

        mAdapter = new FourAdapter(os_version);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        String title = getIntent().getStringExtra("title");
        id = getIntent().getStringExtra("id");
        txt_title.setText(title);

        if (!NetTest.yes(getApplicationContext())){
            Toast.makeText(this, "لطفا ابتدا دستگاه خود را به اینترنت متصل نمایید", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            loadFour();
        }

    }

    private void loadFour() {
        String URL = "http://192.168.23.2:8000/api/four/" + id;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONArray(response);
                            Log.d(TAG, "onResponse: " + response);
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject food = array.getJSONObject(i);
                                os_version.add(new Food(
                                        food.getInt("id"),
                                        food.getString("name"),
                                        food.getString("text"),
                                        food.getString("image"),
                                        food.getInt("price"),
                                        food.getString("type_one"),
                                        food.getString("type_two"),
                                        food.getString("type_three"),
                                        food.getString("type_four"),
                                        food.getString("created_at"),
                                        food.getString("updated_at")
                                ));
                            }
                            recyclerView.setAdapter(mAdapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(FourActivity.this, "متاسفانه خطایی نامشخصی رخ داده است ، لطفا بعدا مجددا تلاش نمایید", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this.getApplicationContext());
        requestQueue.add(stringRequest);
    }
}
