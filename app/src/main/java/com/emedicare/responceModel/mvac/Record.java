
package com.emedicare.responceModel.mvac;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Record {

    @SerializedName("vaccinationto")
    @Expose
    private String vaccinationto;
    @SerializedName("vaccinename")
    @Expose
    private String vaccinename;
    @SerializedName("scheduledate")
    @Expose
    private String scheduledate;
    @SerializedName("vaccinedate")
    @Expose
    private String vaccinedate;
    @SerializedName("vaccinenotes")
    @Expose
    private String vaccinenotes;

    public String getVaccinationto() {
        return vaccinationto;
    }

    public void setVaccinationto(String vaccinationto) {
        this.vaccinationto = vaccinationto;
    }

    public String getVaccinename() {
        return vaccinename;
    }

    public void setVaccinename(String vaccinename) {
        this.vaccinename = vaccinename;
    }

    public String getScheduledate() {
        return scheduledate;
    }

    public void setScheduledate(String scheduledate) {
        this.scheduledate = scheduledate;
    }

    public String getVaccinedate() {
        return vaccinedate;
    }

    public void setVaccinedate(String vaccinedate) {
        this.vaccinedate = vaccinedate;
    }

    public String getVaccinenotes() {
        return vaccinenotes;
    }

    public void setVaccinenotes(String vaccinenotes) {
        this.vaccinenotes = vaccinenotes;
    }

}
