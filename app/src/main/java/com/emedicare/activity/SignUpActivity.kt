package com.activity

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import java.util.regex.Matcher
import java.util.regex.Pattern
import android.R.attr.password
import android.app.DatePickerDialog
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.widget.*
import com.bruce.pickerview.popwindow.DatePickerPopWin
import com.emedicare.MainActivity
import com.emedicare.PostInterface
import com.emedicare.R
import com.emedicare.activity.ForgotPassword
import com.emedicare.responceModel.LoginResponce
import com.emedicare.responceModel.SignUpResponce
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.register_activity.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*


class SignUpActivity : AppCompatActivity(), View.OnClickListener{


    lateinit var loginlay: LinearLayout
    lateinit var edt_fname:TextInputEditText
    lateinit var edt_lname : TextInputEditText
    lateinit var edt_number : TextInputEditText
    lateinit var edt_email :TextInputEditText
    lateinit var edt_pass : TextInputEditText
    lateinit var swchi_Gender:Switch
    lateinit var signUp : TextView

    var NameStr: String?=""
    var EmailStr: String?=""
    var NumberStr: String?=""
    var PasswordStr: String?=""
    var GenderStr: String?="male"
    var DOB:String?=""
    var NameSTR2:String?=""

    private val EMAIL_PATTERN =
        "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"
    var pattern: Pattern ?= null
    var matcher: Matcher ?= null

    lateinit var progressDialog: ProgressDialog

