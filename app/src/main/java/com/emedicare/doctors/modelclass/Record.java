
package com.emedicare.doctors.modelclass;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Record {

    @SerializedName("schedulelist")
    @Expose
    private String schedulelist;
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
    @SerializedName("patientid")
    @Expose
    private String patientid;
    @SerializedName("emailid")
    @Expose
    private String emailid;
    @SerializedName("patientname")
    @Expose
    private String patientname;
    @SerializedName("patientloggedinstatus")
    @Expose
    private String patientloggedinstatus;

    public String getSchedulelist() {
        return schedulelist;
    }

    public void setSchedulelist(String schedulelist) {
        this.schedulelist = schedulelist;
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

    public String getPatientid() {
        return patientid;
    }

    public void setPatientid(String patientid) {
        this.patientid = patientid;
    }

    public String getEmailid() {
        return emailid;
    }

    public void setEmailid(String emailid) {
        this.emailid = emailid;
    }

    public String getPatientname() {
        return patientname;
    }

    public void setPatientname(String patientname) {
        this.patientname = patientname;
    }

    public String getPatientloggedinstatus() {
        return patientloggedinstatus;
    }

    public void setPatientloggedinstatus(String patientloggedinstatus) {
        this.patientloggedinstatus = patientloggedinstatus;
    }

}
