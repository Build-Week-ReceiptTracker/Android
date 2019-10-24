package com.example.receipttracker.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.receipttracker.App
import com.example.receipttracker.model.Receipt

class ChartViewModel : ViewModel() {
    // TODO: Implement the ViewModel

    val repo = App.repo

    fun getFullListOfReceipts(): MutableLiveData<MutableList<Receipt>>? {
        return repo?.getAllReceipts(repo.currentToken)
    }
}
