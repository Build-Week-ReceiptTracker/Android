package com.example.receipttracker.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.*

@Entity(tableName = "receipt_table")
class Receipt (
    var amount_spent: String,
    var date_of_transaction: String = "2019-10-25",
    var category: String,
    var merchant: String,
    var image_url: String?,
    var user_username: String,
    var description: String,

    @PrimaryKey
    var id: Int?
    ): Serializable

data class User(val username: String, val password: String, val email: String? = null)

data class Token(val token: String, val message: String)

data class PostReceiptResponse(val receiptID: String, val message: String)