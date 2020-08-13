package com.emedicare.activity

import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.emedicare.MainActivity
import com.emedicare.PostInterface
import com.emedicare.R
import com.emedicare.adapter.FamilyAdapter
import com.emedicare.adapter.MReportAdapter
import com.emedicare.adapter.ReportDetailsAdapter
import com.emedicare.responceModel.family.FamilyResponce
import com.emedicare.responceModel.mReport.Detail
import com.emedicare.responceModel.mReport.MreportResponce
import com.emedicare.responceModel.mReport.Record
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.medical_report_activity.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import kotlin.collections.ArrayList

class MedicalReportActivity : MainActivity() {

    lateinit var progressDialog : ProgressDialog

    lateinit var recyclerViewReport: RecyclerView
    lateinit var reportAdapter:ReportDetailsAdapter

    lateinit var noData: TextView
    var PatientID=""
    lateinit var no_family_member:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        layoutInflater.inflate(R.layout.medical_report_activity, container)

        titleTv.text="Reports"

        progressDialog= ProgressDialog(this)
        recyclerViewReport = findViewById(R.id.report_rcy_view)
        noData=findViewById(R.id.noDatatxt)
        no_family_member=findViewById(R.id.noFamilyMember)

        PatientID=psh.id

        no_family_member.setOnClickListener {

            if(!PostInterface.isConnected(this@MedicalReportActivity)){

                Toast.makeText(this@MedicalReportActivity, getString(R.string.no_internet), Toast.LENGTH_SHORT).show()
            }
            else{

                progressDialog.setMessage("Please wait ...")
                progressDialog.setCancelable(false)
                progressDialog.show()
                callFamilyList()

            }
        }

        if(!PostInterface.isConnected(this@MedicalReportActivity)){

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




        userAPI.GetMedicalReport(PatientID).enqueue(object :
            Callback<MreportResponce> {
            override fun onResponse(call: Call<MreportResponce>, response: Response<MreportResponce>) {
                println("onResponse")
                System.out.println(response.body()!!.toString())

                if (response?.body() != null) {
                    progressDialog.dismiss()

                    if (response.body()!!.records!=null&&response.body()!!.records.size>0){
                        recyclerViewReport.visibility=View.VISIBLE
                        noData.visibility=View.GONE
                        setData(response.body()!!.records)

                    }
                    else{
                        recyclerViewReport.visibility=View.GONE
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

            override fun onFailure(call: Call<MreportResponce>, t: Throwable) {
                println("onFailure")
                println(t.fillInStackTrace())
                progressDialog.dismiss()
            }
        })


    }

    private fun setData(records: List<Record>) {

        var spinnerAdapter: MReportAdapter = MReportAdapter(this, records)
        var spinner: Spinner = findViewById(R.id.spinner) as Spinner
        spinner?.adapter = spinnerAdapter
        spinner?.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {

                //NationalityCode=records[position].nationalitycode

                reportAdapter = ReportDetailsAdapter(this@MedicalReportActivity)
                recyclerViewReport.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL ,false)
                recyclerViewReport.adapter = reportAdapter
                reportAdapter.setDataListItems(this@MedicalReportActivity,
                    records[position].details as ArrayList<Detail>
                )

            } // to close the onItemSelected

        })


    }

    private fun callFamilyList() {

        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(PostInterface.BaseURL)
            .build()

        val userAPI = retrofit.create(PostInterface::class.java!!)




        userAPI.GetFamilyList(psh.email,psh.getphone()).enqueue(object :
            Callback<FamilyResponce> {
            override fun onResponse(call: Call<FamilyResponce>, response: Response<FamilyResponce>) {
                println("onResponse")
                System.out.println(response.body()!!.toString())

                if (response?.body() != null) {
                    progressDialog.dismiss()

                    if (response.body()!!.records!=null&&response.body()!!.records.size>0){

                        no_family_member.visibility=View.GONE

                        setDataFamily(response.body()!!.records)

                    }
                    else{
                        no_family_member.visibility=View.VISIBLE
                        no_family_member.setText(psh.fullName)
                        PatientID=psh.id

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

            override fun onFailure(call: Call<FamilyResponce>, t: Throwable) {
                println("onFailure")
                println(t.fillInStackTrace())
                progressDialog.dismiss()
            }
        })


    }

    private fun setDataFamily(records: List<com.emedicare.responceModel.family.Record>) {

        var spinnerAdapter: FamilyAdapter = FamilyAdapter(this, records)
        var spinner: Spinner = findViewById(R.id.spinnerfamily) as Spinner
        spinner?.adapter = spinnerAdapter
        spinner?.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {

                PatientID=records[position].patientid
                if(!PostInterface.isConnected(this@MedicalReportActivity)){

                    Toast.makeText(applicationContext, getString(R.string.no_internet), Toast.LENGTH_SHORT).show()
                }
                else{

                    progressDialog.setMessage("Please wait ...")
                    progressDialog.setCancelable(false)
                    progressDialog.show()
                    callDataApi()

                }


            } // to close the onItemSelected

        })

        if (!PatientID.equals(null)){
            for (i in records.indices){

                if (PatientID.equals(psh.id)){
                    spinner.setSelection(i)
                }
            }

        }


    }

}