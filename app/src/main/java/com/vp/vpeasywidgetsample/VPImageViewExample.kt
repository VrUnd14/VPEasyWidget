package com.vp.vpeasywidgetsample

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.vp.vpeasywidget.utils.showToast
import com.vp.vpeasywidget.widgets.VPTextView
import kotlinx.android.synthetic.main.vp_text_view_example.*

class VPImageViewExample : AppCompatActivity() {

    private val context = this@VPImageViewExample

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.vp_image_view_example)

        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "VPImageView"
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}