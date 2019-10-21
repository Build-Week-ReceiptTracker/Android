package com.example.receipttracker.model

import androidx.room.Entity
import java.util.*

@Entity(tableName = "receipt_table")
class Receipt (
    var purchaseAmount: Double,
    var date: Date,
    var category: String,
    var merchant: String,
    var image: String?
    )