package com.example.ecommers.Adapter;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.ecommers.Fragment.Fragment_Interface;
import com.example.ecommers.Fragment.ViewProduct_Fragment;
import com.example.ecommers.Instance_Class;
import com.example.ecommers.Models.Delete_Data_Model;
import com.example.ecommers.Models.ViewProductData_Model;
import com.example.ecommers.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Recyclerview_Adapter extends RecyclerView.Adapter<Recyclerview_Adapter.Holder> {
    ViewProduct_Fragment viewProduct_fragment;
    ArrayList<ViewProductData_Model> ProductData;
    Fragment_Interface fragment_interface;


    public Recyclerview_Adapter(FragmentActivity viewProduct_fragment, ArrayList<ViewProductData_Model> productData, Fragment_Interface fragment_interface) {
        this.viewProduct_fragment=viewProduct_fragment;
        this.ProductData=productData;
        this.fragment_interface=fragment_interface;

    }

    public Recyclerview_Adapter(FragmentActivity activity, ArrayList<ViewProductData_Model> productdata) {
    }

    @NonNull
    @Override
    public Recyclerview_Adapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(viewProduct_fragment.getContext()).inflate(R.layout.recyclerview_item_layout,parent,false);
      Holder holder=new Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Recyclerview_Adapter.Holder holder,@SuppressLint("Recyclerview") int position) {
        holder.t1.setText(""+ProductData.get(position).getPname());
        holder.t2.setText(""+ProductData.get(position).getPdes());
        holder.t3.setText(""+ProductData.get(position).getPprice());
        String img="https://ashmitashop.000webhostapp.com/AshmitaShop/"+ProductData.get(holder.getAdapterPosition()).getPimg();
//        Glide
//                .with(viewProduct_fragment.getContext()).load(img)
//                .diskCacheStrategy(DiskCacheStrategy.NONE)
//                .skipMemoryCache(true)
//                .into(holder.item_img);

//        Picasso.get()
//                .load(img)
//                .placeholder(R.drawable.jmkjkfg)
//                .into(holder.imageView);


        //Picasso.get().load(img).memoryPolicy(MemoryPolicy.NO_CACHE).into(holder.imageView);


        Glide.with(viewProduct_fragment).load(img)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(holder.item_img);
       holder.popmenu.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               PopupMenu popupMenu=new PopupMenu(viewProduct_fragment.getContext(),holder.item_img);
               popupMenu.getMenuInflater().inflate(R.menu.edit_menu,popupMenu.getMenu());
               popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                   @Override
                   public boolean onMenuItemClick(MenuItem item) {

                   if (item.getItemId()==R.id.deleteProduct)
                   {
                       Instance_Class.CallApi().DeleteProduct(ProductData.get(holder.getAdapterPosition()).getId()).enqueue(new Callback<Delete_Data_Model>() {
                           @Override
                           public void onResponse(Call<Delete_Data_Model> call, Response<Delete_Data_Model> response) {
                               Log.d("TAG===", "onResponse: " + ProductData.get(holder.getAdapterPosition()).getId());
                               Log.d("delete", "onResponse: " +response.body().getResult());
                               if (response.body().getConnection()==1&&response.body().getResult()==1)
                               {
                                   Toast.makeText(viewProduct_fragment.getContext(), "product"+(position+1)+"no more available", Toast.LENGTH_SHORT).show();
                                  ProductData.remove(position);
                                  notifyDataSetChanged();
                                  if (ProductData.isEmpty())
                                  {
                                      Toast.makeText(viewProduct_fragment.getContext(), "No more products available..", Toast.LENGTH_LONG).show();
                                  }

                               } else if (response.body().getResult()==0) {
                                   Toast.makeText(viewProduct_fragment.getContext(), "No more products available..", Toast.LENGTH_LONG).show();
                                   
                               }
                               else {
                                   Toast.makeText(viewProduct_fragment.getContext(), "Something went wrong..", Toast.LENGTH_SHORT).show();
                               }

                           }

                           @Override
                           public void onFailure(Call<Delete_Data_Model> call, Throwable t) {
                               Log.e("delete", "onResponse: " + t.getLocalizedMessage());
                               Toast.makeText(viewProduct_fragment.getContext(), "Something went wrong..", Toast.LENGTH_SHORT).show();

                           }
                       });
                   }
                   if (item.getItemId()==R.id.updateProducr)
                   {
                    fragment_interface.onFragmentCall(
                            ProductData.get(holder.getAdapterPosition()).getId(),
                            ProductData.get(holder.getAdapterPosition()).getPname(),
                            ProductData.get(holder.getAdapterPosition()).getPprice(),
                            ProductData.get(holder.getAdapterPosition()).getPdes(),
                            ProductData.get(holder.getAdapterPosition()).getPimg()
                    );

                   }
                       return false;
                   }
               });
               popupMenu.show();
           }
       });

    }

    @Override
    public int getItemCount() {
        return ProductData.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView t1,t2,t3;
        ImageView item_img,popmenu;
        public Holder(@NonNull View itemView) {
            super(itemView);
          t1=itemView.findViewById(R.id.item_name);
          t2=itemView.findViewById(R.id.item_des);
          t3=itemView.findViewById(R.id.item_price);
          item_img=itemView.findViewById(R.id.item_img);
          popmenu=itemView.findViewById(R.id.popmenu);
        }
    }
}
