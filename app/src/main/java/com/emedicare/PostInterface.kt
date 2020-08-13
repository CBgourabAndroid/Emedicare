package com.emedicare

import android.content.Context
import android.net.ConnectivityManager
import com.emedicare.docmodel.DoctorResponce
import com.emedicare.doctors.modelclass.DocListModel
import com.emedicare.responceModel.*
import com.emedicare.responceModel.accedt.AccEditResponce
import com.emedicare.responceModel.appointment.MyAppointmentResponce
import com.emedicare.responceModel.classes.ClassesResponce
import com.emedicare.responceModel.clnote.CNoteResponce
import com.emedicare.responceModel.family.FamilyResponce
import com.emedicare.responceModel.insurance.InsuranceResponce
import com.emedicare.responceModel.mReport.MreportResponce
import com.emedicare.responceModel.medicines.MedicinesResponce
import com.emedicare.responceModel.mvac.MVACResponce
import com.emedicare.responceModel.mvs.MVSResponce
import com.emedicare.responceModel.nationality.NatinalityResponce
import com.emedicare.responceModel.policy.PolicyResponce
import com.emedicare.responceModel.userChat.UserChatResponce
import retrofit2.Call;
import okhttp3.MultipartBody
import retrofit2.http.*
import okhttp3.RequestBody
import retrofit2.http.POST
import retrofit2.http.Multipart
import retrofit2.http.GET
import java.text.ParseException
import java.text.SimpleDateFormat
import retrofit2.http.FormUrlEncoded
import java.util.*


interface PostInterface {





    companion object {
        const val BaseURL = "http://emedicaresystem.com/emedicare/api/"
        const val BaseURLPaytm = "http://semicolonites.tech/zoomcar/paytm/"

        const val MID="Rental63139873548739"
        const val ORDER_ID="Order123"
        const val CUST_ID="XYZ123"
        const val CHANNEL_ID="Wap"
        const val TXN_AMOUNT="20.00"
        const val WEBSITE="APPSTAGING"
        const val CALLBACK_URL="https://pguat.paytm.com/paytmchecksum/paytmCallback.jsp"
        const val INDUSTRY_TYPE_ID="Retail"

        fun isConnected(context: Context): Boolean {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork = cm.activeNetworkInfo
            return activeNetwork != null && activeNetwork.isConnectedOrConnecting
        }

    }



    @FormUrlEncoded
    @POST("login_v3.php")
    fun loginCall( @Field("emailid") emailid: String,
                   @Field("userpassword") userpassword: String): Call<LoginResponce>




    @FormUrlEncoded
    @POST("registration_v3.php")
    fun signUpCall(
        @Field("emailid") emailid: String,
        @Field("mobilenumber") mobilenumber: String,
        @Field("userpassword") userpassword: String,
        @Field("username") username: String,
        @Field("familyname")familyname:String,
        @Field("dob")dob:String,
        @Field("gender") gender: String
    ): Call<SignUpResponce>

    @GET("forgetpassword_v3.php")
    fun forgotPass( @Query("emailid") emailid: String): Call<SignUpResponce>

    @FormUrlEncoded
    @POST("loginauth_v2.php")
    fun getOTP( @Field("otp") otp: String): Call<AuthResponce>


    @GET("speciality_v4.php")
    fun getSpeciality(): Call<SpecialityResponce>

    @FormUrlEncoded
    @POST("physician_speciality_v4.php")
    fun GetSpecialitycode(
        @Field("specialitycode") specialitycode: String

    ): Call<DoctorResponce>


    @FormUrlEncoded
    @POST("appointmentschedule_v3.php")
    fun GetAppointmentschedule(
        @Field("physiciancode") physiciancode: String,
        @Field("patientid") patientid: String


    ): Call<DateSlotResponce>

