
package com.emedicare.responceModel.medicines;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Record {

    @SerializedName("timestamp")
    @Expose
    private String timestamp;
    @SerializedName("sfdacode")
    @Expose
    private String sfdacode;
    @SerializedName("qty")
    @Expose
    private String qty;
    @SerializedName("itemname")
    @Expose
    private String itemname;

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getSfdacode() {
        return sfdacode;
    }

    public void setSfdacode(String sfdacode) {
        this.sfdacode = sfdacode;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getItemname() {
        return itemname;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }

}
