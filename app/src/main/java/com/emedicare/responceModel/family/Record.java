
package com.emedicare.responceModel.family;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Record {

    @SerializedName("patientid")
    @Expose
    private String patientid;
    @SerializedName("patientname")
    @Expose
    private String patientname;
    @SerializedName("familyname")
    @Expose
    private String familyname;
    @SerializedName("altname")
    @Expose
    private String altname;
    @SerializedName("altfamilyname")
    @Expose
    private String altfamilyname;
    @SerializedName("dob")
    @Expose
    private String dob;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("mobilenumber")
    @Expose
    private String mobilenumber;
    @SerializedName("emailid")
    @Expose
    private String emailid;

    public String getPatientid() {
        return patientid;
    }

    public void setPatientid(String patientid) {
        this.patientid = patientid;
    }

    public String getPatientname() {
        return patientname;
    }

    public void setPatientname(String patientname) {
        this.patientname = patientname;
    }

    public String getFamilyname() {
        return familyname;
    }

    public void setFamilyname(String familyname) {
        this.familyname = familyname;
    }

    public String getAltname() {
        return altname;
    }

    public void setAltname(String altname) {
        this.altname = altname;
    }

    public String getAltfamilyname() {
        return altfamilyname;
    }

    public void setAltfamilyname(String altfamilyname) {
        this.altfamilyname = altfamilyname;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMobilenumber() {
        return mobilenumber;
    }

    public void setMobilenumber(String mobilenumber) {
        this.mobilenumber = mobilenumber;
    }

    public String getEmailid() {
        return emailid;
    }

    public void setEmailid(String emailid) {
        this.emailid = emailid;
    }

}
