package com.myapp.presentation.ui.home

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import com.myapp.presentation.R

/**
 *  上・右トリミング画像表示用 Custom ImageView
 */
class CutOutTopRightImageView : View {
    private val mPaint: Paint = Paint()

    private var imgRes = R.drawable.ic_cloudy_and_sunny_96dp

    constructor(context: Context) : super(context)

    constructor(
        context: Context,
        attrs: AttributeSet
    ) : super(context, attrs)

    constructor(
        context: Context,
        attrs: AttributeSet,
        defStyle: Int
    ) : super(context, attrs, defStyle)

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas) // 画像
        val bmp = BitmapFactory.decodeResource(resources, imgRes) // 画像の表示サイズ
        val srcRect1 = Rect(0, 0 + bmp.height / 6, bmp.width * 5 / 6, bmp.height) // 元画像のサイズ
        val destRect1 = Rect(0, 0, width, width * (bmp.height / bmp.width)) //        destRect1.offset(100, 100)
        canvas.drawBitmap(bmp, srcRect1, destRect1, mPaint)
    }

    fun setImg(imgRes: Int) {
        this.imgRes = imgRes
        invalidate()
    }
}
