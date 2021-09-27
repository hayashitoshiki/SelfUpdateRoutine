package com.myapp.presentation.ui.home

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import dagger.hilt.android.AndroidEntryPoint

/**
 * 宣言一覧画面
 *
 */
@AndroidEntryPoint
class StatementListFragment : BaseDetailListFragment() {

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        val args: StatementListFragmentArgs by navArgs()
        binding.list.also {
            val items = args.statementList.statementList.map { statement ->
                BaseSingleTextItem(statement)
            }
            val adapter = GroupAdapter<ViewHolder>()
            adapter.update(items)
            val layoutManager = LinearLayoutManager(requireContext())
            it.adapter = adapter
            it.layoutManager = layoutManager
        }
    }
}
