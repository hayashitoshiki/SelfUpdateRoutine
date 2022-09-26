package com.myapp.presentation.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.myapp.presentation.databinding.FragmentHomeBinding
import com.myapp.presentation.utils.BaseFragment
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import com.xwray.groupie.databinding.BindableItem
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

/**
 * ホーム画面
 */
@AndroidEntryPoint
class HomeFragment : BaseFragment() {

    private val viewModel: HomeViewModel by viewModels()
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
        val layoutManager = LinearLayoutManager(requireContext())
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        setGroupie()
    }

    /**
     * Groupie表示（色々お試し）
     *
     */
    private fun setGroupie() {
        val groupAdapter = GroupAdapter<ViewHolder>()
        val adapter = GroupAdapter<ViewHolder>()
        val items = mutableListOf<BindableItem<*>>()
        val itemList = listOf("りんご🍎", "みかん🍊", "ぶどう🍇", "すいか🍉", "もも🍑", "ばなな🍌")
        itemList.forEach { item ->
            Timber.tag("Groupie set")
                .d("items = " + item)

            val itemGroupie = ListItem(item)
            items.add(itemGroupie)
        }
        binding.listDiary.adapter = adapter
        adapter.update(items)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
