package com.vp.vpeasywidget.widgets

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.widget.RelativeLayout
import com.vp.vpeasywidget.R

class VPRelativeLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : RelativeLayout(context, attrs, defStyleAttr) {

    var isCapsule = false
        set(value) {
            field = value
            setCorners()
        }

    var capsuleRadius = 200F
        set(value) {
            field = value
            setCorners()
        }

    var cornerRadiusTopLeft = 0F
        set(value) {
            field = value
            setCorners()
        }

    var cornerRadiusBottomLeft = 0F
        set(value) {
            field = value
            setCorners()
        }
    var cornerRadiusTopRight = 0F
        set(value) {
            field = value
            setCorners()
        }
    var cornerRadiusBottomRight = 0F
        set(value) {
            field = value
            setCorners()
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
        val parent = context.obtainStyledAttributes(attrs, R.styleable.VPRelativeLayout)
        try {
            isCapsule = parent.getBoolean(R.styleable.VPRelativeLayout_rl_isCapsule, isCapsule)
            capsuleRadius = parent.getDimensionPixelSize(R.styleable.VPRelativeLayout_rl_capsuleRadius, 200).toFloat()
            cornerRadiusTopLeft = parent.getDimensionPixelSize(R.styleable.VPRelativeLayout_rl_cornerRadiusTopLeft, 0).toFloat()
            cornerRadiusTopRight = parent.getDimensionPixelSize(R.styleable.VPRelativeLayout_rl_cornerRadiusTopRight, 0).toFloat()
            cornerRadiusBottomLeft = parent.getDimensionPixelSize(R.styleable.VPRelativeLayout_rl_cornerRadiusBottomLeft, 0).toFloat()
            cornerRadiusBottomRight = parent.getDimensionPixelSize(R.styleable.VPRelativeLayout_rl_cornerRadiusBottomRight, 0).toFloat()
            backColor = parent.getColor(R.styleable.VPRelativeLayout_rl_backColor, backColor)
            borderWidth = parent.getDimensionPixelSize(R.styleable.VPRelativeLayout_rl_borderWidth, borderWidth.toInt()).toFloat()
            borderColor = parent.getColor(R.styleable.VPRelativeLayout_rl_borderColor, borderColor)
        } catch (e: Exception) {
            parent.recycle()
        }
    }

    private fun setCorners() {
        val dg = this.background as GradientDrawable
        if (isCapsule)
            dg.cornerRadii = floatArrayOf(capsuleRadius, capsuleRadius, capsuleRadius, capsuleRadius, capsuleRadius, capsuleRadius, capsuleRadius, capsuleRadius)
        else
            dg.cornerRadii = floatArrayOf(cornerRadiusTopLeft, cornerRadiusTopLeft, cornerRadiusTopRight, cornerRadiusTopRight, cornerRadiusBottomRight, cornerRadiusBottomRight, cornerRadiusBottomLeft, cornerRadiusBottomLeft)
    }
}