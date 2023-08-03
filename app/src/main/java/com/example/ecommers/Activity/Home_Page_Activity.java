package com.example.ecommers.Activity;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ecommers.R;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class Home_Page_Activity extends AppCompatActivity {
    DrawerLayout drawer_Layout;
    Toolbar toolbar;
    NavigationView navigation_View;
   ImageView header_img;
   TextView header_name,header_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        drawer_Layout=findViewById(R.id.drawer_layout);
        toolbar=findViewById(R.id.toolbar);
        navigation_View=findViewById(R.id.navigation_view);
        header_img=findViewById(R.id.header_img);
        View view=navigation_View.getHeaderView(0);

      header_email=view.findViewById(R.id.header_email);
      header_name=view.findViewById(R.id.header_name);

      header_name.setText(MainActivity.preferences.getString("name",null));
      header_email.setText(MainActivity.preferences.getString("email",null));


        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,drawer_Layout,toolbar,R.string.Open_Drawer,R.string.Close_Drawer);
        drawer_Layout.addDrawerListener(toggle);
        toggle.syncState();

    }
}