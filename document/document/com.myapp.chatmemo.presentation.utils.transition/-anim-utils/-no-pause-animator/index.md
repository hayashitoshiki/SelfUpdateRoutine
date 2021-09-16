[document](../../../index.md) / [com.myapp.chatmemo.presentation.utils.transition](../../index.md) / [AnimUtils](../index.md) / [NoPauseAnimator](./index.md)

# NoPauseAnimator

`class NoPauseAnimator : `[`Animator`](https://developer.android.com/reference/android/animation/Animator.html)

https://halfthought.wordpress.com/2014/11/07/reveal-transition/

Interrupting Activity transitions can yield an OperationNotSupportedException when the
transition tries to pause the animator. Yikes! We can fix this by wrapping the Animator:

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `NoPauseAnimator(mAnimator: `[`Animator`](https://developer.android.com/reference/android/animation/Animator.html)`)`<br>https://halfthought.wordpress.com/2014/11/07/reveal-transition/ |

### Functions

| Name | Summary |
|---|---|
| [addListener](add-listener.md) | `fun addListener(listener: `[`AnimatorListener`](https://developer.android.com/reference/android/animation/Animator/AnimatorListener.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [cancel](cancel.md) | `fun cancel(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [end](end.md) | `fun end(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [getDuration](get-duration.md) | `fun getDuration(): `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html) |
| [getInterpolator](get-interpolator.md) | `fun getInterpolator(): `[`TimeInterpolator`](https://developer.android.com/reference/android/animation/TimeInterpolator.html) |
| [getListeners](get-listeners.md) | `fun getListeners(): `[`ArrayList`](https://developer.android.com/reference/java/util/ArrayList.html)`<`[`AnimatorListener`](https://developer.android.com/reference/android/animation/Animator/AnimatorListener.html)`>` |
| [getStartDelay](get-start-delay.md) | `fun getStartDelay(): `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html) |
| [isPaused](is-paused.md) | `fun isPaused(): `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [isRunning](is-running.md) | `fun isRunning(): `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [isStarted](is-started.md) | `fun isStarted(): `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [removeAllListeners](remove-all-listeners.md) | `fun removeAllListeners(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [removeListener](remove-listener.md) | `fun removeListener(listener: `[`AnimatorListener`](https://developer.android.com/reference/android/animation/Animator/AnimatorListener.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [setDuration](set-duration.md) | `fun setDuration(durationMS: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`): `[`Animator`](https://developer.android.com/reference/android/animation/Animator.html) |
| [setInterpolator](set-interpolator.md) | `fun setInterpolator(timeInterpolator: `[`TimeInterpolator`](https://developer.android.com/reference/android/animation/TimeInterpolator.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [setStartDelay](set-start-delay.md) | `fun setStartDelay(delayMS: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [setTarget](set-target.md) | `fun setTarget(target: `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`?): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [setupEndValues](setup-end-values.md) | `fun setupEndValues(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [setupStartValues](setup-start-values.md) | `fun setupStartValues(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [start](start.md) | `fun start(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
