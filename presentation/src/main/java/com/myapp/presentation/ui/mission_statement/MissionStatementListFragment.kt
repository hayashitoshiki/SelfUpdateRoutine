package com.myapp.presentation.ui.mission_statement

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.myapp.presentation.databinding.FragmentMissionStatementListBinding
import com.myapp.presentation.utils.base.BaseAacFragment
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import dagger.hilt.android.AndroidEntryPoint

/**
 * ミッションステートメント一覧画面
 *
 */
@AndroidEntryPoint
class MissionStatementListFragment :
    BaseAacFragment<MissionStatementListContract.State, MissionStatementListContract.Effect, MissionStatementListContract.Event>() {

    override val viewModel: MissionStatementListViewModel by viewModels()
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

        binding.listConstitution.also {
            it.adapter = GroupAdapter<ViewHolder>()
            it.layoutManager = LinearLayoutManager(requireContext())
        }
        binding.listFuneral.also {
            it.adapter = GroupAdapter<ViewHolder>()
            it.layoutManager = LinearLayoutManager(requireContext())
        }
    }

    override fun changedState(state: MissionStatementListContract.State) {

        // メインコンテンツ
        binding.layoutMain.visibility = if (state.missionStatement != null) View.VISIBLE else View.INVISIBLE
        binding.txtNotFoundMessage.visibility = if (state.missionStatement == null) View.VISIBLE else View.INVISIBLE

        // 人生の目的
        binding.txtValueMissionStatement.text = state.purposeLife
        binding.cardPurpose.visibility = if (state.isEnablePurposeLife) View.VISIBLE else View.INVISIBLE

        // 理想の葬式
        binding.cardFuneral.visibility = if (state.isEnableFuneralList) View.VISIBLE else View.INVISIBLE
        val funeralListAdapter = binding.listFuneral.adapter as GroupAdapter<*>
        state.funeralList
            .map { MissionStatementListDiscItem(it, requireContext()) }
            .also{ funeralListAdapter.update(it) }

        // 憲法
        binding.cardConstitution.visibility = if (state.isEnableConstitutionList) View.VISIBLE else View.INVISIBLE
        val constitutionListAdapter = binding.listConstitution.adapter as GroupAdapter<*>
        state.constitutionList
            .map { MissionStatementListDiscItem(it, requireContext()) }
            .also{ constitutionListAdapter.update(it) }
    }

    // Event設定
    override fun setEvent() {
        // 変更ボタン
        binding.btnChange.setOnClickListener { viewModel.setEvent(MissionStatementListContract.Event.OnClickChangeButton) }
    }

    // Effect設定
    override fun setEffect(effect: MissionStatementListContract.Effect) = when(effect) {
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
