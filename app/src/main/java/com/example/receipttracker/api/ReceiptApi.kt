package com.example.receipttracker.api

import com.example.receipttracker.model.PostReceiptResponse
import com.example.receipttracker.model.Receipt
import com.example.receipttracker.model.Token
import com.example.receipttracker.model.User
import retrofit2.Call
import retrofit2.http.*

interface ReceiptApi {

    @POST("/api/login")
    fun loginUser(@Body user: User): Call<Token>
    //TODO: add all necessary http calls

    @POST("/api/register")
    fun registerUser(@Body user: User): Call<Void>

    @POST("api/auth/receipts/add")
    fun addReceipt(@Header("Authorization") token: String, @Body receipt: Receipt): Call<PostReceiptResponse>

    @GET("/api/auth/receipts/all")
    fun getAllReceipts(@Header("Authorization") token: String): Call<MutableList<Receipt>>

    @DELETE("/api/auth/receipts/{id}/del")
    fun deleteReceipt(@Header("Authorizaiton") token: String, @Path("id") id: Int): Call<Void>

    @PUT("/api/auth/receipts/{id}/")
    fun editReceipt(@Header("Authorization") token: String, @Path("id") id: Int, @Body receipt: Receipt): Call<Void>

}