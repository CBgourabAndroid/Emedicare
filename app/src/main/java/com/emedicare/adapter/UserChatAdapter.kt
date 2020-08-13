package com.emedicare.adapter

import android.content.Context
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.emedicare.R
import com.emedicare.responceModel.userChat.Record
import com.emedicare.shared.UserShared
import kotlinx.android.synthetic.main.my_message.view.*
import kotlinx.android.synthetic.main.other_message.view.*

private const val VIEW_TYPE_MY_MESSAGE = 1
private const val VIEW_TYPE_OTHER_MESSAGE = 2


class UserChatAdapter(val context: Context) : RecyclerView.Adapter<MessageViewHolder>() {
    private var messages: ArrayList<Record> = ArrayList<Record>()
    private var OtherName=""

   /* fun addMessage(message: MutableList<Record>){
        messages.add(message)
        notifyDataSetChanged()
    }*/

    fun setDataListItems(
                         historyList: ArrayList<Record>
    ,otherName:String){
        this.messages = historyList
        OtherName=otherName
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return messages.size
    }

    override fun getItemViewType(position: Int): Int {
        val message = messages.get(position)
        val psh=UserShared(context)

        return if(psh.id== message.messagesender) {
            VIEW_TYPE_MY_MESSAGE
        }
        else {
            VIEW_TYPE_OTHER_MESSAGE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        return if(viewType == VIEW_TYPE_MY_MESSAGE) {
            MyMessageViewHolder(LayoutInflater.from(context).inflate(R.layout.my_message, parent, false))
        } else {
            OtherMessageViewHolder(LayoutInflater.from(context).inflate(R.layout.other_message, parent, false))
        }
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val message = messages.get(position)

        holder?.bind(message)
    }

    inner class MyMessageViewHolder (view: View) : MessageViewHolder(view) {
        private var messageText: TextView = view.txtMyMessage

        private var timeText: TextView = view.txtMyMessageTime

        override fun bind(message: Record) {
            messageText.text = message.messagebody
            timeText.text = message.date+" "+message.time
        }
    }

    inner class OtherMessageViewHolder (view: View) : MessageViewHolder(view) {
        private var messageText: TextView = view.txtOtherMessage
        private var userText: TextView = view.txtOtherUser
        private var timeText: TextView = view.txtOtherMessageTime

        override fun bind(message: Record) {
            messageText.text = message.messagebody
            userText.text = OtherName
            timeText.text = message.date+" "+message.time
        }
    }
}

open class MessageViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    open fun bind(message:Record) {}
}
