package com.activity

import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.emedicare.PostInterface
import com.emedicare.R
import com.emedicare.activity.BookAppointment
import com.emedicare.activity.ForgotPassword
import com.emedicare.activity.HomeActivity
import com.emedicare.doctors.DocBookingList
import com.emedicare.doctors.MainActivityDoctor
import com.emedicare.responceModel.AuthResponce
import com.emedicare.responceModel.LoginResponce
import com.emedicare.responceModel.OTPResponce
import com.google.android.material.textfield.TextInputEditText
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LoginActivity : AppCompatActivity() , View.OnClickListener{


    private lateinit var nextpage :TextView
    private lateinit var signUp: TextView

    lateinit var edt_email : EditText
    lateinit var edt_pass : EditText

    lateinit var EmailStr: String
    lateinit var PasswordStr: String
    lateinit var prefs: SharedPreferences

    lateinit var progressDialog : ProgressDialog

    lateinit var forgotPassword:TextView

    val RC_SIGN_IN: Int = 1


    lateinit var textView27:TextView
    lateinit var textView28:TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= 21) {
            val window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = resources.getColor(R.color.colorPrimaryDark)
        }
        setContentView(R.layout.login_activity)
        prefs = getSharedPreferences("MY_SHARED_PREF", Context.MODE_PRIVATE)

        progressDialog =ProgressDialog(this)





        intv()
     //   val dialog = BookAppointment.SBPopupDialog()
       // dialog.showIn(this@LoginActivity)




    }




    lateinit var EMAIL:String
    lateinit var USER_POSTS:String
    lateinit var AUTH_TYPE:String

    private fun intv() {

        nextpage=findViewById(R.id.logintxt) as TextView
        signUp=findViewById(R.id.signup_lay) as TextView

        edt_email=findViewById(R.id.email_id)
        edt_pass=findViewById(R.id.password)

        forgotPassword=findViewById(R.id.forgot_password)






        signUp.setOnClickListener(this)
        nextpage.setOnClickListener(this)
        forgotPassword.setOnClickListener(this)


    }


    override fun onClick(v: View?) {


        when(v?.id){

             R.id.signup_lay->{

                 startActivity(Intent(this@LoginActivity, SignUpActivity::class.java))

             }

            R.id.logintxt->{

                validation()

            }

            R.id.forgot_password->{

              //  checkV()

                startActivity(Intent(this@LoginActivity, ForgotPassword::class.java))
            }


        }
    }










    private fun validation() {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

        EmailStr = edt_email.getText().toString()
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








        if (TextUtils.isEmpty(EmailStr)) {
            message = "Please enter your email address."
            focusView = edt_email
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



            if(!PostInterface.isConnected(this@LoginActivity)){

                Toast.makeText(this, getString(R.string.no_internet), Toast.LENGTH_SHORT).show()
            }
            else{

                progressDialog.setMessage("Please wait ...")
                progressDialog.setCancelable(false)
                progressDialog.show()
                callSignIN(EmailStr,PasswordStr)
                val imm = applicationContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(edt_email.windowToken, 0)

            }


        }




    }




    private fun callSignIN(email: String?,password:String?) {
        // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.



        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(PostInterface.BaseURL)
            .build()

        val userAPI = retrofit.create(PostInterface::class.java!!)

        userAPI.loginCall(email!!,password!!).enqueue(object : Callback<LoginResponce> {
            override fun onResponse(call: Call<LoginResponce>, response: Response<LoginResponce>) {
                println("onResponse")
                System.out.println(response.toString())

                if (response.code()==200){
                    progressDialog.dismiss()

                    val statusCode = response.code()
                    val avv = response.body()!!.response
                    Log.i("onSuccess", avv.toString());
                    if (avv.equals("ok")) {

                       // Toast.makeText(applicationContext, "Login Success", Toast.LENGTH_SHORT).show()





                        showOTPVerify(email!!,password!!)

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

            override fun onFailure(call: Call<LoginResponce>, t: Throwable) {
                println("onFailure")
                println(t.fillInStackTrace())
                progressDialog.dismiss()
            }
        })
    }

    private fun showOTPVerify(
        email: String,
        password: String
    ) {


        val viewGroup =findViewById<ViewGroup>(android.R.id.content)
        val dialogView = layoutInflater.inflate(R.layout.otp_dialog,viewGroup,false)
        val dialogBuilder = AlertDialog.Builder(this)
        dialogBuilder.setView(dialogView)



        val resendBtn=dialogView.findViewById<TextView>(R.id.resendbtn)
        val verifyBtn=dialogView.findViewById<TextView>(R.id.verifybtn)
        val otpinput=dialogView.findViewById<TextInputEditText>(R.id.otpNumber)
        val idText=dialogView.findViewById<TextView>(R.id.textid)


        val alert = dialogBuilder.create()
        alert.show()

        idText.setText("Please type the verification code send to "+email)

        resendBtn.setOnClickListener {

            if(!PostInterface.isConnected(this@LoginActivity)){

                Toast.makeText(this, getString(R.string.no_internet), Toast.LENGTH_SHORT).show()
            }
            else{

                progressDialog.setMessage("Please wait ...")
                progressDialog.setCancelable(false)
                progressDialog.show()
                callSignIN(email,password)

            }

            alert.dismiss()



        }

        verifyBtn.setOnClickListener {


            if (TextUtils.isEmpty(otpinput.text.toString())) {

                Toast.makeText(applicationContext, "Please enter OTP", Toast.LENGTH_SHORT).show()
            }
            else{

                if(!PostInterface.isConnected(this@LoginActivity)){

                    Toast.makeText(this, getString(R.string.no_internet), Toast.LENGTH_SHORT).show()
                }
                else{

                    progressDialog.setMessage("Please wait ...")
                    progressDialog.setCancelable(false)
                    progressDialog.show()
                    callOTPVerify(otpinput.text.toString(),alert)


                }

            }



        }

    }

    private fun callOTPVerify(
        otp: String,
        alert: AlertDialog
    ) {


        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(PostInterface.BaseURL)
            .build()

        val userAPI = retrofit.create(PostInterface::class.java!!)

        userAPI.getOTP(otp).enqueue(object : Callback<AuthResponce> {
            override fun onResponse(call: Call<AuthResponce>, response: Response<AuthResponce>) {
                println("onResponse")
                System.out.println(response.toString())

                if (response.code()==200){
                    progressDialog.dismiss()

                    val statusCode = response.code()
                    val avv = response.body()!!.response
                    Log.i("onSuccess", avv.toString());
                    if (avv.equals("ok")) {

                      //  Toast.makeText(applicationContext, "Login Success", Toast.LENGTH_SHORT).show()

                        if(response.body()!!.loginType.equals("2")){

                            val editor = prefs.edit()
                            editor.putBoolean(getString(R.string.shared_loggedin_status), true)
                            editor.putString(getString(R.string.loginType),response.body()!!.loginType)
                            editor.putString(getString(R.string.shared_user_id),response.body()!!.id)
                            editor.putString(getString(R.string.shared_userphone),response.body()!!.mobilenumber)
                            editor.putString(getString(R.string.shared_user_full_name),response.body()!!.username+" "+response.body()!!.familyname)
                            editor.putString(getString(R.string.shared_user_email),response.body()!!.emailid)
                            editor.putString(getString(R.string.shared_user_insurancecode),response.body()!!.insurancecode)

                            editor.commit()

                            alert.dismiss()
                            // startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                            val i1 = Intent(this@LoginActivity, HomeActivity::class.java)
                            startActivity(i1)
                        }
                        else{
                            val editor = prefs.edit()
                            editor.putString(getString(R.string.loginType),response.body()!!.loginType)
                            editor.putBoolean(getString(R.string.shared_loggedin_status), true)
                            editor.putString(getString(R.string.shared_user_id),response.body()!!.id)
                            editor.putString(getString(R.string.shared_user_full_name),"Test Doctor")
                            editor.putString(getString(R.string.shared_user_email),response.body()!!.emailid)

                            editor.commit()

                            alert.dismiss()
                            // startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                            val i1 = Intent(this@LoginActivity, DocBookingList::class.java)
                            startActivity(i1)

                        }




                    } else {
                        Toast.makeText(applicationContext, response.body()!!.response, Toast.LENGTH_SHORT).show()
                        progressDialog.dismiss()
                        Log.i("onEmptyResponse", "Returned empty response");//Toast.makeText(getContext(),"Nothing returned",Toast.LENGTH_LONG).show();
                    }

                }
                else{
                    progressDialog.dismiss()
                    Toast.makeText(applicationContext, "Fail!!", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<AuthResponce>, t: Throwable) {
                println("onFailure")
                println(t.fillInStackTrace())
                progressDialog.dismiss()
            }
        })

    }


    private fun showToastLong(message: String) {

        Toast.makeText(applicationContext,message,Toast.LENGTH_SHORT).show()

    }

    override fun onBackPressed() {

        finishAffinity()
        finish()
    }

}