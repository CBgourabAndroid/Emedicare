package com.emedicare.doctors

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.emedicare.R
import com.emedicare.activity.DoclistActivity
import com.emedicare.activity.UserChatActivity
import com.emedicare.doctors.modelclass.Record


class DocBookingAdapter(val cvtx: Context) : RecyclerView.Adapter<DocBookingAdapter.MyViewHolder>() {

    var dataList : List<Record> = listOf()
    lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item__book_appointment,parent,false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    @SuppressLint("NewApi")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {




        holder.tv_slotNumber.setText(dataList[position].slotnumber)
        holder.tv_patientname.setText(dataList[position].patientname)
        holder.tv_slotDetails.setText(dataList[position].slottype+" at "+dataList[position].slotdate+" "+dataList[position].slottime)

        if (dataList[position].patientloggedinstatus.equals("loggedin")){

            holder.tv_loginOrnot.setText("Logged in")
            holder.tv_slotNumber.setBackgroundResource(R.drawable.rect_green)
        }
        else{
            holder.tv_loginOrnot.setText("Logged out")
            holder.tv_slotNumber.setBackgroundResource(R.drawable.rect_red)

        }

        //holder.img_cat.setImageResource(currResource)
        /*Glide.with(context).load(dataList[position].offerImage)
            //.apply(RequestOptions().centerCrop())
            //.apply(RequestOptions().override(100,100))
            .into(holder.img_cat)*/

        holder.img_cat.setOnClickListener {

          /*  val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/html"
            intent.putExtra(Intent.EXTRA_EMAIL, dataList[position].emailid)
            intent.putExtra(Intent.EXTRA_SUBJECT, "You Have An Appointment")
            intent.putExtra(Intent.EXTRA_TEXT, "I'm email body.")

            context.startActivity(Intent.createChooser(intent, "Send Email"))*/
            val emailIntent = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:"+dataList[position].emailid))
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "You Have An Appointment")
            emailIntent.putExtra(Intent.EXTRA_TEXT, "I'm email body.")
            context.startActivity(Intent.createChooser(emailIntent, "Send Email"))
        }

        holder.itemView.setOnClickListener {

            val i1 = Intent(context, PatientDetailActivity::class.java)
            i1.putExtra("id", dataList[position].emailid)
            context.startActivity(i1)
        }

        holder.img_Chat.setOnClickListener {

            val i1 = Intent(context, UserChatActivity::class.java)
            i1.putExtra("reciverID",dataList[position].patientid)
            i1.putExtra("reciverName",dataList[position].patientname)
            i1.putExtra("type","doctor")
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

        val tv_slotNumber: TextView = itemView!!.findViewById(R.id.slotNumber)
        val tv_patientname: TextView = itemView!!.findViewById(R.id.patientname)
        val tv_slotDetails: TextView = itemView!!.findViewById(R.id.slotDetails)
        val tv_loginOrnot: TextView = itemView!!.findViewById(R.id.loginOrnot)
        val img_cat: ImageView = itemView!!.findViewById(R.id.imageView3)
        val img_Chat:ImageView=itemView!!.findViewById(R.id.chatnow)



    }
}