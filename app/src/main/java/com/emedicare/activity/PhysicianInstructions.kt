package com.emedicare.activity

import android.os.Bundle
import com.emedicare.MainActivity
import com.emedicare.R
import kotlinx.android.synthetic.main.content_main.*

class PhysicianInstructions: MainActivity(){


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        layoutInflater.inflate(R.layout.about_us_activity, container)

        titleTv.text="Physician Instructions"

    }


}