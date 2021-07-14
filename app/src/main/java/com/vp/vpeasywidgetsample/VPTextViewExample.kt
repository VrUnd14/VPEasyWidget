package com.vp.vpeasywidgetsample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.vp.vpeasywidget.utils.showToast
import com.vp.vpeasywidget.widgets.VPTextView
import kotlinx.android.synthetic.main.vp_text_view_example.*

class VPTextViewExample : AppCompatActivity() {

    private val context = this@VPTextViewExample

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.vp_text_view_example)
    }
}