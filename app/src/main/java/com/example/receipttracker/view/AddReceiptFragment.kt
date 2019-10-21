package com.example.receipttracker.view

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.receipttracker.viewmodel.AddReceiptViewModel
import com.example.receipttracker.R


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
    }

}
