[document](../../index.md) / [com.myapp.presentation.ui.setting](../index.md) / [SettingViewModel](./index.md)

# SettingViewModel

`class SettingViewModel : ViewModel`

設定画面 画面ロジック

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `SettingViewModel(settingUseCase: `[`SettingUseCase`](../../com.myapp.domain.usecase/-setting-use-case/index.md)`)`<br>設定画面 画面ロジック |

### Properties

| Name | Summary |
|---|---|
| [alarmMode](alarm-mode.md) | `val alarmMode: LiveData<`[`AlarmMode`](../../com.myapp.domain.model.value/-alarm-mode/index.md)`>` |
| [alarmModeDiffColor](alarm-mode-diff-color.md) | `val alarmModeDiffColor: LiveData<`[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`>` |
| [alarmModeExplanation](alarm-mode-explanation.md) | `val alarmModeExplanation: LiveData<`[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`>` |
| [alarmTimeDiff](alarm-time-diff.md) | `val alarmTimeDiff: LiveData<`[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`>` |
| [alarmTimeDiffColor](alarm-time-diff-color.md) | `val alarmTimeDiffColor: LiveData<`[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`>` |
| [beforeAlarmMode](before-alarm-mode.md) | `var beforeAlarmMode: `[`AlarmMode`](../../com.myapp.domain.model.value/-alarm-mode/index.md) |
| [beforeDate](before-date.md) | `val beforeDate: LiveData<`[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`>` |
| [hourDate](hour-date.md) | `val hourDate: MutableLiveData<`[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`>` |
| [isEnableConfirmButton](is-enable-confirm-button.md) | `val isEnableConfirmButton: LiveData<`[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)`>` |
| [minutesDate](minutes-date.md) | `val minutesDate: MutableLiveData<`[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`>` |
| [nextAlarmTime](next-alarm-time.md) | `val nextAlarmTime: LiveData<`[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`>` |
| [secondsDate](seconds-date.md) | `val secondsDate: MutableLiveData<`[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`>` |
| [updateState](update-state.md) | `val updateState: LiveData<`[`Status`](../../com.myapp.presentation.utils/-status/index.md)`<`[`LocalDateTime`](https://developer.android.com/reference/java/time/LocalDateTime.html)`>>` |

### Functions

| Name | Summary |
|---|---|
| [setAlarmMode](set-alarm-mode.md) | `fun setAlarmMode(mode: `[`AlarmMode`](../../com.myapp.domain.model.value/-alarm-mode/index.md)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [updateDate](update-date.md) | `fun updateDate(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
