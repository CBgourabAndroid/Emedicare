package com.emedicare.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.borjabravo.readmoretextview.ReadMoreTextView
import com.emedicare.R
import com.emedicare.responceModel.mReport.Detail
import java.util.*
import kotlin.collections.ArrayList

class ReportDetailsAdapter (ctx: Context) : RecyclerView.Adapter<ReportDetailsAdapter.MyViewHolder>(){

    private var originalData: ArrayList<Detail>?=null
    private var filteredData: ArrayList<Detail>? = null
    lateinit var context: Context

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        internal var con_numberTv: TextView
        internal var report_noTv: TextView
        internal var report_dateTv: TextView
        internal var report_detailsTv: ReadMoreTextView
        internal var attachmentFileTv: TextView


        init {

            con_numberTv = itemView.findViewById(R.id.con_number)
            report_noTv = itemView.findViewById(R.id.report_no)
            report_dateTv=itemView.findViewById(R.id.report_date)
            report_detailsTv=itemView.findViewById(R.id.report_details)
            attachmentFileTv=itemView.findViewById(R.id.attachmentFile)


        }
    }

    fun setDataListItems(context: Context,
                         historyList: ArrayList<Detail>
    ){
        this.originalData = historyList
        this.filteredData = ArrayList()
        this.filteredData!!.addAll(historyList)
        this.context=context
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: MyViewHolder, listPosition: Int) {

        holder.con_numberTv.setText(originalData!![listPosition].consultationnumber)
        holder.report_noTv.setText(originalData!![listPosition].reportnumber)
        holder.report_dateTv.setText(originalData!![listPosition].reportdate)
        holder.report_detailsTv.setText(originalData!![listPosition].reportdetail)

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
        }
*/

        holder.attachmentFileTv.setOnClickListener {

            val openURL = Intent(android.content.Intent.ACTION_VIEW)
            openURL.data = Uri.parse(originalData!![listPosition].reportattachment)
            context.startActivity(openURL)
        }

    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ReportDetailsAdapter.MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_medical_report, parent, false)


        return ReportDetailsAdapter.MyViewHolder(view)
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
