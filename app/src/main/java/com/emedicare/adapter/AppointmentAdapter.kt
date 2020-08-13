package com.emedicare.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.activity.LoginActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.emedicare.R
import com.emedicare.activity.UserChatActivity
import com.emedicare.responceModel.appointment.Record

class AppointmentAdapter(val cvtx: Context) : RecyclerView.Adapter<AppointmentAdapter.MyViewHolder>() {

    var dataList : List<Record> = listOf()
    lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_appointment,parent,false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    @SuppressLint("NewApi")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {



        holder.tv_docName.setText(dataList[position].physicianname)
        holder.tv_speciality.setText(dataList[position].specialityname)
        holder.tv_bookDate.setText(dataList[position].slotdate)
        holder.tv_slotNo.setText("Slot No "+dataList[position].slotnumber)
        holder.tv_slotTime.setText(dataList[position].slottype+"\n"+dataList[position].slottime)
        holder.tv_msg.setText("Message : "+dataList[position].message)
        //holder.img_cat.setImageResource(currResource)
        Glide.with(context).load(dataList[position].physicianimage)
            //.apply(RequestOptions().centerCrop())
            .apply(RequestOptions().override(100,100))
            .into(holder.img_cat)

        holder.layMsg.setOnClickListener {
            val i1 = Intent(context, UserChatActivity::class.java)
            i1.putExtra("reciverID",dataList[position].physiciancode)
            i1.putExtra("reciverName",dataList[position].physicianname)
            i1.putExtra("type","patient")
            context.startActivity(i1)

        }

    }




    fun setDataListItems(context: Context,
        historyList: List<Record>
    ){
        this.dataList = historyList
        this.context=context
        notifyDataSetChanged()
    }

    class MyViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {

        val tv_docName: TextView = itemView!!.findViewById(R.id.doc_name)
        val tv_speciality: TextView = itemView!!.findViewById(R.id.speciality)
        val tv_bookDate: TextView = itemView!!.findViewById(R.id.bookDate)
        val tv_slotNo: TextView = itemView!!.findViewById(R.id.slotNo)
        val tv_slotTime: TextView = itemView!!.findViewById(R.id.slotTime)
        val tv_msg:TextView=itemView!!.findViewById(R.id.message)
        val img_cat: ImageView = itemView!!.findViewById(R.id.doc_image)
        val layMsg:LinearLayout=itemView!!.findViewById(R.id.msgLay)



    }
}