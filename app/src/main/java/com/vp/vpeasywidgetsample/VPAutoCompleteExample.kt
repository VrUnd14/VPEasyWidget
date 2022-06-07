package com.vp.vpeasywidgetsample

import android.os.Bundle
import android.view.MenuItem
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.vp_autocomplete_example.*

class VPAutoCompleteExample : AppCompatActivity() {

    private val context = this@VPAutoCompleteExample

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.vp_autocomplete_example)

        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "VpAutocomplete"

        countriesSpn3.setAdapter(ArrayAdapter(context, android.R.layout.simple_list_item_1, arrayOf("Vrund", "Nirali", "Madhavi", "Dev", "Vishva")))
        countriesSpn2.setAdapter(ArrayAdapter(context, android.R.layout.simple_list_item_1, arrayOf("Vrund", "Nirali", "Madhavi", "Dev", "Vishva")))
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}