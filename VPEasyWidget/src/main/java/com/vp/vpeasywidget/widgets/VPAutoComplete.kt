package com.vp.vpeasywidget.widgets

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.text.InputFilter
import android.text.InputType
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Adapter
import android.widget.AdapterView
import android.widget.Filterable
import android.widget.ListAdapter
import android.widget.RelativeLayout
import androidx.core.widget.ImageViewCompat
import com.vp.vpeasywidget.R
import com.vp.vpeasywidget.databinding.VpAutoSpinnerBinding
import com.vp.vpeasywidget.utils.getDrawableRes
import com.vp.vpeasywidget.utils.px
import com.vp.vpeasywidget.utils.sPx
import com.vp.vpeasywidget.utils.setVisible
import com.vp.vpeasywidget.utils.trimmedText

class VPAutoComplete @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : RelativeLayout(context, attrs, defStyleAttr) {

    private val mContext = context
    private val binding = VpAutoSpinnerBinding.inflate(LayoutInflater.from(context), this, true)

    companion object {
        const val IN = 1
        const val TOP = 2
    }

    var cornerRadius = 5.px.toFloat()
        set(value) {
            field = value
            updateCorners()
        }

    var backColor = 0xFFF1F1F1.toInt()
        set(value) {
            field = value
            val mainGD = binding.vpParentLayout.background as GradientDrawable
            mainGD.setColor(field)
        }

    var hasBorder = true
        set(value) {
            field = value
            val mainGD = binding.vpParentLayout.background as GradientDrawable
            mainGD.setStroke(borderWidth.toInt().takeIf { field } ?: 0, tintColor)
        }

    var borderWidth = 1.px.toFloat()
        set(value) {
            field = value
            val mainGD = binding.vpParentLayout.background as GradientDrawable
            mainGD.setStroke(field.toInt().takeIf { hasBorder } ?: 0, tintColor)
        }

    var tintColor = 0xFFc3c3c3.toInt()
        set(value) {
            field = value
            setTint()
        }

    var crossTintColor = 0xFFc3c3c3.toInt()
        set(value) {
            field = value
            binding.vpLabelTop.setTextColor(field)
            binding.vpLabel.setTextColor(field)
            binding.vpDropIcon.setColorFilter(field, PorterDuff.Mode.SRC_ATOP)
        }

    var hasLabel = true
        set(value) {
            field = value
            binding.vpInLayout.setVisible(field && labelPosition == IN)
            binding.vpTopLayout.setVisible(field && labelPosition == TOP)
            val lp = binding.vpAutoText.layoutParams as MarginLayoutParams
            if (labelPosition == IN) {
                lp.leftMargin = (-10).px.takeIf { hasLabel } ?: 0
            } else {
                lp.leftMargin = 0
            }
            binding.vpAutoText.layoutParams = lp
        }

    var labelPosition = IN
        set(value) {
            field = value
            binding.vpInLayout.setVisible(hasLabel && field == IN)
            binding.vpTopLayout.setVisible(hasLabel && field == TOP)
            val lp = binding.vpAutoText.layoutParams as MarginLayoutParams
            if (labelPosition == IN) {
                lp.leftMargin = (-10).px.takeIf { hasLabel } ?: 0
            } else {
                lp.leftMargin = 0
            }
            binding.vpAutoText.layoutParams = lp
        }

    var labelText = ""
        set(value) {
            field = value
            binding.vpLabel.text = labelText
            binding.vpLabelTop.text = labelText
        }

    var labelTextSize = 16.sPx
        set(value) {
            field = value
            binding.vpLabel.setTextSize(TypedValue.COMPLEX_UNIT_PX, field.toFloat())
        }

    var dropSize = 36.px.toFloat()
        set(value) {
            field = value
            val params = binding.vpDropFrame.layoutParams
            params.width = dropSize.toInt()
            binding.vpDropFrame.layoutParams = params
        }

