package com.example.receipttracker.view

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.receipttracker.*
import com.example.receipttracker.model.Receipt
import com.example.receipttracker.viewmodel.ChartViewModel
import kotlinx.android.synthetic.main.chart_fragment.*
import java.util.*


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
            url = "https://image-charts.com/chart?&chs=300x150&cht=lc&chd=t:"
            var startDate = et_start_date.text.toString()
            var endDate = et_end_date.text.toString()
            val newList = fullList.filterByDate(startDate,endDate)

            val startDateInt = startDate.dateStringToDate().get(Calendar.DAY_OF_MONTH)
            val endDateInt = endDate.dateStringToDate().get(Calendar.DAY_OF_MONTH)
            val dateDiff = endDateInt - startDateInt

            var dailyAverage: Double = 0.0
            for (i in 0 until dateDiff) {
                var subList = newList.filterByDate(startDate, startDate)

                dailyAverage = subList.averageReceiptAmounts()

                Log.i("BIGBRAIN", dailyAverage.toString())
                url += (dailyAverage.toString() + ",")
                dailyAverage = 0.0
                startDate = startDate.dateStringIncrementDay()
            }

            url = url.dropLast(1)
            Log.i("BIGBRAIN", url)
            Glide.with(this.context!!)
                .load(url)
                .into(iv_chart)
        }
    }
}
