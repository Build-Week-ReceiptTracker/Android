package com.example.receipttracker.api

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.receipttracker.App
import com.example.receipttracker.R
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

        const val RECEIPT_ADDED_KEY = "Receipt Added"
        const val RECEIPT_FAILED_KEY = "Failed to add Receipt"
        const val REGISTER_SUCCESS_KEY = "Registered Successfully"
        const val REGISTER_FAILED_KEY = "Failed to Register"
        const val WAIT_KEY = "WAITING"
    }

    val addReceiptReponse = MutableLiveData<String>()
    val editReceiptResponse = MutableLiveData<String>()
    val deleteReceiptResponse = MutableLiveData<String>()
    val getAllReceiptsResponse = MutableLiveData<MutableList<Receipt>>()
    val loginResponse = MutableLiveData<Boolean>()
    val registerResponse = MutableLiveData<String>()

    fun login(username: String, password: String): MutableLiveData<Boolean>{
        loginResponse.value = null
        ReceiptApiBuilder.receiptApi.loginUser(User(username, password)).enqueue(object :
            Callback<Token>{
            override fun onFailure(call: Call<Token>, t: Throwable) {
                Log.i("BIGBRAIN", t.toString())
                loginResponse.value = false
            }

            override fun onResponse(call: Call<Token>, response: Response<Token>) {
                if (response.isSuccessful) {
                    val token = response.body()
                    if (token != null) {
                        Log.i("BIGBRAIN", response.body()?.token.toString())
                        val userToken = response.body()?.token.toString()
                        saveTokenAndUser(userToken, username)
                        loginResponse.value = true
                    }
                } else {
                    Log.i("BIGBRAIN", response.body()?.message ?: "Response Not Successful")
                    loginResponse.value = false
                }
            }
        })
        return loginResponse
    }

    fun saveTokenAndUser(token: String, username: String){
        App.sharedPrefs.edit().remove(App.NAME_PREF_KEY).apply()
        App.sharedPrefs.edit().remove(App.TOKEN_PREF_KEY).apply()
        App.sharedPrefs.edit().putString(App.TOKEN_PREF_KEY, token).apply()
        App.sharedPrefs.edit().putString(App.NAME_PREF_KEY, username).apply()
        App.repo?.resetUserAndToken()

    }

    fun register(username: String, password: String, email: String): MutableLiveData<String>{
        registerResponse.value = WAIT_KEY

        ReceiptApiBuilder.receiptApi.registerUser(User(username, password, email)).enqueue(object : Callback<Void>{
            override fun onFailure(call: Call<Void>, t: Throwable) {
                registerResponse.value = REGISTER_FAILED_KEY
            }

            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                registerResponse.value = REGISTER_SUCCESS_KEY
            }
        })

        return registerResponse
    }

    fun addReceipt(token: String, receipt: Receipt): MutableLiveData<String> {

        addReceiptReponse.value = WAIT_KEY
        Log.i("BIGBRAIN", token)
        ReceiptApiBuilder.receiptApi.addReceipt(token, receipt).enqueue(object : Callback<PostReceiptResponse> {
            override fun onFailure(call: Call<PostReceiptResponse>, t: Throwable) {
                Log.i("BIGBRAIN", t.toString())
                addReceiptReponse.value = "Failed to add receipt $t"
            }

            override fun onResponse(call: Call<PostReceiptResponse>, response: Response<PostReceiptResponse>) {
                Log.i("BIGBRAIN", response.toString() + response.body()?.receiptID + response.body()?.message)
                if (response.isSuccessful) {
                    addReceiptReponse.value = RECEIPT_ADDED_KEY
                } else {
                    addReceiptReponse.value = RECEIPT_FAILED_KEY
                }
            }
        })
        return addReceiptReponse
    }

    fun getAllReceipts(token: String): MutableLiveData<MutableList<Receipt>> {
        ReceiptApiBuilder.receiptApi.getAllReceipts(token).enqueue(object: Callback<MutableList<Receipt>>{

            override fun onFailure(call: Call<MutableList<Receipt>>, t: Throwable) {
                getAllReceiptsResponse.value = null
                Log.i("BIGBRAIN", t.toString())
            }

            override fun onResponse(call: Call<MutableList<Receipt>>, response: Response<MutableList<Receipt>>) {
                if(response.isSuccessful) {
                    getAllReceiptsResponse.value = response.body()
                } else {
                    getAllReceiptsResponse.value = null
                    Log.i("BIGBRAIN", response.toString())
                }
            }
        })
        return getAllReceiptsResponse
    }

    fun deleteReceipt(token: String, id: Int): MutableLiveData<String> {
        ReceiptApiBuilder.receiptApi.deleteReceipt(token, id.toString()).enqueue(object : Callback<Void> {

            override fun onFailure(call: Call<Void>, t: Throwable) {
                deleteReceiptResponse.value = "Failed to connect to API"
            }

            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    deleteReceiptResponse.value = "Successfully Deleted"
                    getAllReceipts(App.repo?.currentToken!!)

                } else {
                    deleteReceiptResponse.value = "Failed to delete from API"
                    Log.i("BIGBRAIN", response.message())
                }
            }
        })
        return deleteReceiptResponse
    }

    fun editReceipt(token: String, id: Int, receipt: Receipt): MutableLiveData<String> {
        ReceiptApiBuilder.receiptApi.editReceipt(token, id, receipt).enqueue(object: Callback<Void> {

            override fun onFailure(call: Call<Void>, t: Throwable) {
                editReceiptResponse.value = "Failed to connect to API"
            }

            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    editReceiptResponse.value = "Successfully Edited Receipt"
                } else {
                    editReceiptResponse.value = "Failed to edit receipt on API"
                }
            }
        })
        return editReceiptResponse
    }
}