package com.example.receipttracker.api

import android.widget.Toast
import com.example.receipttracker.App
import com.example.receipttracker.model.Token
import com.example.receipttracker.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReceiptApiDao {

    companion object {
        var apiDaoInstance: ReceiptApiDao? = null
            private set
            get() {
                if (field == null) apiDaoInstance = ReceiptApiDao()
                return field
            }
    }

    //TODO: add necessary retrofitcalls

    lateinit var userToken: String

    fun login(username: String, password: String){
        ReceiptApiBuilder.receiptApi.loginUser(User(username, password)).enqueue(object :
            Callback<Token>{
            override fun onFailure(call: Call<Token>, t: Throwable) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onResponse(call: Call<Token>, response: Response<Token>) {
                val token = response.body()
                if (token != null){
                    userToken = token.token
                    saveTokenAndUser(userToken, username)
                }
            }
        })
    }

    fun saveTokenAndUser(token: String, username: String){
        App.sharedPrefs.edit().putString(App.TOKEN_PREF_KEY, token).apply()
        App.sharedPrefs.edit().putString(App.NAME_PREF_KEY, username).apply()
    }

    fun register(username: String, password: String, email: String){
        ReceiptApiBuilder.receiptApi.registerUser(User(username, password, email)).enqueue(object : Callback<Void>{
            override fun onFailure(call: Call<Void>, t: Throwable) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                val int = 0
            }
        })
    }

    fun addReceipt() {}

    fun getAllReceipts() {}

    fun deleteReceipt() {}

    fun editReceipt() {}
}