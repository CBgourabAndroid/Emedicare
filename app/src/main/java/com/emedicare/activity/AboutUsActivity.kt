package com.emedicare.activity

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.emedicare.MainActivity
import com.emedicare.R
import com.emedicare.adapter.AppointmentAdapter
import kotlinx.android.synthetic.main.content_main.*

class AboutUsActivity : MainActivity(){


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        layoutInflater.inflate(R.layout.about_us_activity, container)

        titleTv.text="About Us"

    }


}