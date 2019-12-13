package com.basilgroup.basilfood.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.basilgroup.basilfood.R;
import com.basilgroup.basilfood.utils.NetTest;
import com.basilgroup.basilfood.utils.SharedContract;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.messaging.FirebaseMessaging;

public class SettingsActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.TAG;
    SwitchCompat switchCompat_general, switchCompat_news;
    private Boolean check_general, check_news;
    TextView txt_general, txt_news;
    ImageView btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        switchCompat_general = findViewById(R.id.switch_general);
        switchCompat_news = findViewById(R.id.switch_news);
        txt_general = findViewById(R.id.txt_general);
        txt_news = findViewById(R.id.txt_news);

        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        check_general = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getBoolean(SharedContract.Check_General,false);
        check_news = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getBoolean(SharedContract.Check_News,false);

        if (check_general == true){
            switchCompat_general.setChecked(true);
            txt_general.setText("فعال است");
        }

        if (check_news == true){
            switchCompat_news.setChecked(true);
            txt_news.setText("فعال است");
        }

        switchCompat_general.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (!NetTest.yes(getApplicationContext())){
                        Toast.makeText(SettingsActivity.this, "لطفا ابتدا دستگاه خود را به اینترنت متصل نمایید", Toast.LENGTH_SHORT).show();
                    } else{
                        PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putBoolean(SharedContract.Check_General,switchCompat_general.isChecked()).apply();
                        txt_general.setText("فعال است");
                        FirebaseMessaging.getInstance().subscribeToTopic("general").addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(TAG, "onSuccess: " + "subscribe To Topic general finish");
                            }
                        });
                    }

                } else {
                    if (!NetTest.yes(getApplicationContext())){
                        Toast.makeText(SettingsActivity.this, "لطفا ابتدا دستگاه خود را به اینترنت متصل نمایید", Toast.LENGTH_SHORT).show();
                    } else{
                        PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putBoolean(SharedContract.Check_General, switchCompat_general.isChecked()).apply();
                        txt_general.setText("غیرفعال است");
                        FirebaseMessaging.getInstance().unsubscribeFromTopic("general").addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(TAG, "onSuccess: " + "unsubscribe To Topic general finish");
                            }
                        });
                    }
                }
            }
        });

        switchCompat_news.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (!NetTest.yes(getApplicationContext())){
                        Toast.makeText(SettingsActivity.this, "لطفا ابتدا دستگاه خود را به اینترنت متصل نمایید", Toast.LENGTH_SHORT).show();
                    } else{
                        PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putBoolean(SharedContract.Check_News,switchCompat_news.isChecked()).apply();
                        txt_news.setText("فعال است");
                        FirebaseMessaging.getInstance().subscribeToTopic("news").addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(TAG, "onSuccess: " + "subscribe To Topic news finish");
                            }
                        });
                    }

                } else {
                    if (!NetTest.yes(getApplicationContext())){
                        Toast.makeText(SettingsActivity.this, "لطفا ابتدا دستگاه خود را به اینترنت متصل نمایید", Toast.LENGTH_SHORT).show();
                    } else{
                        PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putBoolean(SharedContract.Check_News, switchCompat_news.isChecked()).apply();
                        txt_news.setText("غیرفعال است");
                        FirebaseMessaging.getInstance().unsubscribeFromTopic("news").addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(TAG, "onSuccess: " + "unsubscribe To news finish");
                            }
                        });
                    }
                }
            }
        });
    }
}
