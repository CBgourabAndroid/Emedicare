
package com.emedicare.responceModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


    public class Record {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("specialitycode")
        @Expose
        private String specialitycode;
        @SerializedName("specialityname")
        @Expose
        private String specialityname;
        @SerializedName("altname")
        @Expose
        private String altname;
        @SerializedName("specialityimage")
        @Expose
        private Object specialityimage;
        @SerializedName("status")
        @Expose
        private String status;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getSpecialitycode() {
            return specialitycode;
        }

        public void setSpecialitycode(String specialitycode) {
            this.specialitycode = specialitycode;
        }

        public String getSpecialityname() {
            return specialityname;
        }

        public void setSpecialityname(String specialityname) {
            this.specialityname = specialityname;
        }

        public String getAltname() {
            return altname;
        }

        public void setAltname(String altname) {
            this.altname = altname;
        }

        public Object getSpecialityimage() {
            return specialityimage;
        }

        public void setSpecialityimage(Object specialityimage) {
            this.specialityimage = specialityimage;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

}
