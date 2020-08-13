
package com.emedicare.docmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Record {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("physiciancode")
    @Expose
    private String physiciancode;
    @SerializedName("physicianname")
    @Expose
    private String physicianname;
    @SerializedName("altname")
    @Expose
    private String altname;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("physicianimage")
    @Expose
    private String physicianimage;
    @SerializedName("specialityname")
    @Expose
    private String specialityname;
    @SerializedName("price")
    @Expose
    private String price;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhysiciancode() {
        return physiciancode;
    }

    public void setPhysiciancode(String physiciancode) {
        this.physiciancode = physiciancode;
    }

    public String getPhysicianname() {
        return physicianname;
    }

    public void setPhysicianname(String physicianname) {
        this.physicianname = physicianname;
    }

    public String getAltname() {
        return altname;
    }

    public void setAltname(String altname) {
        this.altname = altname;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPhysicianimage() {
        return physicianimage;
    }

    public void setPhysicianimage(String physicianimage) {
        this.physicianimage = physicianimage;
    }

    public String getSpecialityname() {
        return specialityname;
    }

    public void setSpecialityname(String specialityname) {
        this.specialityname = specialityname;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

}
