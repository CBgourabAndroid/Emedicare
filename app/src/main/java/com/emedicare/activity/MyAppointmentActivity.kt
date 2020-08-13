package com.emedicare.activity

import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.emedicare.MainActivity
import com.emedicare.PostInterface
import com.emedicare.R
import com.emedicare.adapter.AppointmentAdapter
import com.emedicare.adapter.MainCatAdapter
import com.emedicare.docmodel.DoctorAdapter
import com.emedicare.docmodel.DoctorResponce
import com.emedicare.responceModel.appointment.MyAppointmentResponce
import com.emedicare.shared.UserShared
import kotlinx.android.synthetic.main.content_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.ArrayList
import com.emedicare.responceModel.appointment.Record

class MyAppointmentActivity :MainActivity(){

    lateinit var recyclerViewCategory: RecyclerView
    lateinit var appointmentAdapter: AppointmentAdapter
    lateinit var progressDialog : ProgressDialog
    lateinit var pshs:UserShared

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        layoutInflater.inflate(R.layout.my_appointment_activity, container)

        titleTv.text="Appointments"
        img_refresh.visibility=View.VISIBLE
        pshs= UserShared(this)
        progressDialog= ProgressDialog(this)
        recyclerViewCategory=findViewById(R.id.category_rcy_view)

       /* appointmentAdapter = AppointmentAdapter(this@MyAppointmentActivity)
        recyclerViewCategory.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL ,false)
        recyclerViewCategory.adapter = appointmentAdapter*/

        if(!PostInterface.isConnected(this@MyAppointmentActivity)){

            Toast.makeText(this, getString(R.string.no_internet), Toast.LENGTH_SHORT).show()
        }
        else{

            progressDialog.setMessage("Please wait ...")
            progressDialog.setCancelable(false)
            progressDialog.show()
            callDataApi()

        }


        img_refresh.setOnClickListener {
            if(!PostInterface.isConnected(this@MyAppointmentActivity)){

                Toast.makeText(this, getString(R.string.no_internet), Toast.LENGTH_SHORT).show()
            }
            else{

                progressDialog.setMessage("Please wait ...")
                progressDialog.setCancelable(false)
                progressDialog.show()
                callDataApi()

            }

        }
    }

    private fun callDataApi() {

        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(PostInterface.BaseURL)
            .build()

        val userAPI = retrofit.create(PostInterface::class.java!!)




        userAPI.Getappointments(psh.id).enqueue(object :
            Callback<MyAppointmentResponce> {
            override fun onResponse(call: Call<MyAppointmentResponce>, response: Response<MyAppointmentResponce>) {
                println("onResponse")
                System.out.println(response.body()!!.toString())

                if (response?.body() != null) {
                    progressDialog.dismiss()

                    if (response.body()!!.records!=null){

                        appointmentAdapter = AppointmentAdapter(this@MyAppointmentActivity)
                        recyclerViewCategory.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL ,false)
                        recyclerViewCategory.adapter = appointmentAdapter
                        appointmentAdapter.setDataListItems(this@MyAppointmentActivity,
                            response.body()!!.records as ArrayList<Record>
                        )

                     /*   allAdapter = DoctorAdapter(this@DoclistActivity)
                        recyclerViewCategory.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL ,false)
                        recyclerViewCategory.adapter = allAdapter
                        allAdapter.setDataListItems(this@DoclistActivity,
                            response.body()!!.records as ArrayList<Record>
                        )*/
                    }
                    else{
                        Toast.makeText(applicationContext, "No Data Found!!", Toast.LENGTH_SHORT).show()
                        finish()
                    }






                } else {
                    progressDialog.dismiss()
                    Log.i(
                        "onEmptyResponse",
                        "Returned empty response"
                    );//Toast.makeText(getContext(),"Nothing returned",Toast.LENGTH_LONG).show();
                    Toast.makeText(applicationContext, "No Data", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<MyAppointmentResponce>, t: Throwable) {
                println("onFailure")
                println(t.fillInStackTrace())
                progressDialog.dismiss()
            }
        })


    }


}