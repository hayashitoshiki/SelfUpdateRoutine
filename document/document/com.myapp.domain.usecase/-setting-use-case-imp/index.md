[document](../../index.md) / [com.myapp.domain.usecase](../index.md) / [SettingUseCaseImp](./index.md)

# SettingUseCaseImp

`class SettingUseCaseImp : `[`SettingUseCase`](../-setting-use-case/index.md)

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `SettingUseCaseImp(localSettingRepository: `[`LocalSettingRepository`](../../com.myapp.domain.repository/-local-setting-repository/index.md)`)` |

### Functions

| Name | Summary |
|---|---|
| [getAlarmDate](get-alarm-date.md) | `fun getAlarmDate(): `[`LocalDateTime`](https://developer.android.com/reference/java/time/LocalDateTime.html)<br>現在設定しているアラーム時間取得 |
| [getAlarmMode](get-alarm-mode.md) | `fun getAlarmMode(): `[`AlarmMode`](../../com.myapp.domain.model.value/-alarm-mode/index.md)<br>現在のアラームモード取得 |
| [getNextAlarmDate](get-next-alarm-date.md) | `fun getNextAlarmDate(dto: `[`NextAlarmTimeInputDto`](../../com.myapp.domain.dto/-next-alarm-time-input-dto/index.md)`): `[`LocalDateTime`](https://developer.android.com/reference/java/time/LocalDateTime.html)<br>次回のアラーム時刻取得 |
| [updateAlarmDate](update-alarm-date.md) | `fun updateAlarmDate(dto: `[`NextAlarmTimeInputDto`](../../com.myapp.domain.dto/-next-alarm-time-input-dto/index.md)`): `[`LocalDateTime`](https://developer.android.com/reference/java/time/LocalDateTime.html)<br>アラーム設定更新 |
