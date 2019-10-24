package com.example.receipttracker.view

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.receipttracker.R
import com.example.receipttracker.averageReceiptAmounts
import com.example.receipttracker.filterByDate
import com.example.receipttracker.model.Receipt
import com.example.receipttracker.viewmodel.ChartViewModel
import kotlinx.android.synthetic.main.chart_fragment.*


class ChartFragment : Fragment() {

    companion object {
        fun newInstance() = ChartFragment()
    }

    private lateinit var viewModel: ChartViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.chart_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ChartViewModel::class.java)

        var url = "https://image-charts.com/chart?&chs=500x500&cht=lc&chd=t:"
        var fullList = mutableListOf<Receipt>()

        viewModel.getFullListOfReceipts()?.observe(this, Observer {
            if (it != null) {
                fullList = it as MutableList<Receipt>
            }
        })

        button_get_chart.setOnClickListener {
            var startDate = et_start_date.text.toString()
            var endDate = et_end_date.text.toString()
            val newList = fullList.filterByDate(startDate,endDate)
            for (i in 0 until newList.size) {
                for (i in 0 until newList.size) {
                    var subList = newList.filterByDate(startDate, startDate)
                    var dailyAverage = subList.averageReceiptAmounts()
                    url += (dailyAverage.toString() + ",")
                }
                //startDate = LocalDateTime.from()
            }

            Glide.with(this.context!!).load("https://image-charts.com/chart?&chs=500x500&cht=lc&chd=t:40,60,60,45,47,75,70,72").into(iv_chart)
        }
    }

}
