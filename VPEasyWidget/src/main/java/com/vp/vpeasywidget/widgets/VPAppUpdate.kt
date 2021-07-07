package com.vp.vpeasywidget.widgets

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.vp.vpeasywidget.R
import com.vp.vpeasywidget.utils.applyHTML
import com.vp.vpeasywidget.utils.setVisible
import kotlinx.android.synthetic.main.vp_update_available.*
import kotlinx.android.synthetic.main.vp_update_available.view.*

@SuppressLint("SetTextI18n", "RestrictedApi", "ViewConstructor")
class VPAppUpdate @JvmOverloads constructor(withIcon: Int, context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : LinearLayout(context, attrs, defStyleAttr) {

    private val mContext = context

    private var appUpdateDialog: AppUpdateDialog? = null

    var appIcon = withIcon
        set(value) {
            field = value
            vpAppLogo.setImageResource(appIcon)
        }
    var laterBackColor = Color.BLACK
        set(value) {
            field = value
            remindLater.supportBackgroundTintList = ColorStateList.valueOf(field)
        }

    var laterTextColor = Color.WHITE
        set(value) {
            field = value
            remindLater.setTextColor(field)
        }

    var updateBackColor = Color.RED
        set(value) {
            field = value
            updateNow.supportBackgroundTintList = ColorStateList.valueOf(updateBackColor)
        }

    var updateTextColor = Color.WHITE
        set(value) {
            field = value
            updateNow.setTextColor(updateTextColor)
        }

    var forceUpdate = false
        set(value) {
            field = value
            remindLater.setVisible(!forceUpdate)
        }

    var onUpdateActionListener: OnUpdateActionListener? = null

    init {
        View.inflate(mContext, R.layout.vp_update_available, this)
        vpAppLogo.setImageResource(appIcon)
        remindLater.supportBackgroundTintList = ColorStateList.valueOf(laterBackColor)
        remindLater.setTextColor(laterTextColor)
        updateNow.supportBackgroundTintList = ColorStateList.valueOf(updateBackColor)
        updateNow.setTextColor(updateTextColor)
        remindLater.setVisible(!forceUpdate)

        vpUpdateTxt.text = "${mContext.getString(R.string.update_txt)} <b>${mContext.applicationInfo.loadLabel(mContext.packageManager)}</b>".applyHTML()
    }

    fun show() {
        val fragManger = when (mContext) {
            is AppCompatActivity -> mContext.supportFragmentManager
            is Fragment -> mContext.childFragmentManager
            else -> null
        }
        if (fragManger != null) {
            appUpdateDialog = AppUpdateDialog.newInstance(this)
            appUpdateDialog!!.isCancelable = false
            appUpdateDialog!!.show(fragManger, "")
        }
    }

    fun dismiss() {
        if (appUpdateDialog != null)
            appUpdateDialog!!.dismiss()
    }

    class AppUpdateDialog : DialogFragment() {

        private lateinit var root: VPAppUpdate

        companion object {
            fun newInstance(root: VPAppUpdate) = AppUpdateDialog().apply {
                this.root = root
            }
        }

        @SuppressLint("RestrictedApi")
        override fun setupDialog(dialog: Dialog, style: Int) {
            super.setupDialog(dialog, style)
            dialog.setContentView(root)

            dialog.remindLater.setOnClickListener {
                dismiss()
                root.onUpdateActionListener?.onRemindMeLater()
            }

            dialog.updateNow.setOnClickListener {
                root.onUpdateActionListener?.onUpdate()
            }
        }
    }
}

interface OnUpdateActionListener {
    fun onUpdate()
    fun onRemindMeLater()
}