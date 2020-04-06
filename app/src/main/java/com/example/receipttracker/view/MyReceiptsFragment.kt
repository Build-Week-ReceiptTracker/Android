package com.example.receipttracker.view

import android.content.Context
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

import com.example.receipttracker.R
import com.example.receipttracker.dateStringToDate
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.my_receipts_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MyReceiptsViewModel::class.java)

        rv_my_receipts.apply {
            setHasFixedSize(false)
            layoutManager = LinearLayoutManager(context)
            adapter = ReceiptListAdapter(mutableListOf<Receipt>())
        }

        fab_add_receipt.setOnClickListener {
            AddReceiptFragment().let {
                activity?.supportFragmentManager?.beginTransaction()
                    ?.replace(R.id.nav_host_fragment, it)?.commit()
            }
        }

        viewModel.getAllReceipts(viewModel.repo?.currentToken!!)?.observe(this, Observer { receiptsList ->
            if(receiptsList != null) {
                receiptsList.sortBy { it.date_of_transaction.dateStringToDate() }
                rv_my_receipts.adapter = ReceiptListAdapter(receiptsList)

                // button is used to return receipts containing user's string
                var searchList = mutableListOf<Receipt>()
                btn_search.setOnClickListener{
                    val userSearch = et_search.text.toString()
                    if (userSearch == ""){
                        rv_my_receipts.adapter = ReceiptListAdapter(receiptsList)
                    }else{
                        searchList.clear()
                        receiptsList.forEach {
                            if (viewModel.isUserStringInReceipt(it, userSearch)) {
                                searchList.add(it)
                                viewModel.hideSoftKeyboard(this.requireActivity())
                            }
                        }
                        rv_my_receipts.adapter = ReceiptListAdapter(searchList)
                    }

                }

            } else {
                Toast.makeText(this.context, "Failed to get receipts", Toast.LENGTH_LONG).show()
            }
        })
    }

    inner class ReceiptListAdapter(private val receipts: MutableList<Receipt>) : RecyclerView.Adapter<ReceiptListAdapter.ViewHolder>() {

        lateinit var context: Context

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            context = parent.context
            val view = LayoutInflater.from(parent.context).inflate(R.layout.receipt_item_list_view, parent, false)
            return ViewHolder(view)
        }

        override fun getItemCount(): Int = receipts.size

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val data = receipts[position]
            var isExpanded = false
            holder.amountView.text = data.amount_spent
            holder.categoryView.text = data.category
            holder.dateView.text = data.date_of_transaction
            holder.merchantView.text = data.merchant
            holder.idView.text = data.id.toString()
            holder.descriptionView.text = data.description
            Glide.with(context).load(data.image_url).into(holder.imageView)

            holder.expandedView.visibility = View.GONE

            holder.cardView.setOnClickListener {
                isExpanded = !isExpanded
                if (isExpanded) {
                    holder.expandedView.visibility = View.VISIBLE
                } else {
                    holder.expandedView.visibility = View.GONE
                }

            }
            holder.editButton.setOnClickListener {
                EditReceiptFragment(data).let {
                    activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.nav_host_fragment, it)?.commit()
                }
            }

            holder.cardView.setOnLongClickListener {

                val builder = AlertDialog.Builder(context!!)
                builder.setTitle("Delete Receipt")
                builder.setMessage("Are you sure you want to delete this receipt?")
                builder.setPositiveButton("YES"){ dialog, which ->
                    viewModel.deleteReceipt(viewModel.repo?.currentToken!!, data.id!!)?.observe(this@MyReceiptsFragment, Observer {
                        if (it != null) Toast.makeText(context, it, Toast.LENGTH_LONG).show()
                    })
                }
                builder.setNegativeButton("NO"){_,_->}

                val dialog: AlertDialog = builder.create()
                dialog.show()
                true
            }
        }

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val dateView: TextView = view.text_receipt_date
            val amountView: TextView = view.text_receipt_amount
            val merchantView: TextView = view.text_receipt_merchant
            val categoryView: TextView = view.text_receipt_category
            val cardView: CardView = view.cardview_parent
            val idView: TextView = view.text_receipt_id
            val descriptionView: TextView = view.text_receipt_description
            val expandedView: LinearLayout = view.ll_expanded_view
            val editButton: TextView = view.text_button_edit
            val imageView: ImageView = view.iv_recycler
        }
    }
}
