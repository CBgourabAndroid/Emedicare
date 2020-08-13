package com.emedicare.activity

import android.app.ProgressDialog
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.emedicare.PostInterface
import com.emedicare.R
import com.emedicare.SBTimer
import com.emedicare.SBTimer.SBTimerListener
import com.emedicare.adapter.UserChatAdapter
import com.emedicare.responceModel.userChat.UserChatResponce
import com.emedicare.shared.UserShared
import kotlinx.android.synthetic.main.activity_chat.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class UserChatActivity : AppCompatActivity() {

    lateinit var progressDialog : ProgressDialog
    lateinit var toolbar:Toolbar
    lateinit var recyclerViewChat: RecyclerView
    lateinit var mAdapter:UserChatAdapter
    lateinit var psh:UserShared
    var sbTimer: SBTimer? = null
    private lateinit var scrollListener: RecyclerView.OnScrollListener
    var patientID=""
    var doctorID=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= 21) {
            val window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = resources.getColor(R.color.colorPrimaryDark)
        }
        setContentView(R.layout.user_chat_activity)
        toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        getSupportActionBar()!!.setDisplayHomeAsUpEnabled(true)
        getSupportActionBar()!!.setDisplayShowHomeEnabled(true)



        toolbar.setNavigationOnClickListener {

            finish()
        }
        psh= UserShared(this)
        progressDialog= ProgressDialog(this)
        recyclerViewChat = findViewById(R.id.messageList)
        mAdapter = UserChatAdapter(this@UserChatActivity)

        if (intent.getStringExtra("type").equals("patient")){

            patientID=psh.id
            doctorID=intent.getStringExtra("reciverID")
        }
        else{

            patientID=intent.getStringExtra("reciverID")
            doctorID=psh.id
        }

        setRecyclerViewScrollListener()

        myFunction()

        btnSend.setOnClickListener {
            if(txtMessage.text.isNotEmpty()) {


                if(!PostInterface.isConnected(this@UserChatActivity)){

                    Toast.makeText(this, getString(R.string.no_internet), Toast.LENGTH_SHORT).show()
                }
                else{

                    progressDialog.setMessage("Please wait ...")
                    progressDialog.setCancelable(false)
                    progressDialog.show()
                    callSendApi()

                }


            } else {
                Toast.makeText(applicationContext,"Message should not be empty", Toast.LENGTH_SHORT).show()
            }
        }



    }

    private fun myFunction() {

        sbTimer = SBTimer(SBTimerListener {
            if(!PostInterface.isConnected(this@UserChatActivity)){

                Toast.makeText(this, getString(R.string.no_internet), Toast.LENGTH_SHORT).show()
            }
            else{


                callDataApi()


            }

        })


        sbTimer!!.start(2000, 2000)



    }


    private fun setRecyclerViewScrollListener() {
        scrollListener = object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerViewChat, newState)
                when (newState) {
                    RecyclerView.SCROLL_STATE_IDLE -> {
                        //Toast.makeText(applicationContext, "The RecyclerView is not scrolling", Toast.LENGTH_SHORT).show()
                        myFunction()
                    }
                    RecyclerView.SCROLL_STATE_DRAGGING ->{
                       // Toast.makeText(applicationContext, "Scrolling now", Toast.LENGTH_SHORT).show()
                        sbTimer!!.stop()
                    }

                    RecyclerView.SCROLL_STATE_SETTLING ->{
                      //  Toast.makeText(applicationContext, "Scroll Settling", Toast.LENGTH_SHORT).show()
                        sbTimer!!.stop()
                    }

                }

                ///sbTimer!!.stop()
            }
        }
        recyclerViewChat.addOnScrollListener(scrollListener)
    }


    private fun callSendApi() {



        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(PostInterface.BaseURL)
            .build()

        val userAPI = retrofit.create(PostInterface::class.java!!)




        userAPI.SendChat(patientID,doctorID,txtMessage.text.toString()).enqueue(object :
            Callback<UserChatResponce> {
            override fun onResponse(call: Call<UserChatResponce>, response: Response<UserChatResponce>) {
                println("onResponse")
                System.out.println(response.body()!!.toString())

                if (response?.body() != null) {
                    progressDialog.dismiss()

                    if (response.body()!!.records!=null&&response.body()!!.records.size>0){
                        /*  */
                        resetInput()
                    }
                    else{
                        recyclerViewChat.visibility= View.GONE
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

            override fun onFailure(call: Call<UserChatResponce>, t: Throwable) {
                println("onFailure")
                println(t.fillInStackTrace())
                progressDialog.dismiss()
            }
        })

    }

    private fun resetInput() {
        // Clean text box
        txtMessage.text.clear()

        // Hide keyboard
        val inputManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(currentFocus!!.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
    }


    private fun callDataApi() {

        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(PostInterface.BaseURL)
            .build()

        val userAPI = retrofit.create(PostInterface::class.java!!)




        userAPI.GetChat(patientID,doctorID).enqueue(object :
            Callback<UserChatResponce> {
            override fun onResponse(call: Call<UserChatResponce>, response: Response<UserChatResponce>) {
                println("onResponse")
                System.out.println(response.body()!!.toString())

                if (response?.body() != null) {
                    progressDialog.dismiss()

                    if (response.body()!!.records!=null&&response.body()!!.records.size>0){
                        /*  */
                        recyclerViewChat.visibility= View.VISIBLE
                        mAdapter = UserChatAdapter(this@UserChatActivity)
                        recyclerViewChat.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL ,false)
                        recyclerViewChat.adapter = mAdapter
                        mAdapter.setDataListItems(
                            response.body()!!.records as ArrayList<com.emedicare.responceModel.userChat.Record>
                        ,intent.getStringExtra("reciverName"))

                        messageList.scrollToPosition(mAdapter.itemCount - 1)

                    }
                    else{
                        recyclerViewChat.visibility= View.GONE
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

            override fun onFailure(call: Call<UserChatResponce>, t: Throwable) {
                println("onFailure")
                println(t.fillInStackTrace())
                progressDialog.dismiss()
            }
        })


    }
}