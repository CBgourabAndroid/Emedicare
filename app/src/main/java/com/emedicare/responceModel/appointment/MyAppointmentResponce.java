
package com.emedicare.responceModel.appointment;

import java.util.List;

import com.emedicare.responceModel.appointment.Record;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MyAppointmentResponce {

    @SerializedName("records")
    @Expose
    private List<Record> records = null;

    public List<Record> getRecords() {
        return records;
    }

    public void setRecords(List<Record> records) {
        this.records = records;
    }

}
