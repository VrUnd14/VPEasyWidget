package com.vp.vpeasywidgetsample

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vp.vpeasywidget.utils.GridSpacingItemDecoration
import com.vp.vpeasywidget.utils.generateRandomColor
import com.vp.vpeasywidget.utils.getJsonFromAssets
import com.vp.vpeasywidget.widgets.OnUpdateActionListener
import com.vp.vpeasywidget.widgets.VPAppUpdate
import com.vp.vpeasywidgetsample.databinding.ActivityMainBinding
import com.vp.vpeasywidgetsample.databinding.WidgetItemBinding
import com.vp.vpeasywidgetsample.models.WidgetModel
import org.json.JSONArray

class MainActivity : AppCompatActivity() {

    private val context = this@MainActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.widgetList.layoutManager = GridLayoutManager(context, 1)
        binding.widgetList.addItemDecoration(GridSpacingItemDecoration(1, 8, true))
        binding.widgetList.adapter = WidgetAdapter()
    }

    inner class WidgetAdapter : RecyclerView.Adapter<WidgetAdapter.Holder>() {

        private var data = ArrayList<WidgetModel>()

        init {
            val json = JSONArray(context.getJsonFromAssets("widgets"))
            for (i in 0 until json.length()) {
                val wm = WidgetModel(json.optJSONObject(i))
                wm.tintColor = generateRandomColor()
                data.add(wm)
            }
        }

        @SuppressLint("InflateParams")
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
            val root = WidgetItemBinding.inflate(LayoutInflater.from(context))
            return Holder(root)
        }

        override fun getItemCount(): Int {
            return data.size
        }

        override fun onBindViewHolder(holder: Holder, position: Int) {
            holder.colorView.setBackgroundColor(data[position].tintColor)
            holder.widgetName.text = data[position].widget
            holder.lastUpdated.text = "Updated ${data[position].lastUpdate}"
            holder.forwardIC.setColorFilter(data[position].tintColor, PorterDuff.Mode.SRC_ATOP)
        }

        inner class Holder(root: WidgetItemBinding) : RecyclerView.ViewHolder(root.root) {
            val colorView: View = root.colorView
            val widgetName: TextView = root.widgetName
            val lastUpdated: TextView = root.lastUpdated
            val forwardIC: ImageView = root.forwardIC

            init {
                root.mainCard.setOnClickListener {
                    when (data[adapterPosition].widget) {
                        "VPAppUpdate" -> actionVPAppUpdate()
                        "VPTextView" -> startActivity(Intent(context, VPTextViewExample::class.java))
                        "VPImageView" -> startActivity(Intent(context, VPImageViewExample::class.java))
                        "VPNoDataWidget" -> startActivity(Intent(context, VPNoDataExample::class.java))
                        "VPSpinner" -> startActivity(Intent(context, VPSpinnerExample::class.java))
                        "VPEditText" -> startActivity(Intent(context, VPEditTextExample::class.java))
                        "VPNonScrollListGrid" -> startActivity(Intent(context, VPNonScrollListGridExample::class.java))
                        "VPLayouts" -> startActivity(Intent(context, VPLayoutsExample::class.java))
                        "VPCaptcha" -> startActivity(Intent(context, VPCaptchaExample::class.java))
                        "VPAutocomplete" -> startActivity(Intent(context, VPAutoCompleteExample::class.java))
                    }
                }
            }
        }
    }

    private fun actionVPAppUpdate() {
        val kek = VPAppUpdate(0, context)
        kek.show()

        kek.onUpdateActionListener = object : OnUpdateActionListener {
            override fun onUpdate() {
            }

            override fun onRemindMeLater() {
            }
        }
    }
}