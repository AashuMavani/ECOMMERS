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
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Recyclerview_Adapter extends RecyclerView.Adapter<Recyclerview_Adapter.Holder> {

    ArrayList<ViewProductData_Model> productdata;
    Fragment_Interface fragment_interface;
    FragmentActivity context;


    public Recyclerview_Adapter(FragmentActivity context, ArrayList<ViewProductData_Model> productdata, Fragment_Interface fragment_interface) {
        this.context=context;
        this.productdata=productdata;
        this.fragment_interface=fragment_interface;

    }
    public Recyclerview_Adapter(){

    }

    public Recyclerview_Adapter(FragmentActivity context, ArrayList<ViewProductData_Model> productdata) {
        this.context=context;
        this.productdata=productdata;
    }

    @NonNull
    @Override
    public Recyclerview_Adapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.recyclerview_item_layout,parent,false);
      Holder holder=new Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Recyclerview_Adapter.Holder holder,@SuppressLint("Recyclerview") int position) {

        holder.t1.setText(""+productdata.get(position).getPname());
        holder.t2.setText(""+productdata.get(position).getPdes());
        holder.t3.setText(""+productdata.get(position).getPprice());
        String img="https://ashmitashop.000webhostapp.com/AshmitaShop/"+productdata.get(holder.getAdapterPosition()).getPimg();
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
//        Picasso.get().invalidate(img);
//        Picasso.get().load(img).networkPolicy(NetworkPolicy.NO_CACHE).memoryPolicy(MemoryPolicy.NO_CACHE).placeholder(R.drawable.jmkjkfg).into(holder.imageView);


        Glide.with(context).load(img)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(holder.item_img);
       holder.popmenu.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               PopupMenu popupMenu=new PopupMenu(context,holder.item_img);
               popupMenu.getMenuInflater().inflate(R.menu.edit_menu,popupMenu.getMenu());
               popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                   @Override
                   public boolean onMenuItemClick(MenuItem item) {

                   if (item.getItemId()==R.id.deleteProduct)
                   {
                       Instance_Class.CallApi().DeleteProduct(productdata.get(holder.getAdapterPosition()).getId()).enqueue(new Callback<Delete_Data_Model>() {
                           @Override
                           public void onResponse(Call<Delete_Data_Model> call, Response<Delete_Data_Model> response) {
                               Log.d("TAG===", "onResponse: " + productdata.get(holder.getAdapterPosition()).getId());
                               Log.d("delete", "onResponse: " +response.body().getResult());
                               if (response.body().getConnection()==1&&response.body().getResult()==1)
                               {
                                   Toast.makeText(context, "product"+(position+1)+"no more available", Toast.LENGTH_SHORT).show();
                                  productdata.remove(position);
                                  notifyDataSetChanged();
                                  if (productdata.isEmpty())
                                  {
                                      Toast.makeText(context, "No more products available..", Toast.LENGTH_LONG).show();
                                  }

                               } else if (response.body().getResult()==0) {
                                   Toast.makeText(context, "No more products available..", Toast.LENGTH_LONG).show();
                                   
                               }
                               else {
                                   Toast.makeText(context, "Something went wrong..", Toast.LENGTH_SHORT).show();
                               }

                           }

                           @Override
                           public void onFailure(Call<Delete_Data_Model> call, Throwable t) {
                               Log.e("delete", "onResponse: " + t.getLocalizedMessage());
                               Toast.makeText(context, "Something went wrong..", Toast.LENGTH_SHORT).show();

                           }
                       });
                   }
                   if (item.getItemId()==R.id.updateProducr)
                   {
                    fragment_interface.onFragmentCall(
                            productdata.get(holder.getAdapterPosition()).getId(),
                            productdata.get(holder.getAdapterPosition()).getPname(),
                            productdata.get(holder.getAdapterPosition()).getPprice(),
                            productdata.get(holder.getAdapterPosition()).getPdes(),
                            productdata.get(holder.getAdapterPosition()).getPimg()
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
        return productdata.size();
    }

    public void filterList(ArrayList<ViewProductData_Model> filteredlist) {
        productdata=filteredlist;
        notifyDataSetChanged();
    }

    public void sortData(ArrayList<ViewProductData_Model> product_data)
    {
        productdata=product_data;
        notifyDataSetChanged();
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
