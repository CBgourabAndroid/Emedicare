
package com.emedicare.responceModel.accedt;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AccEditResponce {

    @SerializedName("response")
    @Expose
    private String response;
    @SerializedName("patientid")
    @Expose
    private String patientid;
    @SerializedName("altname")
    @Expose
    private String altname;
    @SerializedName("altfamilyname")
    @Expose
    private String altfamilyname;
    @SerializedName("emailid")
    @Expose
    private String emailid;
    @SerializedName("mobilenumber")
    @Expose
    private String mobilenumber;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("familyname")
    @Expose
    private String familyname;
    @SerializedName("dob")
    @Expose
    private String dob;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("nationality")
    @Expose
    private Nationality nationality;
    @SerializedName("insurance")
    @Expose
    private Insurance insurance;
    @SerializedName("policy")
    @Expose
    private Policy policy;
    @SerializedName("class")
    @Expose
    private Class _class;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getPatientid() {
        return patientid;
    }

    public void setPatientid(String patientid) {
        this.patientid = patientid;
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

    public String getEmailid() {
        return emailid;
    }

    public void setEmailid(String emailid) {
        this.emailid = emailid;
    }

    public String getMobilenumber() {
        return mobilenumber;
    }

    public void setMobilenumber(String mobilenumber) {
        this.mobilenumber = mobilenumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFamilyname() {
        return familyname;
    }

    public void setFamilyname(String familyname) {
        this.familyname = familyname;
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

    public Nationality getNationality() {
        return nationality;
    }

    public void setNationality(Nationality nationality) {
        this.nationality = nationality;
    }

    public Insurance getInsurance() {
        return insurance;
    }

    public void setInsurance(Insurance insurance) {
        this.insurance = insurance;
    }

    public Policy getPolicy() {
        return policy;
    }

    public void setPolicy(Policy policy) {
        this.policy = policy;
    }

    public Class getClass_() {
        return _class;
    }

    public void setClass_(Class _class) {
        this._class = _class;
    }

}
