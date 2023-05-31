package com.vp.vpeasywidgetsample

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity

class VPTextViewExample : AppCompatActivity() {

    private val context = this@VPTextViewExample

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.vp_text_view_example)

        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "VPTextView"
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}