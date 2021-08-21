package com.myapp.presentation.ui.mission_statement

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.myapp.presentation.databinding.FragmentMissionStatementSettingBinding
import com.myapp.presentation.utils.BaseFragment
import com.myapp.presentation.utils.Status
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import es.dmoral.toasty.Toasty
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

/**
 * ミッションステートメント設定画面
 *
 */
class MissionStatementSettingFragment : BaseFragment() {

    private val args: MissionStatementSettingFragmentArgs by navArgs()
    private val viewModel: MissionStatementSettingViewModel by inject { parametersOf(args.missionStatement) }
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
        binding.btnChange.setOnClickListener { viewModel.onClickConfirmButton() }
    }

    // 理想の葬儀リスト設定
    private fun setFuneralList(data: List<Pair<Long, String>>) {
        val items = data.mapIndexed { index, s ->
            val funeralInputItemViewModel = FuneralInputItemViewModel(index, s.first, s.second)
            FuneralInputItem(funeralInputItemViewModel)
        }
        val adapter = binding.listFuneral.adapter as GroupAdapter<*>
        adapter.update(items)
    }

    // 憲法リスト設定
    private fun setConstitutionList(data: List<Pair<Long, String>>) {
        val items = data.mapIndexed { index, s ->
            val constitutionInputItemViewModel = ConstitutionInputItemViewModel(index, s.first, s.second)
            ConstitutionInputItem(constitutionInputItemViewModel)
        }
        val adapter = binding.listConstitution.adapter as GroupAdapter<*>
        adapter.update(items)
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