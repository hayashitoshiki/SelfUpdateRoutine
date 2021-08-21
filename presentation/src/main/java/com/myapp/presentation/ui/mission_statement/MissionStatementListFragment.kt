package com.myapp.presentation.ui.mission_statement

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.myapp.presentation.databinding.FragmentMissionStatementListBinding
import com.myapp.presentation.utils.BaseFragment
import com.myapp.presentation.utils.DiscItem
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * ミッションステートメント一覧画面
 *
 */
class MissionStatementListFragment : BaseFragment() {

    private val viewModel: MissionStatementListViewModel by viewModel()
    private var _binding: FragmentMissionStatementListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMissionStatementListBinding.inflate(inflater, container, false)
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
        binding.btnChange.setOnClickListener {
            val data = viewModel.missionStatement
            val action = MissionStatementListFragmentDirections.actionNavConstitutionToNavConstitutionSetting(data)
            findNavController().navigate(action)
        }
    }

    // 理想の葬儀リスト設定
    private fun setFuneralList(data: List<String>) {
        val items = data.map { DiscItem(it) }
        val adapter = binding.listFuneral.adapter as GroupAdapter<*>
        adapter.update(items)
    }

    // 憲法リスト設定
    private fun setConstitutionList(data: List<String>) {
        val items = data.map { DiscItem(it) }
        val adapter = binding.listConstitution.adapter as GroupAdapter<*>
        adapter.update(items)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}