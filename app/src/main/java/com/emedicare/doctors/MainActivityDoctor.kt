package com.emedicare.doctors

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.ui.AppBarConfiguration
import com.emedicare.R
import com.emedicare.SplashScreen
import com.emedicare.activity.AboutUsActivity
import com.emedicare.activity.HelpSupportActivity
import com.emedicare.activity.HomeActivity
import com.emedicare.activity.MyAppointmentActivity
import com.emedicare.shared.UserShared
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar

open class MainActivityDoctor: AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var appBarConfiguration: AppBarConfiguration
    protected var drawerToggle: ActionBarDrawerToggle? = null
    lateinit var drawerLayout: DrawerLayout
    lateinit var psh: UserShared
    lateinit var titleTv: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_doctor)
        val toolbar: Toolbar = findViewById(R.id.toolbars)
        setSupportActionBar(toolbar)
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
        val nav_user = hView.findViewById<TextView>(R.id.docnameTxt)
        val nav_user_email =hView.findViewById<TextView>(R.id.docemailTxt)

        nav_user.text=psh.fullName
        nav_user_email.text=psh.email

        titleTv=findViewById(R.id.textView5)
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


            R.id.nav_home -> {


                val i1 = Intent(this@MainActivityDoctor, DocBookingList::class.java)
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



            val myPrefs = getSharedPreferences("MY_SHARED_PREF", Context.MODE_PRIVATE)
            val editor = myPrefs.edit()
            editor.clear()
            editor.apply()


            val intent = Intent(this, SplashScreen::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)

            dialog.dismiss()
        })
        dialogBuilder.setNegativeButton("Cancel", { dialog, whichButton ->



            dialog.dismiss()
        })
        val b = dialogBuilder.create()
        b.show()





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
