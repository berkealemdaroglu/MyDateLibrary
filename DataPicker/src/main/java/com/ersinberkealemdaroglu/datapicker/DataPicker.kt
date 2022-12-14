package com.ersinberkealemdaroglu.datapicker

import android.app.Activity
import android.app.DatePickerDialog
import android.util.Log
import androidx.fragment.app.Fragment
import java.text.SimpleDateFormat
import java.util.*

class DataPicker(
    private val activity: Activity?,
    private val fragment: Fragment?,
    private var style: Int = R.style.DefaultDatePickerStyle,
    private var pattern: String = "yyyy-MM-dd",
    private var listener: OnDataSelectedListener? = null
) {

    /**
     * The user can create his own style if he wishes.
     * Until then, the default style is considered selected.
     * @see R.style.DefaultDatePickerStyle
     * */

    fun setDatePickerStyle(style: Int): DataPicker {
        this.style = style
        return this
    }

    /**
     * When the user completes the date selection process,
     * we send her the selected date information with interface.
     * @see OnDateSelectListener
     * */
    fun setListener(listener: OnDataSelectedListener): DataPicker {
        this.listener = listener
        return this
    }

    /**
     * The format in which the user-selected date will be converted.
     * If the user does not specify, the local format is accepted.
     * */

    fun setFormatType(type: String): DataPicker {
        this.pattern = type
        return this
    }

    fun showActivity() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR) // Current Year
        val month = calendar.get(Calendar.MONTH) // Current Month
        val day = calendar.get(Calendar.DAY_OF_MONTH) // Current Day

        // Create DatePickerDialog
        val datePickerActivity = this.activity?.let {
            DatePickerDialog(
                it, this.style,
                { _, year, month, dayOfMonth ->

                    // FormatDate
                    val formatDate = SimpleDateFormat(pattern, Locale.getDefault())

                    /**
                     * I get the incoming year, month and day information and
                     * set it to the Calendar object.
                     * */

                    /**
                     * I get the incoming year, month and day information and
                     * set it to the Calendar object.
                     * */
                    calendar.set(Calendar.YEAR, year)
                    calendar.set(Calendar.MONTH, month)
                    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                    /**
                     * I take the date information in date format and
                     * format it into String data type.
                     * */
                    /**
                     * I take the date information in date format and
                     * format it into String data type.
                     * */

                    try {
                        val formattedDate = formatDate.format(calendar.time) // Date Format
                        listener?.isSelected(formattedDate) // Send user
                    } catch (e: Exception) {
                        Log.e("Date Format Exception :", "${e.message}")
                    }

                }, year, month, day // Set current year / month / day
            )
        }
        try {
            /**
             * I'm showing it in a try block in case of an unexpected error.
             * */
            datePickerActivity?.show()
        } catch (e: Exception) {
            Log.e("Easy date picker exception :", "${e.message}")
        }
    }

    fun showFragment() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR) // Current Year
        val month = calendar.get(Calendar.MONTH) // Current Month
        val day = calendar.get(Calendar.DAY_OF_MONTH) // Current Day

        // Create DatePickerDialog
        val datePickerFragment = this.fragment?.let {
            it.context?.let { it ->
                DatePickerDialog(
                    it, this.style,
                    { _, year, month, dayOfMonth ->

                        // FormatDate
                        val formatDate = SimpleDateFormat(pattern, Locale.getDefault())

                        /**
                         * I get the incoming year, month and day information and
                         * set it to the Calendar object.
                         * */

                        /**
                         * I get the incoming year, month and day information and
                         * set it to the Calendar object.
                         * */
                        calendar.set(Calendar.YEAR, year)
                        calendar.set(Calendar.MONTH, month)
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                        /**
                         * I take the date information in date format and
                         * format it into String data type.
                         * */
                        /**
                         * I take the date information in date format and
                         * format it into String data type.
                         * */

                        try {
                            val formattedDate = formatDate.format(calendar.time) // Date Format
                            listener?.isSelected(formattedDate) // Send user
                        } catch (e: Exception) {
                            Log.e("Date Format Exception :", "${e.message}")
                        }

                    }, year, month, day // Set current year / month / day
                )
            }
        }
        try {
            /**
             * I'm showing it in a try block in case of an unexpected error.
             * */
            datePickerFragment?.show()
        } catch (e: Exception) {
            Log.e("Easy date picker exception :", "${e.message}")
        }
    }


}
