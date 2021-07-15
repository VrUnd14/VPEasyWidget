package com.vp.vpeasywidget.widgets

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Path
import android.graphics.RectF
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import com.vp.vpeasywidget.R
import kotlin.math.ceil

@Suppress("DEPRECATION")
class VPImageView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : AppCompatImageView(context, attrs, defStyleAttr) {

    private val mContext = context
    private var path: Path? = null
    private var rect: RectF? = null

    var cornerRadius = 0F
        set(value) {
            field = value
            invalidate()
        }

    var dynamicHeight = false
        set(value) {
            field = value
            invalidate()
        }

    init {
        val parent = mContext.obtainStyledAttributes(attrs, R.styleable.VPImageView)

        cornerRadius = parent.getDimensionPixelSize(R.styleable.VPImageView_cornerRadius, 0).toFloat()
        dynamicHeight = parent.getBoolean(R.styleable.VPImageView_dynamicHeight, false)

        parent.recycle()

        path = Path()
    }

    @SuppressLint("CanvasSize", "DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        rect = RectF(0F, 0F, this.width.toFloat(), this.height.toFloat())
        path?.addRoundRect(rect!!, cornerRadius, cornerRadius, Path.Direction.CW)
        canvas.clipPath(path!!)
        super.onDraw(canvas)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val d = this.drawable
        if (d != null && dynamicHeight) {
            val width = MeasureSpec.getSize(widthMeasureSpec)
            val height = ceil((width * d.intrinsicHeight / d.intrinsicWidth).toDouble())
            this.setMeasuredDimension(width, height.toInt())
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        }
    }
}