
package com.example.ecommers.Models;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class ViewUser_Model {

    @SerializedName("connection")
    @Expose
    private Integer connection;
    @SerializedName("result")
    @Expose
    private Integer result;
    @SerializedName("productdata")
    @Expose
    private List<ViewProductData_Model> productdata;

    public Integer getConnection() {
        return connection;
    }

    public void setConnection(Integer connection) {
        this.connection = connection;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public List<ViewProductData_Model> getProductdata() {
        return productdata;
    }

    public void setProductdata(List<ViewProductData_Model> productdata) {
        this.productdata = productdata;
    }

    @Override
    public String toString() {
        return "ViewUser_Model{" +
                "connection=" + connection +
                ", result=" + result +
                ", productdata=" + productdata +
                '}';
    }
}
