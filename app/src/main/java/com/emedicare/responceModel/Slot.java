
package com.emedicare.responceModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Slot {

    @SerializedName("slotnumber")
    @Expose
    private String slotnumber;
    @SerializedName("slottype")
    @Expose
    private String slottype;
    @SerializedName("slottime")
    @Expose
    private String slottime;
    @SerializedName("status")
    @Expose
    private String status;

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

    public String getSlottime() {
        return slottime;
    }

    public void setSlottime(String slottime) {
        this.slottime = slottime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
