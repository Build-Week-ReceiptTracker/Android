package com.example.receipttracker

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.receipttracker.model.Receipt

class EditReceiptViewModel : ViewModel() {

    val repo = App.repo

    fun editReceipt(token: String, id: Int, receipt: Receipt): MutableLiveData<String>? {
        return repo?.editReceipt(token, id, receipt)
    }
}
