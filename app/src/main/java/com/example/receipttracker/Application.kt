package com.example.receipttracker

import android.app.Application
import android.content.Context
import android.content.SharedPreferences

class App: Application() {

    // TODO Access Shared Preferences

    companion object{
        var repo: ReceiptRepository? = null
        const val NAME_PREF_KEY = "name_pref_key"
        const val TOKEN_PREF_KEY = "token_pref_key"
        lateinit var sharedPrefs: SharedPreferences
    }

    override fun onCreate() {
        super.onCreate()
        sharedPrefs = getSharedPreferences("Token", Context.MODE_PRIVATE)
        repo = ReceiptRepository(this)


    }
}