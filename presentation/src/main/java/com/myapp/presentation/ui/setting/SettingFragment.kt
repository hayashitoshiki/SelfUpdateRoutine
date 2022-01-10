package com.myapp.presentation.ui.setting

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.NumberPicker
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavHostController
import androidx.navigation.fragment.findNavController
import com.myapp.common.getStrHHmm
import com.myapp.domain.model.value.AlarmMode
import com.myapp.presentation.R
import com.myapp.presentation.ui.diary.AlarmNotificationReceiver
import com.myapp.presentation.utils.component.ButtonPrimaryText
import com.myapp.presentation.utils.component.ListMainDarkText
import com.myapp.presentation.utils.component.ListSubDarkText
import com.myapp.presentation.utils.component.MainLightText
import com.myapp.presentation.utils.expansion.explanation
import com.myapp.presentation.utils.expansion.text
import com.myapp.presentation.utils.theme.*
import dagger.hilt.android.AndroidEntryPoint
import es.dmoral.toasty.Toasty
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.collect
import timber.log.Timber
import java.time.LocalDateTime
import java.time.OffsetDateTime

/**
 * 設定画面
 */
@AndroidEntryPoint
class SettingFragment : Fragment() {

    private val viewModel: SettingViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                val state = viewModel.state.value
                SettingScreenContent(viewModel, state) { setAlarmAndBack(it) }
            }
        }
    }

    // 通知バーアラーム表示設定(次の日の設定)
    private fun setAlarmAndBack(date: LocalDateTime) {
        val alarmManager = requireActivity().getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmNotificationReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context, AlarmNotificationReceiver.NOTIFICATION_ID, intent, PendingIntent.FLAG_UPDATE_CURRENT
        )
        val alarmTimeMilli = date.toEpochSecond(OffsetDateTime.now().offset) * 1000
        val clockInfo = AlarmManager.AlarmClockInfo(alarmTimeMilli, null)
        alarmManager.setAlarmClock(clockInfo, pendingIntent)
        Timber.tag(this.javaClass.simpleName)
            .d("*******************")
        Timber.tag(this.javaClass.simpleName)
            .d("アラーム登録日時= %s", date)
        Timber.tag(this.javaClass.simpleName)
            .d("*******************")
        Toasty.success(requireContext(), "アラーム設定を更新しました！", Toast.LENGTH_SHORT, true)
            .show()
        findNavController().popBackStack()
    }
}


/**
 * 振り返り_事実画面
 *
 */
@ExperimentalMaterialApi
@Composable
fun SettingScreen(
    navController: NavHostController,
    viewModel: SettingViewModel
) {
    val state = viewModel.state.value
    SettingScreenContent(viewModel, state) { }
}


/**
 * 設定画面表示用コンテンツ
 *
 */
