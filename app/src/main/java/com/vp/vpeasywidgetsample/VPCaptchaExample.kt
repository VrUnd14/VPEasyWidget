package com.vp.vpeasywidgetsample

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.vp.vpeasywidget.utils.showToast
import com.vp.vpeasywidget.widgets.VPCaptcha
import com.vp.vpeasywidget.widgets.VPTextView
import kotlinx.android.synthetic.main.vp_captch_example.*
import kotlinx.android.synthetic.main.vp_text_view_example.*

class VPCaptchaExample : AppCompatActivity() {

    private val context = this@VPCaptchaExample

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.vp_captch_example)

        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "VPCaptcha"

        mCaptcha.setIsDotNeeded(true)
        mCaptcha.setCaptchaLength(4)
        mCaptcha.setCaptchaType(VPCaptcha.CaptchaGenerator.BOTH)
        mCaptcha.setTextStyle(VPCaptcha.TYPE_PLAIN_TEXT)
    }

    fun refresh(view: View) {
        mCaptcha.regenerate()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}