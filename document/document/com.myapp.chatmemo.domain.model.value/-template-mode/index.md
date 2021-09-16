[document](../../index.md) / [com.myapp.chatmemo.domain.model.value](../index.md) / [TemplateMode](./index.md)

# TemplateMode

`sealed class TemplateMode`

テンプレートの表示形式定義

### Types

| Name | Summary |
|---|---|
| [Order](-order/index.md) | `data class Order : `[`TemplateMode`](./index.md)<br>登録順 |
| [Randam](-randam/index.md) | `data class Randam : `[`TemplateMode`](./index.md)<br>ランダム |

### Properties

| Name | Summary |
|---|---|
| [massage](massage.md) | `var massage: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |

### Functions

| Name | Summary |
|---|---|
| [getInt](get-int.md) | `fun getInt(): `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)<br>テンプレート表示モードの数値取得 |

### Companion Object Functions

| Name | Summary |
|---|---|
| [toStatus](to-status.md) | `fun toStatus(index: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`): `[`TemplateMode`](./index.md)<br>数値からテンプレートモード取得 |

### Extension Properties

| Name | Summary |
|---|---|
| [text](../../com.myapp.chatmemo.presentation.utils.expansion/text.md) | `val `[`TemplateMode`](./index.md)`.text: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |

### Inheritors

| Name | Summary |
|---|---|
| [Order](-order/index.md) | `data class Order : `[`TemplateMode`](./index.md)<br>登録順 |
| [Randam](-randam/index.md) | `data class Randam : `[`TemplateMode`](./index.md)<br>ランダム |
