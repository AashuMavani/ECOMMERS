package com.example.ecommers.Fragment;

import static com.example.ecommers.Activity.MainActivity.preferences;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.ecommers.Adapter.Recyclerview_Adapter;
import com.example.ecommers.Instance_Class;
import com.example.ecommers.Models.ViewProductData_Model;
import com.example.ecommers.Models.ViewUser_Model;
import com.example.ecommers.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ViewProduct_Fragment extends Fragment {
    RecyclerView recyclerView;
    String id;
    ArrayList<ViewProductData_Model> ProductData = new ArrayList<>();





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_product_, container, false);
        recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(ViewProduct_Fragment.this.getContext()));

        id = preferences.getString("userid", null);
        Log.d("AAA", "onCreateView: "+id);
        Instance_Class.CallApi().ViewProduct(id).enqueue(new Callback<ViewUser_Model>() {
            @Override
            public void onResponse(Call<ViewUser_Model> call, Response<ViewUser_Model> response) {
                Log.d("AAA", "onResponse: ggg " + response.body().getProductdata());
                if (response.body().getConnection() == 1 && response.body().getResult() == 1) {


                    ProductData.addAll(response.body().getProductdata());

                    //Log.d("UUU", "onResponse: " + ProductData.get(0).getPNAME());
                    Recyclerview_Adapter adapter = new Recyclerview_Adapter(ViewProduct_Fragment.this.getActivity(), ProductData, new  Fragment_Interface() {
                        @Override
                        public void onFragmentCall(String id, String pName, String pPrice, String pDes, String pImg) {
                            AddProduct_Fragment fragment=new AddProduct_Fragment();
                            Bundle bundle=new Bundle();
                            bundle.putString("id",id);
                            bundle.putString("name",pName);
                            bundle.putString("price",pPrice);
                            bundle.putString("des",pDes);
                            bundle.putString("img",pImg);

                            fragment.setArguments(bundle);
                            FragmentManager manager=getActivity().getSupportFragmentManager();
                            FragmentTransaction transaction=manager.beginTransaction();
                            transaction.replace(R.id.content_view,fragment);
                            transaction.commit();
                        }

                    });
                    recyclerView.setAdapter(adapter);
                    Toast.makeText(getContext(), "Data Found...", Toast.LENGTH_LONG).show();
                } else {
                    Log.d("TTT", "onResponse: No Data Found...");
                    Toast.makeText(ViewProduct_Fragment.this.getContext(), "No Data Found...", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ViewUser_Model> call, Throwable t) {
                Log.e("TTT", "onFailure: " + t.getLocalizedMessage());

            }
        });

        return view;
    }
}