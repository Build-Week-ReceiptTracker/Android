package com.example.receipttracker.api

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.receipttracker.App
import com.example.receipttracker.model.PostReceiptResponse
import com.example.receipttracker.model.Receipt
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

    val addReceiptReponse = MutableLiveData<String>()
    val editReceiptResponse = MutableLiveData<String>()
    val deleteReceiptResponse = MutableLiveData<String>()
    val getAllReceiptsResponse = MutableLiveData<MutableList<Receipt>>()

    //TODO: add necessary retrofitcalls

    fun login(username: String, password: String){
        ReceiptApiBuilder.receiptApi.loginUser(User(username, password)).enqueue(object :
            Callback<Token>{
            override fun onFailure(call: Call<Token>, t: Throwable) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onResponse(call: Call<Token>, response: Response<Token>) {
                val token = response.body()
                if (token != null){
                    val userToken = token.token
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

    fun addReceipt(token: String, receipt: Receipt): MutableLiveData<String> {
        Log.i("BIGBRAIN", token)
        ReceiptApiBuilder.receiptApi.addReceipt(token, receipt).enqueue(object : Callback<PostReceiptResponse> {
            override fun onFailure(call: Call<PostReceiptResponse>, t: Throwable) {
                Log.i("BIGBRAIN", t.toString())
                addReceiptReponse.value = "Failed to add receipt $t"
            }

            override fun onResponse(call: Call<PostReceiptResponse>, response: Response<PostReceiptResponse>) {
                Log.i("BIGBRAIN", response.toString() + response.body()?.receiptID + response.body()?.message)
                if (response.isSuccessful) {
                    addReceiptReponse.value = "Receipt Added"
                } else {
                    addReceiptReponse.value = "Failed to add receipt ${response.errorBody()}"
                }
            }
        })
        return addReceiptReponse
    }

    fun getAllReceipts(token: String): MutableLiveData<MutableList<Receipt>> {
        ReceiptApiBuilder.receiptApi.getAllReceipts(token).enqueue(object: Callback<MutableList<Receipt>>{

            override fun onFailure(call: Call<MutableList<Receipt>>, t: Throwable) {
                getAllReceiptsResponse.value = null
            }

            override fun onResponse(call: Call<MutableList<Receipt>>, response: Response<MutableList<Receipt>>) {
                if(response.isSuccessful) {
                    getAllReceiptsResponse.value = response.body()
                } else {
                    getAllReceiptsResponse.value = null
                }
            }
        })
        return getAllReceiptsResponse
    }

    fun deleteReceipt(token: String, id: Int) {
        ReceiptApiBuilder.receiptApi.deleteReceipt(token, id)
    }

    fun editReceipt(token: String, id: Int, receipt: Receipt) {
        ReceiptApiBuilder.receiptApi.editReceipt(token, id, receipt)
    }
}