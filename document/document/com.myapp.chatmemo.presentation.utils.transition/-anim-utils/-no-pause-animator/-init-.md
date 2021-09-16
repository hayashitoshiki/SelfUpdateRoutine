[document](../../../index.md) / [com.myapp.chatmemo.presentation.utils.transition](../../index.md) / [AnimUtils](../index.md) / [NoPauseAnimator](index.md) / [&lt;init&gt;](./-init-.md)

# &lt;init&gt;

`NoPauseAnimator(mAnimator: `[`Animator`](https://developer.android.com/reference/android/animation/Animator.html)`)`

https://halfthought.wordpress.com/2014/11/07/reveal-transition/

Interrupting Activity transitions can yield an OperationNotSupportedException when the
transition tries to pause the animator. Yikes! We can fix this by wrapping the Animator:

