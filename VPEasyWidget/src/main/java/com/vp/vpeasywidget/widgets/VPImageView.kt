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

class VPImageView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : AppCompatImageView(context, attrs, defStyleAttr) {

    private val mContext = context
    private var path: Path? = null
    private var rect: RectF? = null

    companion object {
        const val NORMAL = 0
        const val DYNAMIC = 1
        const val SQUARE = 2
    }

    var cornerRadius = 0F
        set(value) {
            field = value
            invalidate()
        }

    var shape = NORMAL
        set(value) {
            field = value
            invalidate()
        }

    init {
        val parent = mContext.obtainStyledAttributes(attrs, R.styleable.VPImageView)

        cornerRadius = parent.getDimensionPixelSize(R.styleable.VPImageView_cornerRadius, 0).toFloat()
        shape = parent.getInt(R.styleable.VPImageView_shape, NORMAL)

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
        if (d != null) {
            when (shape) {
                DYNAMIC -> {
                    val width = MeasureSpec.getSize(widthMeasureSpec)
                    val height = ceil((width * d.intrinsicHeight / d.intrinsicWidth).toDouble())
                    this.setMeasuredDimension(width, height.toInt())
                }
                SQUARE -> {
                    super.onMeasure(widthMeasureSpec, heightMeasureSpec)
                    val width = measuredWidth
                    setMeasuredDimension(width, width)
                }
                NORMAL -> super.onMeasure(widthMeasureSpec, heightMeasureSpec)
            }
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        }
    }
}