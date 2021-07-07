package com.vp.vpeasywidget.widgets

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatTextView
import com.vp.vpeasywidget.R
import com.vp.vpeasywidget.utils.getDrawableRes

class VPTextView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = android.R.attr.textViewStyle) : AppCompatTextView(context, attrs, defStyleAttr) {

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

    var drawableClickListener: DrawableClickListener? = null

    init {
        val shape = GradientDrawable()
        shape.shape = GradientDrawable.RECTANGLE
        this.background = shape

        val parent = mContext.obtainStyledAttributes(attrs, R.styleable.VPTextView)

        icon = parent.getResourceId(R.styleable.VPTextView_icon, icon)
        showIcon = parent.getBoolean(R.styleable.VPTextView_showIcon, showIcon)
        iconSize = parent.getDimensionPixelSize(R.styleable.VPTextView_icon_size, iconSize)
        iconTint = parent.getColor(R.styleable.VPTextView_iconTint, iconTint)

        if (parent.hasValue(R.styleable.VPTextView_icon_position)) {
            iconPosition = parent.getInt(R.styleable.VPTextView_icon_position, LEFT)
        }

        backColor = parent.getColor(R.styleable.VPTextView_backColor, backColor)
        isCapsule = parent.getBoolean(R.styleable.VPTextView_isCapsule, isCapsule)
        capsuleRadius = parent.getDimensionPixelSize(R.styleable.VPTextView_capsuleRadius, 200).toFloat()
        cornerRadiusTopLeft = parent.getDimensionPixelSize(R.styleable.VPTextView_cornerRadiusTopLeft, 0).toFloat()
        cornerRadiusTopRight = parent.getDimensionPixelSize(R.styleable.VPTextView_cornerRadiusTopRight, 0).toFloat()
        cornerRadiusBottomLeft = parent.getDimensionPixelSize(R.styleable.VPTextView_cornerRadiusBottomLeft, 0).toFloat()
        cornerRadiusBottomRight = parent.getDimensionPixelSize(R.styleable.VPTextView_cornerRadiusBottomRight, 0).toFloat()
        borderColor = parent.getColor(R.styleable.VPTextView_borderColor, borderColor)
        borderWidth = parent.getDimensionPixelSize(R.styleable.VPTextView_borderWidth, borderWidth)
        hasBorder = parent.getBoolean(R.styleable.VPTextView_hasBorder, hasBorder)

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
                item.setBounds(0,0, iconSize, iconSize)
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

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event?.action == MotionEvent.ACTION_DOWN && showIcon && resizeIcon() != null) {
            when (iconPosition) {
                LEFT -> if(event.rawX <= (this.left + this.totalPaddingLeft)) {
                    drawableClickListener?.onClick(this,DrawableClickListener.DrawablePosition.LEFT)
                    return true
                }
                TOP -> if(event.rawY <= (this.top + this.totalPaddingTop)) {
                    drawableClickListener?.onClick(this,DrawableClickListener.DrawablePosition.TOP)
                    return true
                }
                RIGHT -> if(event.rawX <= (this.right + this.totalPaddingEnd)) {
                    drawableClickListener?.onClick(this, DrawableClickListener.DrawablePosition.RIGHT)
                    return true
                }
                BOTTOM -> if(event.rawY <= (this.bottom + this.totalPaddingBottom)) {
                    drawableClickListener?.onClick(this,DrawableClickListener.DrawablePosition.BOTTOM)
                    return true
                }
            }
            return false
        }
        return false
    }

    inner class WrappedDrawable(private val drawable: Drawable) : Drawable() {

        override fun setBounds(left: Int, top: Int, right: Int, bottom: Int) {
            //update bounds to get correctly
            super.setBounds(left, top, right, bottom)
            val drawable: Drawable? = drawable
            drawable?.setBounds(left, top, right, bottom)
        }

        override fun setAlpha(alpha: Int) {
            val drawable: Drawable? = drawable
            if (drawable != null) {
                drawable.alpha = alpha
            }
        }

        override fun setColorFilter(colorFilter: ColorFilter?) {
            val drawable: Drawable? = drawable
            if (drawable != null) {
                drawable.colorFilter = colorFilter
            }
        }

        override fun getOpacity(): Int {
            val drawable: Drawable? = drawable
            return drawable?.opacity ?: PixelFormat.UNKNOWN
        }

        override fun draw(canvas: Canvas) {
            val drawable: Drawable? = drawable
            drawable?.draw(canvas)
        }

        override fun getIntrinsicWidth(): Int {
            val drawable: Drawable? = drawable
            return drawable?.bounds?.width() ?: 0
        }

        override fun getIntrinsicHeight(): Int {
            val drawable: Drawable? = drawable
            return drawable?.bounds?.height() ?: 0
        }
    }

    private fun setCorners() {
        val dg = this.background as GradientDrawable
        if (isCapsule)
            dg.cornerRadii = floatArrayOf(capsuleRadius, capsuleRadius, capsuleRadius, capsuleRadius, capsuleRadius, capsuleRadius, capsuleRadius, capsuleRadius)
        else
            dg.cornerRadii = floatArrayOf(cornerRadiusTopLeft, cornerRadiusTopLeft, cornerRadiusTopRight, cornerRadiusTopRight, cornerRadiusBottomRight, cornerRadiusBottomRight, cornerRadiusBottomLeft, cornerRadiusBottomLeft)
    }

    interface DrawableClickListener {
        enum class DrawablePosition {
            TOP, BOTTOM, LEFT, RIGHT
        }

        fun onClick(view: VPTextView, target: DrawablePosition)
    }
}