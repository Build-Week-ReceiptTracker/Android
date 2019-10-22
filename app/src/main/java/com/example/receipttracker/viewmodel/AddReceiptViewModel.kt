package com.example.receipttracker.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.receipttracker.App
import com.example.receipttracker.model.Receipt

class AddReceiptViewModel : ViewModel() {
    // TODO: Implement the ViewModel

    val repo = App.repo

    fun addReceipt(token: String, receipt: Receipt): MutableLiveData<String>? {
        return repo?.addReceipt(token, receipt)
    }


    fun deleteReceipt(token: String, id: Int) {
        repo?.deleteReceipt(token, id)
    }

    fun editReceipt(token: String, id: Int, receipt: Receipt) {
        repo?.editReceipt(token, id, receipt)
    }
}
