[document](../../index.md) / [com.myapp.domain.usecase](../index.md) / [SettingUseCase](./index.md)

# SettingUseCase

`interface SettingUseCase`

設定関連機能

### Functions

| Name | Summary |
|---|---|
| [getAlarmDate](get-alarm-date.md) | `abstract fun getAlarmDate(): `[`LocalDateTime`](https://developer.android.com/reference/java/time/LocalDateTime.html)<br>現在設定しているアラーム時間取得 |
| [getAlarmMode](get-alarm-mode.md) | `abstract fun getAlarmMode(): `[`AlarmMode`](../../com.myapp.domain.model.value/-alarm-mode/index.md)<br>現在のアラームモード取得 |
| [getNextAlarmDate](get-next-alarm-date.md) | `abstract fun getNextAlarmDate(dto: `[`NextAlarmTimeInputDto`](../../com.myapp.domain.dto/-next-alarm-time-input-dto/index.md)`): `[`LocalDateTime`](https://developer.android.com/reference/java/time/LocalDateTime.html)<br>次回のアラーム時刻取得 |
| [updateAlarmDate](update-alarm-date.md) | `abstract fun updateAlarmDate(dto: `[`NextAlarmTimeInputDto`](../../com.myapp.domain.dto/-next-alarm-time-input-dto/index.md)`): `[`LocalDateTime`](https://developer.android.com/reference/java/time/LocalDateTime.html)<br>アラーム設定更新 |

### Inheritors

| Name | Summary |
|---|---|
| [SettingUseCaseImp](../-setting-use-case-imp/index.md) | `class SettingUseCaseImp : `[`SettingUseCase`](./index.md) |
