
package com.emedicare.responceModel.accedt;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Policy {

    @SerializedName("policycode")
    @Expose
    private String policycode;
    @SerializedName("policyname")
    @Expose
    private String policyname;
    @SerializedName("altname")
    @Expose
    private String altname;

    public String getPolicycode() {
        return policycode;
    }

    public void setPolicycode(String policycode) {
        this.policycode = policycode;
    }

    public String getPolicyname() {
        return policyname;
    }

    public void setPolicyname(String policyname) {
        this.policyname = policyname;
    }

    public String getAltname() {
        return altname;
    }

    public void setAltname(String altname) {
        this.altname = altname;
    }

}
