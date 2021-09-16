[document](../../index.md) / [com.myapp.presentation.utils](../index.md) / [Status](./index.md)

# Status

`sealed class Status<out T>`

UIのステータス管理

### Parameters

`T` -

### Types

| Name | Summary |
|---|---|
| [Failure](-failure/index.md) | `data class Failure : `[`Status`](./index.md)`<`[`Nothing`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-nothing/index.html)`>` |
| [Loading](-loading.md) | `object Loading : `[`Status`](./index.md)`<`[`Nothing`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-nothing/index.html)`>` |
| [Non](-non.md) | `object Non : `[`Status`](./index.md)`<`[`Nothing`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-nothing/index.html)`>` |
| [Success](-success/index.md) | `data class Success<T> : `[`Status`](./index.md)`<`[`T`](-success/index.md#T)`>` |

### Inheritors

| Name | Summary |
|---|---|
| [Failure](-failure/index.md) | `data class Failure : `[`Status`](./index.md)`<`[`Nothing`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-nothing/index.html)`>` |
| [Loading](-loading.md) | `object Loading : `[`Status`](./index.md)`<`[`Nothing`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-nothing/index.html)`>` |
| [Non](-non.md) | `object Non : `[`Status`](./index.md)`<`[`Nothing`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-nothing/index.html)`>` |
| [Success](-success/index.md) | `data class Success<T> : `[`Status`](./index.md)`<`[`T`](-success/index.md#T)`>` |
