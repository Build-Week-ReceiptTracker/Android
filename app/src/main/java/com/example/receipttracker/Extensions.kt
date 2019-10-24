package com.example.receipttracker

import com.example.receipttracker.model.Receipt
import java.util.*

fun MutableList<Receipt>.filterByDate(startDate: String, endDate: String): MutableList<Receipt>{

    val actualStartDate = startDate.dateStringToDate()
    val actualEndDate = endDate.dateStringToDate()

    val returnList = this.filter {
        it.date_of_transaction.dateStringToDate() <= actualStartDate &&
                it.date_of_transaction.dateStringToDate() >= actualEndDate
    }
    return returnList as MutableList<Receipt>
}

fun MutableList<Receipt>.averageReceiptAmounts(): Double {
    var average = 0.0
    this.forEach {
        val amountDouble = it.amount_spent.split("$")[1].toDouble()
        average += amountDouble
    }
    average /= this.size
    return average
}

fun String.dateStringToDate(): Date {
    val dateList = this.split("-")[0]
    val dateYear = dateList[0].toInt()
    val dateMonth = dateList[1].toInt()
    val dateDayAndTime: String = dateList[2].toString()
    val dateDay = dateDayAndTime.split("T")[0].toInt()

    return Date(dateYear, dateMonth, dateDay)
}