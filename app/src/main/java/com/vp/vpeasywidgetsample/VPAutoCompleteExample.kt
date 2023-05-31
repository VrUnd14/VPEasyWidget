package com.vp.vpeasywidgetsample

import android.os.Bundle
import android.view.MenuItem
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.vp.vpeasywidgetsample.databinding.VpAutocompleteExampleBinding

class VPAutoCompleteExample : AppCompatActivity() {

    private val context = this@VPAutoCompleteExample

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = VpAutocompleteExampleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "VpAutocomplete"

        binding.countriesSpn3.setAdapter(ArrayAdapter(context, android.R.layout.simple_list_item_1, arrayOf("Vrund", "Nirali", "Madhavi", "Dev", "Vishva")))
        binding.countriesSpn2.setAdapter(ArrayAdapter(context, android.R.layout.simple_list_item_1, arrayOf("Vrund", "Nirali", "Madhavi", "Dev", "Vishva")))
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}