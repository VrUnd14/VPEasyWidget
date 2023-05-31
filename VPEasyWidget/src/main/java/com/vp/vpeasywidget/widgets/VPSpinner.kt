package com.vp.vpeasywidget.widgets

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.PorterDuff
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.widget.Adapter
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Filterable
import android.widget.RelativeLayout
import android.widget.SpinnerAdapter
import androidx.core.widget.ImageViewCompat
import com.vp.vpeasywidget.R
import com.vp.vpeasywidget.databinding.VpAutoSpinnerBinding
import com.vp.vpeasywidget.utils.getDrawableRes
import com.vp.vpeasywidget.utils.px
import com.vp.vpeasywidget.utils.sPx
import com.vp.vpeasywidget.utils.setVisible

class VPSpinner @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : RelativeLayout(context, attrs, defStyleAttr) {

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
            val lp = binding.vpSpinner.layoutParams as MarginLayoutParams
            if (labelPosition == IN) {
                lp.leftMargin = (-10).px.takeIf { hasLabel } ?: 0
            } else {
                lp.leftMargin = 0
            }
            binding.vpSpinner.layoutParams = lp
        }

    var labelPosition = IN
        set(value) {
            field = value
            binding.vpInLayout.setVisible(hasLabel && field == IN)
            binding.vpTopLayout.setVisible(hasLabel && field == TOP)
            val lp = binding.vpSpinner.layoutParams as MarginLayoutParams
            if (labelPosition == IN) {
                lp.leftMargin = (-10).px.takeIf { hasLabel } ?: 0
            } else {
                lp.leftMargin = 0
            }
            binding.vpSpinner.layoutParams = lp
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

    var defaultArray: Array<String>? = null
        set(value) {
            field = value
            if (defaultArray != null) {
                val adp = ArrayAdapter<String>(mContext, R.layout.vp_drop_item, R.id.txt, defaultArray!!)
                binding.vpSpinner.adapter = adp
            }
        }

    var selectedPosition = 0
        set(value) {
            field = value
            binding.vpSpinner.setSelection(field)
        }
        get() = binding.vpSpinner.selectedItemPosition

    val selectedItem: Any?
        get() = binding.vpSpinner.selectedItem

    var enable = true
        set(value) {
            field = value
            this.isEnabled = field
            binding.vpSpinner.isEnabled = field
        }

    var itemSelectedListener: OnItemSelectedListener? = null

    val instance: VPSpinner
        get() = this

    override fun isEnabled(): Boolean {
        this.alpha = 1F.takeIf { enable } ?: 0.5F
        return super.isEnabled()
    }

    init {
        val parent = mContext.obtainStyledAttributes(attrs, R.styleable.VPSpinner)
        try {
            cornerRadius = parent.getDimensionPixelSize(R.styleable.VPSpinner_sp_cornerRadius, 5.px).toFloat()
            backColor = parent.getColor(R.styleable.VPSpinner_sp_backColor, 0xFFF1F1F1.toInt())
            hasBorder = parent.getBoolean(R.styleable.VPSpinner_sp_hasBorder, true)
            borderWidth = parent.getDimensionPixelSize(R.styleable.VPSpinner_sp_borderWidth, 1.px).toFloat()
            tintColor = parent.getColor(R.styleable.VPSpinner_sp_tint, 0xFFc3c3c3.toInt())
            crossTintColor = parent.getColor(R.styleable.VPSpinner_sp_cross_tint, 0xFFc3c3c3.toInt())

            hasLabel = parent.getBoolean(R.styleable.VPSpinner_sp_hasLabel, true)
            labelTextSize = parent.getDimensionPixelSize(R.styleable.VPSpinner_sp_labelTextSize, labelTextSize)
            labelPosition = parent.getInt(R.styleable.VPSpinner_sp_label_position, IN)
            if (parent.hasValue(R.styleable.VPSpinner_sp_labelText))
                labelText = parent.getString(R.styleable.VPSpinner_sp_labelText).toString()

            dropSize = parent.getDimensionPixelSize(R.styleable.VPSpinner_sp_dropSize, 36.px).toFloat()
            dropIcon = mContext.getDrawableRes(parent.getResourceId(R.styleable.VPSpinner_sp_dropIcon, R.drawable.vp_drop_icon))
            enable = parent.getBoolean(R.styleable.VPAutoComplete_android_enabled, enable)

            if (parent.hasValue(R.styleable.VPSpinner_sp_array)) {
                val arrayID: Int = parent.getResourceId(R.styleable.VPSpinner_sp_array, 0)
                defaultArray = parent.resources.getStringArray(arrayID)
            }

            binding.vpSpinner.setVisible(true)
            binding.vpSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {}

                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    val item = parent?.selectedItem
                    itemSelectedListener?.onItemSelected(instance, item, position)
                }

            }
            this.post {
                if (labelPosition == IN)
                    binding.vpSpinner.dropDownVerticalOffset = this.height
                else
                    binding.vpSpinner.dropDownVerticalOffset = this.height - binding.vpTopLayout.height + 5.px
            }
        } catch (e: Exception) {
            parent.recycle()
        }
    }

    private fun updateCorners() {
        val mainGD = binding.vpParentLayout.background as GradientDrawable
        mainGD.cornerRadius = cornerRadius
        val dropGD = binding.vpDropFrame.background as GradientDrawable
        dropGD.cornerRadii = floatArrayOf(0f, 0f, cornerRadius, cornerRadius, cornerRadius, cornerRadius, 0f, 0f)
        val topLblGD = binding.vpLabelTop.background as GradientDrawable
        topLblGD.cornerRadii = floatArrayOf(cornerRadius, cornerRadius, 0f, 0f, 0f, 0f, 0f, 0f)
        val lblGD = binding.vpLabel.background as GradientDrawable
        lblGD.cornerRadii = floatArrayOf(cornerRadius, cornerRadius, 0f, 0f, 0f, 0f, cornerRadius, cornerRadius)
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

    fun <T> setAdapter(adapter: T) where T : SpinnerAdapter?, T : Filterable? {
        binding.vpSpinner.adapter = adapter
    }

    fun getAdapter(): Adapter? {
        return binding.vpSpinner.adapter
    }

    interface OnItemSelectedListener {
        fun onItemSelected(view: VPSpinner, selectedItem: Any?, position: Int)
    }
}