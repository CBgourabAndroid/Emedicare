
package com.emedicare.responceModel.userChat;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Record {

    @SerializedName("chatnumber")
    @Expose
    private String chatnumber;
    @SerializedName("patientid")
    @Expose
    private String patientid;
    @SerializedName("physiciancode")
    @Expose
    private String physiciancode;
    @SerializedName("messagebody")
    @Expose
    private String messagebody;
    @SerializedName("attachment")
    @Expose
    private String attachment;
    @SerializedName("messagesender")
    @Expose
    private String messagesender;
    @SerializedName("messagereceiver")
    @Expose
    private String messagereceiver;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("time")
    @Expose
    private String time;

    public String getChatnumber() {
        return chatnumber;
    }

    public void setChatnumber(String chatnumber) {
        this.chatnumber = chatnumber;
    }

    public String getPatientid() {
        return patientid;
    }

    public void setPatientid(String patientid) {
        this.patientid = patientid;
    }

    public String getPhysiciancode() {
        return physiciancode;
    }

    public void setPhysiciancode(String physiciancode) {
        this.physiciancode = physiciancode;
    }

    public String getMessagebody() {
        return messagebody;
    }

    public void setMessagebody(String messagebody) {
        this.messagebody = messagebody;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public String getMessagesender() {
        return messagesender;
    }

    public void setMessagesender(String messagesender) {
        this.messagesender = messagesender;
    }

    public String getMessagereceiver() {
        return messagereceiver;
    }

    public void setMessagereceiver(String messagereceiver) {
        this.messagereceiver = messagereceiver;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

}
