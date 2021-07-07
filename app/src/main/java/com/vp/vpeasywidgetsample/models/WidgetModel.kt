package com.vp.vpeasywidgetsample.models

import com.vp.vpeasywidget.utils.changeDateFormat
import org.json.JSONObject
import java.io.Serializable

class WidgetModel() : Serializable {

    var widget = ""
    var lastUpdate = ""
    var tintColor = 0

    constructor(j: JSONObject) : this() {
        widget = j.optString("widget")
        lastUpdate = j.optString("lastUpdate").changeDateFormat("dd/MM/YYYY", "MMM dd, yyyy")!!
    }
}