    var calendar = Calendar.getInstance()
    var year = calendar.get(Calendar.YEAR)
    var month = calendar.get(Calendar.MONTH)
    var dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)
    var hour = calendar.get(Calendar.HOUR_OF_DAY)
    var minute = calendar.get(Calendar.MINUTE)
    lateinit var datePickerDialog: DatePickerDialog





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= 21) {
            val window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = resources.getColor(R.color.colorPrimaryDark)
        }
        setContentView(R.layout.register_activity)

        progressDialog= ProgressDialog(this)


        inti()
    }

    private fun inti() {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

         loginlay=findViewById(R.id.login_lay)
         edt_email=findViewById(R.id.email_id)
         edt_fname=findViewById(R.id.firstName)
         edt_lname=findViewById(R.id.lastName)
         edt_number=findViewById(R.id.mobileNumber)
         edt_pass=findViewById(R.id.etPassword)
         signUp=findViewById(R.id.signuptxt)
        swchi_Gender=findViewById(R.id.switch1)

        // Set an checked change listener for switch button
        swchi_Gender.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                // The switch is enabled/checked
                swchi_Gender.text = "Male"
                GenderStr="male"


            } else {
                // The switch is disabled
                swchi_Gender.text = "Female"
                GenderStr="female"


            }
        }


        loginlay.setOnClickListener(this)
         signUp.setOnClickListener(this)

        txtdob.setOnClickListener {

           // dateFun()

            opendate()
        }

    }

    private fun opendate() {

        val cal=Calendar.getInstance()
        val cYear=cal.get(Calendar.YEAR)

        val pickerPopWin =
            DatePickerPopWin.Builder(this@SignUpActivity,
                DatePickerPopWin.OnDatePickedListener { year, month, day, dateDesc ->
                    Toast.makeText(
                        this@SignUpActivity,
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
        pickerPopWin.showPopWin(this@SignUpActivity)
    }

    private fun dateFun() {



        datePickerDialog = DatePickerDialog(this,
            DatePickerDialog.OnDateSetListener { datePicker, fyear, fmonth, fday ->


                val mont=fmonth+1
                var mt=""
                var dy=""

                if (mont<10){
                    mt="0"+mont.toString()
                }
                else{
                    mt=mont.toString()
                }

                if (fday<10){

                    dy="0"+fday.toString()
                }
                else{

                    dy=fday.toString()
                }


               // val currentDateStart=dy+"-"+mt+"-"+fyear.toString()

                val dobstr=fyear.toString()+"-"+mt+"-"+dy

                txtdob.setText(dobstr)
                DOB=dobstr




            },
            year,
            month,
            dayOfMonth
        )
        datePickerDialog.datePicker.maxDate = System.currentTimeMillis()
        datePickerDialog.show()


    }


    override fun onClick(p0: View?) {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

        when(p0?.id){

            R.id.login_lay->{

                startActivity(Intent(this@SignUpActivity, LoginActivity::class.java))

                //validation()
            }
            R.id.signuptxt->{
                validation()
            }
        }
    }

    private fun validation() {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

        NameStr = edt_fname.getText().toString()
        NameSTR2=edt_lname.text.toString()
        EmailStr = edt_email.getText().toString()
        NumberStr = edt_number.getText().toString()
        PasswordStr = edt_pass.getText().toString()


        var cancel = false
        var message = ""
        var focusView: View? = null
        var tempCond = false




        if (TextUtils.isEmpty(PasswordStr)) {
            message = "Please Enter Your Password"
            focusView = edt_pass
            cancel = true
            tempCond = false
        }


        if (PasswordStr!!.length < 6) {
            Toast.makeText(applicationContext, "Type minimum 6 characters password", Toast.LENGTH_LONG).show()

        }


        if (TextUtils.isEmpty(NumberStr)) {
            message = "Enter Phone number ."
            focusView = edt_number
            cancel = true
            tempCond = false
        }


        if (TextUtils.isEmpty(EmailStr!!)) {
            message = "Please enter your email address."
            focusView = edt_email
            cancel = true
            tempCond = false
        } else if (!isValidEmail(EmailStr!!)) {
            message = "Invalid email id."
            focusView = edt_email
            cancel = true
            tempCond = false
        }



        if (TextUtils.isEmpty(NameStr)) {
            message = "Please enter first name."
            focusView = edt_fname
            cancel = true
            tempCond = false
        }

        if (TextUtils.isEmpty(NameSTR2)) {
            message = "Please enter family name."
            focusView = edt_lname
            cancel = true
            tempCond = false
        }

        if (TextUtils.isEmpty(GenderStr)) {
            message = "Please select the gender."
            focusView = swchi_Gender
            cancel = true
            tempCond = false
        }

        if (TextUtils.isEmpty(DOB)) {
            message = "Please select DOB."
            focusView = txtdob
            cancel = true
            tempCond = false
        }


        if (cancel) {
            // focusView.requestFocus();
            if (!tempCond) {
                focusView!!.requestFocus()
            }
            showToastLong(message)
        } else {
            val imm = this
                .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

            if(!PostInterface.isConnected(this@SignUpActivity)){

                Toast.makeText(this, getString(R.string.no_internet), Toast.LENGTH_SHORT).show()
            }
            else{

                progressDialog.setMessage("Please wait ...")
                progressDialog.setCancelable(false)
                progressDialog.show()
                callSignUp()

            }


        }




    }

    override fun onBackPressed() {
        finish()
    }




    private fun callSignUp() {
        // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.


        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(PostInterface.BaseURL)
            .build()

        val userAPI = retrofit.create(PostInterface::class.java!!)

        userAPI.signUpCall(EmailStr!!,NumberStr!!,PasswordStr!!,NameStr!!,NameSTR2!!,DOB!!,GenderStr!!).enqueue(object : Callback<SignUpResponce> {
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
                        //    val editor = prefs.edit()
                        //    editor.putBoolean(getString(R.string.shared_loggedin_status), true)

                        //    editor.commit()

                          val i1 = Intent(this@SignUpActivity, LoginActivity::class.java)
                          startActivity(i1)




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
   private fun showToastLong(message: String) {

       Toast.makeText(applicationContext,message,Toast.LENGTH_SHORT).show()

   }


    fun isValidEmail(target: CharSequence): Boolean {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }

}