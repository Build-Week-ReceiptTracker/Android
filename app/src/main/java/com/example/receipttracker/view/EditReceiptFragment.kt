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
import com.bumptech.glide.Glide
import com.example.receipttracker.R
import com.example.receipttracker.getDateFromEditTexts
import com.example.receipttracker.model.Receipt
import com.example.receipttracker.splitDate
import com.example.receipttracker.viewmodel.AddEditReceiptViewModel
import kotlinx.android.synthetic.main.edit_receipt_fragment.*


class EditReceiptFragment(receipt: Receipt) : Fragment() {

    val oldReceipt = receipt

    private lateinit var viewModel: AddEditReceiptViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.edit_receipt_fragment, container, false)
    }



    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(AddEditReceiptViewModel::class.java)

        val dateTriple: Triple<String, String, String> = oldReceipt?.date_of_transaction.splitDate()

        et_edit_amount_spent.setText(oldReceipt.amount_spent)
        et_edit_year_of_transaction.setText(dateTriple.first)
        et_edit_month_of_transaction.setText(dateTriple.second)
        et_edit_day_of_transaction.setText(dateTriple.third)
        et_edit_description.setText(oldReceipt.description)
        et_edit_merchant.setText(oldReceipt.merchant)
        et_edit_category.setText(oldReceipt.category)

        if(oldReceipt.image_url != null) {
            Glide.with(this.activity!!.applicationContext)
                .load(oldReceipt.image_url)
                .into(iv_edit_receipt_photo)
        }


        iv_edit_receipt_photo.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            startActivityForResult(intent, AddReceiptFragment.IMAGE_REQUEST_CODE)
        }

        button_edit_photo.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            startActivityForResult(intent, AddReceiptFragment.IMAGE_REQUEST_CODE)
        }

        button_save_receipt.setOnClickListener {
            viewModel.hideSoftKeyboard(requireActivity())
            val newReceipt = Receipt(
                et_edit_amount_spent.text.toString(),
                this.context!!.getDateFromEditTexts(et_edit_year_of_transaction, et_edit_month_of_transaction, et_edit_day_of_transaction),
                et_edit_category.text.toString(),
                et_edit_merchant.text.toString(),
                oldReceipt.image_url ?: "",
                viewModel.repo?.currentUser!!,
                et_edit_description.text.toString(),
                null
            )

            if(oldReceipt.id != null ) {

                viewModel.editReceipt(viewModel.repo?.currentToken!!, oldReceipt.id!!, newReceipt)
                    ?.observe(this, Observer {
                        if (it != null) {
                            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
                            if (it == "Successfully Edited Receipt") {
                                activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.nav_host_fragment, MyReceiptsFragment())?.commit()
                            } else {
                                viewModel.updateReceiptToToom(newReceipt)
                            }
                        }
                    })
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == AddReceiptFragment.IMAGE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val photoUri: Uri? = data?.data
            if (photoUri != null) {
                iv_edit_receipt_photo.setImageURI(Uri.parse(photoUri.toString()))
                viewModel.uploadReceiptPhoto(photoUri)?.observe(this, Observer {
                    button_save_receipt.isEnabled = false
                    if (it != AddEditReceiptViewModel.WAIT_KEY) {
                        button_save_receipt.isEnabled = true
                        if (it.isNotBlank()) {
                            oldReceipt.image_url = it
                            Toast.makeText(this.context, "Image Saved Successfully", Toast.LENGTH_LONG).show()
                        } else Toast.makeText(this.context, "Failed to upload image", Toast.LENGTH_LONG).show()
                    }
                })
            }
        }
    }
}
