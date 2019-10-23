package com.example.receipttracker.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.receipttracker.App

class LoginViewModel: ViewModel(){

    fun login(username: String, password: String): MutableLiveData<Boolean>? {
        return App.repo?.login(username, password)
    }

    fun register(username: String, password: String, email: String){
        App.repo?.register(username, password, email)
    }
}