package com.example.receipttracker

import android.content.Context
import android.os.AsyncTask
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.receipttracker.api.ReceiptApiDao
import com.example.receipttracker.model.Receipt
import com.example.receipttracker.room.ReceiptDatabase
import com.example.receipttracker.room.ReceiptRoomDao

class ReceiptRepository (context: Context) {

    private val receiptRoomDao: ReceiptRoomDao

    lateinit var currentUser: String
    lateinit var currentToken: String

    init {
        val database: ReceiptDatabase = ReceiptDatabase.getInstance(context)!!
        receiptRoomDao = database.receiptDao()
        currentUser = App.sharedPrefs.getString(App.NAME_PREF_KEY, "").toString()
        currentToken = App.sharedPrefs.getString(App.TOKEN_PREF_KEY, "").toString()
    }

    fun resetUserAndToken() {
        if (App.sharedPrefs.getString(App.NAME_PREF_KEY, "") != null && App.sharedPrefs.getString(App.TOKEN_PREF_KEY, "") != null) {
            currentUser = App.sharedPrefs.getString(App.NAME_PREF_KEY, "").toString()
            currentToken = App.sharedPrefs.getString(App.TOKEN_PREF_KEY, "").toString()
        }
    }

    fun login(username: String, password: String): MutableLiveData<Boolean>?{
        return ReceiptApiDao.apiDaoInstance?.login(username, password)
    }

    fun register(username: String, password: String, email: String){
        ReceiptApiDao.apiDaoInstance?.register(username, password, email)
    }

    fun addReceipt(token: String, receipt: Receipt): MutableLiveData<String>? {
        return ReceiptApiDao.apiDaoInstance?.addReceipt(token, receipt)
    }

    fun getAllReceipts(token: String): MutableLiveData<MutableList<Receipt>>? {
        return ReceiptApiDao.apiDaoInstance?.getAllReceipts(token)
    }

    fun deleteReceipt(token: String, id: Int): MutableLiveData<String>? {
       return ReceiptApiDao.apiDaoInstance?.deleteReceipt(token, id)
    }

    fun editReceipt(token: String, id: Int, receipt: Receipt): MutableLiveData<String>? {
        return ReceiptApiDao.apiDaoInstance?.editReceipt(token, id, receipt)
    }

    fun insert(receipt: Receipt) {
        InsertReceiptAsyncTask(receiptRoomDao).execute(receipt)
    }

    fun update(receipt: Receipt) {
        UpdateReceiptAsyncTask(receiptRoomDao).execute(receipt)
    }

    fun deleteReceiptById(receipt: Receipt) {
        DeleteReceiptByIdAsyncTask(receiptRoomDao).execute(receipt.id)
    }

    fun deleteAllReceipts() {
        DeleteAllReceiptsAsyncTask(receiptRoomDao).execute()
    }

    fun getAllReceipts(): MutableList<Receipt> {
        return GetAllReceiptsAsyncTask(receiptRoomDao).execute().get()
    }

    companion object {

        private class InsertReceiptAsyncTask(receiptRoomDao: ReceiptRoomDao) : AsyncTask<Receipt, Unit, Unit>() {
            val dao = receiptRoomDao

            override fun doInBackground(vararg p0: Receipt?) {
                dao.insert(p0[0]!!)
            }
        }

        private class UpdateReceiptAsyncTask(receiptRoomDao: ReceiptRoomDao) : AsyncTask<Receipt, Unit, Unit>() {
            val dao = receiptRoomDao

            override fun doInBackground(vararg p0: Receipt?) {
                dao.update(p0[0]!!)
            }
        }

        private class GetAllReceiptsAsyncTask(receiptRoomDao: ReceiptRoomDao) : AsyncTask<Receipt, Unit, MutableList<Receipt>>() {
            val dao = receiptRoomDao

            override fun doInBackground(vararg p0: Receipt?): MutableList<Receipt> {
                val receipts = dao.getAllReceipts()
                return receipts
            }

            override fun onPostExecute(result: MutableList<Receipt>?) {
                super.onPostExecute(result)
                val receipts = result
            }
        }

        private class DeleteReceiptByIdAsyncTask(receiptRoomDao: ReceiptRoomDao) : AsyncTask<Int, Unit, Unit>() {
            val dao = receiptRoomDao

            override fun doInBackground(vararg p0: Int?) {
                dao.deleteReceiptById(p0[0]!!)
            }
        }

        private class DeleteAllReceiptsAsyncTask(receiptRoomDao: ReceiptRoomDao) : AsyncTask<Int, Unit, Unit>() {
            val dao = receiptRoomDao

            override fun doInBackground(vararg p0: Int?) {
                dao.deleteAllReceipts()
            }
        }
    }
}