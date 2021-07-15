package com.vp.vpeasywidget.widgets

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.widget.LinearLayout
import com.vp.vpeasywidget.R
import com.vp.vpeasywidget.utils.sPx
import kotlinx.android.synthetic.main.vp_no_data.view.*

class VPNoDataWidget @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : LinearLayout(context, attrs, defStyleAttr) {

    private val mContext = context

    var icon = 0
        set(value) {
            field = value
            iconNoData.setImageResource(icon)
            Log.e("vsbdvdsbv", field.toString())
        }

    var text = "No data found!!"
        set(value) {
            field = value
            textNoData.text = text
        }

    var textSize = 20.sPx
        set(value) {
            field = value
            textNoData.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize.toFloat())
        }

    var textColor = Color.BLACK
        set(value) {
            field = value
            textNoData.setTextColor(textColor)
        }

    var textStyle = Typeface.NORMAL
        set(value) {
            field = value
            textNoData.setTypeface(textNoData.typeface, textStyle)
        }

    init {
        View.inflate(mContext, R.layout.vp_no_data, this)
        val parent = mContext.obtainStyledAttributes(attrs, R.styleable.VPNoDataWidget)
        if (parent.hasValue(R.styleable.VPNoDataWidget_android_text))
            text = parent.getString(R.styleable.VPNoDataWidget_android_text).toString()
        textSize = parent.getDimensionPixelSize(R.styleable.VPNoDataWidget_android_textSize, textSize)
        textColor = parent.getColor(R.styleable.VPNoDataWidget_android_textColor, textColor)
        textStyle = parent.getInt(R.styleable.VPNoDataWidget_android_textStyle, textStyle)
        icon = parent.getResourceId(R.styleable.VPNoDataWidget_no_icon, icon)
        parent.recycle()
    }
}