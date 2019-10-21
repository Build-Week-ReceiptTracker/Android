package com.example.receipttracker.api

class ReceiptApiDao {

    //TODO: add necessary retrofitcalls

    companion object {
        var apiDaoInstance: ReceiptApiDao? = null
            private set
            get() {
                if (field == null) apiDaoInstance = ReceiptApiDao()
                return field
            }
    }
}