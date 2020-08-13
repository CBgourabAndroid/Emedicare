
package com.emedicare.responceModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AuthResponce {

    @SerializedName("response")
    @Expose
    private String response;
    @SerializedName("loginType")
    @Expose
    private String loginType;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("emailid")
    @Expose
    private String emailid;
    @SerializedName("mobilenumber")
    @Expose
    private String mobilenumber;
    @SerializedName("familyname")
    @Expose
    private String familyname;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("insurancecode")
    @Expose
    private String insurancecode;
    @SerializedName("status")
    @Expose
    private String status;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getLoginType() {
        return loginType;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getFamilyname() {
        return familyname;
    }

    public void setFamilyname(String familyname) {
        this.familyname = familyname;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getInsurancecode() {
        return insurancecode;
    }

    public void setInsurancecode(String insurancecode) {
        this.insurancecode = insurancecode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}