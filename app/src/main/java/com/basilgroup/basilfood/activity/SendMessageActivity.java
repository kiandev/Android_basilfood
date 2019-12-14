package com.basilgroup.basilfood.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.basilgroup.basilfood.R;
import com.basilgroup.basilfood.utils.HttpUrl;
import com.basilgroup.basilfood.utils.NetTest;
import com.basilgroup.basilfood.utils.SharedContract;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class SendMessageActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.TAG;
    private String please_select;
    Spinner spinner;
    Button btnOk;
    EditText txt_text;
    ImageView btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_message);

        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        please_select = "انتخاب کنید";

        spinner = findViewById(R.id.spinner);
        String[] items = new String[]{please_select, "سوال", "انتقاد", "پیشنهاد", "مشکل در اپلیکیشن"};
        ArrayAdapter<String> adapter_spinner = new ArrayAdapter<>(this, R.layout.row_spn, items);
        adapter_spinner.setDropDownViewResource(R.layout.row_spn_dropdown);
        spinner.setAdapter(adapter_spinner);

        txt_text = findViewById(R.id.text);
        btnOk = findViewById(R.id.btnOk);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (spinner.getSelectedItemId() == 0) {
                    Toast.makeText(SendMessageActivity.this, "لطفا موضوع پیام را انتخاب نمایید", Toast.LENGTH_SHORT).show();
                } else if (txt_text.getText().toString().trim().isEmpty()) {
                    Toast.makeText(SendMessageActivity.this, "لطفا متن پیام را وارد نمایید", Toast.LENGTH_SHORT).show();
                } else if (txt_text.length() < 10){
                    Toast.makeText(SendMessageActivity.this, "متن پیام کوتاه می باشد", Toast.LENGTH_SHORT).show();
                } else {
                    if (!NetTest.yes(getApplicationContext())){
                        Toast.makeText(SendMessageActivity.this, "لطفا ابتدا دستگاه خود را به اینترنت متصل نمایید", Toast.LENGTH_SHORT).show();
                    } else{
                        String URL = HttpUrl.url + "message";
                        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                                URL,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        Log.d(TAG, "onResponse: " + response);
                                        try {
                                            if (response.equals("1")){
                                                Toast.makeText(SendMessageActivity.this, "پیام با موفقیت ارسال گردید", Toast.LENGTH_SHORT).show();
                                                finish();
                                            }
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Toast.makeText(SendMessageActivity.this, "متاسفانه خطایی نامشخصی رخ داده است", Toast.LENGTH_SHORT).show();
                                    }
                                }

                        ) {
                            @Override
                            protected Map<String, String> getParams() {
                                Map<String, String> params = new HashMap<String, String>();
                                String tokenId = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString(SharedContract.Token_Id, "");
                                params.put("title", spinner.getSelectedItem().toString());
                                params.put("text_user", txt_text.getText().toString());
                                params.put("date_user", "1398/09/20");
                                params.put("code", "123456789");
                                params.put("token", tokenId);
                                params.put("text_admin", "0");
                                params.put("date_admin", "0");
                                return params;
                            }
                        };
                        RequestQueue requestQueue = Volley.newRequestQueue(SendMessageActivity.this);
                        requestQueue.add(stringRequest);
                    }
                }
            }
        });


    }

}
