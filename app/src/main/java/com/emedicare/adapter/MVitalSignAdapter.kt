package com.emedicare.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.borjabravo.readmoretextview.ReadMoreTextView
import com.emedicare.R
import com.emedicare.responceModel.mvs.Record

class MVitalSignAdapter (ctx: Context) : RecyclerView.Adapter<MVitalSignAdapter.MyViewHolder>(){

    private var originalData: ArrayList<Record>?=null
    private var filteredData: ArrayList<Record>? = null
    lateinit var context: Context

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        internal var v_s_date: TextView
        internal var v_s_bp_high: TextView
        internal var v_s_Disastolic: TextView
        internal var v_s_pulse: TextView
        internal var v_s_Temperature: TextView
        internal var v_s_heartrate: TextView
        internal var v_s_weight: TextView
        internal var v_s_bmi: TextView


        init {

            v_s_date = itemView.findViewById(R.id.v_s_date)
            v_s_bp_high = itemView.findViewById(R.id.v_s_bp_high)
            v_s_Disastolic=itemView.findViewById(R.id.v_s_Disastolic)
            v_s_pulse=itemView.findViewById(R.id.v_s_pulse)
            v_s_Temperature=itemView.findViewById(R.id.v_s_Temperature)
            v_s_heartrate=itemView.findViewById(R.id.v_s_heartrate)
            v_s_weight=itemView.findViewById(R.id.v_s_weight)
            v_s_bmi=itemView.findViewById(R.id.v_s_bmi)


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
        holder.v_s_bp_high.setText(originalData!![listPosition].bphigh)
        holder.v_s_Disastolic.setText(originalData!![listPosition].bpdiastolic)
        holder.v_s_pulse.setText(originalData!![listPosition].pulse)
        holder.v_s_Temperature.setText(originalData!![listPosition].temperature)
        holder.v_s_heartrate.setText(originalData!![listPosition].heartrate)
        holder.v_s_weight.setText(originalData!![listPosition].weight)
        holder.v_s_bmi.setText(originalData!![listPosition].bmi)




    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MVitalSignAdapter.MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_medical_v_s, parent, false)


        return MVitalSignAdapter.MyViewHolder(view)
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