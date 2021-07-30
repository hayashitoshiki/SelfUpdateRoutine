package com.myapp.presentation.home.ui.diary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.myapp.presentation.R
import com.myapp.presentation.databinding.ItemDiaryFfsResultBinding
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * 振り返り_FFS振り返り確認画面
 */
class FfsResultFragment : Fragment() {

    private lateinit var binding: ItemDiaryFfsResultBinding

    private val viewModel: FfsResultViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.item_diary_ffs_result, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        binding.btnOk.setOnClickListener {
            findNavController().navigate(R.id.action_diaryConfirmItem_to_diaryAssessmentItem)
        }
        return binding.root
    }

    @ExperimentalCoroutinesApi
    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

    }

}
