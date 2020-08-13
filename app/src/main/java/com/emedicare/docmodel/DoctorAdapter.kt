package com.emedicare.docmodel

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.emedicare.R
import com.emedicare.activity.BookAppointment
import com.emedicare.activity.HomeActivity
import com.emedicare.shared.UserShared
import java.util.*
import kotlin.collections.ArrayList


class DoctorAdapter(ctx: Context) : RecyclerView.Adapter<DoctorAdapter.MyViewHolder>(){

    private var originalData: ArrayList<Record>?=null
    private var filteredData: ArrayList<Record>? = null
    lateinit var context: Context
    lateinit var psh:UserShared

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        internal var docNameTv: TextView
        internal var specilityTv: TextView
        internal var feeTv: TextView
        internal var bookNowTv: TextView
        internal var imgView: ImageView


        init {

            docNameTv = itemView.findViewById(R.id.doc_name)
            imgView = itemView.findViewById(R.id.doc_image)
            specilityTv=itemView.findViewById(R.id.speciality)
            feeTv=itemView.findViewById(R.id.con_fee)
            bookNowTv=itemView.findViewById(R.id.bookBtn)


        }
    }

    fun setDataListItems(context: Context,
                         historyList: ArrayList<Record>
    ){
        this.originalData = historyList
        this.filteredData = ArrayList()
        this.filteredData!!.addAll(historyList)
        this.context=context
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: MyViewHolder, listPosition: Int) {

        psh= UserShared(context)
        holder.docNameTv.setText(originalData!![listPosition].physicianname)
        holder.specilityTv.setText(originalData!![listPosition].specialityname)
        holder.feeTv.setText("Consultation Fee SAR"+originalData!![listPosition].price)
        Glide.with(context).load(originalData!![listPosition].physicianimage)
            .override(200, 200)
            .into(holder.imgView)

       /* if (originalData!![listPosition].id.equals("1")){
            holder.imgView.setImageResource(R.drawable.s4)
        }
        else if(originalData!![listPosition].id.equals("2")){
            holder.imgView.setImageResource(R.drawable.s10)
        }
        else if(originalData!![listPosition].id.equals("3")){
            holder.imgView.setImageResource(R.drawable.s8)
        }
        else if(originalData!![listPosition].id.equals("4")){
            holder.imgView.setImageResource(R.drawable.s12)
        }*/


        holder.bookNowTv.setOnClickListener {

            if (psh.insurenceStatus.equals("1")){

                val i1 = Intent(context, BookAppointment::class.java)
                i1.putExtra("phyCode",originalData!![listPosition].physiciancode)
                i1.putExtra("docname",originalData!![listPosition].physicianname)
                i1.putExtra("docspl",originalData!![listPosition].specialityname)
                i1.putExtra("docImg",originalData!![listPosition].physicianimage)
                i1.putExtra("price",originalData!![listPosition].price)
                context.startActivity(i1)
            }
            else{
                Toast.makeText(context, "Please Provide Insurence Details!!", Toast.LENGTH_SHORT).show()
            }

        }
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DoctorAdapter.MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_doctor, parent, false)


        return DoctorAdapter.MyViewHolder(view)
    }



    override fun getItemCount(): Int {
        return originalData!!.size
    }


    // Filter Class
    fun filter(charText: String) {
        var charText = charText
        charText = charText.toLowerCase(Locale.getDefault())
        originalData!!.clear()
        if (charText.length == 0) {
            originalData!!.addAll(filteredData!!)
        } else {
            for (wp in filteredData!!) {
                if (wp.specialityname.toLowerCase(Locale.getDefault()).contains(charText)) {
                    originalData!!.add(wp)
                }
            }
        }
        notifyDataSetChanged()
    }


}
