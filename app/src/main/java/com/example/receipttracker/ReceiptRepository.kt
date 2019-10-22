package com.example.receipttracker

import android.content.Context
import android.os.AsyncTask
import com.example.receipttracker.api.ReceiptApiDao
import com.example.receipttracker.model.Receipt
import com.example.receipttracker.room.ReceiptDatabase
import com.example.receipttracker.room.ReceiptRoomDao

class ReceiptRepository (context: Context) {

    private val receiptRoomDao: ReceiptRoomDao

    init {
        val database: ReceiptDatabase = ReceiptDatabase.getInstance(context)!!
        receiptRoomDao = database.receiptDao()
    }

    fun login(username: String, password: String){
        ReceiptApiDao.apiDaoInstance?.login(username, password)
    }

    fun register(username: String, password: String, email: String){
        ReceiptApiDao.apiDaoInstance?.register(username, password, email)
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