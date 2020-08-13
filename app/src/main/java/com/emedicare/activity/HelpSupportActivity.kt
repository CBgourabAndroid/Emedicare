package com.emedicare.activity

import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.emedicare.MainActivity
import com.emedicare.R
import com.emedicare.adapter.AppointmentAdapter
import kotlinx.android.synthetic.main.content_main.*

class HelpSupportActivity: MainActivity(){

    lateinit var recyclerViewCategory: RecyclerView
    lateinit var appointmentAdapter: AppointmentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        layoutInflater.inflate(R.layout.help_spport_activity, container)

        titleTv.text="eMedicare"

    }


}