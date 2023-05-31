package com.vp.vpeasywidgetsample

import android.os.Bundle
import android.view.MenuItem
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.vp.vpeasywidgetsample.databinding.VpSpinnerExampleBinding

class VPSpinnerExample : AppCompatActivity() {

    private val context = this@VPSpinnerExample

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = VpSpinnerExampleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "VpSpinner"

        binding.countriesSpn4.setAdapter(ArrayAdapter(context, android.R.layout.simple_list_item_1, arrayOf("Vrund", "Nirali", "Madhavi", "Dev", "Vishva")))
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}