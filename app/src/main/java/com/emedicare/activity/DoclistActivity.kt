package com.emedicare.activity

import android.app.ProgressDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.emedicare.PostInterface
import com.emedicare.R
import com.emedicare.adapter.MainCatAdapter
import com.emedicare.docmodel.DoctorAdapter
import com.emedicare.docmodel.DoctorResponce
import com.emedicare.docmodel.Record
import com.emedicare.responceModel.SpecialityResponce
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.ArrayList

class DoclistActivity :AppCompatActivity() {

    lateinit var recyclerViewCategory: RecyclerView
    lateinit var allAdapter: DoctorAdapter
    lateinit var dataList : ArrayList<Record>
    lateinit var titleText: TextView
    lateinit var progressDialog : ProgressDialog
    lateinit var toolbar:Toolbar
    lateinit var noData: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= 21) {
            val window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = resources.getColor(R.color.colorPrimaryDark)
        }
        setContentView(R.layout.doclist_activity)
        toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        getSupportActionBar()!!.setDisplayHomeAsUpEnabled(true)
        getSupportActionBar()!!.setDisplayShowHomeEnabled(true)



        toolbar.setNavigationOnClickListener {

            finish()
        }
        noData=findViewById(R.id.noDatatxt)
        progressDialog= ProgressDialog(this)
        recyclerViewCategory = findViewById(R.id.category_rcy_view)

        if(!PostInterface.isConnected(this@DoclistActivity)){

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




        userAPI.GetSpecialitycode(intent.getStringExtra("id")).enqueue(object :
            Callback<DoctorResponce> {
            override fun onResponse(call: Call<DoctorResponce>, response: Response<DoctorResponce>) {
                println("onResponse")
                System.out.println(response.body()!!.toString())

                if (response?.body() != null) {
                    progressDialog.dismiss()

                    if (response.body()!!.records!=null&&response.body()!!.records.size>0){
                      /*  */
                        recyclerViewCategory.visibility= View.VISIBLE
                        noData.visibility= View.GONE
                        allAdapter = DoctorAdapter(this@DoclistActivity)
                        recyclerViewCategory.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL ,false)
                        recyclerViewCategory.adapter = allAdapter
                        allAdapter.setDataListItems(this@DoclistActivity,
                            response.body()!!.records as ArrayList<Record>
                        )
                    }
                    else{
                        recyclerViewCategory.visibility=View.GONE
                        noData.visibility=View.VISIBLE
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

            override fun onFailure(call: Call<DoctorResponce>, t: Throwable) {
                println("onFailure")
                println(t.fillInStackTrace())
                progressDialog.dismiss()
            }
        })


    }
}