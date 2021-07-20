package com.vp.vpeasywidgetsample

import android.os.Bundle
import android.view.MenuItem
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.vp.vpeasywidget.utils.px
import kotlinx.android.synthetic.main.vp_no_data_example.*
import kotlinx.android.synthetic.main.vp_spinner_example.*

class VPSpinnerExample : AppCompatActivity() {

    private val context = this@VPSpinnerExample

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.vp_spinner_example)

        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "VpSpinner"

        countriesSpn4.setAdapter(ArrayAdapter(context, android.R.layout.simple_list_item_1, arrayOf("Vrund", "Nirali", "Madhavi", "Dev", "Vishva")))
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}