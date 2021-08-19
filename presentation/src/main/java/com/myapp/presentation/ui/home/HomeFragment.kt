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
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import com.xwray.groupie.databinding.BindableItem
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * ホーム画面
 */
class HomeFragment : BaseFragment() {

    private val viewModel: HomeViewModel by viewModel()
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.report.observe(viewLifecycleOwner, { setDiaryList(it) })

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
            val item = DiaryCardItem(viewModel, viewLifecycleOwner, lister)
            items.add(item)
        }
        binding.listDiary.adapter = adapter
        adapter.update(items)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}