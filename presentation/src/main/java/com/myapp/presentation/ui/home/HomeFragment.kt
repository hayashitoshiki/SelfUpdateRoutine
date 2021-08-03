package com.myapp.presentation.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.myapp.domain.model.entity.Report
import com.myapp.presentation.databinding.FragmentHomeBinding
import com.myapp.presentation.ui.diary.DiaryActivity
import com.myapp.presentation.utill.BaseFragment
import com.myapp.presentation.utill.Status
import com.myapp.presentation.utill.img
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import com.xwray.groupie.databinding.BindableItem
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * ホーム画面
 */
class HomeFragment : BaseFragment() {

    private val viewModel: HomeViewModel by viewModel()
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.report.observe(viewLifecycleOwner, { setDiaryList(it) }) // TODO : 仮表示 詳細画面遷移
        viewModel.dalyReport.observe(viewLifecycleOwner, { updateView(it) })

        val adapter = GroupAdapter<ViewHolder>()
        val layoutManager = LinearLayoutManager(requireContext())
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL;
        binding.listDiary.adapter = adapter
        binding.listDiary.layoutManager = layoutManager

        // 今日の日記入力ボタン
        binding.btnDiaryInput.setOnClickListener {
            val intent = Intent(context, DiaryActivity::class.java)
            startActivity(intent)
        }
    }

    // レポートリスト設定
    private fun setDiaryList(data: List<Report>) {
        val adapter = GroupAdapter<ViewHolder>()
        val items = mutableListOf<BindableItem<*>>()
        data.forEach { report ->
            val viewModel = DiaryCardViewModel(report)
            val lister = object : OnDiaryCardItemClickListener {
                override fun onItemClick(report: Report) {
                    val action = HomeFragmentDirections.actionNavHomeToNavRememner(report)
                    findNavController().navigate(action)
                }
            }
            val item = DiaryCardItem(viewModel, lister)
            items.add(item)
        }
        binding.listDiary.adapter = adapter
        adapter.update(items)

    }

    // ビューの表示更新
    private fun updateView(state: Status<Report>) = when (state) {
        is Status.Loading -> {
            binding.layoutDalyCard.visibility = View.GONE
            binding.layoutNoDalyCard.visibility = View.GONE
        }
        is Status.Success -> {
            enableDalyCard(state.data)
        }
        is Status.Failure -> {
            binding.layoutDalyCard.visibility = View.GONE
            binding.layoutNoDalyCard.visibility = View.VISIBLE
        }
        is Status.Non -> {
        }
    }

    // 今日の振り返りカード表示
    private fun enableDalyCard(data: Report) {
        binding.layoutDalyCard.visibility = View.VISIBLE
        binding.layoutNoDalyCard.visibility = View.GONE
        binding.txtAnswer11.text = data.ffsReport.factComment
        binding.txtAnswer12.text = data.ffsReport.findComment
        binding.txtAnswer13.text = data.ffsReport.learnComment
        binding.txtAnswer14.text = data.ffsReport.statementComment
        binding.txtAnswer21.setImageResource(data.weatherReport.heartScore.img)
        binding.txtAnswer22.text = data.weatherReport.reasonComment
        binding.txtAnswer23.text = data.weatherReport.improveComment
    }
}