package com.ersinberkealemdaroglu.datapicker.ui

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.ersinberkealemdaroglu.datapicker.R
import com.ersinberkealemdaroglu.datapicker.data.MessageData
import com.ersinberkealemdaroglu.datapicker.ui.adapter.MessageAdapter
import com.ersinberkealemdaroglu.datapicker.utils.BotResponse
import com.ersinberkealemdaroglu.datapicker.utils.Constants
import com.ersinberkealemdaroglu.datapicker.utils.TimeObject
import kotlinx.coroutines.*

class ChatBotResponse : AppCompatActivity() {
    var messagesList = mutableListOf<MessageData>()



    @OptIn(DelicateCoroutinesApi::class)
    fun botResponse(message: String) {
        val timeStamp = TimeObject.timeStamp()

        GlobalScope.launch {
            //Fake response delay
            delay(1000)

            withContext(Dispatchers.Main) {
                //Gets the response
                val response = BotResponse.basicResponses(message)

                //Adds it to our local list
                messagesList.add(MessageData(response, Constants.RECEIVE_ID, timeStamp))

                //Inserts our message into the adapter
                val adapter = MessageAdapter()
                adapter.insertMessage(MessageData(response, Constants.RECEIVE_ID, timeStamp))

                //Scrolls us to the position of the latest message
                findViewById<RecyclerView>(R.id.recyclerview).scrollToPosition(adapter.itemCount - 1)

                //Starts Google
                when (response) {
                    Constants.OPEN_GOOGLE -> {
                        val site = Intent(Intent.ACTION_VIEW)
                        site.data = Uri.parse("https://www.google.com/")
                    }
                    Constants.OPEN_SEARCH -> {
                        val site = Intent(Intent.ACTION_VIEW)
                        val searchTerm: String = message.substringAfterLast("search")
                        site.data = Uri.parse("https://www.google.com/search?&q=$searchTerm")
                    }
                    Constants.OPEN_PAKET -> {
                        val site = Intent(Intent.ACTION_VIEW)
                        site.data = Uri.parse("https://www.turkcell.com.tr/paket-ve-tarifeler")
                        ContextCompat.startActivity(this@ChatBotResponse, site, null)
                    }
                    Constants.OPEN_FATURA ->{
                        //yapılacak.
                    }

                }
            }
        }
    }
}