package com.example.receipttracker.view

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.receipttracker.R
import com.example.receipttracker.model.Receipt
import com.example.receipttracker.viewmodel.MyReceiptsViewModel
import kotlinx.android.synthetic.main.my_receipts_fragment.*
import kotlinx.android.synthetic.main.receipt_item_list_view.view.*

class MyReceiptsFragment : Fragment() {

    companion object {
        fun newInstance() = MyReceiptsFragment()
    }

    private lateinit var viewModel: MyReceiptsViewModel
    lateinit var receiptListAdapter: ReceiptListAdapter
    private var receiptList = mutableListOf<Receipt>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.my_receipts_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MyReceiptsViewModel::class.java)
        // TODO: Use the ViewModel

        receiptListAdapter = ReceiptListAdapter(receiptList)
        rv_my_receipts.apply {
            setHasFixedSize(false)
            layoutManager = LinearLayoutManager(context)
            adapter = receiptListAdapter
        }

        viewModel.getAllReceipts(viewModel.repo?.currentToken!!)?.observe(this, Observer {
            if(it != null) {
                Log.i("BIGBRAIN", it[0].amount_spent)
                receiptList = it
                Log.i("BIGBRAIN", receiptList[0].amount_spent)
                rv_my_receipts.adapter = ReceiptListAdapter(receiptList)
            } else {
                Toast.makeText(this.context, "Failed to get receipts", Toast.LENGTH_LONG).show()
            }
        })
    }

    inner class ReceiptListAdapter(private val receipts: MutableList<Receipt>) : RecyclerView.Adapter<ReceiptListAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.receipt_item_list_view, parent, false)
            return ViewHolder(view)
        }

        override fun getItemCount(): Int = receipts.size

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val data = receipts[position]
            holder.amountView.text = data.amount_spent.toString()
            holder.categoryView.text = data.category
            holder.dateView.text = data.date_of_transaction.toString()
            holder.merchantView.text = data.merchant
        }

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val dateView: TextView = view.text_receipt_date
            val amountView: TextView = view.text_receipt_amount
            val merchantView: TextView = view.text_receipt_merchant
            val categoryView: TextView = view. text_receipt_category
        }
    }
}
