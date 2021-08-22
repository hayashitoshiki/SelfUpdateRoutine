package com.myapp.presentation.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.myapp.domain.model.entity.Report
import com.myapp.presentation.R
import com.myapp.presentation.databinding.FragmentHomeBinding
import com.myapp.presentation.ui.diary.DiaryActivity
import com.myapp.presentation.utils.BaseFragment
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import com.xwray.groupie.databinding.BindableItem
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber


/**
 * ホーム画面
 */
class HomeFragment : BaseFragment() {

    private val viewModel: HomeViewModel by viewModel()
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private var isFABOpen = false

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

        // Fabボタン
        binding.fab.setOnClickListener {
            if (!isFABOpen) {
                showFABMenu()
            } else {
                closeFABMenu()
            }
        }

        // Fab_宣言一覧ボタン
        binding.fabStatement.setOnClickListener {
            viewModel.report.value?.let { reportList ->
                val statementList = reportList.map { report ->
                    ReportDetail(report.ffsReport.statementComment, report.ffsReport.dataTime)
                }
                val data = ReportDetailList(statementList)
                val action = HomeFragmentDirections.actionNavHomeToNavStatementList(data)
                findNavController().navigate(action)
                isFABOpen = false
            } ?: run {
                Timber.tag(this.javaClass.simpleName)
                    .d("レポートリストが登録されていないのに宣言一覧画面を表示しようとしています")
            }
        }

        // Fab_格言一覧ボタン
        binding.fabLearn.setOnClickListener {
            viewModel.report.value?.let { reportList ->
                val statementList = reportList.map { report ->
                    ReportDetail(report.ffsReport.learnComment, report.ffsReport.dataTime)
                }
                val data = ReportDetailList(statementList)
                val action = HomeFragmentDirections.actionNavHomeToNavLearnList(data)
                findNavController().navigate(action)
                isFABOpen = false
            } ?: run {
                Timber.tag(this.javaClass.simpleName)
                    .d("レポートリストが登録されていないのに宣言一覧画面を表示しようとしています")
            }
        }
    }

    private fun showFABMenu() {
        isFABOpen = true
        binding.fabStatement.animate()
            .translationY(resources.getDimension(R.dimen.standard_55))
        binding.fabLearn.animate()
            .translationY(resources.getDimension(R.dimen.standard_105))
    }

    private fun closeFABMenu() {
        isFABOpen = false
        binding.fabStatement.animate()
            .translationY(0.toFloat())
        binding.fabLearn.animate()
            .translationY(0.toFloat())
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