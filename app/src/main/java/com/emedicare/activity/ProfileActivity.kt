package com.emedicare.activity

import android.app.ProgressDialog
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.bruce.pickerview.popwindow.DatePickerPopWin
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.emedicare.MainActivity
import com.emedicare.PostInterface
import com.emedicare.R
import com.emedicare.adapter.ClassesAdapter
import com.emedicare.adapter.InsuranceAdapter
import com.emedicare.adapter.NationalityAdapter
import com.emedicare.adapter.PolicyAdapter
import com.emedicare.responceModel.SignUpResponce
import com.emedicare.responceModel.accedt.AccEditResponce
import com.emedicare.responceModel.classes.ClassesResponce
import com.emedicare.responceModel.insurance.InsuranceResponce
import com.emedicare.responceModel.nationality.NatinalityResponce
import com.emedicare.responceModel.nationality.Record
import com.emedicare.responceModel.policy.PolicyResponce
import com.emedicare.shared.UserShared
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.book_appointment_main.doc_image
import kotlinx.android.synthetic.main.contain_profile.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.profile_activity.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class ProfileActivity : MainActivity() {

    var languages = arrayOf("Java", "PHP", "Kotlin", "Javascript", "Python", "Swift")

    lateinit var progressDialog : ProgressDialog
    var PatientName=""
    var PatientFamilyName=""
    var AltName=""
    var AltFamilyName=""
    var DOB=""
    var Gender=""
    var NationalityCode=""
    var InsuranceCode=""
    var PolicyCode=""
    var ClassCode=""

    var NCode=""
    var ICode=""
    var PCode=""
    var CCode=""

    lateinit var pName:TextInputEditText
    lateinit var pfamilyName:TextInputEditText
    lateinit var altName:TextInputEditText
    lateinit var altFamilyName:TextInputEditText
    lateinit var saveBtn:TextView
    lateinit var toolbar:Toolbar
    lateinit var prefs: SharedPreferences

    lateinit var nationality_txt:TextView
    lateinit var policy_txt:TextView
    lateinit var insurence_txt:TextView
    lateinit var class_txt:TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        layoutInflater.inflate(R.layout.profile_activity, container)

        titleTv.text="Profile"
        prefs = getSharedPreferences("MY_SHARED_PREF", Context.MODE_PRIVATE)
        progressDialog= ProgressDialog(this)
        psh=UserShared(this)
        initi()
        Glide.with(this).load(R.drawable.s4)
            .apply(RequestOptions().circleCrop())
            //.apply(RequestOptions().override(100,100))
            .into(doc_image)
        callAllData()




        userName.text=psh.fullName
        mailID.text=psh.email


       // callNatinalityApi()
       // callInsuranceApi()
       // callPolicyApi()
       // callClassesApi()

        nationality_txt=findViewById(R.id.nationalityTxt)
        policy_txt=findViewById(R.id.policyTXT)
        insurence_txt=findViewById(R.id.insuranceTXT)
        class_txt=findViewById(R.id.classTXT)

        clickTocall()


    }

    private fun clickTocall() {

        nationality_txt.setOnClickListener {

            callNatinalityApi()
        }
        policy_txt.setOnClickListener {

            if (InsuranceCode.equals("")){

                Toast.makeText(this,"Please Provide Insurance Details First!!",Toast.LENGTH_SHORT).show()
            }
            else{
                callPolicyApi()
            }


        }
        insurence_txt.setOnClickListener {


            callInsuranceApi()
        }
        class_txt.setOnClickListener {

            if (InsuranceCode.equals("")||PolicyCode.equals("")){

                Toast.makeText(this,"Please Provide Insurance & Policy Details First!!",Toast.LENGTH_SHORT).show()
            }
            else{
                callClassesApi()
            }


        }

    }

    private fun initi() {

        val yourRadioGroup: RadioGroup =
            findViewById<View>(R.id.rdGroup) as RadioGroup
        yourRadioGroup.setOnCheckedChangeListener(object : RadioGroup.OnCheckedChangeListener {
            override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
                when (checkedId) {
                    R.id.radioButton -> {
                        Gender="male"
                    }
                    R.id.radioButton2 -> {
                        Gender="female"
                    }

                }
            }
        })
        pName=findViewById(R.id.firstName)
        pfamilyName=findViewById(R.id.familyName)
        altName=findViewById(R.id.altfirstName)
        altFamilyName=findViewById(R.id.altfamilyName)
        saveBtn=findViewById(R.id.saveData)

        txtdob.setOnClickListener {

            // dateFun()

            opendate()
        }
        saveBtn.setOnClickListener {

            callUpdateApi()
        }
    }

    private fun callUpdateApi() {
        if(!PostInterface.isConnected(this@ProfileActivity)){

            Toast.makeText(this, getString(R.string.no_internet), Toast.LENGTH_SHORT).show()
        }
        else{

            progressDialog.setMessage("Please wait ...")
            progressDialog.setCancelable(false)
            progressDialog.show()
            callApi()

        }
    }

    private fun callApi() {

        PatientName=pName.text.toString()
        PatientFamilyName=pfamilyName.text.toString()
        AltName=altName.text.toString()
        AltFamilyName=altFamilyName.text.toString()



        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(PostInterface.BaseURL)
            .build()

        val userAPI = retrofit.create(PostInterface::class.java!!)

        userAPI.Getaccountupdate(psh.id,PatientName,PatientFamilyName,AltName,AltFamilyName,DOB,Gender,NationalityCode
        ,InsuranceCode,PolicyCode,ClassCode).enqueue(object : Callback<SignUpResponce> {
            override fun onResponse(call: Call<SignUpResponce>, response: Response<SignUpResponce>) {
                println("onResponse")
                System.out.println(response.toString())

                if (response.code()==200){
                    progressDialog.dismiss()

                    val statusCode = response.code()
                    val avv = response.body()!!.response
                    Log.i("onSuccess", avv.toString());
                    if (avv.equals("ok")) {
                        Toast.makeText(applicationContext, response.body()!!.message, Toast.LENGTH_SHORT).show()

                        if (InsuranceCode.equals("1")){

                            val editor = prefs.edit()
                            editor.putString(getString(R.string.shared_user_insurancecode),"1")
                            editor.commit()
                        }







                    } else {
                        Toast.makeText(applicationContext, response.body()!!.message, Toast.LENGTH_SHORT).show()
                        progressDialog.dismiss()
                        Log.i("onEmptyResponse", "Returned empty response");//Toast.makeText(getContext(),"Nothing returned",Toast.LENGTH_LONG).show();
                    }

                }
                else{
                    progressDialog.dismiss()
                    Toast.makeText(applicationContext, "Fail!!", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<SignUpResponce>, t: Throwable) {
                println("onFailure")
                println(t.fillInStackTrace())
                progressDialog.dismiss()
            }
        })
    }

    private fun opendate() {

        val cal= Calendar.getInstance()
        val cYear=cal.get(Calendar.YEAR)

        val pickerPopWin =
            DatePickerPopWin.Builder(this@ProfileActivity,
                DatePickerPopWin.OnDatePickedListener { year, month, day, dateDesc ->
                    Toast.makeText(
                        this@ProfileActivity,
                        dateDesc,
                        Toast.LENGTH_SHORT
                    ).show()
                    txtdob.setText(dateDesc)
                    DOB=dateDesc

                }).textConfirm("CONFIRM") //text of confirm button
                .textCancel("CANCEL") //text of cancel button
                .btnTextSize(16) // button text size
                .viewTextSize(25) // pick view text size
                .colorCancel(Color.parseColor("#999999")) //color of cancel button
                .colorConfirm(Color.parseColor("#009900")) //color of confirm button
                .minYear(1890) //min year in loop
                .maxYear(cYear) // max year in loop
                .showDayMonthYear(true)
                //.dateChose("2013-11-11") // date chose when init popwindow
                .build()
        pickerPopWin.showPopWin(this@ProfileActivity)
    }

    private fun callAllData() {

        progressDialog.setMessage("Please wait ...")
        progressDialog.setCancelable(false)
        progressDialog.show()


        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(PostInterface.BaseURL)
            .build()

        val userAPI = retrofit.create(PostInterface::class.java!!)

        userAPI.Getaccountedit(psh.id).enqueue(object :
            Callback<AccEditResponce> {
            override fun onResponse(call: Call<AccEditResponce>, response: Response<AccEditResponce>) {
                println("onResponse")
                System.out.println(response.body()!!.toString())

                if (response?.body() != null) {
                    progressDialog.dismiss()

                    if (response.body()!!.username!=null&&!response.body()!!.username.equals("")){
                        firstName.setText(response.body()!!.username)
                        PatientName=response.body()!!.username
                    }
                    else{
                        firstName.setText("First Name")
                    }

                    if(response.body()!!.familyname!=null&&!response.body()!!.familyname.equals("")){
                        familyName.setText(response.body()!!.familyname)
                        PatientFamilyName=response.body()!!.familyname
                    }
                    else{
                        familyName.setText("Family Name")
                    }

                    if(response.body()!!.altname!=null&&!response.body()!!.altname.equals("")){
                        altName.setText(response.body()!!.altname)
                        AltName=response.body()!!.altname
                    }
                    else{
                        altName.setText("Alt First Name")
                    }

                    if(response.body()!!.altfamilyname!=null&&!response.body()!!.altfamilyname.equals("")){
                        altfamilyName.setText(response.body()!!.altfamilyname)
                        AltFamilyName=response.body()!!.altfamilyname

                    }
                    else{
                        altfamilyName.setText("Alt Family Name")

                    }

                    if(response.body()!!.gender!=null&&!response.body()!!.gender.equals("")){

                        Gender=response.body()!!.gender
                        if (response.body()!!.gender.equals("male")){
                            radioButton.isChecked=true
                        }
                        else if (response.body()!!.gender.equals("female")){
                            radioButton2.isChecked=true
                        }
                        else{
                            radioButton.isChecked=false
                            radioButton2.isChecked=false
                        }

                    }
                    else{

                        Gender=""

                    }







                    if (response.body()!!.dob!=null&&!response.body()!!.dob.equals("")){
                        txtdob.setText(response.body()!!.dob)
                        DOB=response.body()!!.dob


                    }
                    else{
                        txtdob.setText("Select")
                    }

                    if (response.body()!!.nationality.nationalitycode!=null&&!response.body()!!.nationality.nationalitycode.equals("")&&
                        response.body()!!.insurance.insurancecode!=null&&!response.body()!!.insurance.insurancecode.equals("")&&
                        response.body()!!.policy.policycode!=null&&!response.body()!!.policy.policycode.equals("")&&
                        response.body()!!.class_.classcode!=null&&!response.body()!!.class_.classcode.equals("")){

                        NCode=response.body()!!.nationality.nationalitycode
                        ICode=response.body()!!.insurance.insurancecode
                        PCode=response.body()!!.policy.policycode
                        CCode=response.body()!!.class_.classcode
                        NationalityCode=NCode
                        InsuranceCode=ICode
                        PolicyCode=PCode
                        ClassCode=CCode

                        nationality_txt.text=response.body()!!.nationality.nationality
                        insurence_txt.text=response.body()!!.insurance.insurancename
                        policy_txt.text=response.body()!!.policy.policyname
                        class_txt.text=response.body()!!.class_.classcode
                    }
                    else{

                        nationality_txt.text="Select"
                        insurence_txt.text="Select"
                        policy_txt.text="Select"
                        class_txt.text="Select"


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

            override fun onFailure(call: Call<AccEditResponce>, t: Throwable) {
                println("onFailure")
                println(t.fillInStackTrace())
                progressDialog.dismiss()
            }
        })

    }

    private fun callDataApii() {


    }

    private fun callClassesApi() {

        progressDialog.setMessage("Please wait ...")
        progressDialog.setCancelable(false)
        progressDialog.show()

        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(PostInterface.BaseURL)
            .build()

        val userAPI = retrofit.create(PostInterface::class.java!!)




        userAPI.getClasses(InsuranceCode,PolicyCode).enqueue(object :
            Callback<ClassesResponce> {
            override fun onResponse(call: Call<ClassesResponce>, response: Response<ClassesResponce>) {
                println("onResponse")
                System.out.println(response.body()!!.toString())

                if (response?.body() != null) {
                    progressDialog.dismiss()

                    if (response.body()!!.records!=null){
                        progressDialog.dismiss()
                        class_txt.visibility=View.GONE
                        setClasses(response.body()!!.records)
                    }
                    else{
                        Toast.makeText(applicationContext, "No Data Found!!", Toast.LENGTH_SHORT).show()
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

            override fun onFailure(call: Call<ClassesResponce>, t: Throwable) {
                println("onFailure")
                println(t.fillInStackTrace())
                progressDialog.dismiss()
            }
        })

    }



    private fun callPolicyApi() {

        progressDialog.setMessage("Please wait ...")
        progressDialog.setCancelable(false)
        progressDialog.show()
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(PostInterface.BaseURL)
            .build()

        val userAPI = retrofit.create(PostInterface::class.java!!)




        userAPI.getPolicy(InsuranceCode).enqueue(object :
            Callback<PolicyResponce> {
            override fun onResponse(call: Call<PolicyResponce>, response: Response<PolicyResponce>) {
                println("onResponse")
                System.out.println(response.body()!!.toString())

                if (response?.body() != null) {
                    progressDialog.dismiss()

                    if (response.body()!!.records!=null){
                        progressDialog.dismiss()
                        policy_txt.visibility=View.GONE
                        setPolicys(response.body()!!.records)
                    }
                    else{
                        Toast.makeText(applicationContext, "No Data Found!!", Toast.LENGTH_SHORT).show()
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

            override fun onFailure(call: Call<PolicyResponce>, t: Throwable) {
                println("onFailure")
                println(t.fillInStackTrace())
                progressDialog.dismiss()
            }
        })

    }



    private fun callInsuranceApi() {

        progressDialog.setMessage("Please wait ...")
        progressDialog.setCancelable(false)
        progressDialog.show()
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(PostInterface.BaseURL)
            .build()

        val userAPI = retrofit.create(PostInterface::class.java!!)




        userAPI.getInsurance().enqueue(object :
            Callback<InsuranceResponce> {
            override fun onResponse(call: Call<InsuranceResponce>, response: Response<InsuranceResponce>) {
                println("onResponse")
                System.out.println(response.body()!!.toString())

                if (response?.body() != null) {
                    progressDialog.dismiss()

                    if (response.body()!!.records!=null){
                        progressDialog.dismiss()
                        insurence_txt.visibility=View.GONE
                        setInsurance(response.body()!!.records)
                    }
                    else{
                        Toast.makeText(applicationContext, "No Data Found!!", Toast.LENGTH_SHORT).show()
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

            override fun onFailure(call: Call<InsuranceResponce>, t: Throwable) {
                println("onFailure")
                println(t.fillInStackTrace())
                progressDialog.dismiss()
            }
        })
    }



    private fun callNatinalityApi() {

        progressDialog.setMessage("Please wait ...")
        progressDialog.setCancelable(false)
        progressDialog.show()

        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(PostInterface.BaseURL)
            .build()

        val userAPI = retrofit.create(PostInterface::class.java!!)




        userAPI.getNatinality().enqueue(object :
            Callback<NatinalityResponce> {
            override fun onResponse(call: Call<NatinalityResponce>, response: Response<NatinalityResponce>) {
                println("onResponse")
                System.out.println(response.body()!!.toString())

                if (response?.body() != null) {
                    progressDialog.dismiss()

                    if (response.body()!!.records!=null){
                        progressDialog.dismiss()
                        nationality_txt.visibility=View.GONE
                        setNationality(response.body()!!.records)
                    }
                    else{
                        Toast.makeText(applicationContext, "No Data Found!!", Toast.LENGTH_SHORT).show()
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

            override fun onFailure(call: Call<NatinalityResponce>, t: Throwable) {
                println("onFailure")
                println(t.fillInStackTrace())
                progressDialog.dismiss()
            }
        })


    }

    private fun setNationality(records: List<Record>) {

        var spinnerAdapter: NationalityAdapter = NationalityAdapter(this, records)
        var spinner: Spinner = findViewById(R.id.spinner_nationality) as Spinner
        spinner.visibility=View.VISIBLE
        spinner?.adapter = spinnerAdapter
        spinner?.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {

                NationalityCode=records[position].nationalitycode


            } // to close the onItemSelected

         })

        if (!NCode.equals(null)){
            for (i in records.indices){

                if (NCode.equals(records[i].nationalitycode)){
                    spinner_nationality.setSelection(i)
                }
            }

        }





    }

    private fun setInsurance(records: List<com.emedicare.responceModel.insurance.Record>) {
        var spinnerAdapter: InsuranceAdapter = InsuranceAdapter(this, records)
        var spinner: Spinner = findViewById(R.id.spinner_insurance) as Spinner
        spinner.visibility=View.VISIBLE
        spinner?.adapter = spinnerAdapter
        spinner?.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {

                InsuranceCode=records[position].insurancecode
                callPolicyApi()

            } // to close the onItemSelected

        })

        if (!ICode.equals(null)){
            for (i in records.indices){

                if (ICode.equals(records[i].insurancecode)){
                    spinner_insurance.setSelection(i)
                }
            }

        }
    }

    private fun setPolicys(records: List<com.emedicare.responceModel.policy.Record>) {
        var spinnerAdapter: PolicyAdapter = PolicyAdapter(this, records)
        var spinner: Spinner = findViewById(R.id.spinner_Policy) as Spinner
        spinner.visibility=View.VISIBLE
        spinner?.adapter = spinnerAdapter
        spinner?.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {

                PolicyCode=records[position].policycode
                callClassesApi()

            } // to close the onItemSelected

        })


        if (!PCode.equals(null)){
            for (i in records.indices){

                if (PCode.equals(records[i].policycode)){
                    spinner_Policy.setSelection(i)
                }
            }

        }
    }
    private fun setClasses(records: List<com.emedicare.responceModel.classes.Record>) {
        var spinnerAdapter: ClassesAdapter = ClassesAdapter(this, records)
        var spinner: Spinner = findViewById(R.id.spinner_Class) as Spinner
        spinner.visibility=View.VISIBLE
        spinner?.adapter = spinnerAdapter
        spinner?.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {

                ClassCode=records[position].classcode

            } // to close the onItemSelected

        })


        if (!CCode.equals(null)){
            for (i in records.indices){

                if (CCode.equals(records[i].classcode)){
                    spinner_Class.setSelection(i)
                }
            }

        }
    }

    /*override fun onNothingSelected(parent: AdapterView<*>?) {
        showToast(message = "Nothing selected")
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

        when (view?.id) {
            R.id.spinner_nationality -> showToast(message = position.toString())

        }
    }*/

    private fun showToast(context: Context = applicationContext, message: String, duration: Int = Toast.LENGTH_LONG) {
        Toast.makeText(context, message, duration).show()
    }


}