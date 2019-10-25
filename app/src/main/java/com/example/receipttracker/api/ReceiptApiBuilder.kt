package com.example.receipttracker.api

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object ReceiptApiBuilder {

    private const val BASE_URL = "https://api-receipt-tracker.herokuapp.com/"

    private val retrofit = Retrofit
        .Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    val receiptApi = retrofit.create(ReceiptApi::class.java)
}