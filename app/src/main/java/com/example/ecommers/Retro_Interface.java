package com.example.ecommers;

import com.example.ecommers.Models.AddProductData_Model;
import com.example.ecommers.Models.Delete_Data_Model;
import com.example.ecommers.Models.LoginData_Model;
import com.example.ecommers.Models.RegistrationData_Model;
import com.example.ecommers.Models.ShowAllProduct_Model;
import com.example.ecommers.Models.ViewUser_Model;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Retro_Interface {
    @FormUrlEncoded
    @POST("Register.php")
    Call<RegistrationData_Model> userRegister(
            @Field("name") String name,
            @Field("email") String email,
            @Field("password") String password);

    @FormUrlEncoded
    @POST("Login.php")
    Call<LoginData_Model> userLogin(
            @Field("email") String email,
            @Field("password") String password);

    @FormUrlEncoded
    @POST("AddProduct.php")
    Call<AddProductData_Model>
    Addproduct(@Field("userid") String uid,
               @Field("pname") String pname,
               @Field("pprice") String pprice,
               @Field("pdes") String pdes,
               @Field("productimage") String imgString);

    @FormUrlEncoded
    @POST("ViewProduct.php")
    Call<ViewUser_Model>
    ViewProduct(@Field("userid") String userid);

    @FormUrlEncoded
    @POST("DeleteProduct.php")
    Call<Delete_Data_Model>DeleteProduct(
        @Field("id")String userid);


    @FormUrlEncoded
    @POST("UpdateProduct.php")
        //Response is same for both update and delete, hence pojo is same for both
    Call<Delete_Data_Model> updateProduct(@Field("id") String id,
                                   @Field("name")String name,
                                   @Field("price") String price,
                                   @Field("description")String description,
                                   @Field("imagedata")String imagedata,
                                   @Field("imagename")String imagename);
    @GET("ShowAllproducts.php")

    Call<ShowAllProduct_Model> ShowAllProducts();

}