    @FormUrlEncoded
    @POST("appointmentbooking_v3.php")
    fun GetAppointmentbooking(
        @Field("physiciancode") physiciancode: String,
        @Field("slotnumber") slotnumber: String,
        @Field("slottype") slottype: String,
        @Field("slotdate") slotdate: String,
        @Field("slottime") slottime: String,
        @Field("patientid") patientid: String

    ): Call<OTPResponce>




    @FormUrlEncoded
    @POST("appointments_v1.php")
    fun Getappointments(
        @Field("patientid") patientid: String

    ): Call<MyAppointmentResponce>

    @FormUrlEncoded
    @POST("logout.php")
    fun GetLogout(
        @Field("emailid") emailid: String

    ): Call<OTPResponce>


    @FormUrlEncoded
    @POST("schedulelist.php")
    fun Getschedulelist(
        @Field("physiciancode") physiciancode: String

    ): Call<DocListModel>


    @FormUrlEncoded
    @POST("accountedit.php")
    fun Getaccountedit(
        @Field("patientid") patientid: String

    ): Call<AccEditResponce>


    @GET("nationality.php")
    fun getNatinality(): Call<NatinalityResponce>

    @GET("insurance.php")
    fun getInsurance(): Call<InsuranceResponce>


    @GET("policy_v1.php")
    fun getPolicy(@Query("insurancecode") insurancecode: String): Call<PolicyResponce>

    @GET("classes_v1.php")
    fun getClasses(@Query("insurancecode") insurancecode: String,
                   @Query("policycode") policycode: String): Call<ClassesResponce>

    @FormUrlEncoded
    @POST("accountupdate.php")
    fun Getaccountupdate(
        @Field("patientid") patientid: String,
        @Field("patientname") patientname: String,
        @Field("familyname") familyname: String,
        @Field("altname") altname: String,
        @Field("altfamilyname") altfamilyname: String,
        @Field("dob") dob: String,
        @Field("gender") gender: String,
        @Field("nationalitycode") nationalitycode: String,
        @Field("insurancecode") insurancecode: String,
        @Field("policycode") policycode: String,
        @Field("classcode") classcode: String

    ): Call<SignUpResponce>


    @FormUrlEncoded
    @POST("medicalreports_v1.php")
    fun GetMedicalReport(
        @Field("patientid") patientid: String

    ): Call<MreportResponce>

    @FormUrlEncoded
    @POST("familylist.php")
    fun GetFamilyList(
        @Field("eamildId") eamildId: String,
        @Field("mobilenumber") mobilenumber: String

    ): Call<FamilyResponce>


    @FormUrlEncoded
    @POST("vitalnotes_v1.php")
    fun GetVitalSign(
        @Field("patientid") patientid: String

    ): Call<MVSResponce>

    @FormUrlEncoded
    @POST("vaccine_v1.php")
    fun Getvaccine(
        @Field("patientid") patientid: String

    ): Call<MVACResponce>


    @FormUrlEncoded
    @POST("clinicalnotes_v1.php")
    fun Getclinicalnotes(
        @Field("patientid") patientid: String

    ): Call<CNoteResponce>


    @FormUrlEncoded
    @POST("medicines_v1.php")
    fun Getmedicines(
        @Field("patientid") patientid: String

    ): Call<MedicinesResponce>



    @FormUrlEncoded
    @POST("chat.php")
    fun GetChat(
        @Field("patientid") patientid: String,
        @Field("physiciancode") physiciancode: String

    ): Call<UserChatResponce>

