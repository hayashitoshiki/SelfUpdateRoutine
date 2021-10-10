package com.myapp.presentation.ui.mission_statement

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.myapp.presentation.databinding.FragmentMissionStatementListBinding
import com.myapp.presentation.ui.home.HomeContract
import com.myapp.presentation.utils.base.BaseFragment
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import dagger.hilt.android.AndroidEntryPoint

/**
 * ミッションステートメント一覧画面
 *
 */
@AndroidEntryPoint
class MissionStatementListFragment : BaseFragment() {

    private val viewModel: MissionStatementListViewModel by viewModels()
    private var _binding: FragmentMissionStatementListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMissionStatementListBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        setEvent()
        viewModel.state.observe(viewLifecycleOwner, { changeState(it) })
        viewModel.effect.observe(viewLifecycleOwner, { executionEffect(it) })

        binding.listConstitution.also {
            it.adapter = GroupAdapter<ViewHolder>()
            it.layoutManager = LinearLayoutManager(requireContext())
        }
        binding.listFuneral.also {
            it.adapter = GroupAdapter<ViewHolder>()
            it.layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun changeState(state: MissionStatementListContract.State) {
        viewModel.cashState.let { cash ->
            if (cash == null || state.missionStatement != cash.missionStatement) {
                val mainVisibility = if (state.missionStatement != null) View.VISIBLE else View.INVISIBLE
                val notFoundVisibility = if (state.missionStatement == null) View.VISIBLE else View.INVISIBLE
                binding.layoutMain.visibility = mainVisibility
                binding.txtNotFoundMessage.visibility = notFoundVisibility
            }
            if (cash == null || state.purposeLife != cash.purposeLife) {
                val visibility = if (state.isEnablePurposeLife) View.VISIBLE else View.INVISIBLE
                binding.txtValueMissionStatement.text = state.purposeLife
                binding.cardPurpose.visibility = visibility
            }
            if (cash == null || state.funeralList != cash.funeralList) {
                val visibility = if (state.isEnableFuneralList) View.VISIBLE else View.INVISIBLE
                val items = state.funeralList.map { MissionStatementListDiscItem(it, requireContext()) }
                (binding.listFuneral.adapter as GroupAdapter<*>).update(items)
                binding.cardFuneral.visibility = visibility
            }
            if (cash == null || state.constitutionList != cash.constitutionList) {
                val visibility = if (state.isEnableConstitutionList) View.VISIBLE else View.INVISIBLE
                val items = state.constitutionList.map { MissionStatementListDiscItem(it, requireContext()) }
                (binding.listConstitution.adapter as GroupAdapter<*>).update(items)
                binding.cardConstitution.visibility = visibility
            }
        }
    }

    // イベント設定
    private fun setEvent() {
        // 変更ボタン
        binding.btnChange.setOnClickListener { viewModel.setEvent(MissionStatementListContract.Event.OnClickChangeButton) }
    }

    // エフェクト発火
    private fun executionEffect(effect: MissionStatementListContract.Effect) = when(effect) {
        is MissionStatementListContract.Effect.NavigateMissionStatementSetting -> {
            val action = MissionStatementListFragmentDirections.actionNavConstitutionToNavConstitutionSetting(effect.value)
            findNavController().navigate(action)
        }
        is MissionStatementListContract.Effect.OnDestroyView -> {}
    }

    override fun onDestroyView() {
        viewModel.setEvent(MissionStatementListContract.Event.OnDestroyView)
        super.onDestroyView()
        _binding = null
    }
}
