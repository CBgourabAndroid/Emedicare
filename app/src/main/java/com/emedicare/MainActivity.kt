package com.emedicare

import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.ui.AppBarConfiguration
import androidx.recyclerview.widget.LinearLayoutManager
import com.emedicare.activity.*
import com.emedicare.doctors.DocBookingAdapter
import com.emedicare.doctors.modelclass.DocListModel
import com.emedicare.doctors.modelclass.Record
import com.emedicare.responceModel.OTPResponce
import com.emedicare.shared.UserShared
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.ArrayList

open class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var appBarConfiguration: AppBarConfiguration
    protected var drawerToggle: ActionBarDrawerToggle? = null
    lateinit var drawerLayout: DrawerLayout
    lateinit var psh:UserShared
    lateinit var titleTv:TextView
    lateinit var img_refresh:ImageView
    lateinit var progressDialogs: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbars)
        setSupportActionBar(toolbar)
        progressDialogs= ProgressDialog(this)
        psh= UserShared(this)
        //getSupportActionBar()!!.setTitle("Tittle");
        // drawerToggle!!.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.black))
        drawerLayout = findViewById(R.id.drawer_layout)
        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar, 0, 0
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        // val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        /*     appBarConfiguration = AppBarConfiguration(
                 setOf(
                     R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
                     R.id.nav_tools, R.id.nav_share, R.id.nav_send
                 ), drawerLayout
             )*/
        //  setupActionBarWithNavController(navController, appBarConfiguration)
        //   navView.setupWithNavController(navController)

        navView.setNavigationItemSelectedListener(this)

       /* imageView2.setOnClickListener {

            if(!drawerLayout.isDrawerOpen(Gravity.LEFT)) drawerLayout.openDrawer(Gravity.LEFT);
            else drawerLayout.closeDrawer(Gravity.RIGHT);
        }*/

        val hView=navView.getHeaderView(0)
        val nav_user = hView.findViewById<TextView>(R.id.nameTxt)
        val nav_user_email =hView.findViewById<TextView>(R.id.emailTxt)

        nav_user.text=psh.fullName
        nav_user_email.text=psh.email

        titleTv=findViewById(R.id.textView5)
        img_refresh=findViewById(R.id.imgRefresh)
       // titleTv.text="Specialities"
    }

    /* override fun onCreateOptionsMenu(menu: Menu): Boolean {
         // Inflate the menu; this adds items to the action bar if it is present.
         menuInflater.inflate(R.menu.main, menu)
         return true
     }*/

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.


        when (item.itemId) {


            R.id.nav_home->{

                val i1 = Intent(this@MainActivity, HomeActivity::class.java)
                startActivity(i1)

            }
            R.id.nav_update_profile->{

                val i1 = Intent(this@MainActivity, ProfileActivity::class.java)
                startActivity(i1)
            }

            R.id.nav_gallery->{

                val i1 = Intent(this@MainActivity, MyAppointmentActivity::class.java)
                startActivity(i1)

            }
            R.id.nav_p_e->{
                val i1 = Intent(this@MainActivity, PatientEducationActivity::class.java)
                startActivity(i1)


            }
            R.id.nav_p_i->{

                val i1 = Intent(this@MainActivity, PhysicianInstructions::class.java)
                startActivity(i1)
            }
            R.id.nav_p_c->{

                val i1 = Intent(this@MainActivity, PaymentCancellation::class.java)
                startActivity(i1)
            }


            R.id.nav_send->{
                val i1 = Intent(this@MainActivity, AboutUsActivity::class.java)
                startActivity(i1)

            }
            R.id.nav_m_d->{

                val i1 = Intent(this@MainActivity, MedicalDataActivity::class.java)
                startActivity(i1)

            }


            R.id.nav_report->{

                val i1 = Intent(this@MainActivity, MedicalReportActivity::class.java)
                startActivity(i1)

            }



            R.id.nav_logout -> {

                logout()

            }


        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }



    /* override fun onSupportNavigateUp(): Boolean {
         val navController = findNavController(R.id.nav_host_fragment)
         return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
     }*/

    private fun logout() {


        val dialogBuilder = AlertDialog.Builder(this)

        dialogBuilder.setTitle("Alert")
        dialogBuilder.setMessage("Do you want to logout?")
        dialogBuilder.setPositiveButton("OK", { dialog, whichButton ->





            if(!PostInterface.isConnected(this@MainActivity)){

                Toast.makeText(this, getString(R.string.no_internet), Toast.LENGTH_SHORT).show()
            }
            else{

                progressDialogs.setMessage("Please wait ...")
                progressDialogs.setCancelable(false)
                progressDialogs.show()
                apiLogout(dialog)

            }


           // dialog.dismiss()
        })
        dialogBuilder.setNegativeButton("Cancel", { dialog, whichButton ->



            dialog.dismiss()
        })
        val b = dialogBuilder.create()
        b.show()





    }

    private fun apiLogout(dialog: DialogInterface?) {

        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(PostInterface.BaseURL)
            .build()

        val userAPI = retrofit.create(PostInterface::class.java!!)




        userAPI.GetLogout(psh.email).enqueue(object :
            Callback<OTPResponce> {
            override fun onResponse(call: Call<OTPResponce>, response: Response<OTPResponce>) {
                println("onResponse")
                System.out.println(response.body()!!.toString())

                if (response?.body() != null) {
                    progressDialogs.dismiss()

                    if (response.body()!!.response.equals("ok")){


                        val myPrefs = getSharedPreferences("MY_SHARED_PREF", Context.MODE_PRIVATE)
                        val editor = myPrefs.edit()
                        editor.clear()
                        editor.apply()


                        val intent = Intent(this@MainActivity, SplashScreen::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                        startActivity(intent)
                        dialog!!.dismiss()


                    }
                    else{
                        Toast.makeText(applicationContext, response.body()!!.response, Toast.LENGTH_SHORT).show()
                        //finish()
                        dialog!!.dismiss()
                    }






                } else {
                    progressDialogs.dismiss()
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
                progressDialogs.dismiss()
            }
        })

    }

    override fun onBackPressed() {
        val dialogBuilder = AlertDialog.Builder(this)
        dialogBuilder.setMessage("Do you want to close this application ?")
            .setCancelable(false)
            .setPositiveButton("Proceed", DialogInterface.OnClickListener {

                    dialog, _ -> finishAffinity()
            })
            .setNegativeButton("Cancel", DialogInterface.OnClickListener {


                    dialog, _ -> dialog.cancel()
            })

        val alert = dialogBuilder.create()
        alert.setTitle("Alert")
        alert.show()
    }
}
