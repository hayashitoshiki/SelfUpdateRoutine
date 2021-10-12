package com.myapp.presentation.ui.home

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.myapp.domain.model.entity.Report
import com.myapp.presentation.R
import com.myapp.presentation.databinding.FragmentHomeBinding
import com.myapp.presentation.ui.diary.DiaryActivity
import com.myapp.presentation.utils.base.BaseAacFragment
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import com.xwray.groupie.databinding.BindableItem
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

/**
 * ホーム画面
 */
@AndroidEntryPoint
class HomeFragment :
    BaseAacFragment<HomeContract.State, HomeContract.Effect, HomeContract.Event>() {


    override val viewModel: HomeViewModel by viewModels()
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var  lister: OnDiaryCardItemClickListener

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = GroupAdapter<ViewHolder>()
        val layoutManager = LinearLayoutManager(requireContext())
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        binding.listDiary.adapter = adapter
        binding.listDiary.layoutManager = layoutManager
    }

    // State設定
    override fun changedState(state: HomeContract.State) {
        setDiaryList(state.reportList)
    }

    // イベント設定
    override fun setEvent() {

        // 今日の日記入力ボタン
        binding.btnDiaryInput.setOnClickListener { viewModel.setEvent(HomeContract.Event.OnClickReportButton) }

        // Fabボタン
        binding.fab.setOnClickListener { viewModel.setEvent(HomeContract.Event.OnClickFabButton) }

        // Fab_宣言一覧ボタン
        binding.fabStatement.setOnClickListener { viewModel.setEvent(HomeContract.Event.OnClickFabStatementButton) }

        // Fab_格言一覧ボタン
        binding.fabLearn.setOnClickListener { viewModel.setEvent(HomeContract.Event.OnClickFabLearnButton) }

        // 振り返りカード
        lister = object : OnDiaryCardItemClickListener {
            override fun onItemClick(report: Report) {
                viewModel.setEvent(HomeContract.Event.OnClickReportCard(report))
            }
        }
    }

    // エフェクト設定
    @SuppressLint("TimberExceptionLogging")
    @Suppress("IMPLICIT_CAST_TO_ANY")
    override fun setEffect(effect: HomeContract.Effect) {
        when(effect) {
            is HomeContract.Effect.ChangeFabEnable -> {
                if (effect.value) {
                    binding.fabStatement.animate().translationY(resources.getDimension(R.dimen.standard_55))
                    binding.fabLearn.animate().translationY(resources.getDimension(R.dimen.standard_105))
                } else {
                    binding.fabStatement.animate().translationY(0.toFloat())
                    binding.fabLearn.animate().translationY(0.toFloat())
                }
            }
            is HomeContract.Effect.DiaryReportNavigation -> {
                val intent = Intent(context, DiaryActivity::class.java)
                startActivity(intent)
            }
            is HomeContract.Effect.LearnListNavigation -> {
                val statementList = effect.value.map { report -> ReportDetail(report.ffsReport.learnComment, report.ffsReport.dataTime) }
                val data = ReportDetailList(statementList)
                val action = HomeFragmentDirections.actionNavHomeToNavLearnList(data)
                findNavController().navigate(action)
            }
            is HomeContract.Effect.StatementListNavigation -> {
                val statementList = effect.value.map { report -> ReportDetail(report.ffsReport.statementComment, report.ffsReport.dataTime) }
                val data = ReportDetailList(statementList)
                val action = HomeFragmentDirections.actionNavHomeToNavStatementList(data)
                findNavController().navigate(action)
            }
            is HomeContract.Effect.ReportDetailListNavigation -> {
                val action = HomeFragmentDirections.actionNavHomeToNavRememner(effect.value)
                findNavController().navigate(action)
            }
            is HomeContract.Effect.OnDestroyView -> {}
            is HomeContract.Effect.ShowError -> {
                Timber.tag(this.javaClass.simpleName).d(effect.throwable.message)
            }
        }
    }

    // レポートリスト設定
    private fun setDiaryList(data: List<Report>) {
        val adapter = GroupAdapter<ViewHolder>()
        val items = mutableListOf<BindableItem<*>>()
        data.forEach { report ->
            val diaryCardViewModel = DiaryCardViewModel(report)
            val item = DiaryCardItem(diaryCardViewModel, viewLifecycleOwner, lister)
            items.add(item)
        }
        binding.listDiary.adapter = adapter
        adapter.update(items)
    }

    override fun onDestroyView() {
        viewModel.setEvent(HomeContract.Event.OnDestroyView)
        super.onDestroyView()
        _binding = null
    }
}
