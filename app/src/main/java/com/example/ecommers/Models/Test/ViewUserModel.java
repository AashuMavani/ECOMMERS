
package com.example.ecommers.Models.Test;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ViewUserModel {

    @SerializedName("connection")
    @Expose
    private Long connection;
    @SerializedName("result")
    @Expose
    private Long result;
    @SerializedName("productdata")
    @Expose
    private List<Product_Data> productdata;

    public Long getConnection() {
        return connection;
    }

    public void setConnection(Long connection) {
        this.connection = connection;
    }

    public Long getResult() {
        return result;
    }

    public void setResult(Long result) {
        this.result = result;
    }

    public List<Product_Data> getProductdata() {
        return productdata;
    }

    public void setProductdata(List<Product_Data> productdata) {
        this.productdata = productdata;
    }

    @Override
    public String toString() {
        return "ViewUserModel{" +
                "connection=" + connection +
                ", result=" + result +
                ", productdata=" + productdata +
                '}';
    }

}
