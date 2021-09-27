package com.myapp.presentation.ui.mission_statement

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.myapp.presentation.R
import com.myapp.presentation.databinding.FragmentMissionStatementSettingBinding
import com.myapp.presentation.utils.BaseFragment
import com.myapp.presentation.utils.Status
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
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.funeralList.observe(viewLifecycleOwner, { setFuneralList(it) })
        viewModel.constitutionList.observe(viewLifecycleOwner, { setConstitutionList(it) })
        viewModel.confirmStatus.observe(viewLifecycleOwner, { onStateChanged(it) })

        binding.listConstitution.also {
            val adapter = GroupAdapter<ViewHolder>()
            val layoutManager = LinearLayoutManager(requireContext())
            it.adapter = adapter
            it.layoutManager = layoutManager
        }
        binding.listFuneral.also {
            val adapter = GroupAdapter<ViewHolder>()
            val layoutManager = LinearLayoutManager(requireContext())
            it.adapter = adapter
            it.layoutManager = layoutManager
        }
        binding.listResultFuneral.also {
            val adapter = GroupAdapter<ViewHolder>()
            val layoutManager = LinearLayoutManager(requireContext())
            it.adapter = adapter
            it.layoutManager = layoutManager
        }
        binding.listResultConstitution.also {
            val adapter = GroupAdapter<ViewHolder>()
            val layoutManager = LinearLayoutManager(requireContext())
            it.adapter = adapter
            it.layoutManager = layoutManager
        }
        binding.editMissionStatement.hint = requireContext().getString(R.string.hint_purpose_life)
        binding.btnChange.setOnClickListener { viewModel.onClickConfirmButton() }
    }

    // 理想の葬儀リスト設定
    private fun setFuneralList(data: List<Pair<Long, String>>) {
        (binding.listFuneral.adapter as GroupAdapter<*>).also { adapter ->
            if (adapter.itemCount == data.count()) return@also
            val items = data.mapIndexed { index, s ->
                val funeralInputItemViewModel = FuneralInputItemViewModel(index, s.first, s.second)
                FuneralInputItem(requireContext(), funeralInputItemViewModel)
            }
            adapter.update(items)
        }
        (binding.listResultFuneral.adapter as GroupAdapter<*>).also { adapter ->
            val items = data.filter { it.second.isNotBlank() }
                .map {
                    MissionStatementSettingResultDiscItem(
                        it.first, it.second, viewModel.constitutionListDiffColor, requireContext(), viewLifecycleOwner
                    )
                }
            adapter.update(items)
        }
    }

    // 憲法リスト設定
    private fun setConstitutionList(data: List<Pair<Long, String>>) {
        (binding.listConstitution.adapter as GroupAdapter<*>).also { adapter ->
            if (adapter.itemCount == data.count()) return@also
            val items = data.mapIndexed { index, s ->
                val constitutionInputItemViewModel = ConstitutionInputItemViewModel(index, s.first, s.second)
                ConstitutionInputItem(requireContext(), constitutionInputItemViewModel)
            }
            adapter.update(items)
        }
        (binding.listResultConstitution.adapter as GroupAdapter<*>).also { adapter ->
            val items = data.filter { it.second.isNotBlank() }
                .map {
                    MissionStatementSettingResultDiscItem(
                        it.first, it.second, viewModel.constitutionListDiffColor, requireContext(), viewLifecycleOwner
                    )
                }
            adapter.update(items)
        }
    }

    // 保存State変更通知
    private fun onStateChanged(state: Status<*>) = when (state) {
        is Status.Loading -> {
        }
        is Status.Success -> {
            backMissionStatementView()
        }
        is Status.Failure -> {
            Toasty.error(requireContext(), state.throwable.message!!, Toast.LENGTH_SHORT, true)
                .show()
        }
        is Status.Non -> {
        }
    }

    // ステートメント一覧画面へ戻る
    private fun backMissionStatementView() {
        Toasty.success(requireContext(), "ミッションステートメントを更新しました！", Toast.LENGTH_SHORT, true)
            .show()
        findNavController().popBackStack()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
