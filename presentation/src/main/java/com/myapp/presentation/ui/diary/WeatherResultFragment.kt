package com.myapp.presentation.ui.diary

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.myapp.presentation.R
import com.myapp.presentation.databinding.ItemDiaryWeatherResultBinding
import com.myapp.presentation.ui.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.coroutines.CoroutineContext


/**
 * 振り返り_天気比喩振り返り確認画面
 */
class WeatherResultFragment : Fragment(), CoroutineScope {

    private val job = SupervisorJob()
    override val coroutineContext: CoroutineContext = Dispatchers.Main + job
    private lateinit var binding: ItemDiaryWeatherResultBinding
    private val viewModel: WeatherResultViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.item_diary_weather_result, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        binding.btnOk.setOnClickListener {
            launch {
                launch {
                    viewModel.saveReport()
                }.join()
                val intent = Intent(context, MainActivity::class.java)
                startActivity(intent)
                requireActivity().finish()
            }
        }
        return binding.root
    }

}
