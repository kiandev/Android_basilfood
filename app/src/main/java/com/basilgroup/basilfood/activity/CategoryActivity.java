package com.basilgroup.basilfood.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.basilgroup.basilfood.R;
import com.basilgroup.basilfood.utils.NetTest;
import com.basilgroup.basilfood.utils.SharedContract;

public class CategoryActivity extends AppCompatActivity {

    ImageView btnBack;
    RelativeLayout btnOne, btnTwo, btnThree, btnFour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnOne = findViewById(R.id.btnOne);
        btnOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!NetTest.yes(getApplicationContext())) {
                    Toast.makeText(CategoryActivity.this, "لطفا ابتدا دستگاه خود را به اینترنت متصل نمایید", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(CategoryActivity.this, ShowCategoryActivity.class);
                    PreferenceManager.getDefaultSharedPreferences(CategoryActivity.this).edit().putString(SharedContract.Check_Type_For_Database,"type_one").apply();
                    intent.putExtra("title","بر اساس وعده غذایی");
                    intent.putExtra("type","1");
                    startActivity(intent);
                }

            }
        });

        btnTwo = findViewById(R.id.btnTwo);
        btnTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!NetTest.yes(getApplicationContext())) {
                    Toast.makeText(CategoryActivity.this, "لطفا ابتدا دستگاه خود را به اینترنت متصل نمایید", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(CategoryActivity.this, ShowCategoryActivity.class);
                    PreferenceManager.getDefaultSharedPreferences(CategoryActivity.this).edit().putString(SharedContract.Check_Type_For_Database,"type_two").apply();
                    intent.putExtra("title","بر اساس مواد غذایی");
                    intent.putExtra("type","2");
                    startActivity(intent);
                }

            }
        });

        btnThree = findViewById(R.id.btnThree);
        btnThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!NetTest.yes(getApplicationContext())) {
                    Toast.makeText(CategoryActivity.this, "لطفا ابتدا دستگاه خود را به اینترنت متصل نمایید", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(CategoryActivity.this, ShowCategoryActivity.class);
                    PreferenceManager.getDefaultSharedPreferences(CategoryActivity.this).edit().putString(SharedContract.Check_Type_For_Database,"type_three").apply();
                    intent.putExtra("title","بر اساس جغرافیا");
                    intent.putExtra("type","3");
                    startActivity(intent);
                }

            }
        });

        btnFour = findViewById(R.id.btnFour);
        btnFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!NetTest.yes(getApplicationContext())) {
                    Toast.makeText(CategoryActivity.this, "لطفا ابتدا دستگاه خود را به اینترنت متصل نمایید", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(CategoryActivity.this, ShowCategoryActivity.class);
                    PreferenceManager.getDefaultSharedPreferences(CategoryActivity.this).edit().putString(SharedContract.Check_Type_For_Database,"type_four").apply();
                    intent.putExtra("title","دسته بندی کلی");
                    intent.putExtra("type","4");
                    startActivity(intent);
                }

            }
        });

    }


}
