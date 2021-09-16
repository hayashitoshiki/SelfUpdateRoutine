[document](../../index.md) / [com.myapp.chatmemo.presentation.utils.transition](../index.md) / [FabTransform](./index.md)

# FabTransform

`class FabTransform : `[`Transition`](https://developer.android.com/reference/android/transition/Transition.html)

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `FabTransform(fabColor: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`, fabIconResId: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`)`<br>`FabTransform(context: `[`Context`](https://developer.android.com/reference/android/content/Context.html)`, attrs: `[`AttributeSet`](https://developer.android.com/reference/android/util/AttributeSet.html)`?)` |

### Functions

| Name | Summary |
|---|---|
| [captureEndValues](capture-end-values.md) | `fun captureEndValues(transitionValues: `[`TransitionValues`](https://developer.android.com/reference/android/transition/TransitionValues.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [captureStartValues](capture-start-values.md) | `fun captureStartValues(transitionValues: `[`TransitionValues`](https://developer.android.com/reference/android/transition/TransitionValues.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [createAnimator](create-animator.md) | `fun createAnimator(sceneRoot: `[`ViewGroup`](https://developer.android.com/reference/android/view/ViewGroup.html)`, startValues: `[`TransitionValues`](https://developer.android.com/reference/android/transition/TransitionValues.html)`?, endValues: `[`TransitionValues`](https://developer.android.com/reference/android/transition/TransitionValues.html)`?): `[`Animator`](https://developer.android.com/reference/android/animation/Animator.html)`?` |

### Companion Object Functions

| Name | Summary |
|---|---|
| [setup](setup.md) | `fun setup(activity: `[`Activity`](https://developer.android.com/reference/android/app/Activity.html)`, target: `[`View`](https://developer.android.com/reference/android/view/View.html)`?): `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>Create a [FabTransform](./index.md) from the supplied `activity` extras and set as its shared element enter/return transition. |
