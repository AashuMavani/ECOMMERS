
package com.example.ecommers.Models.Test;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Product_Data {

    @SerializedName("ID")
    @Expose
    private String id;
    @SerializedName("UID")
    @Expose
    private String uid;
    @SerializedName("PNAME")
    @Expose
    private String pname;
    @SerializedName("PPRICE")
    @Expose
    private String pprice;
    @SerializedName("PDES")
    @Expose
    private String pdes;
    @SerializedName("PIMG")
    @Expose
    private String pimg;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getPprice() {
        return pprice;
    }

    public void setPprice(String pprice) {
        this.pprice = pprice;
    }

    public String getPdes() {
        return pdes;
    }

    public void setPdes(String pdes) {
        this.pdes = pdes;
    }

    public String getPimg() {
        return pimg;
    }

    public void setPimg(String pimg) {
        this.pimg = pimg;
    }

    @Override
    public String toString() {
        return "Product_Data{" +
                "id='" + id + '\'' +
                ", uid='" + uid + '\'' +
                ", pname='" + pname + '\'' +
                ", pprice='" + pprice + '\'' +
                ", pdes='" + pdes + '\'' +
                ", pimg='" + pimg + '\'' +
                '}';
    }
}
