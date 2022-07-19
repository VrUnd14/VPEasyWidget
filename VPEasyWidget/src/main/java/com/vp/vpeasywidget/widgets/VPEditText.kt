package com.vp.vpeasywidget.widgets

import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import com.vp.vpeasywidget.R
import com.vp.vpeasywidget.utils.getDrawableRes

@Suppress("DEPRECATION")
class VPEditText @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = android.R.attr.editTextStyle) : AppCompatEditText(context, attrs, defStyleAttr) {

    private val mContext = context

    companion object {
        const val LEFT = 0
        const val TOP = 1
        const val RIGHT = 2
        const val BOTTOM = 3
    }

    var icon = 0
        set(value) {
            field = value
            setupIcon()
        }

    var iconSize = 24
        set(value) {
            field = value
            setupIcon()
        }

    var iconTint = 0
        set(value) {
            field = value
            setupIcon()
        }

    var showIcon = true
        set(value) {
            field = value
            setupIcon()
        }

    var iconPosition = LEFT
        set(value) {
            field = value
            setupIcon()
        }

    var backColor = Color.TRANSPARENT
        set(value) {
            field = value
            val dg = this.background as GradientDrawable
            dg.setColor(backColor)
        }

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

    var borderColor = Color.BLACK
        set(value) {
            field = value
            val dg = this.background as GradientDrawable
            dg.setStroke(borderWidth.takeIf { hasBorder } ?: 0, borderColor)
        }

    var borderWidth = 0
        set(value) {
            field = value
            val dg = this.background as GradientDrawable
            dg.setStroke(borderWidth.takeIf { hasBorder } ?: 0, borderColor)
        }

    var hasBorder = true
        set(value) {
            field = value
            val dg = this.background as GradientDrawable
            dg.setStroke(borderWidth.takeIf { hasBorder } ?: 0, borderColor)
        }

    var customFont = ""
        set(value) {
            field = value
            if (field.isNotEmpty()) {
                val typeface = Typeface.createFromAsset(context.assets, "fonts/$field")
                if (getTypeface() != null)
                    super.setTypeface(typeface, getTypeface().style)
                else
                    super.setTypeface(typeface, Typeface.NORMAL)
            }
        }

    override fun setEnabled(enabled: Boolean) {
        this.alpha = 1F.takeIf { enabled } ?: 0.5F
        super.setEnabled(enabled)
    }

    init {
        val shape = GradientDrawable()
        shape.shape = GradientDrawable.RECTANGLE
        this.background = shape

        val parent = mContext.obtainStyledAttributes(attrs, R.styleable.VPEditText)

        icon = parent.getResourceId(R.styleable.VPEditText_et_icon, icon)
        showIcon = parent.getBoolean(R.styleable.VPEditText_et_showIcon, showIcon)
        iconSize = parent.getDimensionPixelSize(R.styleable.VPEditText_et_icon_size, iconSize)
        iconTint = parent.getColor(R.styleable.VPEditText_et_iconTint, iconTint)

        if (parent.hasValue(R.styleable.VPEditText_et_icon_position)) {
            iconPosition = parent.getInt(R.styleable.VPEditText_et_icon_position, LEFT)
        }

        backColor = parent.getColor(R.styleable.VPEditText_et_backColor, backColor)
        isCapsule = parent.getBoolean(R.styleable.VPEditText_et_isCapsule, isCapsule)
        capsuleRadius = parent.getDimensionPixelSize(R.styleable.VPEditText_et_capsuleRadius, 200).toFloat()
        cornerRadiusTopLeft = parent.getDimensionPixelSize(R.styleable.VPEditText_et_cornerRadiusTopLeft, 0).toFloat()
        cornerRadiusTopRight = parent.getDimensionPixelSize(R.styleable.VPEditText_et_cornerRadiusTopRight, 0).toFloat()
        cornerRadiusBottomLeft = parent.getDimensionPixelSize(R.styleable.VPEditText_et_cornerRadiusBottomLeft, 0).toFloat()
        cornerRadiusBottomRight = parent.getDimensionPixelSize(R.styleable.VPEditText_et_cornerRadiusBottomRight, 0).toFloat()
        borderColor = parent.getColor(R.styleable.VPEditText_et_borderColor, borderColor)
        borderWidth = parent.getDimensionPixelSize(R.styleable.VPEditText_et_borderWidth, borderWidth)
        hasBorder = parent.getBoolean(R.styleable.VPEditText_et_hasBorder, hasBorder)

        if (parent.hasValue(R.styleable.VPEditText_et_customFont)) {
            customFont = parent.getString(R.styleable.VPEditText_et_customFont) ?: ""
        }

        parent.recycle()
    }

    private fun setupIcon() {
        when (iconPosition) {
            LEFT -> this.setCompoundDrawablesRelativeWithIntrinsicBounds(resizeIcon(), null, null, null)
            TOP -> this.setCompoundDrawablesRelativeWithIntrinsicBounds(null, resizeIcon(), null, null)
            RIGHT -> this.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, resizeIcon(), null)
            BOTTOM -> this.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, resizeIcon())
        }
    }

    private fun resizeIcon(): Drawable? {
        if (showIcon) {
            try {
                var item = mContext.getDrawableRes(icon)
                item = WrappedDrawable(item!!)
                item.setBounds(0, 0, iconSize, iconSize)
                if (iconTint != 0) {
//                    DrawableCompat.setTint(item, iconTint)
                    item.setColorFilter(iconTint, PorterDuff.Mode.SRC_ATOP)
                }
                return item
            } catch (e: Exception) {
            }
        }
        return null
    }

    inner class WrappedDrawable(private val drawable: Drawable) : Drawable() {

        override fun setBounds(left: Int, top: Int, right: Int, bottom: Int) {
            //update bounds to get correctly
            super.setBounds(left, top, right, bottom)
            val drawable: Drawable = drawable
            drawable.setBounds(left, top, right, bottom)
        }

        override fun setAlpha(alpha: Int) {
            val drawable: Drawable = drawable
            drawable.alpha = alpha
        }

        override fun setColorFilter(colorFilter: ColorFilter?) {
            val drawable: Drawable = drawable
            drawable.colorFilter = colorFilter
        }

        override fun getOpacity(): Int {
            val drawable: Drawable = drawable
            return drawable.opacity
        }

        override fun draw(canvas: Canvas) {
            val drawable: Drawable = drawable
            drawable.draw(canvas)
        }

        override fun getIntrinsicWidth(): Int {
            val drawable: Drawable = drawable
            return drawable.bounds.width()
        }

        override fun getIntrinsicHeight(): Int {
            val drawable: Drawable = drawable
            return drawable.bounds.height()
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