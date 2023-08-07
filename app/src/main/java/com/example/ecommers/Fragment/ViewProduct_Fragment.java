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
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.ecommers.Adapter.Recyclerview_Adapter;
import com.example.ecommers.Instance_Class;
import com.example.ecommers.Models.Test.Product_Data;
import com.example.ecommers.Models.ViewProductData_Model;
import com.example.ecommers.Models.ViewUser_Model;
import com.example.ecommers.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ViewProduct_Fragment extends Fragment {
    RecyclerView recyclerView;
    String id;
    ArrayList<ViewProductData_Model> productdata = new ArrayList<>();
    SearchView searchView;
    Recyclerview_Adapter adapter;
    Button sort;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_product_, container, false);
        recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(ViewProduct_Fragment.this.getContext()));
        searchView=view.findViewById(R.id.searchView);
        sort=view.findViewById(R.id.sortData);

        sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Collections.sort(productdata, new Comparator<ViewProductData_Model>() {
                    @Override
                    public int compare(ViewProductData_Model o1, ViewProductData_Model o2) {
                        return o1.getPname().compareTo(o2.getPname());
                    }

                    @Override
                    public boolean equals(Object obj) {
                        return false;
                    }
                });
                //adapter=new Recyclerview_Adapter(getActivity(),productdata);
                adapter.sortData(productdata);
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ArrayList<Product_Data> newList=new ArrayList<>();

                filterData(newText);

                return false;
            }
        });



        id = preferences.getString("userid", null);
        Log.d("AAA", "onCreateView: "+id);
        Instance_Class.CallApi().ViewProduct(id).enqueue(new Callback<ViewUser_Model>() {
            @Override
            public void onResponse(Call<ViewUser_Model> call, Response<ViewUser_Model> response) {
                Log.d("AAA", "onResponse: ggg " + response.body().getProductdata());
                if (response.body().getConnection() == 1 && response.body().getResult() == 1) {


                    productdata.addAll(response.body().getProductdata());

                    //Log.d("UUU", "onResponse: " + ProductData.get(0).getPNAME());
                    adapter = new Recyclerview_Adapter(ViewProduct_Fragment.this.getActivity(), productdata, new  Fragment_Interface() {
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
                    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
//                    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//                    recyclerView.setLayoutManager(layoutManager);
//                    MaterialDividerItemDecoration mDividerItemDecoration = new MaterialDividerItemDecoration(recyclerView.getContext(),
//                            layoutManager.getOrientation());
//                    recyclerView.addItemDecoration(mDividerItemDecoration);
                    //myAdapter.notifyDataSetChanged();

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

    private void filterData(String newText)
    {
        ArrayList<ViewProductData_Model> filteredlist = new ArrayList<>();

        // running a for loop to compare elements.
        for (ViewProductData_Model item : productdata) {
            // checking if the entered string matched with any item of our recycler view.
            if (item.getPname().toLowerCase().contains(newText.toLowerCase())) {
                // if the item is matched we are
                // adding it to our filtered list.
                filteredlist.add(item);

            }
        }
        if (filteredlist.isEmpty()) {
            // if no item is added in filtered list we are
            // displaying a toast message as no data found.
            Toast.makeText(ViewProduct_Fragment.this.getContext(), "No Data Found..", Toast.LENGTH_SHORT).show();
        } else {
            // at last we are passing that filtered
            // list to our adapter class.
            adapter.filterList(filteredlist);
        }
    }
}