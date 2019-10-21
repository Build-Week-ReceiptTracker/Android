package com.example.receipttracker

import android.app.Application

class App: Application() {

    companion object{
        var repo: ReceiptRepository? = null
    }

    override fun onCreate() {
        super.onCreate()
        repo = ReceiptRepository(this)
    }
}