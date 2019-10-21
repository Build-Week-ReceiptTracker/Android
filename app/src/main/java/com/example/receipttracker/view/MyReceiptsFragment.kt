package com.example.receipttracker.view

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

import com.example.receipttracker.R
import com.example.receipttracker.model.Receipt
import com.example.receipttracker.viewmodel.MyReceiptsViewModel
import kotlinx.android.synthetic.main.receipt_item_list_view.view.*

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

    inner class ReceiptRecyclerView(private val receipts: MutableList<Receipt>) : RecyclerView.Adapter<ReceiptRecyclerView.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.receipt_item_list_view, parent, false)
            return ViewHolder(view)
        }

        override fun getItemCount(): Int = receipts.size

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val data = receipts[position]
            holder.amountView.text = data.purchaseAmount.toString()
            holder.categoryView.text = data.category
            holder.dateView.text = data.date.toString()
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
