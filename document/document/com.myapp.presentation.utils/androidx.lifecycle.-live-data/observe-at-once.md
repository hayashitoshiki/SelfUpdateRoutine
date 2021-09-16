[document](../../index.md) / [com.myapp.presentation.utils](../index.md) / [androidx.lifecycle.LiveData](index.md) / [observeAtOnce](./observe-at-once.md)

# observeAtOnce

`fun <T> LiveData<`[`T`](observe-at-once.md#T)`>.observeAtOnce(owner: LifecycleOwner, observe: (value: `[`T`](observe-at-once.md#T)`) -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`): <ERROR CLASS>`

初回のみ流す

### Parameters

`T` - LiveData

`owner` - ライフサイクル

`observe` - 処理