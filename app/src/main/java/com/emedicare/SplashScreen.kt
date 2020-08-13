package com.emedicare

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.provider.Settings
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.activity.LoginActivity
import com.emedicare.activity.HomeActivity
import com.emedicare.doctors.DocBookingList
import com.emedicare.shared.PrefManager
import com.emedicare.shared.UserShared
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.DexterError
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.PermissionRequestErrorListener
import com.karumi.dexter.listener.multi.MultiplePermissionsListener

class SplashScreen : AppCompatActivity(){


    private var mDelayHandler: Handler? = null
    private val SPLASH_DELAY: Long = 3500 //3 seconds
    lateinit var prefManager: PrefManager

    internal val mRunnable: Runnable = Runnable {
        if (!isFinishing) {

            // val intent = Intent(applicationContext, MainActivity::class.java)
            //startActivity(intent)
            if (prefManager.isFirstTimeLaunch) {



                requestPermission()

            }
            else{

                if (UserShared(this@SplashScreen).getLoggedInStatus()) {

                    if (UserShared(this@SplashScreen).typeId().equals("2")){
                        startActivity(Intent(this@SplashScreen, HomeActivity::class.java))
                        finish()
                    }
                    else{
                        startActivity(Intent(this@SplashScreen, DocBookingList::class.java))
                        finish()

                    }


                } else {

                    startActivity(Intent(this@SplashScreen, LoginActivity::class.java))
                    finish()
                }


            }

        }
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= 21) {
            val window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = resources.getColor(R.color.colorPrimaryDark)
        }
        setContentView(R.layout.splash_screen)

        prefManager = PrefManager(this)


        /*  //4second splash time
          Handler().postDelayed({
              //start main activity
              startActivity(Intent(this@SplashScreen, MainActivity::class.java))
              //finish this activity
              finish()
          },4000)*/

        mDelayHandler = Handler()

        //Navigate with delay
        mDelayHandler!!.postDelayed(mRunnable, SPLASH_DELAY)
    }


    public override fun onDestroy() {

        if (mDelayHandler != null) {
            mDelayHandler!!.removeCallbacks(mRunnable)
        }

        super.onDestroy()
    }


    private fun requestPermission() {
        Dexter.withActivity(this)
            .withPermissions(
                android.Manifest.permission.ACCESS_FINE_LOCATION

            )
            .withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                    // check if all permissions are granted
                    if (report.areAllPermissionsGranted()) {
                        // Toast.makeText(getApplicationContext(), "All permissions are granted!", Toast.LENGTH_SHORT).show();
                        // ScanFinction();
                        if (UserShared(this@SplashScreen).getLoggedInStatus()) {


                            if (UserShared(this@SplashScreen).typeId().equals("2")){
                                startActivity(Intent(this@SplashScreen, HomeActivity::class.java))
                                finish()
                            }
                            else{
                                startActivity(Intent(this@SplashScreen, DocBookingList::class.java))
                                finish()

                            }
                        } else {

                            startActivity(Intent(this@SplashScreen, LoginActivity::class.java))
                            finish()
                        }
                    }

                    // check for permanent denial of any permission
                    if (report.isAnyPermissionPermanentlyDenied()) {
                        // show alert dialog navigating to Settings
                        //Toast.makeText(getApplicationContext(), "All permissions are Denied!", Toast.LENGTH_SHORT).show();
                        showSettingsDialog()
                    }
                }

                override fun onPermissionRationaleShouldBeShown(permissions: List<PermissionRequest>, token: PermissionToken) {
                    token.continuePermissionRequest()
                }
            }).withErrorListener(object : PermissionRequestErrorListener {
                override fun onError(error: DexterError) {
                    Toast.makeText(applicationContext, "Error occurred! " + error.toString(), Toast.LENGTH_SHORT).show()
                }
            })
            .onSameThread()
            .check()
    }

    private fun showSettingsDialog() {



        val dialogBuilder = AlertDialog.Builder(this)

        dialogBuilder.setTitle("Need Permissions")
        dialogBuilder.setMessage("This app needs permission to use this feature. You can grant them in app settings.")
        dialogBuilder.setPositiveButton("GOTO SETTINGS", { dialog, whichButton ->


            dialog.dismiss()
            openSettings()
        })
        dialogBuilder.setNegativeButton("Cancel", { dialog, whichButton ->



            dialog.dismiss()
        })
        val b = dialogBuilder.create()
        b.show()
    }


    // navigating user to app settings
    private fun openSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri = Uri.fromParts("package", packageName, null)
        intent.data = uri
        startActivityForResult(intent, 101)
    }


}