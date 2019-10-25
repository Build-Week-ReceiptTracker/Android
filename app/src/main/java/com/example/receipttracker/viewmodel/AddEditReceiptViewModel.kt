package com.example.receipttracker.viewmodel

import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cloudinary.android.MediaManager
import com.cloudinary.android.callback.ErrorInfo
import com.cloudinary.android.callback.UploadCallback
import com.example.receipttracker.App
import com.example.receipttracker.model.Receipt
import java.net.URI

class AddEditReceiptViewModel : ViewModel() {

    val repo = App.repo
    companion object {
        const val WAIT_KEY = "WAITING"
    }

    fun addReceipt(token: String, receipt: Receipt): MutableLiveData<String>? {
        return repo?.addReceipt(token, receipt)
    }

    fun editReceipt(token: String, id: Int, receipt: Receipt): MutableLiveData<String>? {
        return repo?.editReceipt(token, id, receipt)
    }

    fun addReceiptToRoom(receipt: Receipt) {
        repo?.insert(receipt)
    }

    fun updateReceiptToToom(receipt: Receipt) {
        repo?.update(receipt)
    }

    fun uploadReceiptPhoto(photoUri: Uri): MutableLiveData<String>? {

        var imageUrl = MutableLiveData<String>()
        imageUrl.value = WAIT_KEY

        MediaManager.get().upload(photoUri)
            .unsigned("unsigned_upload")
            .callback(object : UploadCallback {
                override fun onSuccess(requestId: String?, resultData: MutableMap<Any?, Any?>?) {
                    val url = resultData?.get("url")
                    imageUrl.value = url.toString()
                    Log.i("BIGBRAIN", url.toString())
                }

                override fun onProgress(requestId: String?, bytes: Long, totalBytes: Long) {
                    Log.i("BIGBRAIN", "progress")
                }

                override fun onReschedule(requestId: String?, error: ErrorInfo?) {
                    Log.i("BIGBRAIN", "reschedule")
                }

                override fun onError(requestId: String?, error: ErrorInfo?) {
                    Log.i("BIGBRAIN", "${error?.code}  ${error?.description}")

                    //Toast.makeText(this@AddReceiptFragment.context, "Failed to upload photo", Toast.LENGTH_LONG).show()
                    imageUrl.value = ""

                }

                override fun onStart(requestId: String?) {
                    Log.i("BIGBRAIN", "start")
                }
            })
            .dispatch()
        return imageUrl
    }
}
