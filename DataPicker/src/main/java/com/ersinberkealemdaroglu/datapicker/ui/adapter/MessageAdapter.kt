package com.ersinberkealemdaroglu.datapicker.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ersinberkealemdaroglu.datapicker.R
import com.ersinberkealemdaroglu.datapicker.data.MessageData
import com.ersinberkealemdaroglu.datapicker.databinding.ChatItemBinding
import com.ersinberkealemdaroglu.datapicker.utils.Constants.RECEIVE_ID
import com.ersinberkealemdaroglu.datapicker.utils.Constants.SEND_ID

class MessageAdapter: RecyclerView.Adapter<MessageAdapter.MessageViewHolder>() {

    var messagesList = mutableListOf<MessageData>()
    private lateinit var binding: ChatItemBinding

    inner class MessageViewHolder(binding: ChatItemBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        binding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.chat_item, parent, false)
        return MessageViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return messagesList.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val currentMessage = messagesList[position]
        when (currentMessage.id) {
            SEND_ID -> {

                binding.tvMessage.apply {
                    text = currentMessage.message
                    visibility = View.VISIBLE
                }
                binding.tvBotMessage.visibility = View.GONE
            }
            RECEIVE_ID -> {
                binding.tvBotMessage.apply {
                    text = currentMessage.message
                    visibility = View.VISIBLE
                }
                binding.tvMessage.visibility = View.GONE
            }
        }
    }

    fun insertMessage(message: MessageData) {
        this.messagesList.add(message)
        notifyItemInserted(messagesList.size)
    }

}