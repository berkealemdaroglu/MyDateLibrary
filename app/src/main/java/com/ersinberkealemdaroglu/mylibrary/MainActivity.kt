package com.ersinberkealemdaroglu.mylibrary

import android.icu.util.Calendar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.ersinberkealemdaroglu.datapicker.DataPicker
import com.ersinberkealemdaroglu.datapicker.OnDataSelectedListener
import com.ersinberkealemdaroglu.mylibrary.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var myDate: String = ""

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        init()
    }

    private fun init(){
        dataPicker()
    }

    private fun dataPicker() {
        val myDataPicker = DataPicker(this@MainActivity, null)
            .setFormatType("dd.MM.yyyy")
            .setListener(object : OnDataSelectedListener {
                override fun isSelected(date: String) {
                    myDate = date
                    binding.textView2.text = myDate
                    Toast.makeText(this@MainActivity, myDate, Toast.LENGTH_SHORT).show()
                }
            })
        binding.button.setOnClickListener {
            myDataPicker.showActivity()
        }


    }

}