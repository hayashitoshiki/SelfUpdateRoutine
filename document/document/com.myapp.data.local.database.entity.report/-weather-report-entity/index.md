[document](../../index.md) / [com.myapp.data.local.database.entity.report](../index.md) / [WeatherReportEntity](./index.md)

# WeatherReportEntity

`data class WeatherReportEntity`

感情日記_テーブル

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `WeatherReportEntity(id: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`?, heartScore: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`, reasonComment: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, improveComment: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, createTime: `[`LocalDateTime`](https://developer.android.com/reference/java/time/LocalDateTime.html)`)`<br>感情日記_テーブル |

### Properties

| Name | Summary |
|---|---|
| [createTime](create-time.md) | `val createTime: `[`LocalDateTime`](https://developer.android.com/reference/java/time/LocalDateTime.html)<br>生成日時 |
| [heartScore](heart-score.md) | `val heartScore: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)<br>今日の天気（感情） |
| [id](id.md) | `val id: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`?`<br>ID |
| [improveComment](improve-comment.md) | `val improveComment: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>明日への改善点 |
| [reasonComment](reason-comment.md) | `val reasonComment: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>理由 |
