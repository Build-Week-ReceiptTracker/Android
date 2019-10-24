package com.example.receipttracker

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.cloudinary.android.MediaManager
import com.example.receipttracker.room.ReceiptDatabase
import com.example.receipttracker.room.ReceiptRoomDao

class App: Application() {

    // TODO Access Shared Preferences

    //lateinit var mediaManager: MediaManager

    companion object{
        var repo: ReceiptRepository? = null
        const val NAME_PREF_KEY = "name_pref_key"
        const val TOKEN_PREF_KEY = "token_pref_key"
        lateinit var sharedPrefs: SharedPreferences
        var databaseDao: ReceiptRoomDao? = null
    }

    override fun onCreate() {
        super.onCreate()
        sharedPrefs = getSharedPreferences("Token", Context.MODE_PRIVATE)
        repo = ReceiptRepository()
        MediaManager.init(this)

        val database = ReceiptDatabase.getInstance(this)
        databaseDao = database?.receiptDao()
    }
}