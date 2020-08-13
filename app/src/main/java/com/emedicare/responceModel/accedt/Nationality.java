
package com.emedicare.responceModel.accedt;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Nationality {

    @SerializedName("nationalitycode")
    @Expose
    private String nationalitycode;
    @SerializedName("nationality")
    @Expose
    private String nationality;
    @SerializedName("altname")
    @Expose
    private String altname;

    public String getNationalitycode() {
        return nationalitycode;
    }

    public void setNationalitycode(String nationalitycode) {
        this.nationalitycode = nationalitycode;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getAltname() {
        return altname;
    }

    public void setAltname(String altname) {
        this.altname = altname;
    }

}
