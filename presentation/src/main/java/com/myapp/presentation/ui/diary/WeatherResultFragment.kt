package com.myapp.presentation.ui.diary

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.myapp.presentation.R
import com.myapp.presentation.databinding.ItemDiaryWeatherResultBinding
import com.myapp.presentation.ui.MainActivity
import com.myapp.presentation.utill.Status
import es.dmoral.toasty.Toasty
import org.koin.androidx.viewmodel.ext.android.viewModel


/**
 * 振り返り_天気比喩振り返り確認画面
 */
class WeatherResultFragment : Fragment() {

    private var _binding: ItemDiaryWeatherResultBinding? = null
    private val binding get() = _binding!!
    private val viewModel: WeatherResultViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.item_diary_weather_result, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.saveState.observe(viewLifecycleOwner, { onStateChanged(it) })

        binding.btnOk.setOnClickListener {
            viewModel.saveReport()
        }
    }

    // 保存State変更通知
    private fun onStateChanged(state: Status<*>) = when (state) {
        is Status.Loading -> {
        }
        is Status.Success -> {
            backMainActivity()
        }
        is Status.Failure -> {
            Toasty.error(requireContext(), state.throwable.message!!, Toast.LENGTH_SHORT, true)
                .show()
        }
        is Status.Non -> {
        }
    }

    // メイン画面へ戻る
    private fun backMainActivity() {
        Toasty.success(requireContext(), "日記を保存しました！", Toast.LENGTH_SHORT, true)
            .show()
        val intent = Intent(context, MainActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
