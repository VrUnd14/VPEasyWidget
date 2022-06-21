package com.vp.vpeasywidget.widgets

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.PorterDuff
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.View
import android.widget.*
import androidx.core.widget.ImageViewCompat
import com.vp.vpeasywidget.R
import com.vp.vpeasywidget.utils.getDrawableRes
import com.vp.vpeasywidget.utils.px
import com.vp.vpeasywidget.utils.setVisible
import kotlinx.android.synthetic.main.vp_auto_spinner.view.*

    class VPSpinner @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : RelativeLayout(context, attrs, defStyleAttr) {

    private val mContext = context

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
            val mainGD = vpParentLayout.background as GradientDrawable
            mainGD.setColor(field)
        }

    var hasBorder = true
        set(value) {
            field = value
            val mainGD = vpParentLayout.background as GradientDrawable
            mainGD.setStroke(borderWidth.toInt().takeIf { field } ?: 0, tintColor)
        }

    var borderWidth = 1.px.toFloat()
        set(value) {
            field = value
            val mainGD = vpParentLayout.background as GradientDrawable
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
            vpLabelTop.setTextColor(field)
            vpLabel.setTextColor(field)
            vpDropIcon.setColorFilter(field, PorterDuff.Mode.SRC_ATOP)
        }

    var hasLabel = true
        set(value) {
            field = value
            vpInLayout.setVisible(field && labelPosition == IN)
            vpTopLayout.setVisible(field && labelPosition == TOP)
            val lp = vpSpinner.layoutParams as MarginLayoutParams
            if (labelPosition == IN) {
                lp.leftMargin = (-10).px.takeIf { hasLabel } ?: 0
            } else {
                lp.leftMargin = 0
            }
            vpSpinner.layoutParams = lp
        }

    var labelPosition = IN
        set(value) {
            field = value
            vpInLayout.setVisible(hasLabel && field == IN)
            vpTopLayout.setVisible(hasLabel && field == TOP)
            val lp = vpSpinner.layoutParams as MarginLayoutParams
            if (labelPosition == IN) {
                lp.leftMargin = (-10).px.takeIf { hasLabel } ?: 0
            } else {
                lp.leftMargin = 0
            }
            vpSpinner.layoutParams = lp
        }

    var labelText = ""
        set(value) {
            field = value
            vpLabel.text = labelText
            vpLabelTop.text = labelText
        }

    var dropSize = 36.px.toFloat()
        set(value) {
            field = value
            val params = vpDropFrame.layoutParams
            params.width = dropSize.toInt()
            vpDropFrame.layoutParams = params
        }

    var dropIcon = mContext.getDrawableRes(R.drawable.vp_drop_icon)
        set(value) {
            field = value
            vpDropIcon.setImageDrawable(dropIcon)
        }

    var defaultArray: Array<String>? = null
        set(value) {
            field = value
            if (defaultArray != null) {
                val adp = ArrayAdapter<String>(mContext, R.layout.vp_drop_item, R.id.txt, defaultArray!!)
                vpSpinner.adapter = adp
            }
        }

    var selectedPosition = 0
        set(value) {
            field = value
            vpSpinner.setSelection(field)
        }
        get() = vpSpinner.selectedItemPosition

    val selectedItem: Any?
        get() = vpSpinner.selectedItem


    var itemSelectedListener: OnItemSelectedListener? = null

    val instance: VPSpinner
        get() = this

    init {
        View.inflate(mContext, R.layout.vp_auto_spinner, this)
        val parent = mContext.obtainStyledAttributes(attrs, R.styleable.VPSpinner)
        try {
            cornerRadius = parent.getDimensionPixelSize(R.styleable.VPSpinner_sp_cornerRadius, 5.px).toFloat()
            backColor = parent.getColor(R.styleable.VPSpinner_sp_backColor, 0xFFF1F1F1.toInt())
            hasBorder = parent.getBoolean(R.styleable.VPSpinner_sp_hasBorder, true)
            borderWidth = parent.getDimensionPixelSize(R.styleable.VPSpinner_sp_borderWidth, 1.px).toFloat()
            tintColor = parent.getColor(R.styleable.VPSpinner_sp_tint, 0xFFc3c3c3.toInt())
            crossTintColor = parent.getColor(R.styleable.VPSpinner_sp_cross_tint, 0xFFc3c3c3.toInt())

            hasLabel = parent.getBoolean(R.styleable.VPSpinner_sp_hasLabel, true)
            labelPosition = parent.getInt(R.styleable.VPSpinner_sp_label_position, IN)
            if (parent.hasValue(R.styleable.VPSpinner_sp_labelText))
                labelText = parent.getString(R.styleable.VPSpinner_sp_labelText).toString()

            dropSize = parent.getDimensionPixelSize(R.styleable.VPSpinner_sp_dropSize, 36.px).toFloat()
            dropIcon = mContext.getDrawableRes(parent.getResourceId(R.styleable.VPSpinner_sp_dropIcon, R.drawable.vp_drop_icon))

            if (parent.hasValue(R.styleable.VPSpinner_sp_array)) {
                val arrayID: Int = parent.getResourceId(R.styleable.VPSpinner_sp_array, 0)
                defaultArray = parent.resources.getStringArray(arrayID)
            }

            vpSpinner.setVisible(true)
            vpSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {}

                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    val item = parent?.selectedItem
                    itemSelectedListener?.onItemSelected(instance, item, position)
                }

            }
            this.post {
                if (labelPosition == IN)
                    vpSpinner.dropDownVerticalOffset = this.height
                else
                    vpSpinner.dropDownVerticalOffset = this.height - vpTopLayout.height + 5.px
            }
        } catch (e: Exception) {
            parent.recycle()
        }
    }

    private fun updateCorners() {
        val mainGD = vpParentLayout.background as GradientDrawable
        mainGD.cornerRadius = cornerRadius
        val dropGD = vpDropFrame.background as GradientDrawable
        dropGD.cornerRadii = floatArrayOf(0f, 0f, cornerRadius, cornerRadius, cornerRadius, cornerRadius, 0f, 0f)
        val topLblGD = vpLabelTop.background as GradientDrawable
        topLblGD.cornerRadii = floatArrayOf(cornerRadius, cornerRadius, 0f, 0f, 0f, 0f, 0f, 0f)
        val lblGD = vpLabel.background as GradientDrawable
        lblGD.cornerRadii = floatArrayOf(cornerRadius, cornerRadius, 0f, 0f, 0f, 0f, cornerRadius, cornerRadius)
    }

    private fun setTint() {
        val mainGD = vpParentLayout.background as GradientDrawable
        mainGD.setStroke(borderWidth.toInt().takeIf { hasBorder } ?: 0, tintColor)
        val labelGD = vpLabel.background as GradientDrawable
        labelGD.setColor(tintColor)
        val labelGDTop = vpLabelTop.background as GradientDrawable
        labelGDTop.setColor(tintColor)
        ImageViewCompat.setImageTintList(curveImg, ColorStateList.valueOf(tintColor))
        ImageViewCompat.setImageTintList(curveImgTop, ColorStateList.valueOf(tintColor))
        val dropGD = vpDropFrame.background as GradientDrawable
        dropGD.setColor(tintColor)
    }

    fun <T> setAdapter(adapter: T) where T : SpinnerAdapter?, T : Filterable? {
        vpSpinner.adapter = adapter
    }

    fun getAdapter(): Adapter? {
        return vpSpinner.adapter
    }

    interface OnItemSelectedListener {
        fun onItemSelected(view: VPSpinner, selectedItem: Any?, position: Int)
    }
}