package com.vp.vpeasywidget.widgets

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.widget.LinearLayout
import com.vp.vpeasywidget.R
import com.vp.vpeasywidget.utils.getDrawableRes

class VPLinearLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : LinearLayout(context, attrs, defStyleAttr) {

    var cornerRadius = 0F
        set(value) {
            field = value
            val mainGD = this.background as GradientDrawable
            mainGD.cornerRadius = field
        }

    var backColor = Color.TRANSPARENT
        set(value) {
            field = value
            val mainGD = this.background as GradientDrawable
            mainGD.setColor(field)
        }

    var borderWidth = 0F
        set(value) {
            field = value
            val mainGD = this.background as GradientDrawable
            mainGD.setStroke(field.toInt(), borderColor)
        }

    var borderColor = Color.GRAY
        set(value) {
            field = value
            val mainGD = this.background as GradientDrawable
            mainGD.setStroke(borderWidth.toInt(), field)
        }

    init {
        background = GradientDrawable()
        val parent = context.obtainStyledAttributes(attrs, R.styleable.VPLinearLayout)
        try {
            cornerRadius = parent.getDimensionPixelSize(R.styleable.VPLinearLayout_ll_cornerRadius, cornerRadius.toInt()).toFloat()
            backColor = parent.getColor(R.styleable.VPLinearLayout_ll_backColor, backColor)
            borderWidth = parent.getDimensionPixelSize(R.styleable.VPLinearLayout_ll_borderWidth, borderWidth.toInt()).toFloat()
            borderColor = parent.getColor(R.styleable.VPLinearLayout_ll_borderColor, borderColor)
        } catch (e: Exception) {
            parent.recycle()
        }
    }
}