package com.example.ecommers.Activity;

import static com.example.ecommers.Activity.MainActivity.editor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ecommers.Fragment.AddProduct_Fragment;
import com.example.ecommers.Fragment.ShowProduct_Fragment;
import com.example.ecommers.Fragment.ViewProduct_Fragment;
import com.example.ecommers.R;
import com.google.android.material.navigation.NavigationView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Home_Page_Activity extends AppCompatActivity {
    DrawerLayout drawer_Layout;
    Toolbar toolbar;
    NavigationView navigation_View;
   ImageView header_img;
   TextView header_name,header_email;
    ArrayList<String> listImages=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        drawer_Layout=findViewById(R.id.drawer_layout);
        toolbar=findViewById(R.id.toolbar);
        navigation_View=findViewById(R.id.navigation_view);
        header_img=findViewById(R.id.header_img);

        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,drawer_Layout,toolbar,R.string.Open_Drawer,R.string.Close_Drawer);
        drawer_Layout.addDrawerListener(toggle);
        toggle.syncState();

        View view=navigation_View.getHeaderView(0);

      header_email=view.findViewById(R.id.header_email);
      header_name=view.findViewById(R.id.header_name);

      header_name.setText(MainActivity.preferences.getString("name",null));
      header_email.setText(MainActivity.preferences.getString("email",null));

        String name=header_name.getText().toString();
        String[] images = new String[0];
        try {
            images = getAssets().list("");
            listImages = new ArrayList<String>(Arrays.asList(images));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("List of images="+listImages);




        addFragment(new ViewProduct_Fragment());
        navigation_View.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id=item.getItemId();
                if (id==R.id.addproduct)
                {
                    replaceFragment(new AddProduct_Fragment());
                    drawer_Layout.closeDrawer(Gravity.LEFT);
                } else if (id==R.id.viewproduct)
                {
                    replaceFragment(new ViewProduct_Fragment());
                    drawer_Layout.closeDrawer(Gravity.LEFT);
                } else if (id==R.id.showallproduct) {
                     replaceFragment(new ShowProduct_Fragment());
                     drawer_Layout.closeDrawer(Gravity.LEFT);
                } else if (id==R.id.logout) {
                    editor.putBoolean("LoginStatus",false);
                    editor.commit();
                    Intent intent=new Intent(Home_Page_Activity.this,Login_Activity.class);
                    startActivity(intent);

                }
                return true;
            }
        });

    }
    private void addFragment(Fragment fragment)
    {
        FragmentManager fm=getSupportFragmentManager();
        FragmentTransaction transaction= fm.beginTransaction();
        transaction.add(R.id.content_view, fragment);
        transaction.commit();
    }
    private void replaceFragment(Fragment fragment) {
        FragmentManager fm=getSupportFragmentManager();
        FragmentTransaction transaction= fm.beginTransaction();
        transaction.replace(R.id.content_view,fragment);
        transaction.commit();
    }
}