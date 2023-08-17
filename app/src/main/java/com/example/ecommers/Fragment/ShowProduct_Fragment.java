package com.example.ecommers.Fragment;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.ecommers.Adapter.Recyclerview_Adapter;
import com.example.ecommers.Instance_Class;
import com.example.ecommers.Models.ViewProductData_Model;
import com.example.ecommers.Models.ViewUser_Model;
import com.example.ecommers.R;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ShowProduct_Fragment extends Fragment {
    RecyclerView recyclerView;
    ArrayList<ViewProductData_Model>productdata=new ArrayList<>();
    Recyclerview_Adapter adapter;
    ImageView imageView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

               View view= inflater.inflate(R.layout.fragment_show_product_, container, false);
               recyclerView=view.findViewById(R.id.show_prod_recyclerview);
               recyclerView.setLayoutManager(new LinearLayoutManager(ShowProduct_Fragment.this.getContext()));


        Instance_Class.CallApi().showAllProducts().enqueue(new Callback<ViewUser_Model>() {
            @Override
            public void onResponse(Call<ViewUser_Model> call, Response<ViewUser_Model> response) {
                Log.d("ttt", "onResponse: " + response.body());
                if (response.body().getConnection()==1&&response.body().getResult()==1)
                {
                   productdata.addAll(response.body().getProductdata());
                    Log.d("TTT", "onResponse: "+productdata);
                  adapter=new Recyclerview_Adapter(ShowProduct_Fragment.this.getActivity(),productdata);
                  recyclerView.setAdapter(adapter);
            } else if (response.body().getResult()==0) {
                    Toast.makeText(getContext(), "No more items available", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                }
                    
                }

            @Override
            public void onFailure(Call<ViewUser_Model> call, Throwable t) {
                Log.e("TTT", "onFailure: "+t.getLocalizedMessage() );

            }


        });

        return view;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();

                Log.d("======", "onActivityResult: " + resultUri);
                imageView.setImageURI(resultUri);


            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }
}