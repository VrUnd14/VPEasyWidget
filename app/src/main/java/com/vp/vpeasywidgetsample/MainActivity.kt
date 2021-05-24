package com.vp.vpeasywidgetsample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.vp.vpeasywidget.widgets.OnUpdateActionListener
import com.vp.vpeasywidget.widgets.VPAppUpdate

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val kek = VPAppUpdate(0, this)
        kek.show()

        kek.onUpdateActionListener = object : OnUpdateActionListener{
            override fun onUpdate() {
                Log.e("ssdhvs","Update")
            }

            override fun onRemindMeLater() {
                Log.e("ssdhvs","Remind Later")
            }
        }
    }
}