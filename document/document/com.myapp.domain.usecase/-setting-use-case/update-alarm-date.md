[document](../../index.md) / [com.myapp.domain.usecase](../index.md) / [SettingUseCase](index.md) / [updateAlarmDate](./update-alarm-date.md)

# updateAlarmDate

`abstract fun updateAlarmDate(dto: `[`NextAlarmTimeInputDto`](../../com.myapp.domain.dto/-next-alarm-time-input-dto/index.md)`): `[`LocalDateTime`](https://developer.android.com/reference/java/time/LocalDateTime.html)

アラーム設定更新

Dtoを元に次回のアラーム時間とアラームモードを設定。
設定後、設定したアラーム時間を返す

### Parameters

`dto` - アラーム設定オブジェクト生成用Dto

**Return**
設定したアラーム時間

