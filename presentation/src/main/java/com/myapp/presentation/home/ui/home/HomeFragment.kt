package com.myapp.presentation.home.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.myapp.presentation.databinding.FragmentHomeBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModel()
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        // TODO : 仮表示 詳細画面遷移
        binding.calenderView.setOnDayClickListener {
            Log.d("HomeFragment", "setOnDayClickListener")
            val reportList = viewModel.report.value ?: return@setOnDayClickListener
            var action: NavDirections? = null
            val touchYear: Int = it.calendar.get(Calendar.YEAR)
            val touchMonth: Int = it.calendar.get(Calendar.MONTH) + 1
            val touchDay: Int = it.calendar.get(Calendar.DATE)
            val touchDate = "%d/%02d/%d".format(touchYear, touchMonth, touchDay)
            reportList.forEach { report ->
                val reportDate = report.weatherReport.dataTime.toSectionDate()
                Log.d("HomeFragment", "touchDate = " + touchDate + ". reportDate = " + reportDate)
                if (touchDate == reportDate) {
                    Log.d("HomeFragment", "Date1 is equals Date2")
                    action = HomeFragmentDirections.actionNavHomeToNavRememner(report)
                }
            }
            if (action == null) return@setOnDayClickListener
            findNavController().navigate(action!!)

        }
    }
}