    @FormUrlEncoded
    @POST("sendchat.php")
    fun SendChat(
        @Field("patientid") patientid: String,
        @Field("physiciancode") physiciancode: String,
        @Field("message") message:String

    ): Call<UserChatResponce>





/*


physiciancode=1
slotnumber=3
slottype=MORNING
slotdate=2020-04-23
slottime=2020-04-23 00:00:00
patientid=1

    @FormUrlEncoded
    @POST("password_recovery")
    fun forgotCall(
        @Field("email") email: String

    ): Call<ForgotResponce>



    @Multipart
    @POST("verification_file_upload")
    fun uploadImage(@Part("userfile") file1: RequestBody,
                    @Part("user_id") user_id: String,
                    @Part("userfile2") file2:RequestBody
    ): Call<ImageResponce>


    @Multipart
    @POST("verification_file_upload")
    fun uploadFile(
        @Part file1: MultipartBody.Part,
        @Part file2: MultipartBody.Part,
        @Part("user_id") user_id: String
    ): Call<ValidationResponce>

    @Multipart
    @POST("retro_upload")
    fun uploadMulFile(
        @Part file1: MultipartBody.Part
       // @Part file2: MultipartBody.Part,
       // @Part("user_id") user_id: String
    ): Call<RetroTest>


    @GET("get_all_cabs")
    fun doGetListResources(): Call<SearchResponce>


  //  @GET("get_all_cabs_new")
   // fun GetListResources(): Call<CablistResponce>


    @FormUrlEncoded
    @POST("get_all_cabs_new")
    fun GetListResources(
        @Field("device_start_location")locationID: String
    ): Call<CablistResponce>



    @FormUrlEncoded
    @POST("booking")
    fun bookingCall(
        @Field("user_id") userid: String,
        @Field("bike_id") bid: String,
        @Field("package_id") pid: String,
        @Field("hour") hours: String,
        @Field("payment_amount") pAmount: String,
        @Field("start_date") sdate: String,
        @Field("end_date") edate: String,
        @Field("start_position") sloc: String,
        @Field("end_position") eloc: String,
        @Field("payment_status") pStatus: String,
        @Field("payment_mode") pMode: String,
        @Field("ride_status") rStatus: String,
        @Field("start_location") startloc: String,
        @Field("end_location") endloc: String

    ): Call<BookingResponce>

    @FormUrlEncoded
    @POST("booking_history")
    fun getBookingHistory(
        @Field("user_id")userid: String
    ): Call<BookingHistoryResponce>


    @FormUrlEncoded
    @POST("change_password")
    fun getChangePass(
        @Field("user_id")user_id: String,
        @Field("old_password")oldpass: String,
        @Field("new_password")newpass: String
    ): Call<SignupResponce>


    @FormUrlEncoded
    @POST("checkuser")
    fun getApproveStatus(
        @Field("user_id")user_id: String
    ): Call<ApproveResponce>






    @FormUrlEncoded
    @POST("insert_checklist")
    fun getCheckList(
        @Field("user_id")user_id: String,
        @Field("booking_id")booking_id: String,
        @Field("bike_id")bike_id: String,
        @Field("odometer_reading")odometer_reading: String,
        @Field("headlight")headlight: String,
        @Field("instrument_console")instrument_console: String,
        @Field("self_start")self_start: String,
        @Field("number_plate")number_plate: String,
        @Field("wiser")wiser: String,
        @Field("mud_guard")mud_guard: String,
        @Field("other_damage")other_damage: String,

        @Field("left_panel")left_panel: String,
        @Field("left_indicator_front")left_indicator_front: String,
        @Field("left_indicator_back")left_indicator_back: String,
        @Field("left_rear_view_mirror")left_rear_view_mirror: String,
        @Field("damage_lrft_side")damage_lrft_side: String,

        @Field("right_panel")right_panel: String,
        @Field("right_indicator_front")right_indicator_front: String,
        @Field("right_indicator_back")right_indicator_back: String,
        @Field("right_rear_view_mirror")right_rear_view_mirror: String,
        @Field("damage_right_side")damage_right_side: String,

        @Field("tail_light")tail_light: String,
        @Field("mud_guard_back")mud_guard_back: String,
        @Field("number_plate_back")number_plate_back: String,
        @Field("backside_damage")backside_damage: String,
        @Field("other_bike_issues")other_bike_issues: String
    ): Call<SignupResponce>




    @GET("get_all_pickup_and_drop_location")
    fun getAllLocation(): Call<LocationResponce>
*/






}