package com.example.ecommers.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ecommers.Instance_Class;
import com.example.ecommers.Models.RegistrationData_Model;
import com.example.ecommers.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Register_Activity extends AppCompatActivity {
    EditText name,regEmail,regPassword;
    Button btnRegister;
    String t1,t2,t3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        name=findViewById(R.id.name);
        regEmail=findViewById(R.id.regEmail);
        regPassword=findViewById(R.id.regPassword);
        btnRegister=findViewById(R.id.btnRegister);


      t2=getIntent().getStringExtra("email");
      t3=getIntent().getStringExtra("password");

      regEmail.setText(t2);
      regPassword.setText(t3);

      System.out.println("email="+regEmail.getText().toString() +"\tpass="+regPassword.getText().toString());

     btnRegister.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             t1=name.getText().toString();
             Instance_Class.CallApi().userRegister(t1,t2,t3).enqueue(new Callback<RegistrationData_Model>() {
                 @Override
                 public void onResponse(Call<RegistrationData_Model> call, Response<RegistrationData_Model> response) {
                     Log.d("AAA", "onResponse: " + response.body());
                     if (response.body().getConnection() == 1) {
                         if (response.body().getResult() == 1) {
                             Toast.makeText(Register_Activity.this, "User Registered", Toast.LENGTH_SHORT).show();
                         } else if (response.body().getResult() == 2) {
                             Toast.makeText(Register_Activity.this, "User already exists", Toast.LENGTH_SHORT).show();
                         }
                     } else {
                         Toast.makeText(Register_Activity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                     }
                 }

                 @Override
                 public void onFailure(Call<RegistrationData_Model> call, Throwable t) {

                 }
             });
         }
     });
    }
}