package com.emedicare.activity

import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.emedicare.MainActivity
import com.emedicare.PostInterface
import com.emedicare.R
import com.emedicare.adapter.*
import com.emedicare.responceModel.clnote.CNoteResponce
import com.emedicare.responceModel.family.FamilyResponce
import com.emedicare.responceModel.mReport.Detail
import com.emedicare.responceModel.mReport.MreportResponce
import com.emedicare.responceModel.mReport.Record
import com.emedicare.responceModel.medicines.MedicinesResponce
import com.emedicare.responceModel.mvac.MVACResponce
import com.emedicare.responceModel.mvs.MVSResponce
import com.emedicare.shared.UserShared
import kotlinx.android.synthetic.main.content_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MedicalDataActivity: MainActivity() {

    lateinit var progressDialog : ProgressDialog

    lateinit var recyclerViewReport: RecyclerView
    lateinit var mvsAdapter: MVitalSignAdapter
    lateinit var mediAdapter: MedicinesAdapter
    lateinit var vacAdapter: VaccineAdapter
    lateinit var clnAdapter: ClinicalNoteAdapter
    lateinit var noData:TextView

    var languages = arrayOf("Medicine", "Vaccine", "Clinical Note", "Vital Sign")
    var PatientID=""
    lateinit var no_family_member:TextView
    var selectedLanguage="";

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        layoutInflater.inflate(R.layout.medical_report_activity, container)

        titleTv.text="Medical Data"

        progressDialog= ProgressDialog(this)
        recyclerViewReport = findViewById(R.id.report_rcy_view)
        noData=findViewById(R.id.noDatatxt)

        no_family_member=findViewById(R.id.noFamilyMember)

        PatientID=psh.id

        no_family_member.setOnClickListener {

            if(!PostInterface.isConnected(this@MedicalDataActivity)){

                Toast.makeText(this@MedicalDataActivity, getString(R.string.no_internet), Toast.LENGTH_SHORT).show()
            }
            else{

                progressDialog.setMessage("Please wait ...")
                progressDialog.setCancelable(false)
                progressDialog.show()
                callFamilyList()

            }



        }



        val spinner = findViewById<Spinner>(R.id.spinner)
        if (spinner != null) {
            val adapter = ArrayAdapter(this,
                android.R.layout.simple_spinner_item, languages)
            spinner.adapter = adapter

            spinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>,
                                            view: View, position: Int, id: Long) {
                  //  Toast.makeText(this@MedicalDataActivity,
                  //      languages[position], Toast.LENGTH_SHORT).show()

                    selectedLanguage=languages[position].toString()


                    if (languages[position].equals("Vital Sign")){

                        if(!PostInterface.isConnected(this@MedicalDataActivity)){

                            Toast.makeText(this@MedicalDataActivity, getString(R.string.no_internet), Toast.LENGTH_SHORT).show()
                        }
                        else{

                            progressDialog.setMessage("Please wait ...")
                            progressDialog.setCancelable(false)
                            progressDialog.show()
                            callvitalSign()

                        }

                    }
                    else if (languages[position].equals("Vaccine")){
                        if(!PostInterface.isConnected(this@MedicalDataActivity)){

                            Toast.makeText(this@MedicalDataActivity, getString(R.string.no_internet), Toast.LENGTH_SHORT).show()
                        }
                        else{

                            progressDialog.setMessage("Please wait ...")
                            progressDialog.setCancelable(false)
                            progressDialog.show()
                            callVaccine()

                        }

                    }
                    else if (languages[position].equals("Clinical Note")){

                        if(!PostInterface.isConnected(this@MedicalDataActivity)){

                            Toast.makeText(this@MedicalDataActivity, getString(R.string.no_internet), Toast.LENGTH_SHORT).show()
                        }
                        else{

                            progressDialog.setMessage("Please wait ...")
                            progressDialog.setCancelable(false)
                            progressDialog.show()
                            callClinicalNote()

                        }

                    }
                    else if (languages[position].equals("Medicine")){

                        if(!PostInterface.isConnected(this@MedicalDataActivity)){

                            Toast.makeText(this@MedicalDataActivity, getString(R.string.no_internet), Toast.LENGTH_SHORT).show()
                        }
                        else{

                            progressDialog.setMessage("Please wait ...")
                            progressDialog.setCancelable(false)
                            progressDialog.show()
                            callMedicine()

                        }

                    }


                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }
        }

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

    private fun callMedicine() {
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(PostInterface.BaseURL)
            .build()

        val userAPI = retrofit.create(PostInterface::class.java!!)




        userAPI.Getmedicines(PatientID).enqueue(object :
            Callback<MedicinesResponce> {
            override fun onResponse(call: Call<MedicinesResponce>, response: Response<MedicinesResponce>) {
                println("onResponse")
              //  System.out.println(response.body()!!.toString())


                if (response?.body() != null) {
                    progressDialog.dismiss()

                    if (response.body()!!.records!=null&&response.body()!!.records.size>0){
                        recyclerViewReport.visibility=View.VISIBLE
                        noData.visibility=View.GONE
                        setMedicines(response.body()!!.records)

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

            override fun onFailure(call: Call<MedicinesResponce>, t: Throwable) {
                println("onFailure")
                println(t.fillInStackTrace())
                progressDialog.dismiss()
            }
        })
    }

    private fun callClinicalNote() {

        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(PostInterface.BaseURL)
            .build()

        val userAPI = retrofit.create(PostInterface::class.java!!)




        userAPI.Getclinicalnotes(PatientID).enqueue(object :
            Callback<CNoteResponce> {
            override fun onResponse(call: Call<CNoteResponce>, response: Response<CNoteResponce>) {
                println("onResponse")
                System.out.println(response.body()!!.toString())

                if (response?.body() != null) {
                    progressDialog.dismiss()

                    if (response.body()!!.records!=null&&response.body()!!.records.size>0){

                        recyclerViewReport.visibility=View.VISIBLE
                        noData.visibility=View.GONE
                        setCNote(response.body()!!.records)

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

            override fun onFailure(call: Call<CNoteResponce>, t: Throwable) {
                println("onFailure")
                println(t.fillInStackTrace())
                progressDialog.dismiss()
            }
        })
    }

    private fun callVaccine() {

        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(PostInterface.BaseURL)
            .build()

        val userAPI = retrofit.create(PostInterface::class.java!!)




        userAPI.Getvaccine(PatientID).enqueue(object :
            Callback<MVACResponce> {
            override fun onResponse(call: Call<MVACResponce>, response: Response<MVACResponce>) {
                println("onResponse")
                System.out.println(response.body()!!.toString())

                if (response?.body() != null) {
                    progressDialog.dismiss()

                    if (response.body()!!.records!=null&&response.body()!!.records.size>0){

                        recyclerViewReport.visibility=View.VISIBLE
                        noData.visibility=View.GONE
                        setMvac(response.body()!!.records)

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

            override fun onFailure(call: Call<MVACResponce>, t: Throwable) {
                println("onFailure")
                println(t.fillInStackTrace())
                progressDialog.dismiss()
            }
        })
    }



    private fun callvitalSign() {


        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(PostInterface.BaseURL)
            .build()

        val userAPI = retrofit.create(PostInterface::class.java!!)




        userAPI.GetVitalSign(PatientID).enqueue(object :
            Callback<MVSResponce> {
            override fun onResponse(call: Call<MVSResponce>, response: Response<MVSResponce>) {
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

            override fun onFailure(call: Call<MVSResponce>, t: Throwable) {
                println("onFailure")
                println(t.fillInStackTrace())
                progressDialog.dismiss()
            }
        })

    }

    private fun setData(records: List<com.emedicare.responceModel.mvs.Record>) {

        mvsAdapter = MVitalSignAdapter(this@MedicalDataActivity)
        recyclerViewReport.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL ,false)
        recyclerViewReport.adapter = mvsAdapter
        mvsAdapter.setDataListItems(this@MedicalDataActivity,
            records as ArrayList<com.emedicare.responceModel.mvs.Record>
        )
    }

    private fun setMvac(records: List<com.emedicare.responceModel.mvac.Record>) {

        vacAdapter = VaccineAdapter(this@MedicalDataActivity)
        recyclerViewReport.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL ,false)
        recyclerViewReport.adapter = vacAdapter
        vacAdapter.setDataListItems(this@MedicalDataActivity,
            records as ArrayList<com.emedicare.responceModel.mvac.Record>
        )


    }

    private fun setMedicines(records: List<com.emedicare.responceModel.medicines.Record>) {


        mediAdapter = MedicinesAdapter(this@MedicalDataActivity)
        recyclerViewReport.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL ,false)
        recyclerViewReport.adapter = mediAdapter
        mediAdapter.setDataListItems(this@MedicalDataActivity,
            records as ArrayList<com.emedicare.responceModel.medicines.Record>
        )

    }

    private fun setCNote(records: List<com.emedicare.responceModel.clnote.Record>) {

        clnAdapter = ClinicalNoteAdapter(this@MedicalDataActivity)
        recyclerViewReport.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL ,false)
        recyclerViewReport.adapter = clnAdapter
        clnAdapter.setDataListItems(this@MedicalDataActivity,
            records as ArrayList<com.emedicare.responceModel.clnote.Record>
        )

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

                if(!PostInterface.isConnected(this@MedicalDataActivity)){

                    Toast.makeText(applicationContext, getString(R.string.no_internet), Toast.LENGTH_SHORT).show()
                }
                else{

                    progressDialog.setMessage("Please wait ...")
                    progressDialog.setCancelable(false)
                    progressDialog.show()

                    if (selectedLanguage.equals("Vital Sign")){

                        if(!PostInterface.isConnected(this@MedicalDataActivity)){

                            Toast.makeText(this@MedicalDataActivity, getString(R.string.no_internet), Toast.LENGTH_SHORT).show()
                        }
                        else{

                            progressDialog.setMessage("Please wait ...")
                            progressDialog.setCancelable(false)
                            progressDialog.show()
                            callvitalSign()

                        }

                    }
                    else if (selectedLanguage.equals("Vaccine")){
                        if(!PostInterface.isConnected(this@MedicalDataActivity)){

                            Toast.makeText(this@MedicalDataActivity, getString(R.string.no_internet), Toast.LENGTH_SHORT).show()
                        }
                        else{

                            progressDialog.setMessage("Please wait ...")
                            progressDialog.setCancelable(false)
                            progressDialog.show()
                            callVaccine()

                        }

                    }
                    else if (selectedLanguage.equals("Clinical Note")){

                        if(!PostInterface.isConnected(this@MedicalDataActivity)){

                            Toast.makeText(this@MedicalDataActivity, getString(R.string.no_internet), Toast.LENGTH_SHORT).show()
                        }
                        else{

                            progressDialog.setMessage("Please wait ...")
                            progressDialog.setCancelable(false)
                            progressDialog.show()
                            callClinicalNote()

                        }

                    }
                    else if (selectedLanguage.equals("Medicine")){

                        if(!PostInterface.isConnected(this@MedicalDataActivity)){

                            Toast.makeText(this@MedicalDataActivity, getString(R.string.no_internet), Toast.LENGTH_SHORT).show()
                        }
                        else{

                            progressDialog.setMessage("Please wait ...")
                            progressDialog.setCancelable(false)
                            progressDialog.show()
                            callMedicine()

                        }

                    }


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