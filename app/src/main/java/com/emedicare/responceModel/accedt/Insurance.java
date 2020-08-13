
package com.emedicare.responceModel.accedt;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Insurance {

    @SerializedName("insurancecode")
    @Expose
    private String insurancecode;
    @SerializedName("insurancename")
    @Expose
    private String insurancename;
    @SerializedName("altname")
    @Expose
    private String altname;

    public String getInsurancecode() {
        return insurancecode;
    }

    public void setInsurancecode(String insurancecode) {
        this.insurancecode = insurancecode;
    }

    public String getInsurancename() {
        return insurancename;
    }

    public void setInsurancename(String insurancename) {
        this.insurancename = insurancename;
    }

    public String getAltname() {
        return altname;
    }

    public void setAltname(String altname) {
        this.altname = altname;
    }

}
