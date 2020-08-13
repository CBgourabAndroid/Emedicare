package com.emedicare.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.emedicare.R
import com.emedicare.responceModel.medicines.Record

class MedicinesAdapter (ctx: Context) : RecyclerView.Adapter<MedicinesAdapter.MyViewHolder>(){

    private var originalData: ArrayList<Record>?=null
    private var filteredData: ArrayList<Record>? = null
    lateinit var context: Context

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        internal var v_s_date: TextView
        internal var v_s_bp_high: TextView
        internal var v_s_Disastolic: TextView
        internal var v_s_pulse: TextView



        init {

            v_s_date = itemView.findViewById(R.id.v_s_date)
            v_s_bp_high = itemView.findViewById(R.id.v_s_bp_high)
            v_s_Disastolic=itemView.findViewById(R.id.v_s_Disastolic)
            v_s_pulse=itemView.findViewById(R.id.v_s_pulse)



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

        holder.v_s_date.setText(originalData!![listPosition].timestamp)
        holder.v_s_bp_high.setText(originalData!![listPosition].sfdacode)
        holder.v_s_Disastolic.setText(originalData!![listPosition].itemname)
        holder.v_s_pulse.setText(originalData!![listPosition].qty)





    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MedicinesAdapter.MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_medicine, parent, false)


        return MedicinesAdapter.MyViewHolder(view)
    }



    override fun getItemCount(): Int {
        return originalData!!.size
    }


    /*// Filter Class
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
*/

}