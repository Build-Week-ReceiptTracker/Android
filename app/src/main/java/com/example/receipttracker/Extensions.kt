package com.example.receipttracker

import android.content.Context
import android.widget.EditText
import com.example.receipttracker.model.Receipt
import java.util.*

fun MutableList<Receipt>.filterByDate(startDate: String, endDate: String): MutableList<Receipt>{

    val actualStartDate = startDate.dateStringToDate()
    val actualEndDate = endDate.dateStringToDate()

    val returnList = this.filter {
        (it.date_of_transaction.dateStringToDate()).time >= actualStartDate.time &&
                (it.date_of_transaction.dateStringToDate()).time <= actualEndDate.time
    }
    return returnList as MutableList<Receipt>
}

fun MutableList<Receipt>.averageReceiptAmounts(): Double {
    var average: Double = 0.0
    if (this.size > 0) {
        this.forEach {
            val amountDouble = it.amount_spent.split("$")[1].toDouble()
            average += amountDouble
        }

        average /= this.size
    }

    return average
}

fun String.dateStringToDate(): Calendar {
    val dateList = this.split("-")
    val dateYear = dateList[0].toInt()
    val dateMonth = dateList[1].toInt()
    val dateDayAndTime: String = dateList[2].toString()
    val dateDay = dateDayAndTime.split("T")[0].toInt()

    val calendar = Calendar.getInstance()

    val date = Date(dateYear, dateMonth, dateDay)
    calendar.set(dateYear, dateMonth, dateDay)
    calendar.time = date
    return calendar
}

fun String.dateStringIncrementDay(): String {
    val dateList = this.split("-")
    val dateYear = dateList[0].toInt()
    val dateMonth = dateList[1].toInt()
    val dateDayAndTime: String = dateList[2].toString()
    var dateDay = dateDayAndTime.split("T")[0].toInt()

    val dateDayIncremented = ++dateDay

    return if (dateDayIncremented < 10) {
        "$dateYear-$dateMonth-0$dateDayIncremented"
    } else "$dateYear-$dateMonth-$dateDayIncremented"
}

fun Context.getDateFromEditTexts(et_year: EditText, et_month: EditText, et_day: EditText): String {
    return "${et_year.text}-${et_month.text}-${et_day.text}"
}