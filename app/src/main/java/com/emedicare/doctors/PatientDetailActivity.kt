package com.emedicare.doctors

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.emedicare.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.book_appointment_main.*

class PatientDetailActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= 21) {
            val window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = resources.getColor(R.color.colorPrimaryDark)
        }
        setContentView(R.layout.patient_details_main)

        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        getSupportActionBar()!!.setDisplayHomeAsUpEnabled(true)
        getSupportActionBar()!!.setDisplayShowHomeEnabled(true)



        toolbar.setNavigationOnClickListener {

            onBackPressed()
        }

        Glide.with(this).load(R.drawable.s10)
            .apply(RequestOptions().circleCrop())
            //.apply(RequestOptions().override(100,100))
            .into(doc_image)

        val fab: FloatingActionButton = findViewById(R.id.l_fab)
        fab.setOnClickListener { view ->
            val emailIntent = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:"+intent.getStringExtra("id")))
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "You Have An Appointment")
            emailIntent.putExtra(Intent.EXTRA_TEXT, "I'm email body.")
            startActivity(Intent.createChooser(emailIntent, "Send Email"))
        }
    }
}