package com.emedicare.activity

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.emedicare.PostInterface
import com.emedicare.R
import com.emedicare.adapter.FamilyAdapter
import com.emedicare.responceModel.Date
import com.emedicare.responceModel.DateSlotResponce
import com.emedicare.responceModel.OTPResponce
import com.emedicare.responceModel.Slot
import com.emedicare.responceModel.family.FamilyResponce
import com.emedicare.responceModel.family.Record
import com.emedicare.responceModel.mReport.MreportResponce
import com.emedicare.shared.UserShared
import kotlinx.android.synthetic.main.book_appointment_main.*
import kotlinx.android.synthetic.main.contain_book_appointment.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import kotlin.collections.ArrayList

class BookAppointment: AppCompatActivity() {

    lateinit var radioGroupDate: RadioGroup
    lateinit var rbnDate: RadioButton
    /*internal var colorStateList = ColorStateList(
        arrayOf(

            intArrayOf(-R.attr.state_enabled), //disabled
            intArrayOf(R.attr.state_enabled) //enabled
        ),
        intArrayOf(

            Color.WHITE //disabled
            , Color.GRAY //enabled
        )
    )*/

    val colorList = ColorStateList(
        arrayOf(
            intArrayOf(-android.R.attr.state_enabled),  // Disabled
            intArrayOf(android.R.attr.state_enabled)    // Enabled
        ),
        intArrayOf(
            Color.WHITE,     // The color for the Disabled state
            Color.GRAY        // The color for the Enabled state
        )
    )
    lateinit var radioGroupSlot: RadioGroup
    lateinit var rbnSlot: RadioButton
    //lateinit var txtAccountNumber: TextView
    lateinit var progressDialog : ProgressDialog
    var dataList:ArrayList<Date>?=null
    lateinit var bookAppointment:TextView
    var SlotDate:String?=""
    var Slotnumber:String?=""
    var Slottype:String?=""
    var Slottime:String?=""
    var SlotStatus:String?=""
    lateinit var psh:UserShared
    lateinit var nodata:TextView
    lateinit var nestedData:NestedScrollView
    var PatientID=""
    lateinit var no_family_member:TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= 21) {
            val window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = resources.getColor(R.color.colorPrimaryDark)
        }
        setContentView(R.layout.book_appointment_main)
        psh= UserShared(this)


        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        getSupportActionBar()!!.setDisplayHomeAsUpEnabled(true)
        getSupportActionBar()!!.setDisplayShowHomeEnabled(true)



        toolbar.setNavigationOnClickListener {

            onBackPressed()
        }


        docNAME.setText(intent.getStringExtra("docname"))
        docSpl.setText(intent.getStringExtra("docspl"))
        Glide.with(this@BookAppointment).load(intent.getStringExtra("docImg"))
            .apply(RequestOptions().circleCrop())
            //.apply(RequestOptions().override(100,100))
            .into(doc_image)

        nestedData=findViewById(R.id.nestedData)
        nodata=findViewById(R.id.noDaTa)
        no_family_member=findViewById(R.id.noFamilyMember)

        radioGroupDate = findViewById(R.id.radiogroup_bank)
        radioGroupSlot = findViewById(R.id.radiogroup_mode)



        //txtAccountNumber=findViewById(R.id.tvAccNumber)

       // addBankButtons(radioGroupDate)
      //  addModeButtons(radioGroupSlot)

        progressDialog= ProgressDialog(this)

        if(!PostInterface.isConnected(this@BookAppointment)){

            Toast.makeText(this, getString(R.string.no_internet), Toast.LENGTH_SHORT).show()
        }
        else{

            progressDialog.setMessage("Please wait ...")
            progressDialog.setCancelable(false)
            progressDialog.show()
            callDataApi()

        }

        bookAppointment=findViewById(R.id.bookApt)
        bookAppointment.setOnClickListener {


            if (!SlotDate.equals("")&&!Slotnumber.equals("")){

                if (SlotStatus.equals("ACTIVE")){
                    progressDialog.setMessage("Please wait ...")
                    progressDialog.setCancelable(false)
                    progressDialog.show()
                    callBookApi()
                }
                else{
                    Toast.makeText(applicationContext, "Slot Already Booked", Toast.LENGTH_SHORT).show()
                }
            }

        }


    }

    private fun callBookApi() {

        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(PostInterface.BaseURL)
            .build()

        val userAPI = retrofit.create(PostInterface::class.java!!)




        userAPI.GetAppointmentbooking(intent.getStringExtra("phyCode"),
            Slotnumber!!,Slottype!!,SlotDate!!,Slottime!!,PatientID).enqueue(object :
            Callback<OTPResponce> {
            override fun onResponse(call: Call<OTPResponce>, response: Response<OTPResponce>) {
                println("onResponse")
                System.out.println(response.body()!!.toString())

                if (response?.body() != null) {
                    progressDialog.dismiss()
                    val statusCode = response.code()
                    val avv = response.body()!!.response
                    Log.i("onSuccess", avv.toString());
                    if (avv.equals("ok")){

                       // val dialog = SBPopupDialog()
                       // dialog.showIn(this@BookAppointment)

                        showStatus()
                    }
                    else{
                        Toast.makeText(applicationContext, avv, Toast.LENGTH_SHORT).show()
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

            override fun onFailure(call: Call<OTPResponce>, t: Throwable) {
                println("onFailure")
                println(t.fillInStackTrace())
                progressDialog.dismiss()
            }
        })


    }



    private fun callDataApi() {

        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(PostInterface.BaseURL)
            .build()

        val userAPI = retrofit.create(PostInterface::class.java!!)




        userAPI.GetAppointmentschedule(intent.getStringExtra("phyCode"),psh.id).enqueue(object :
            Callback<DateSlotResponce> {
            override fun onResponse(call: Call<DateSlotResponce>, response: Response<DateSlotResponce>) {
                println("onResponse")
                System.out.println(response.body()!!.toString())

                if (response?.body() != null) {
                    progressDialog.dismiss()


                    if (response.body()!!.dates!=null&&response.body()!!.status.equals("Y")){

                        callFamilyList()

                        nestedData.visibility=View.VISIBLE
                        nodata.visibility=View.GONE

                        emailidS.text=response.body()!!.emailid
                        consultation_fee.text="Consultation Fee : "+response.body()!!.consultationfee+" "+response.body()!!.currencycode
                        deduction_amt.text="Deduction : "+response.body()!!.deductableamount+"%"
                        vat_amt.text="VAT : "+response.body()!!.vatamount
                        total_amt.text="Total To Pay : "+response.body()!!.totaltopay+" "+response.body()!!.currencycode



                        
                        addDateButtons(radioGroupDate,response.body()!!.dates)
                    }
                    else{
                        nestedData.visibility=View.GONE
                        nodata.visibility=View.VISIBLE
                       // finish()
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

            override fun onFailure(call: Call<DateSlotResponce>, t: Throwable) {
                println("onFailure")
                println(t.fillInStackTrace())
                progressDialog.dismiss()
            }
        })


    }




    @SuppressLint("NewApi")
    private fun addDateButtons(
        radioGroup: RadioGroup,
        dates: MutableList<Date>
    ) {
        Collections.reverse(dates)


        for (i in dates.indices) {


            rbnSlot = RadioButton(this)

            rbnSlot.id = i
            rbnSlot.setText(dates[i].slotdate)
            rbnSlot.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18f)
            rbnSlot.setTextColor(ContextCompat.getColorStateList(this, R.color.text_selector))
            rbnSlot.buttonTintList = colorList
            rbnSlot.setButtonDrawable(null);

            rbnSlot.setBackgroundResource(R.drawable.buttonbackground)
            rbnSlot.setPadding(20, 10, 20, 10)
            val params =
                RadioGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )

            params.setMargins(10, 0, 0, 0)
            rbnSlot.layoutParams = params
            radioGroup!!.addView(rbnSlot)

        }


        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            val checkedRadioButtonId = radioGroup.checkedRadioButtonId
            val PID = checkedRadioButtonId.toString()



            addModeButtons(radioGroupSlot, dates[checkedId].slots)


            /* Toast.makeText(this,
                 "Radio button clicked " + dates[checkedId].slotdate,
                 Toast.LENGTH_SHORT).show();*/

            SlotDate=dates[checkedId].slotdate
            /*Toast.makeText(this,
                dates[checkedId].slotdate,
                Toast.LENGTH_SHORT).show();*/

            // Snackbar.make(group,"Estimated Price Rs : ₹"+EAmount+"\n"+PID+"   "+CHECKBIKEID,
            //    Snackbar.LENGTH_LONG).setAction("Action", null).show()
        }


    }


    @SuppressLint("NewApi")
    private fun addModeButtons(
        radioGroupModes: RadioGroup?,
        slots: MutableList<Slot>
    ) {
        radioGroupModes!!.removeAllViews()


        for (i in slots!!.indices) {

            rbnDate = RadioButton(this)
            rbnDate.id = i
            rbnDate.setText(slots[i].slotnumber)
            rbnDate.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18f)
            rbnDate.setTextColor(ContextCompat.getColorStateList(this, R.color.text_selector))
            rbnDate.buttonTintList = colorList
            rbnDate.setButtonDrawable(null);
            if (slots[i].status.equals("ACTIVE")){
                rbnDate.setBackgroundResource(R.drawable.buttonbackground)
            }
            else{
                rbnDate.setBackgroundResource(R.drawable.btn2)
            }

            rbnDate.setPadding(30, 20, 30, 20)
            val params =
                RadioGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )

            params.setMargins(10, 0, 0, 0)
            rbnDate.layoutParams = params
            radioGroupModes!!.addView(rbnDate)

        }


        radioGroupModes.setOnCheckedChangeListener { group, checkedId ->
            val checkedRadioButtonId = radioGroupModes.checkedRadioButtonId
            val PID = checkedRadioButtonId.toString()





            /* Toast.makeText(this,
                 "Radio button clicked " + dates[checkedId].slotdate,
                 Toast.LENGTH_SHORT).show();*/

            Slotnumber=slots[checkedId].slotnumber
            Slottype=slots[checkedId].slottype
            Slottime=slots[checkedId].slottime
            SlotStatus=slots[checkedId].status

           /* Toast.makeText(this,
                slots[checkedId].slotnumber+"   "+slots[checkedId].slottype,
                Toast.LENGTH_SHORT).show();*/

            // Snackbar.make(group,"Estimated Price Rs : ₹"+EAmount+"\n"+PID+"   "+CHECKBIKEID,
            //    Snackbar.LENGTH_LONG).setAction("Action", null).show()


        }


    }

    private fun showStatus() {

        val viewGroup =findViewById<ViewGroup>(android.R.id.content)
        val dialogView = layoutInflater.inflate(R.layout.my_dialog,viewGroup,false)
        val dialogBuilder = AlertDialog.Builder(this)
        dialogBuilder.setView(dialogView)


        val homeClick=dialogView.findViewById<TextView>(R.id.homeClick)
        val continueClick=dialogView.findViewById<TextView>(R.id.continueClick)
        val alert = dialogBuilder.create()
        alert.show()

        homeClick.setOnClickListener {

              alert.dismiss()
              startActivity(Intent(this@BookAppointment, HomeActivity::class.java))
              finish()
        }

        continueClick.setOnClickListener {

            alert.dismiss()
            finish()

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

                        setData(response.body()!!.records)

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


    private fun setData(records: List<Record>) {

        var spinnerAdapter: FamilyAdapter = FamilyAdapter(this, records)
        var spinner: Spinner = findViewById(R.id.spinner) as Spinner
        spinner?.adapter = spinnerAdapter
        spinner?.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {

                PatientID=records[position].patientid


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