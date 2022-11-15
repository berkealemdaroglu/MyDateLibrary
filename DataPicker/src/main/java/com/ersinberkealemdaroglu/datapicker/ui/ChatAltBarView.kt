package com.ersinberkealemdaroglu.datapicker.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.AttributeSet
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ersinberkealemdaroglu.datapicker.R
import com.ersinberkealemdaroglu.datapicker.data.MessageData
import com.ersinberkealemdaroglu.datapicker.ui.adapter.MessageAdapter
import com.ersinberkealemdaroglu.datapicker.utils.BotResponse
import com.ersinberkealemdaroglu.datapicker.utils.Constants.OPEN_GOOGLE
import com.ersinberkealemdaroglu.datapicker.utils.Constants.OPEN_PAKET
import com.ersinberkealemdaroglu.datapicker.utils.Constants.OPEN_SEARCH
import com.ersinberkealemdaroglu.datapicker.utils.Constants.RECEIVE_ID
import com.ersinberkealemdaroglu.datapicker.utils.Constants.SEND_ID
import com.ersinberkealemdaroglu.datapicker.utils.TimeObject
import kotlinx.coroutines.*

class ChatAltBarView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : ConstraintLayout(context, attrs, defStyle) {

    //private lateinit var binding: ChatAltBarBinding
    private lateinit var adapter: MessageAdapter
    var messagesList = mutableListOf<MessageData>()

    init {
        View.inflate(context, R.layout.chat_alt_bar, this)


        adapter = MessageAdapter()
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)
    }

    fun setTextMessage() {
        val button = findViewById<Button>(R.id.btn_send)
        button.setOnClickListener {
            val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
            val message = findViewById<EditText>(R.id.et_message)
            val timeStamp = TimeObject.timeStamp()

            if (message.text.isNotBlank()) {
                //Adds it to our local list
                messagesList.add(MessageData(message.text.toString(), SEND_ID, timeStamp))

                adapter.insertMessage(MessageData(message.text.toString(), SEND_ID, timeStamp))
                recyclerView.scrollToPosition(adapter.itemCount - 1)

                botResponse(message.text.toString())
            }


            val text = findViewById<EditText>(R.id.et_message)
            Toast.makeText(context, text.text, Toast.LENGTH_SHORT).show()
            println(messagesList.toString())
            message.text.clear()
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun botResponse(message: String) {
        val timeStamp = TimeObject.timeStamp()

        GlobalScope.launch {
            //Fake response delay
            delay(1000)

            withContext(Dispatchers.Main) {
                //Gets the response
                val response = BotResponse.basicResponses(message)

                //Adds it to our local list
                messagesList.add(MessageData(response, RECEIVE_ID, timeStamp))

                //Inserts our message into the adapter
                adapter.insertMessage(MessageData(response, RECEIVE_ID, timeStamp))

                //Scrolls us to the position of the latest message
                findViewById<RecyclerView>(R.id.recyclerview).scrollToPosition(adapter.itemCount - 1)

                //Starts Google
                when (response) {
                    OPEN_GOOGLE -> {
                        val site = Intent(Intent.ACTION_VIEW)
                        site.data = Uri.parse("https://www.google.com/")
                    }
                    OPEN_SEARCH -> {
                        val site = Intent(Intent.ACTION_VIEW)
                        val searchTerm: String? = message.substringAfterLast("search")
                        site.data = Uri.parse("https://www.google.com/search?&q=$searchTerm")
                    }
                    OPEN_PAKET -> {
                        val site = Intent(Intent.ACTION_VIEW)
                        site.data = Uri.parse("https://www.turkcell.com.tr/paket-ve-tarifeler")
                    }

                }
            }
        }
    }
}