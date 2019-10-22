package com.example.receipttracker

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import com.example.receipttracker.model.Receipt
import kotlinx.android.synthetic.main.add_receipt_fragment.*
import kotlinx.android.synthetic.main.edit_receipt_fragment.*


class EditReceiptFragment(receipt: Receipt) : Fragment() {

    val oldReceipt = receipt

    

    private lateinit var viewModel: EditReceiptViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.edit_receipt_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(EditReceiptViewModel::class.java)

        //oldReceipt = arguments?.getSerializable("RECEIPT") as Receipt?
        Log.i("BIGBRAIN", oldReceipt?.amount_spent ?: "null af")
        et_edit_amount_spent.setText(oldReceipt?.amount_spent)
        et_edit_date_of_transaction.setText(oldReceipt?.date_of_transaction)
        et_edit_description.setText(oldReceipt?.description)
        et_edit_merchant.setText(oldReceipt?.merchant)
        et_edit_category.setText(oldReceipt?.category)


        button_save_receipt.setOnClickListener {
            val newReceipt = Receipt(
                et_edit_amount_spent.text.toString(),
                et_edit_date_of_transaction.text.toString(),
                et_edit_category.text.toString(),
                et_edit_merchant.text.toString(),
                "",
                viewModel.repo?.currentUser!!,
                et_edit_description.text.toString(),
                null
            )

            if(oldReceipt.id != null ) {


                viewModel.editReceipt(viewModel.repo?.currentToken!!, oldReceipt.id!!, newReceipt)
                    ?.observe(this, Observer {
                        if (it != null) {
                            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
                        }
                    })
            }
        }
    }

}
