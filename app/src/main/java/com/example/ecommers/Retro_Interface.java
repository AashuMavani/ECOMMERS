package com.example.ecommers;

import com.example.ecommers.Models.LoginData_Model;
import com.example.ecommers.Models.RegistrationData_Model;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Retro_Interface {
  @FormUrlEncoded
  @POST("Register.php")
    Call<RegistrationData_Model>userRegister(
            @Field("name") String name,
            @Field("email")String email,
            @Field("password")String password);

  @FormUrlEncoded
  @POST("Login.php")
  Call<LoginData_Model> userLogin(
          @Field("email") String email,
          @Field("password") String password);

  @FormUrlEncoded
  @POST("AddProduct.php")
  Call<>

}
