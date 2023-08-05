package com.example.ecommers.Activity;

import static com.example.ecommers.Activity.MainActivity.editor;
import static com.example.ecommers.Activity.MainActivity.preferences;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ecommers.Instance_Class;
import com.example.ecommers.Models.LoginData_Model;
import com.example.ecommers.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login_Activity extends AppCompatActivity {
  EditText LoginEmail,LoginPassword;
  Button btnLogin,btnRegister;
  String t1,t2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        LoginEmail=findViewById(R.id.LoginEmail);
        LoginPassword=findViewById(R.id.LoginPassword);
        btnLogin=findViewById(R.id.btnLogin);
        btnRegister=findViewById(R.id.btnRegister);

        LoginEmail.addTextChangedListener(textWatcher);
        LoginPassword.addTextChangedListener(textWatcher);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email_1=preferences.getString("email",null);
                String name_1=preferences.getString("name",null);

                Instance_Class.CallApi().userLogin(t1,t2).enqueue(new Callback<LoginData_Model>() {
                    @Override
                    public void onResponse(Call<LoginData_Model> call, Response<LoginData_Model> response) {
                        Log.d("AAA", "onResponse: "+response.body());
                       if (response.body().getConnection()==1)
                       {
                           if (response.body().getResult()==1)
                           {
                               Toast.makeText(Login_Activity.this, "User Logged in", Toast.LENGTH_SHORT).show();
                               editor.putBoolean("LoginStatus",true);
                               editor.putString("userid",response.body().getUserdata().getId());
                               editor.putString("name",response.body().getUserdata().getName());
                               editor.putString("email",response.body().getUserdata().getEmail());
                               editor.commit();
                               Intent intent=new Intent(Login_Activity.this, Home_Page_Activity.class);
                               startActivity(intent);

                           }
                           else if (response.body().getResult()==2) {
                               Toast.makeText(Login_Activity.this, "User already Logged in", Toast.LENGTH_SHORT).show();

                           } else if (response.body().getResult()==-0) {
                               Toast.makeText(Login_Activity.this, "Invalid Email or Password or Register first", Toast.LENGTH_LONG).show();
                               btnRegister.setOnClickListener(new View.OnClickListener() {
                                   @Override
                                   public void onClick(View v) {
                                       Intent intent=new Intent(Login_Activity.this, Register_Activity.class);

                                       intent.putExtra("email",LoginEmail.getText().toString());
                                       intent.putExtra("password",LoginPassword.getText().toString());
                                       startActivity(intent);
                                       finish();
                                   }
                               });

                           }
                       }
                       else
                       {
                           Toast.makeText(Login_Activity.this, "Something Went wrong", Toast.LENGTH_SHORT).show();
                       }


                    }

                    @Override
                    public void onFailure(Call<LoginData_Model> call, Throwable t) {

                    }
                });
            }
        });



    }
    TextWatcher textWatcher=new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
         t1=LoginEmail.getText().toString();
         t2=LoginPassword.getText().toString();
         btnLogin.setEnabled(!t1.isEmpty()&&!t2.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
}