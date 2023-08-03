package com.example.ecommers.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.example.ecommers.R;

public class MainActivity extends AppCompatActivity {
    public static SharedPreferences preferences;
    public  static SharedPreferences.Editor editor;
    Boolean Islogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

      preferences=getSharedPreferences("asmita",0);
      editor=preferences.edit();

    Islogin=preferences.getBoolean("LoginStatus",false);
    new Handler().postDelayed(new Runnable() {
        @Override
        public void run() {
            if (Islogin)
            {
              startActivity(new Intent(MainActivity.this, Home_Page_Activity.class));
              finish();
            }
           else
            {
                startActivity(new Intent(MainActivity.this, Login_Activity.class));
                finish();
            }

        }
    },3000);

    }
}