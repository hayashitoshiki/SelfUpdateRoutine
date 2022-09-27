package com.myapp.presentation.ui.diary

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import com.myapp.presentation.R
import com.myapp.presentation.databinding.ItemDiaryTextInputBinding
import com.myapp.presentation.utils.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

/**
 * 振り返り画面_テキスト入力系画面 BaseFragment
 */
@AndroidEntryPoint
abstract class DiaryBaseFragment : BaseFragment() {

    private var _binding: ItemDiaryTextInputBinding? = null
    protected val binding get() = _binding!!
    abstract val viewModel: DiaryBaseViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.item_diary_text_input, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        return binding.root
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        // editTextフォーカス制御
        binding.edtInput.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(v.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
            }
        }
        binding.root.setOnTouchListener { v, event ->
            binding.root.requestFocus()
            v?.onTouchEvent(event) ?: true
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}