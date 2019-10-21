package com.example.receipttracker.model

import java.util.*

class Receipt (
    var purchaseAmount: Double,
    var date: Date,
    var category: String,
    var merchant: String,
    var image: String?
    )