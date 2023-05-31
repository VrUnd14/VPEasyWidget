package com.vp.vpeasywidgetsample

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.vp.vpeasywidget.widgets.VPCaptcha
import com.vp.vpeasywidgetsample.databinding.VpCaptchExampleBinding

class VPCaptchaExample : AppCompatActivity() {

    private val context = this@VPCaptchaExample
    private lateinit var binding: VpCaptchExampleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = VpCaptchExampleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "VPCaptcha"

        binding.mCaptcha.setIsDotNeeded(true)
        binding.mCaptcha.setCaptchaLength(4)
        binding.mCaptcha.setCaptchaType(VPCaptcha.CaptchaGenerator.BOTH)
        binding.mCaptcha.setTextStyle(VPCaptcha.TYPE_PLAIN_TEXT)
    }

    fun refresh(view: View) {
        binding.mCaptcha.regenerate()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}