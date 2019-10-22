package com.example.receipttracker.view

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import com.example.receipttracker.viewmodel.AddReceiptViewModel
import com.example.receipttracker.R
import com.example.receipttracker.model.Receipt
import kotlinx.android.synthetic.main.add_receipt_fragment.*


class AddReceiptFragment : Fragment() {

    companion object {
        fun newInstance() = AddReceiptFragment()
    }

    private lateinit var viewModel: AddReceiptViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.add_receipt_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(AddReceiptViewModel::class.java)
        // TODO: Use the ViewModel

        button_add_receipt.setOnClickListener {
            val newReceipt = Receipt(
                et_amount_spent.text.toString(),
                et_date_of_transaction.text.toString(),
                et_category.text.toString(),
                et_merchant.text.toString(),
                "",
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

}
