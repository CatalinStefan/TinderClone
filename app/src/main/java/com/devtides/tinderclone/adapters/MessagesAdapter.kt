package com.devtides.tinderclone.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.devtides.tinderclone.Message
import com.devtides.tinderclone.R
import kotlinx.android.synthetic.main.item_current_user_message.view.*

class MessagesAdapter(private var messages: ArrayList<Message>, val userId: String) :
    RecyclerView.Adapter<MessagesAdapter.MessageViewHolder>() {

    companion object {
        val MESSAGE_CURRENT_USER = 1
        val MESSAGE_OTHER_USER = 2
    }

    fun addMessage(message: Message) {
        messages.add(message)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, itemViewType: Int): MessageViewHolder {
        if(itemViewType == MESSAGE_CURRENT_USER) {
            return MessageViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_current_user_message, parent, false)
            )
        } else {
            return MessageViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_other_user_message, parent, false)
            )
        }
    }

    override fun getItemCount() = messages.size

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        holder.bind(messages[position])
    }

    override fun getItemViewType(position: Int): Int {
        if(messages[position].sentBy.equals(userId)) {
            return MESSAGE_CURRENT_USER
        } else {
            return MESSAGE_OTHER_USER
        }
    }

    class MessageViewHolder(private val view: View): RecyclerView.ViewHolder(view) {
        fun bind(message: Message) {
            view.findViewById<TextView>(R.id.messageTV).text = message.message
        }
    }
}