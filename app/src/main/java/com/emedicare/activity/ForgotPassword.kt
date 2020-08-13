package com.emedicare.activity

import android.app.ProgressDialog
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.emedicare.PostInterface
import com.emedicare.R
import com.emedicare.responceModel.LoginResponce
import com.emedicare.responceModel.SignUpResponce
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.forgot_passwordlay.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ForgotPassword : AppCompatActivity() {

    lateinit var emailId:TextInputEditText
    lateinit var progressDialog : ProgressDialog
    lateinit var EmailStr:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= 21) {
            val window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = resources.getColor(R.color.colorPrimaryDark)
        }
        setContentView(R.layout.forgot_passwordlay)

        progressDialog = ProgressDialog(this)

        textView2.setOnClickListener {

            finish()
        }
        emailId=findViewById(R.id.mobileNumber)

        signuptxt.setOnClickListener {

            validation()
        }

    }

    private fun validation() {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

        EmailStr = emailId.getText().toString()


        var cancel = false
        var message = ""
        var focusView: View? = null
        var tempCond = false



        if (TextUtils.isEmpty(EmailStr)) {
            message = "Please enter your email address."
            focusView = emailId
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

            if(!PostInterface.isConnected(this@ForgotPassword)){

                Toast.makeText(this, getString(R.string.no_internet), Toast.LENGTH_SHORT).show()
            }
            else{

                progressDialog.setMessage("Please wait ...")
                progressDialog.setCancelable(false)
                progressDialog.show()
                callSignIN(EmailStr)

            }


            // startActivity(Intent(this@LoginActivity, MainActivity::class.java))


        }




    }

    private fun callSignIN(email: String?) {
        // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.



        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(PostInterface.BaseURL)
            .build()

        val userAPI = retrofit.create(PostInterface::class.java!!)

        userAPI.forgotPass(email!!).enqueue(object : Callback<SignUpResponce> {
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

                        //  val i1 = Intent(this@LoginActivity, MainActivity::class.java)
                        //  startActivity(i1)

                        finish()



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

        Toast.makeText(applicationContext,message, Toast.LENGTH_SHORT).show()

    }
}