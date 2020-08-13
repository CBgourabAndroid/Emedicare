package com.emedicare.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.emedicare.R
import com.emedicare.activity.DoclistActivity
import com.emedicare.responceModel.Record
import java.io.Serializable
import java.util.*

class MainCatAdapter(ctx: Context) :RecyclerView.Adapter<MainCatAdapter.MyViewHolder>(){

    private var originalData: ArrayList<Record>? = null
    private var filteredData: ArrayList<Record>? = null
    lateinit var context:Context

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        internal var lbl_tv: TextView
        internal var imgView: ImageView


        init {

            lbl_tv = itemView.findViewById(R.id.cat_name)
            imgView = itemView.findViewById(R.id.cat_image)


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

        holder.lbl_tv.setText(originalData!![listPosition].specialityname)
        Glide.with(context).load(originalData!![listPosition].specialityimage)
            //.apply(RequestOptions().centerCrop())
            //.apply(RequestOptions().override(100,100))
            .into(holder.imgView)

        holder.itemView.setOnClickListener {


            //val intent =Intent()

            val i1 = Intent(context, DoclistActivity::class.java)
            i1.putExtra("id", originalData!![listPosition].specialitycode)
            context.startActivity(i1)
        }
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MainCatAdapter.MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_book_now, parent, false)


        return MainCatAdapter.MyViewHolder(view)
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