    var dropIcon = mContext.getDrawableRes(R.drawable.vp_drop_icon)
        set(value) {
            field = value
            binding.vpDropIcon.setImageDrawable(dropIcon)
        }

    var hasDrop = true
        set(value) {
            field = value
            binding.vpDropFrame.setVisible(field)
        }

    var hint = ""
        set(value) {
            field = value
            binding.vpAutoText.hint = field
        }
    var text = ""
        get() = binding.vpAutoText.trimmedText
        set(value) {
            field = value
            binding.vpAutoText.setText(field)
        }
    var textColorHint = 0x9E9E9E
        set(value) {
            field = value
            binding.vpAutoText.setHintTextColor(field)
        }
    var textColor = Color.BLACK
        set(value) {
            field = value
            binding.vpAutoText.setTextColor(field)
        }
    var textSize = 14.sPx
        set(value) {
            field = value
            binding.vpAutoText.setTextSize(TypedValue.COMPLEX_UNIT_PX, field.toFloat())
        }

    var textAllCaps = false
        set(value) {
            field = value
            if (field)
                binding.vpAutoText.filters = arrayOf(InputFilter.AllCaps())
            else
                binding.vpAutoText.filters = arrayOf()
        }

    var textStyle = Typeface.NORMAL
        set(value) {
            field = value
            binding.vpAutoText.setTypeface(binding.vpAutoText.typeface, textStyle)
        }

    private var inputType = InputType.TYPE_CLASS_TEXT
        set(value) {
            field = value
            binding.vpAutoText.inputType = field
        }

    var enable = true
        set(value) {
            field = value
            this.isEnabled = field
            binding.vpAutoText.isEnabled = field
        }

    var itemClickListener: OnItemClickListener? = null

    val instance: VPAutoComplete
        get() = this

    override fun isEnabled(): Boolean {
        this.alpha = 1F.takeIf { enable } ?: 0.5F
        return super.isEnabled()
    }

    init {
        val parent = mContext.obtainStyledAttributes(attrs, R.styleable.VPAutoComplete)
        try {
            cornerRadius =
                parent.getDimensionPixelSize(R.styleable.VPAutoComplete_act_cornerRadius, 5.px)
                    .toFloat()
            backColor =
                parent.getColor(R.styleable.VPAutoComplete_act_backColor, 0xFFF1F1F1.toInt())
            hasBorder = parent.getBoolean(R.styleable.VPAutoComplete_act_hasBorder, hasBorder)
            borderWidth =
                parent.getDimensionPixelSize(R.styleable.VPAutoComplete_act_borderWidth, 1.px)
                    .toFloat()
            tintColor = parent.getColor(R.styleable.VPAutoComplete_act_tint, 0xFFc3c3c3.toInt())
            crossTintColor =
                parent.getColor(R.styleable.VPAutoComplete_act_cross_tint, 0xFFc3c3c3.toInt())

            hasDrop = parent.getBoolean(R.styleable.VPAutoComplete_act_hasDrop, hasDrop)
            hasLabel = parent.getBoolean(R.styleable.VPAutoComplete_act_hasLabel, hasLabel)
            labelPosition =
                parent.getInt(R.styleable.VPAutoComplete_act_label_position, labelPosition)
            if (parent.hasValue(R.styleable.VPAutoComplete_act_labelText))
                labelText = parent.getString(R.styleable.VPAutoComplete_act_labelText).toString()
            labelTextSize = parent.getDimensionPixelSize(R.styleable.VPAutoComplete_act_labelTextSize, labelTextSize)

            dropSize =
                parent.getDimensionPixelSize(R.styleable.VPAutoComplete_act_dropSize, 36.px)
                    .toFloat()
            dropIcon = mContext.getDrawableRes(
                parent.getResourceId(
                    R.styleable.VPAutoComplete_act_dropIcon,
                    R.drawable.vp_drop_icon
                )
            )
            if (parent.hasValue(R.styleable.VPAutoComplete_android_text))
                text = parent.getString(R.styleable.VPAutoComplete_android_text).toString()
            if (parent.hasValue(R.styleable.VPAutoComplete_android_hint))
                hint = parent.getString(R.styleable.VPAutoComplete_android_hint).toString()
            textColorHint =
                parent.getColor(R.styleable.VPAutoComplete_android_textColorHint, textColorHint)
            textColor = parent.getColor(R.styleable.VPAutoComplete_android_textColor, textColor)
            textSize =
                parent.getDimensionPixelSize(R.styleable.VPAutoComplete_android_textSize, textSize)
            textAllCaps = parent.getBoolean(R.styleable.VPAutoComplete_act_textAllCaps, textAllCaps)
            textStyle = parent.getInt(R.styleable.VPAutoComplete_android_textStyle, textStyle)
            if (parent.hasValue(R.styleable.VPAutoComplete_android_inputType))
                inputType = parent.getInt(R.styleable.VPAutoComplete_android_inputType, inputType)
            enable = parent.getBoolean(R.styleable.VPAutoComplete_android_enabled, enable)

            binding.vpAutoText.setVisible(true)
            binding.vpAutoText.onItemClickListener = MyItemClickListener(this)

            binding.vpDropFrame.setOnClickListener {
                binding.vpAutoText.showDropDown()
            }
        } catch (e: Exception) {
            parent.recycle()
        }
    }

