package com.vp.vpeasywidgetsample

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.appcompat.app.AppCompatActivity
import com.vp.vpeasywidget.utils.setVisible
import com.vp.vpeasywidgetsample.databinding.ListGridItemBinding
import com.vp.vpeasywidgetsample.databinding.VpListgridExampleBinding

class VPNonScrollListGridExample : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = VpListgridExampleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "VPNonScrollListGrid"

        binding.listView.adapter = ListGridAdapter("list")
        binding.gridView.adapter = ListGridAdapter("grid")
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    inner class ListGridAdapter(private val type: String): BaseAdapter() {
        override fun getCount(): Int {
            return 9
        }

        override fun getItem(p0: Int): Any {
            return 0
        }

        override fun getItemId(p0: Int): Long {
            return 0
        }

        @SuppressLint("SetTextI18n", "ViewHolder")
        override fun getView(position: Int, p1: View?, p2: ViewGroup?): View {
            val root = ListGridItemBinding.inflate(LayoutInflater.from(this@VPNonScrollListGridExample))
            if(type == "list") {
                root.listText.text = "List Index $position"
            }
            root.listText.setVisible(type == "list")
            root.listText.text = "List Index ${position+1}"
            root.gridText.setVisible(type == "grid")
            root.gridText.text = "${position +1}"
            return root.root
        }

    }
}