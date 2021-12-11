package com.example.marvelcharacters.utilities


import java.text.SimpleDateFormat
import java.util.*

object DateUtils {
    const val DATE_FORMAT = "dd/MM/yyyy HH:mm"

    fun getDateFormatted(date: Long?): String {
        val formatter = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())
        val calendar: Calendar = Calendar.getInstance()
        if (date != null) {
            calendar.timeInMillis = date
        }
        return formatter.format(calendar.time)
    }

}