    private fun updateCorners() {
        val mainGD = binding.vpParentLayout.background as GradientDrawable
        mainGD.cornerRadius = cornerRadius
        val dropGD = binding.vpDropFrame.background as GradientDrawable
        dropGD.cornerRadii =
            floatArrayOf(0f, 0f, cornerRadius, cornerRadius, cornerRadius, cornerRadius, 0f, 0f)
        val topLblGD = binding.vpLabelTop.background as GradientDrawable
        topLblGD.cornerRadii = floatArrayOf(cornerRadius, cornerRadius, 0f, 0f, 0f, 0f, 0f, 0f)
        val lblGD = binding.vpLabel.background as GradientDrawable
        lblGD.cornerRadii =
            floatArrayOf(cornerRadius, cornerRadius, 0f, 0f, 0f, 0f, cornerRadius, cornerRadius)
    }

    private fun setTint() {
        val mainGD = binding.vpParentLayout.background as GradientDrawable
        mainGD.setStroke(borderWidth.toInt().takeIf { hasBorder } ?: 0, tintColor)
        val labelGD = binding.vpLabel.background as GradientDrawable
        labelGD.setColor(tintColor)
        val labelGDTop = binding.vpLabelTop.background as GradientDrawable
        labelGDTop.setColor(tintColor)
        ImageViewCompat.setImageTintList(binding.curveImg, ColorStateList.valueOf(tintColor))
        ImageViewCompat.setImageTintList(binding.curveImgTop, ColorStateList.valueOf(tintColor))
        val dropGD = binding.vpDropFrame.background as GradientDrawable
        dropGD.setColor(tintColor)
    }

    fun <T> setAdapter(adapter: T) where T : ListAdapter?, T : Filterable? {
        binding.vpAutoText.setAdapter(adapter)
    }

    fun getAdapter(): Adapter? {
        return binding.vpAutoText.adapter
    }

    interface OnItemClickListener {
        fun onItemClick(view: VPAutoComplete, position: Int)
    }

    inner class MyItemClickListener(myAc: VPAutoComplete) :
        AdapterView.OnItemClickListener {

        private var ac: VPAutoComplete = myAc

        override fun onItemClick(adapterView: AdapterView<*>, view: View, position: Int, l: Long) {
            val imm = mContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(ac.applicationWindowToken, 0)
            itemClickListener?.onItemClick(ac, position)
        }

    }
}