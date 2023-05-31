package com.vp.vpeasywidget.widgets

import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.Typeface
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.vp.vpeasywidget.R
import com.vp.vpeasywidget.databinding.VpNoDataBinding
import com.vp.vpeasywidget.utils.px
import com.vp.vpeasywidget.utils.sPx

class VPNoDataWidget @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : LinearLayout(context, attrs, defStyleAttr) {

    private val mContext = context
    private val binding = VpNoDataBinding.inflate(LayoutInflater.from(context), this, true)

    var icon = 0
        set(value) {
            field = value
            binding.iconNoData.setImageResource(icon)
        }

    var iconSize = 120.px
        set(value) {
            field = value
            val lp = binding.iconNoData.layoutParams
            lp.width = field
            lp.height = field
            binding.iconNoData.layoutParams = lp
        }

    var iconTint = 0
        set(value) {
            field = value
            if (field != 0) {
                binding.iconNoData.setColorFilter(iconTint, PorterDuff.Mode.SRC_ATOP)
            }
        }

    var text = "No data found!!"
        set(value) {
            field = value
            binding.textNoData.text = text
        }

    var textSize = 20.sPx
        set(value) {
            field = value
            binding.textNoData.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize.toFloat())
        }

    var textColor = Color.BLACK
        set(value) {
            field = value
            binding.textNoData.setTextColor(textColor)
        }

    var textStyle = Typeface.NORMAL
        set(value) {
            field = value
            binding.textNoData.setTypeface(binding.textNoData.typeface, textStyle)
        }

    init {
        val parent = mContext.obtainStyledAttributes(attrs, R.styleable.VPNoDataWidget)
        if (parent.hasValue(R.styleable.VPNoDataWidget_android_text))
            text = parent.getString(R.styleable.VPNoDataWidget_android_text).toString()
        textSize = parent.getDimensionPixelSize(R.styleable.VPNoDataWidget_android_textSize, textSize)
        textColor = parent.getColor(R.styleable.VPNoDataWidget_android_textColor, textColor)
        textStyle = parent.getInt(R.styleable.VPNoDataWidget_android_textStyle, textStyle)
        icon = parent.getResourceId(R.styleable.VPNoDataWidget_no_icon, icon)
        iconSize = parent.getDimensionPixelSize(R.styleable.VPNoDataWidget_no_iconSize, iconSize)
        iconTint = parent.getColor(R.styleable.VPNoDataWidget_no_iconTint, iconTint)
        parent.recycle()
    }
}