package com.example.receipttracker

import android.os.AsyncTask
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.receipttracker.api.ReceiptApiDao
import com.example.receipttracker.model.Receipt
import com.example.receipttracker.room.ReceiptRoomDao

class ReceiptRepository {

    val receiptRoomDao: ReceiptRoomDao by lazy {
        App.databaseDao!!
    }
    lateinit var currentUser: String
    lateinit var currentToken: String

    fun initiate(){
        currentUser = App.sharedPrefs.getString(App.NAME_PREF_KEY, "").toString()
        currentToken = App.sharedPrefs.getString(App.TOKEN_PREF_KEY, "").toString()
    }

    fun resetUserAndToken() {
        initiate()
        if (App.sharedPrefs.getString(App.NAME_PREF_KEY, "") != null && App.sharedPrefs.getString(App.TOKEN_PREF_KEY, "") != null) {
            currentUser = App.sharedPrefs.getString(App.NAME_PREF_KEY, "").toString()
            currentToken = App.sharedPrefs.getString(App.TOKEN_PREF_KEY, "").toString()
        }
    }

    fun login(username: String, password: String, instance: ReceiptApiDao? = ReceiptApiDao.apiDaoInstance): MutableLiveData<Boolean>?{
        return instance?.login(username, password)
    }

    fun register(username: String, password: String, email: String): MutableLiveData<String>? {
        return ReceiptApiDao.apiDaoInstance?.register(username, password, email)
    }

    fun addReceipt(token: String, receipt: Receipt, instance: ReceiptApiDao? = ReceiptApiDao.apiDaoInstance): MutableLiveData<String>? {
        return instance?.addReceipt(token, receipt)
    }

    fun getAllReceipts(token: String, instance: ReceiptApiDao? = ReceiptApiDao.apiDaoInstance): MutableLiveData<MutableList<Receipt>>? {
        return instance?.getAllReceipts(token)
    }

    fun deleteReceipt(token: String, id: Int, instance: ReceiptApiDao? = ReceiptApiDao.apiDaoInstance): MutableLiveData<String>? {
       return instance?.deleteReceipt(token, id)
    }

    fun editReceipt(token: String, id: Int, receipt: Receipt, instance: ReceiptApiDao? = ReceiptApiDao.apiDaoInstance): MutableLiveData<String>? {
        return instance?.editReceipt(token, id, receipt)
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