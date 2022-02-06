package com.myapp.presentation.ui.setting

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.widget.NumberPicker
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavHostController
import com.myapp.common.getStrHHmm
import com.myapp.domain.model.value.AlarmMode
import com.myapp.presentation.R
import com.myapp.presentation.ui.diary.AlarmNotificationReceiver
import com.myapp.presentation.utils.component.*
import com.myapp.presentation.utils.expansion.explanation
import com.myapp.presentation.utils.expansion.text
import es.dmoral.toasty.Toasty
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.collect
import timber.log.Timber
import java.time.OffsetDateTime

/**
 * 設定画面
 *
 */
@ExperimentalMaterialApi
@Composable
fun SettingScreen(
    navHostController: NavHostController,
    viewModel: SettingViewModel
) {
    val context = LocalContext.current
    val state = viewModel.state.value
    val event: (SettingContract.Event) -> Unit = { viewModel.setEvent(it) }
    LaunchedEffect(true) {
        viewModel.setEvent(SettingContract.Event.CreatedView)
        viewModel.effect.onEach { effect ->
            when (effect) {
                is SettingContract.Effect.NextNavigation -> {
                    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
                    val intent = Intent(context, AlarmNotificationReceiver::class.java)
                    val pendingIntent = PendingIntent.getBroadcast(
                        context, AlarmNotificationReceiver.NOTIFICATION_ID, intent, PendingIntent.FLAG_UPDATE_CURRENT
                    )
                    val alarmTimeMilli = effect.value.toEpochSecond(OffsetDateTime.now().offset) * 1000
                    val clockInfo = AlarmManager.AlarmClockInfo(alarmTimeMilli, null)
                    alarmManager.setAlarmClock(clockInfo, pendingIntent)
                    Timber.tag("SettingScreen").d("*******************")
                    Timber.tag("SettingScreen").d("アラーム登録日時= %s", effect.value)
                    Timber.tag("SettingScreen").d("*******************")
                    Toasty.success(context, "アラーム設定を更新しました！", Toast.LENGTH_SHORT, true).show()
                    navHostController.popBackStack()
                }
                is SettingContract.Effect.ShowError -> {
                    Toasty.error(context, "アラーム設定の更新に失敗しました。", Toast.LENGTH_SHORT, true).show()
                }
            }
        }.collect()
    }
    if (state.init) {
        SettingScreenContent(event, state)
    }
}


/**
 * 設定画面表示用コンテンツ
 *
 * @param event ユーザアクションイベント
 * @param state 設定値
 */
@Composable
private fun SettingScreenContent(
    event: (SettingContract.Event) -> Unit,
    state: SettingContract.State
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
                    height = Dimension.fillToConstraints
                }
        ) {
            // アラーム時間設定
            SettingCard(title = R.string.title_item_alarm_time) {
                Row {
                    CardIcon(resId = R.drawable.ic_schedule_black_48, modifier = Modifier.align(alignment = Alignment.CenterVertically))
                    val changeHour: (Int) -> Unit = { event(SettingContract.Event.OnChangeAlarmHour(it)) }
                    CardTimePicker(initTime = state.beforeTime.hour, min = 18, max = 23, changeEvent = changeHour)
                    ListMainDarkText(
                        text = ":",
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .align(alignment = Alignment.CenterVertically),
                    )
                    val changeMinute: (Int) -> Unit = { event(SettingContract.Event.OnChangeAlarmMinute(it)) }
                    CardTimePicker(initTime = state.beforeTime.minute, min = 0, max = 59, changeEvent = changeMinute)
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
                                onClick = { event(SettingContract.Event.OnChangeAlarmMode(AlarmMode.NORMAL)) }
                            )
                            ListMainDarkText(text = stringResource(id = R.string.radio_btn_alarm_mode_normal))
                            RadioButton(
                                selected = state.afterAlarmMode == AlarmMode.HARD,
                                onClick = { event(SettingContract.Event.OnChangeAlarmMode(AlarmMode.HARD)) },
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
        BottomResultCard(modifier = Modifier.constrainAs(button) { bottom.linkTo(parent.bottom) }) {
            val alarmTimeDiff = state.beforeTime.getStrHHmm() + "->" + state.afterTime.getStrHHmm()
            val alarmTimeDiffEnable = state.beforeTime.getStrHHmm() != state.afterTime.getStrHHmm()
            val alarmModeDiff = stringResource(state.beforeAlarmMode.text) + "->" + stringResource(state.afterAlarmMode.text)
            val alarmModeDiffEnable = state.beforeAlarmMode != state.afterAlarmMode
            ResultList(title = stringResource(id = R.string.title_item_alarm_time), value = alarmTimeDiff, enable = alarmTimeDiffEnable)
            ResultList(title = stringResource(id = R.string.title_item_alarm_mode), value = alarmModeDiff, enable = alarmModeDiffEnable)
            ResultList(title = stringResource(id = R.string.title_item_next_alarm), value = state.nextAlarmTime, enable = alarmTimeDiffEnable)
            WhiteColorButton(
                onClick = { event(SettingContract.Event.OnClickNextButton) },
                enabled = state.isEnableConfirmButton,
                text = stringResource(id = R.string.btn_update),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
            )
        }
    }
}

/**
 * 設定値表示用リスト
 *
 * @param title 設定項目タイトル
 * @param value 設定内容
 * @param enable 活性非活性
 */
@Composable
private fun ResultList(
    title: String,
    value: String,
    enable: Boolean
) {
    Box(
        modifier = Modifier
            .padding(top = 8.dp)
            .fillMaxWidth()
    ){
        MainLightText(text = title, enable = enable)
        MainLightText(text = value, enable = enable, modifier = Modifier.align(alignment = Alignment.BottomEnd))
    }
}

/**
 * カード用TimePicker
 *
 * @param initTime 初期値
 * @param max 最大値
 * @param min 最小値
 * @param changeEvent 変更イベント
 */
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
            NumberPicker(context).also {
                it.setOnValueChangedListener { _, _, time -> changeEvent(time) }
                it.minValue = min
                it.maxValue = max
                it.value = initTime
            }
        }
    )
}
