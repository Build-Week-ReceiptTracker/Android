package com.example.receipttracker

import android.content.Context
import com.example.receipttracker.api.ReceiptApiDao

class ReceiptRepository (context: Context) {

    fun login(username: String, password: String){
        ReceiptApiDao.apiDaoInstance?.login(username, password)
    }

    fun register(username: String, password: String, email: String){
        ReceiptApiDao.apiDaoInstance?.register(username, password, email)
    }
}