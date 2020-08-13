
package com.emedicare.responceModel;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DateSlotResponce {

    @SerializedName("companycode")
    @Expose
    private String companycode;
    @SerializedName("physiciancode")
    @Expose
    private String physiciancode;
    @SerializedName("physicianname")
    @Expose
    private String physicianname;
    @SerializedName("consultationfee")
    @Expose
    private String consultationfee;
    @SerializedName("deductableamount")
    @Expose
    private String deductableamount;
    @SerializedName("vatamount")
    @Expose
    private String vatamount;
    @SerializedName("totaltopay")
    @Expose
    private String totaltopay;
    @SerializedName("currencycode")
    @Expose
    private String currencycode;
    @SerializedName("altname")
    @Expose
    private String altname;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("physicianimage")
    @Expose
    private String physicianimage;
    @SerializedName("emailid")
    @Expose
    private String emailid;
    @SerializedName("dates")
    @Expose
    private List<Date> dates = null;

    public String getCompanycode() {
        return companycode;
    }

    public void setCompanycode(String companycode) {
        this.companycode = companycode;
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

    public String getConsultationfee() {
        return consultationfee;
    }

    public void setConsultationfee(String consultationfee) {
        this.consultationfee = consultationfee;
    }

    public String getDeductableamount() {
        return deductableamount;
    }

    public void setDeductableamount(String deductableamount) {
        this.deductableamount = deductableamount;
    }

    public String getVatamount() {
        return vatamount;
    }

    public void setVatamount(String vatamount) {
        this.vatamount = vatamount;
    }

    public String getTotaltopay() {
        return totaltopay;
    }

    public void setTotaltopay(String totaltopay) {
        this.totaltopay = totaltopay;
    }

    public String getCurrencycode() {
        return currencycode;
    }

    public void setCurrencycode(String currencycode) {
        this.currencycode = currencycode;
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

    public String getEmailid() {
        return emailid;
    }

    public void setEmailid(String emailid) {
        this.emailid = emailid;
    }

    public List<Date> getDates() {
        return dates;
    }

    public void setDates(List<Date> dates) {
        this.dates = dates;
    }

}
