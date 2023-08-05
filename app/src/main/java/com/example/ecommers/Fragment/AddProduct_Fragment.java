package com.example.ecommers.Fragment;

import static android.app.Activity.RESULT_OK;

import static com.example.ecommers.Activity.MainActivity.preferences;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;

import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.ecommers.Activity.MainActivity;
import com.example.ecommers.Instance_Class;
import com.example.ecommers.Models.AddProductData_Model;
import com.example.ecommers.Models.Delete_Data_Model;
import com.example.ecommers.R;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.ByteArrayOutputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AddProduct_Fragment extends Fragment {
    AppCompatEditText  pname, pprice, pdes;
    ImageView pimg;

    Button Addbutton;
    Button btn_update;

    String t1,t2, t3, t4;


    Uri resultUri;

    String id,name,price,des,imageName;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_add, container, false);

       pname=view.findViewById(R.id.pname);
       pdes=view.findViewById(R.id.pdes);
       pprice=view.findViewById(R.id.pprice);
       pimg=view.findViewById(R.id.pimg);
       Addbutton=view.findViewById(R.id.Addbutton);
       btn_update=view.findViewById(R.id.btn_update);

       if (getArguments()==null)
       {
          Addbutton.setVisibility(View.VISIBLE);
       }
       if (getArguments()!=null)
       {
          btn_update.setVisibility(View.VISIBLE);
          //id= preferences.getString("userid",null);
           id=getArguments().getString("id");
           name=getArguments().getString("name");
           price=getArguments().getString("price");
           des=getArguments().getString("des");
           imageName=getArguments().getString("img");


           Log.d("ggg", "onCreateView: id="+id);
           Log.d("ggg", "onCreateView: name="+name);
           Log.d("ggg", "onCreateView: price="+price);
           Log.d("ggg", "onCreateView: des="+des);
           Log.d("ggg", "onCreateView: image="+imageName);

           pname.setText(""+name);
           pprice.setText(""+price);
           pdes.setText(""+des);

           String img = "https://ashmitashop.000webhostapp.com/AshmitaShop/"+imageName;
           Glide.with(getContext()).load(img)
                   .diskCacheStrategy(DiskCacheStrategy.NONE)
                   .skipMemoryCache(true)
                   .into(pimg);
           System.out.println("ImgName="+img);


           pimg.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   CropImage.activity()
                           .start(getContext(), AddProduct_Fragment.this);
               }
           });
           btn_update.setOnClickListener(new View.OnClickListener() {
               @RequiresApi(api = Build.VERSION_CODES.O)
               @Override
               public void onClick(View v) {
                   Bitmap bitmap = ((BitmapDrawable) pimg.getDrawable()).getBitmap();
                   ByteArrayOutputStream baos = new ByteArrayOutputStream();
                   bitmap.compress(Bitmap.CompressFormat.JPEG, 20, baos);
                   byte[] imageInByte = baos.toByteArray();
                   String imagedata = java.util.Base64.getEncoder().encodeToString(imageInByte);

                  // Get the Base64 string


                   Log.d("UUU", "onClick: imgString="+imagedata);
                   Instance_Class.CallApi().updateProduct(id,pname.getText().toString(),pprice.getText().toString(),pdes.getText().toString(),imagedata,imageName).enqueue(new Callback<Delete_Data_Model>() {
                       @Override
                       public void onResponse(Call<Delete_Data_Model> call, Response<Delete_Data_Model> response) {
                           if(response.body().getConnection()==1 && response.body().getResult()==1)
                           {
                               Toast.makeText(getContext(), "Product updated..", Toast.LENGTH_SHORT).show();

//                                startActivity(new Intent(Fragment_Add_Product.));

                           }
                           else if (response.body().getConnection()==1 && response.body().getResult()==0)
                           {
                               Toast.makeText(getContext(), "Product not updated", Toast.LENGTH_SHORT).show();
                           }
                           else {
                               Toast.makeText(getContext(),"Something went wrong", Toast.LENGTH_SHORT).show();
                           }

                       }

                       @Override
                       public void onFailure(Call<Delete_Data_Model> call, Throwable t) {
                           Log.d("===", "onFailure: ");
                           Toast.makeText(getContext(),"Something went wrong"+t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

                       }
                   });
               }
           });

       }
        pimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropImage.activity()
                        .start(getContext(), AddProduct_Fragment.this);
            }
        });

       Addbutton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               t1= preferences.getString("userid",null);
               t2=pname.getText().toString();
               t3=pdes.getText().toString();
               t4=pprice.getText().toString();

               Bitmap bitmap=((BitmapDrawable)pimg.getDrawable()).getBitmap();
               ByteArrayOutputStream baos = new ByteArrayOutputStream();
               bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos); // bm is the bitmap object
               byte[] b = baos.toByteArray();


               // Get the Base64 string
               String imgString = Base64.encodeToString(b, Base64.DEFAULT);

               Log.d("UUU", "onClick: imgString="+imgString);

               Instance_Class.CallApi().Addproduct(t1,t2,t3,t4,imgString).enqueue(new Callback<AddProductData_Model>() {
                   @Override
                   public void onResponse(Call<AddProductData_Model> call, Response<AddProductData_Model> response) {
                       Log.d("AAA", "onResponse: "+response.body());
                       if (response.body().getConnection()==1&&response.body().getProductaddd()==1)
                       {
                           Log.d("AAA", "onResponse: product Add");
                           Toast.makeText(AddProduct_Fragment.this.getContext(), "Product ADD", Toast.LENGTH_SHORT).show();

                       } else if (response.body().getProductaddd()==0) {
                           Log.d("AAA", "onResponse: product not Add");
                           Toast.makeText(AddProduct_Fragment.this.getContext(), "Product not ADD", Toast.LENGTH_SHORT).show();

                       }
                       else
                       {
                           Log.d("TTT", "Something went wrong.");
                           Toast.makeText(AddProduct_Fragment.this.getContext(), "Something went wrong..", Toast.LENGTH_LONG).show();
                       }

                   }

                   @Override
                   public void onFailure(Call<AddProductData_Model> call, Throwable t) {

                   }
               });
           }
       });


        return view;

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                resultUri = result.getUri();
                pimg.setImageURI(resultUri);

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }
}