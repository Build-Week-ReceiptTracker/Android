package com.example.receipttracker.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "receipt_table")
class Receipt (
    var amount_spent: String,
    var date_of_transaction: String,
    var category: String,
    var merchant: String,
    var image: String?,

    @PrimaryKey
    var id: Int?
    )

data class User(val username: String, val password: String, val email: String? = null)

data class Token(val token: String, val message: String)