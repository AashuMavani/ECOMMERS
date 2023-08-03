package com.example.ecommers;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Instance_Class {
    public static Retro_Interface CallApi(){
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("https://ashmitashop.000webhostapp.com/AshmitaShop/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(Retro_Interface.class);
    }
}
