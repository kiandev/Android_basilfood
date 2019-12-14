package com.basilgroup.basilfood.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.basilgroup.basilfood.R;
import com.basilgroup.basilfood.adapter.KebabAdapter;
import com.basilgroup.basilfood.adapter.PizzaAdapter;
import com.basilgroup.basilfood.adapter.SaladAdapter;
import com.basilgroup.basilfood.adapter.TypeFourMainAdapter;
import com.basilgroup.basilfood.model.Food;
import com.basilgroup.basilfood.model.TypeFour;
import com.basilgroup.basilfood.utils.HttpUrl;
import com.basilgroup.basilfood.utils.NetTest;
import com.google.firebase.analytics.FirebaseAnalytics;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "basilfood_tag";

    RecyclerView recyclerView_typefour, recyclerView_pizaa, recyclerView_kebab, recyclerView_salad;
    ArrayList<TypeFour> os_version_typefour = new ArrayList<>();
    ArrayList<Food> os_version_pizza = new ArrayList<>();
    ArrayList<Food> os_version_kebab = new ArrayList<>();
    ArrayList<Food> os_version_salad = new ArrayList<>();
    TypeFourMainAdapter mAdapter_typefour;
    PizzaAdapter mAdapter_pizza;
    KebabAdapter mAdapter_kebab;
    SaladAdapter mAdapter_salad;
    LinearLayoutManager layoutManager_typefour, layoutManager_pizza, layoutManager_kebab, layoutManager_salad;
    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;
    private final GestureDetector detector = new GestureDetector(new SwipeGestureDetector());
    private ViewFlipper viewFlipper;
    private Boolean exit = false;
    FirebaseAnalytics firebaseAnalytics;
    ImageView btnMenu, btnNotification;
    LinearLayout btnSettings, btnSend, btnTeam, btnMessage;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final DrawerLayout drawer = findViewById(R.id.drawer_layout);

        firebaseAnalytics = FirebaseAnalytics.getInstance(this);

        btnSettings = findViewById(R.id.btnSettings);
        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SettingsActivity.class));
            }
        });

        btnMessage = findViewById(R.id.btnMessage);
        btnMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MessageActivity.class));
            }
        });

        btnTeam = findViewById(R.id.btnTeam);
        btnTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TeamActivity.class));
            }
        });

        btnSend = findViewById(R.id.btnSend);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent i = new Intent(Intent.ACTION_SEND);
                    i.setType("text/plain");
                    i.putExtra(Intent.EXTRA_SUBJECT, "basilfood");
                    String sAux = "\n بازیل فود اهواز \n\n";
                    sAux = sAux + "https://basilfood.ir \n";
                    i.putExtra(Intent.EXTRA_TEXT, sAux);
                    startActivity(Intent.createChooser(i, "لطفا یکی از گزینه های زیر را جهت ارسال اپلیکیشن انتخاب نمایید"));
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, "متاسفانه مشکلی پیش آمده ، لطفا بعدا تلاش نمایید", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnMenu = findViewById(R.id.btnMenu);
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(Gravity.RIGHT);
            }
        });

        btnNotification = findViewById(R.id.btnNotification);
        btnNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, NotificationActivity.class));
            }
        });

        viewFlipper = findViewById(R.id.view_flipper);
        viewFlipper.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(final View view, final MotionEvent event) {
                detector.onTouchEvent(event);
                return true;
            }
        });

        recyclerView_typefour = findViewById(R.id.recycler_view_typefour);
        recyclerView_typefour.setHasFixedSize(true);
        recyclerView_typefour.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));
        layoutManager_typefour = ((LinearLayoutManager) recyclerView_typefour.getLayoutManager());

        recyclerView_pizaa = findViewById(R.id.recycler_view_pizza);
        recyclerView_pizaa.setHasFixedSize(true);
        recyclerView_pizaa.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));
        layoutManager_pizza = ((LinearLayoutManager) recyclerView_pizaa.getLayoutManager());

        recyclerView_kebab = findViewById(R.id.recycler_view_kebab);
        recyclerView_kebab.setHasFixedSize(true);
        recyclerView_kebab.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));
        layoutManager_kebab = ((LinearLayoutManager) recyclerView_kebab.getLayoutManager());

        recyclerView_salad = findViewById(R.id.recycler_view_salad);
        recyclerView_salad.setHasFixedSize(true);
        recyclerView_salad.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));
        layoutManager_salad = ((LinearLayoutManager) recyclerView_salad.getLayoutManager());

        mAdapter_typefour = new TypeFourMainAdapter(os_version_typefour);
        mAdapter_pizza = new PizzaAdapter(os_version_pizza);
        mAdapter_kebab = new KebabAdapter(os_version_kebab);
        mAdapter_salad = new SaladAdapter(os_version_salad);

        if (!NetTest.yes(getApplicationContext())){
            Toast.makeText(this, "لطفا ابتدا دستگاه خود را به اینترنت متصل نمایید", Toast.LENGTH_SHORT).show();
        } else {
            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {
                    loadTypeFour();
                }
            });

            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {
                    loadPizza();
                }
            });

            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {
                    loadKebab();
                }
            });

            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {
                    loadSalad();
                }
            });
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    class SwipeGestureDetector extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            try {
                if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    viewFlipper.stopFlipping();
                    viewFlipper.setInAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.fade_in));
                    viewFlipper.setOutAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.fade_out));
                    viewFlipper.showNext();
                    viewFlipper.startFlipping();
                    return true;
                } else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    viewFlipper.stopFlipping();
                    viewFlipper.setInAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.fade_in));
                    viewFlipper.setOutAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.fade_out));
                    viewFlipper.showPrevious();
                    viewFlipper.startFlipping();
                    return true;
                }

            } catch (Exception e) {
                return true;
            }

            return false;
        }
    }

    public void btnNews(View view) {
        startActivity(new Intent(MainActivity.this, NewsActivity.class));
    }

    public void btnInfo(View view) {
        startActivity(new Intent(MainActivity.this, InfoActivity.class));
    }

    public void btnCategory(View view) {
        startActivity(new Intent(MainActivity.this, CategoryActivity.class));
    }


    private void loadTypeFour() {
        String URL = HttpUrl.url + "typefour";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONArray(response);
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject typefour = array.getJSONObject(i);
                                os_version_typefour.add(new TypeFour(
                                        typefour.getInt("id"),
                                        typefour.getString("name"),
                                        typefour.getString("created_at"),
                                        typefour.getString("updated_at"),
                                        typefour.getString("image")
                                ));
                            }
                            recyclerView_typefour.setAdapter(mAdapter_typefour);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "متاسفانه خطایی نامشخصی رخ داده است ، لطفا بعدا مجددا تلاش نمایید", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this.getApplicationContext());
        requestQueue.add(stringRequest);

    }

    private void loadPizza() {
        String URL = HttpUrl.url + "pizza";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONArray(response);
                            Log.d(TAG, "onResponse: " + response);
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject food = array.getJSONObject(i);
                                os_version_pizza.add(new Food(
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
                            recyclerView_pizaa.setAdapter(mAdapter_pizza);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "متاسفانه خطایی نامشخصی رخ داده است ، لطفا بعدا مجددا تلاش نمایید", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this.getApplicationContext());
        requestQueue.add(stringRequest);

    }

    private void loadKebab() {
        String URL = HttpUrl.url + "kebab";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONArray(response);
                            Log.d(TAG, "onResponse: " + response);
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject food = array.getJSONObject(i);
                                os_version_kebab.add(new Food(
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
                            recyclerView_kebab.setAdapter(mAdapter_kebab);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "متاسفانه خطایی نامشخصی رخ داده است ، لطفا بعدا مجددا تلاش نمایید", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this.getApplicationContext());
        requestQueue.add(stringRequest);

    }

    private void loadSalad() {
        String URL = HttpUrl.url + "salad";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONArray(response);
                            Log.d(TAG, "onResponse: " + response);
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject food = array.getJSONObject(i);
                                os_version_salad.add(new Food(
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
                            recyclerView_salad.setAdapter(mAdapter_salad);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "متاسفانه خطایی نامشخصی رخ داده است ، لطفا بعدا مجددا تلاش نمایید", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this.getApplicationContext());
        requestQueue.add(stringRequest);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(Gravity.RIGHT)) {
            drawer.closeDrawer(Gravity.RIGHT);
        } else {
            if (exit) {
                super.onBackPressed(); // finish activity
            } else {
                Toast.makeText(this, "برای خروج کلید بازگشت را مجدد فشار دهید !",
                        Toast.LENGTH_SHORT).show();
                exit = true;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        exit = false;
                    }
                }, 3 * 1000);

            }
        }
    }
}
