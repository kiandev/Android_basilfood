package com.basilgroup.basilfood.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.basilgroup.basilfood.R;
import com.basilgroup.basilfood.classes.MyFirebaseMessagingService;
import com.basilgroup.basilfood.utils.SharedContract;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

public class SplashActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.TAG;
    private static String tokenId;
    private Boolean check_general, check_news;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                check_general = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getBoolean(SharedContract.Check_General, false);
                check_news = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getBoolean(SharedContract.Check_News, false);
                if (check_general == false) {
                    FirebaseMessaging.getInstance().subscribeToTopic("general").addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putBoolean(SharedContract.Check_General, true).apply();
                        }
                    });
                }
                if (check_news == false) {
                    FirebaseMessaging.getInstance().subscribeToTopic("news").addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putBoolean(SharedContract.Check_News, true).apply();
                        }
                    });
                }
            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent;
                intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 1500);
        subscribeUserToParse();
    }

    private void subscribeUserToParse() {
        tokenId = FirebaseInstanceId.getInstance().getToken();
        if (TextUtils.isEmpty(tokenId)) {
            Intent intent = new Intent(this, MyFirebaseMessagingService.class);
            startService(intent);
            return;
        }
        PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString(SharedContract.Token_Id, tokenId).apply();
    }
}
