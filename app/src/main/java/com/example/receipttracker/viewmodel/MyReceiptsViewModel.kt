package com.example.receipttracker.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.receipttracker.App
import com.example.receipttracker.model.Receipt

class MyReceiptsViewModel : ViewModel() {
    val repo = App.repo

    fun getAllReceipts(token: String): MutableLiveData<MutableList<Receipt>>? {
        return repo?.getAllReceipts(token)
    }


    fun deleteReceipt(token: String, id: Int): MutableLiveData<String>? {
        return repo?.deleteReceipt(token, id)
    }

    fun isUserStringInReceipt(receipt: Receipt, userSearch: String): Boolean{
        return receipt.amount_spent.toLowerCase().contains(userSearch.toLowerCase()) ||
                receipt.date_of_transaction.toLowerCase().contains(userSearch.toLowerCase()) ||
                receipt.category.toLowerCase().contains(userSearch.toLowerCase()) ||
                receipt.merchant.toLowerCase().contains(userSearch.toLowerCase()) ||
                receipt.description.toLowerCase().contains(userSearch.toLowerCase())
    }

}
