package com.vp.vpeasywidgetsample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.vp.vpeasywidget.utils.showToast
import com.vp.vpeasywidget.widgets.VPTextView
import kotlinx.android.synthetic.main.vp_text_view_example.*

class VPTextViewExample : AppCompatActivity(), VPTextView.DrawableClickListener {

    private val context = this@VPTextViewExample

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.vp_text_view_example)

        leftIcon.drawableClickListener = this
        topIcon.drawableClickListener = this
        rightIcon.drawableClickListener = this
    }

    override fun onClick(view: VPTextView, target: VPTextView.DrawableClickListener.DrawablePosition) {
       when(view.id) {
           R.id.leftIcon -> context.showToast("Icon at LEFT")
           R.id.topIcon -> context.showToast("Icon at TOP")
           R.id.rightIcon -> context.showToast("Icon at RIGHT")
       }
    }
}