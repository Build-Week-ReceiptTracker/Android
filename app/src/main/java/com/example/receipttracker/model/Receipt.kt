package com.example.receipttracker.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "receipt_table")
class Receipt (
    var amount_spent: Double,
    var date_of_transaction: Date,
    var category: String,
    var merchant: String,
    var image: String?,

    @PrimaryKey
    var id: Int?
    )