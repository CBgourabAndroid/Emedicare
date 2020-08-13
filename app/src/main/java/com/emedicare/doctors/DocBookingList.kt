package com.emedicare.doctors

import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.emedicare.MainActivity
import com.emedicare.PostInterface
import com.emedicare.R
import com.emedicare.adapter.MainCatAdapter
import com.emedicare.docmodel.DoctorAdapter
import com.emedicare.doctors.modelclass.DocListModel
import com.emedicare.doctors.modelclass.Record
import com.emedicare.shared.UserShared
import kotlinx.android.synthetic.main.content_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.ArrayList

class DocBookingList : MainActivityDoctor() {

    lateinit var recyclerViewCategory: RecyclerView
    lateinit var categoryAdapter: DocBookingAdapter
    lateinit var progressDialog: ProgressDialog
    lateinit var pshs:UserShared



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        layoutInflater.inflate(R.layout.doc_bookinglist_activity, container)

        titleTv.text="Today's Appointment"
        progressDialog= ProgressDialog(this)
        pshs=UserShared(this)
        recyclerViewCategory = findViewById(R.id.category_view)


        if(!PostInterface.isConnected(this@DocBookingList)){

            Toast.makeText(this, getString(R.string.no_internet), Toast.LENGTH_SHORT).show()
        }
        else{

            progressDialog.setMessage("Please wait ...")
            progressDialog.setCancelable(false)
            progressDialog.show()
            callDataApi()

        }
    }


    private fun callDataApi() {

        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(PostInterface.BaseURL)
            .build()

        val userAPI = retrofit.create(PostInterface::class.java!!)


        val id=pshs.id


        userAPI.Getschedulelist(pshs.id).enqueue(object :
            Callback<DocListModel> {
            override fun onResponse(call: Call<DocListModel>, response: Response<DocListModel>) {
                println("onResponse")
                System.out.println(response.body()!!.toString())

                if (response?.body() != null) {
                    progressDialog.dismiss()

                    if (response.body()!!.records!=null&&response.body()!!.records.size>0){
                        categoryAdapter = DocBookingAdapter(this@DocBookingList)
                        recyclerViewCategory.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL ,false)
                        recyclerViewCategory.adapter = categoryAdapter
                        categoryAdapter.setDataListItems(this@DocBookingList,
                            response.body()!!.records as ArrayList<Record>
                        )
                    }
                    else{
                        Toast.makeText(applicationContext, "No Data Found!!", Toast.LENGTH_SHORT).show()
                        //finish()
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

            override fun onFailure(call: Call<DocListModel>, t: Throwable) {
                println("onFailure")
                println(t.fillInStackTrace())
                progressDialog.dismiss()
            }
        })


    }
}
