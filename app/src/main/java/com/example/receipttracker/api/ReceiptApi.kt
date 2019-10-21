package com.example.receipttracker.api

import com.example.receipttracker.model.Token
import com.example.receipttracker.model.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ReceiptApi {

    @POST("/api/login")
    fun loginUser(@Body user: User): Call<Token>
    //TODO: add all necessary http calls

    @POST("/api/register")
    fun registerUser(@Body user: User): Call<Void>
}