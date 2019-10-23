package com.example.receipttracker.view

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import com.example.receipttracker.viewmodel.AddEditReceiptViewModel
import com.example.receipttracker.R
import com.example.receipttracker.model.Receipt
import com.example.receipttracker.viewmodel.AddEditReceiptViewModel.Companion.WAIT_KEY
import kotlinx.android.synthetic.main.add_receipt_fragment.*


class AddReceiptFragment : Fragment() {

    companion object {
        fun newInstance() = AddReceiptFragment()
        const val IMAGE_REQUEST_CODE = 42
    }

    private var imageUrl: String? = null
    private lateinit var viewModel: AddEditReceiptViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.add_receipt_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(AddEditReceiptViewModel::class.java)

        button_add_photo.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            startActivityForResult(intent, IMAGE_REQUEST_CODE)
        }

        button_add_receipt.setOnClickListener {
            val newReceipt = Receipt(
                et_amount_spent.text.toString(),
                et_date_of_transaction.text.toString(),
                et_category.text.toString(),
                et_merchant.text.toString(),
                imageUrl ?: "",
                viewModel.repo?.currentUser!!,
                et_description.text.toString(),
                null
            )

            viewModel.addReceipt(viewModel.repo?.currentToken!!, newReceipt)?.observe(this, Observer {
                if (it != null) {
                    Toast.makeText(this.context, it, Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMAGE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val photoUri: Uri? = data?.data
            if (photoUri != null) {
                iv_receipt_photo.setImageURI(Uri.parse(photoUri.toString()))
                viewModel.uploadReceiptPhoto(photoUri)?.observe(this, Observer {
                    if (it != WAIT_KEY) {
                        if (it.isNotBlank()) {
                            imageUrl = it
                        } else Toast.makeText(this.context, "Failed to upload image", Toast.LENGTH_LONG).show()
                    }
                })
            }
        }
    }
}
