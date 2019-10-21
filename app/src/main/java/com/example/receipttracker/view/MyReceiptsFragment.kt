package com.example.receipttracker.view

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.receipttracker.R
import com.example.receipttracker.viewmodel.MyReceiptsViewModel

class MyReceiptsFragment : Fragment() {

    companion object {
        fun newInstance() = MyReceiptsFragment()
    }

    private lateinit var viewModel: MyReceiptsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.my_receipts_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MyReceiptsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