@Composable
private fun SettingScreenContent(
    viewModel: SettingViewModel,
    state: SettingContract.State,
    setAlarmAndBack: (LocalDateTime) -> Unit
) {
    LaunchedEffect(true) {
        viewModel.setEvent(SettingContract.Event.CreatedView)
        viewModel.effect.onEach { effect ->
            when (effect) {
                is SettingContract.Effect.NextNavigation -> {
                    setAlarmAndBack(effect.value)
                }
            }
        }.collect()
    }

    // レイアウト
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        backgroundColor = Color.Transparent
    ) {
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val (scroll, button) = createRefs()

            Column(
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp)
                    .verticalScroll(rememberScrollState())
                    .constrainAs(scroll) {
                        top.linkTo(parent.top)
                        bottom.linkTo(button.top)
                    }
            ) {
                // アラーム時間設定
                SettingCard(title = R.string.title_item_alarm_time) {
                    Row {
                        CardIcon(resId = R.drawable.ic_schedule_black_48, modifier = Modifier.align(alignment = Alignment.CenterVertically))
                        val changeHour: (Int) -> Unit = { viewModel.setEvent(SettingContract.Event.OnChangeAlarmHour(it)) }
                        CardTimePicker(
                            initTime = state.beforeTime.hour,
                            min = 18,
                            max = 23,
                            changeEvent = changeHour
                        )
                        ListMainDarkText(
                            text = ":",
                            modifier = Modifier
                                .padding(start = 8.dp)
                                .align(alignment = Alignment.CenterVertically),
                        )
                        val changeMinute: (Int) -> Unit = { viewModel.setEvent(SettingContract.Event.OnChangeAlarmMinute(it)) }
                        CardTimePicker(
                            initTime = state.beforeTime.minute,
                            min = 0,
                            max = 59,
                            changeEvent = changeMinute
                        )
                    }
                }
                // アラームモード設定
                SettingCard(title = R.string.title_item_alarm_mode) {
                    Row {
                        CardIcon(resId = R.drawable.ic_notifications_black_48)
                        Column(modifier = Modifier.padding(top = 8.dp, start = 8.dp)) {
                            Row {
                                RadioButton(
                                    selected = state.afterAlarmMode == AlarmMode.NORMAL,
                                    onClick = { viewModel.setEvent(SettingContract.Event.OnChangeAlarmMode(AlarmMode.NORMAL)) }
                                )
                                ListMainDarkText(text = stringResource(id = R.string.radio_btn_alarm_mode_normal))
                                RadioButton(
                                    selected = state.afterAlarmMode == AlarmMode.HARD,
                                    onClick = { viewModel.setEvent(SettingContract.Event.OnChangeAlarmMode(AlarmMode.HARD)) },
                                    modifier = Modifier.padding(start = 16.dp)
                                )
                                ListMainDarkText(text = stringResource(id = R.string.radio_btn_alarm_mode_hard))
                            }
                            ListSubDarkText(
                                text = stringResource(state.afterAlarmMode.explanation),
                                modifier = Modifier.padding(top = 8.dp)
                            )
                        }
                    }
                }
            }

            // 変更結果
            Column(
                modifier = Modifier
                    .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                PrimaryColor,
                                PrimaryDarkColor
                            )
                        )
                    )
                    .padding(16.dp)
                    .fillMaxWidth()
                    .constrainAs(button) { bottom.linkTo(parent.bottom) },
            ) {
                val alarmTimeDiff = state.beforeTime.getStrHHmm() + "->" + state.afterTime.getStrHHmm()
                val alarmTimeDiffEnable = state.beforeTime.getStrHHmm() != state.afterTime.getStrHHmm()
                val alarmModeDiff = stringResource(state.beforeAlarmMode.text) + "->" + stringResource(state.afterAlarmMode.text)
                val alarmModeDiffEnable = state.beforeAlarmMode != state.afterAlarmMode
                resultList(title = stringResource(id = R.string.title_item_alarm_time), value = alarmTimeDiff, enable = alarmTimeDiffEnable)
                resultList(title = stringResource(id = R.string.title_item_alarm_mode), value = alarmModeDiff, enable = alarmModeDiffEnable)
                resultList(title = stringResource(id = R.string.title_item_next_alarm), value = state.nextAlarmTime, enable = alarmTimeDiffEnable)
                Button(
                    onClick = { viewModel.setEvent(SettingContract.Event.OnClickNextButton) },
                    enabled = state.isEnableConfirmButton,
                    shape = buttonRoundedCornerShape,
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.White,
                        disabledContentColor = ButtonDisableColor
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                        .clip(CircleShape)
                ) {
                    ButtonPrimaryText(text = stringResource(id = R.string.btn_update), enable = state.isEnableConfirmButton)
                }
            }
        }
    }
}

@Composable
private fun resultList(
    title: String,
    value: String,
    enable: Boolean
) {
    Box(modifier = Modifier
        .padding(top = 8.dp)
        .fillMaxWidth()){
        MainLightText(text = title, enable = enable)
        MainLightText(text = value, enable = enable, modifier = Modifier.align(alignment = Alignment.BottomEnd))
    }
}
@Composable
private fun CardIcon(resId: Int, modifier: Modifier = Modifier) {
        Icon(
            modifier = modifier.size(64.dp),
            painter = painterResource(id = resId),
            contentDescription = null
        )
}

@Composable
private fun CardTimePicker(
    initTime: Int,
    max: Int,
    min: Int,
    changeEvent: (Int) -> Unit
) {
    AndroidView(
        modifier = Modifier
            .width(64.dp)
            .height(120.dp)
            .padding(start = 8.dp),
        factory = { context ->
            NumberPicker(context).apply {
                setOnValueChangedListener { _, _, time -> changeEvent(time) }
                minValue = min
                maxValue = max
                value = initTime
            }
        }
    )
}

@Composable
private fun SettingCard(title: Int, content: @Composable () -> Unit){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = 8.dp
    ){
        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
        ) {
            ListMainDarkText(text = stringResource(id = title))
            content()
        }
    }
}