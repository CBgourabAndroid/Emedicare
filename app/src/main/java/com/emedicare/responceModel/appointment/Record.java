
package com.emedicare.responceModel.appointment;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Record {

    @SerializedName("bookingnumber")
    @Expose
    private String bookingnumber;
    @SerializedName("slotnumber")
    @Expose
    private String slotnumber;
    @SerializedName("slottype")
    @Expose
    private String slottype;
    @SerializedName("slotdate")
    @Expose
    private String slotdate;
    @SerializedName("slottime")
    @Expose
    private String slottime;
    @SerializedName("bookingdate")
    @Expose
    private String bookingdate;
    @SerializedName("physiciancode")
    @Expose
    private String physiciancode;
    @SerializedName("physicianname")
    @Expose
    private String physicianname;
    @SerializedName("physicianimage")
    @Expose
    private String physicianimage;
    @SerializedName("specialityname")
    @Expose
    private String specialityname;
    @SerializedName("message")
    @Expose
    private String message;

    public String getBookingnumber() {
        return bookingnumber;
    }

    public void setBookingnumber(String bookingnumber) {
        this.bookingnumber = bookingnumber;
    }

    public String getSlotnumber() {
        return slotnumber;
    }

    public void setSlotnumber(String slotnumber) {
        this.slotnumber = slotnumber;
    }

    public String getSlottype() {
        return slottype;
    }

    public void setSlottype(String slottype) {
        this.slottype = slottype;
    }

    public String getSlotdate() {
        return slotdate;
    }

    public void setSlotdate(String slotdate) {
        this.slotdate = slotdate;
    }

    public String getSlottime() {
        return slottime;
    }

    public void setSlottime(String slottime) {
        this.slottime = slottime;
    }

    public String getBookingdate() {
        return bookingdate;
    }

    public void setBookingdate(String bookingdate) {
        this.bookingdate = bookingdate;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}