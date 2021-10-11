package com.myapp.presentation.ui.mission_statement

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.myapp.common.listEquals
import com.myapp.presentation.R
import com.myapp.presentation.databinding.FragmentMissionStatementSettingBinding
import com.myapp.presentation.utils.base.BaseFragment
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import dagger.hilt.android.AndroidEntryPoint
import es.dmoral.toasty.Toasty
import javax.inject.Inject

/**
 * ミッションステートメント設定画面
 *
 */
@AndroidEntryPoint
class MissionStatementSettingFragment : BaseFragment() {

    private val args: MissionStatementSettingFragmentArgs by navArgs()

    @Inject
    lateinit var viewModelFactory: MissionStatementSettingViewModel.Factory
    private val viewModel: MissionStatementSettingViewModel by viewModels {
        MissionStatementSettingViewModel.provideFactory(viewModelFactory, args.missionStatement)
    }

    private var _binding: FragmentMissionStatementSettingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMissionStatementSettingBinding.inflate(inflater, container, false)
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
        binding.listResultFuneral.also {
            it.adapter = GroupAdapter<ViewHolder>()
            it.layoutManager = LinearLayoutManager(requireContext())
        }
        binding.listResultConstitution.also {
            it.adapter = GroupAdapter<ViewHolder>()
            it.layoutManager = LinearLayoutManager(requireContext())
        }
        binding.editMissionStatement.hint = requireContext().getString(R.string.hint_purpose_life)
        viewModel.state.value?.let { state ->
            setFuneralList(state)
            binding.purposeDiffValue.text = state.purposeLife
            binding.editMissionStatement.setText(state.purposeLife)
            binding.purposeDiffValue.setTextColor(requireContext().resources.getColor(state.purposeLifeDiffColor, null))
            binding.purposeTitle.setTextColor(requireContext().resources.getColor(state.purposeLifeDiffColor, null))
            setConstitutionList(state)
            binding.btnChange.isEnabled = state.isEnableConfirmButton
        }
    }

    // State反映
    private fun changeState(state: MissionStatementSettingContract.State) {
        viewModel.cashState.let { cash ->
            if (cash == null || !listEquals(cash.funeralList,state.funeralList)) setFuneralList(state)
            if (cash == null || state.purposeLife != cash.purposeLife) {
                binding.purposeDiffValue.text = state.purposeLife
                binding.purposeDiffValue.setTextColor(requireContext().resources.getColor(state.purposeLifeDiffColor, null))
                binding.purposeTitle.setTextColor(requireContext().resources.getColor(state.purposeLifeDiffColor, null))
            }
            if (cash == null || !listEquals(cash.constitutionList,state.constitutionList)) setConstitutionList(state)
            if (cash == null || state.isEnableConfirmButton != cash.isEnableConfirmButton) {
                binding.btnChange.isEnabled = state.isEnableConfirmButton
            }
        }
    }

    // Event設定
    private fun setEvent() {
        binding.btnChange.setOnClickListener { viewModel.setEvent(MissionStatementSettingContract.Event.OnClickChangeButton) }
        binding.editMissionStatement.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.setEvent(MissionStatementSettingContract.Event.OnChangePurposeText(s.toString()))
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }

    // Effect設定
    private fun executionEffect(effect: MissionStatementSettingContract.Effect) = when(effect) {
        is MissionStatementSettingContract.Effect.NavigateMissionStatementSetting -> backMissionStatementView()
        is MissionStatementSettingContract.Effect.OnDestroyView -> {}
        is MissionStatementSettingContract.Effect.ShowError -> {
            Toasty.error(requireContext(), effect.value.message!!, Toast.LENGTH_SHORT, true).show()
        }
    }

    // 理想の葬儀リスト設定
    private fun setFuneralList(state: MissionStatementSettingContract.State) {
        binding.funeralTitle.setTextColor(requireContext().resources.getColor(state.funeralListDiffColor, null))
        val resultAdapter = binding.listResultFuneral.adapter as GroupAdapter<*>
        val inputAdapter = binding.listFuneral.adapter as GroupAdapter<*>
        state.funeralList
            .filter { it.second.isNotBlank() }
            .map { MissionStatementSettingResultDiscItem(it.first, it.second, state.funeralListDiffColor, requireContext()) }
            .let{ resultAdapter.update(it) }
        if (inputAdapter.itemCount == state.funeralList.count()) return
        state.funeralList
            .mapIndexed { index, s -> FuneralInputItemViewModel(index, s.first, s.second) }
            .map{ FuneralInputItem(requireContext(), it)}
            .let{ inputAdapter.update(it) }
    }

    // 憲法リスト設定
    private fun setConstitutionList(state: MissionStatementSettingContract.State) {
        binding.constitutionTitle.setTextColor(requireContext().resources.getColor(state.constitutionListDiffColor, null))
        val resultAdapter = binding.listResultConstitution.adapter as GroupAdapter<*>
        val inputAdapter = binding.listConstitution.adapter as GroupAdapter<*>
        state.constitutionList
            .filter { it.second.isNotBlank() }
            .map { MissionStatementSettingResultDiscItem(it.first, it.second, state.constitutionListDiffColor, requireContext()) }
            .let{ resultAdapter.update(it) }
        if (inputAdapter.itemCount == state.constitutionList.count()) return
        state.constitutionList
            .mapIndexed { index, s -> ConstitutionInputItemViewModel(index, s.first, s.second) }
            .map{ ConstitutionInputItem(requireContext(), it) }
            .let{ inputAdapter.update(it) }
    }

    // ステートメント一覧画面へ戻る
    private fun backMissionStatementView() {
        Toasty.success(requireContext(), "ミッションステートメントを更新しました！", Toast.LENGTH_SHORT, true).show()
        findNavController().popBackStack()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
