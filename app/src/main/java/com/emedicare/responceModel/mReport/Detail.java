
package com.emedicare.responceModel.mReport;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Detail {

    @SerializedName("reportnumber")
    @Expose
    private String reportnumber;
    @SerializedName("consultationnumber")
    @Expose
    private String consultationnumber;
    @SerializedName("reportdate")
    @Expose
    private String reportdate;
    @SerializedName("reportdetail")
    @Expose
    private String reportdetail;
    @SerializedName("reportattachment")
    @Expose
    private String reportattachment;

    public String getReportnumber() {
        return reportnumber;
    }

    public void setReportnumber(String reportnumber) {
        this.reportnumber = reportnumber;
    }

    public String getConsultationnumber() {
        return consultationnumber;
    }

    public void setConsultationnumber(String consultationnumber) {
        this.consultationnumber = consultationnumber;
    }

    public String getReportdate() {
        return reportdate;
    }

    public void setReportdate(String reportdate) {
        this.reportdate = reportdate;
    }

    public String getReportdetail() {
        return reportdetail;
    }

    public void setReportdetail(String reportdetail) {
        this.reportdetail = reportdetail;
    }

    public String getReportattachment() {
        return reportattachment;
    }

    public void setReportattachment(String reportattachment) {
        this.reportattachment = reportattachment;
    }

}
