
package com.emedicare.responceModel.mvs;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Record {

    @SerializedName("timestamp")
    @Expose
    private String timestamp;
    @SerializedName("bphigh")
    @Expose
    private String bphigh;
    @SerializedName("bpdiastolic")
    @Expose
    private String bpdiastolic;
    @SerializedName("pulse")
    @Expose
    private String pulse;
    @SerializedName("temperature")
    @Expose
    private String temperature;
    @SerializedName("heartrate")
    @Expose
    private String heartrate;
    @SerializedName("weight")
    @Expose
    private String weight;
    @SerializedName("bmi")
    @Expose
    private String bmi;

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getBphigh() {
        return bphigh;
    }

    public void setBphigh(String bphigh) {
        this.bphigh = bphigh;
    }

    public String getBpdiastolic() {
        return bpdiastolic;
    }

    public void setBpdiastolic(String bpdiastolic) {
        this.bpdiastolic = bpdiastolic;
    }

    public String getPulse() {
        return pulse;
    }

    public void setPulse(String pulse) {
        this.pulse = pulse;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getHeartrate() {
        return heartrate;
    }

    public void setHeartrate(String heartrate) {
        this.heartrate = heartrate;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getBmi() {
        return bmi;
    }

    public void setBmi(String bmi) {
        this.bmi = bmi;
    }

}
