package com.example.receipttracker.viewmodel

import android.app.Activity
import android.view.inputmethod.InputMethodManager
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

    fun hideSoftKeyboard(activity: Activity){
        val inputMethodManager: InputMethodManager = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(activity.currentFocus?.windowToken, 0)
    }
}