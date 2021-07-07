@file:Suppress("unused")

package com.vp.vpeasywidget.utils

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.AsyncTask
import android.os.Build
import android.text.Html
import android.text.Spanned
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.random.Random

// Date - String
fun String.formatDate(dateFormat: String): Date? {
    val format = SimpleDateFormat(dateFormat, Locale.getDefault())
    var result: Date? = null
    try {
        result = format.parse(this)
    } catch (e: Exception) {
        e.printStackTrace()
    }

    return result
}

fun String.changeDateFormat(from: String, to: String): String? {
    val date = this.formatDate(from)
    if (date != null) {
        return date.convertString(to)
    }
    return ""
}

fun Date.convertString(dateFormat: String): String? {
    val format = SimpleDateFormat(dateFormat, Locale.getDefault())
    return format.format(this)
}

// Close Keyboard
fun View.closeKeyboard(context: Context) {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(this.applicationWindowToken, 0)
}

// String upper-lower
fun String.upperCased(): String {
    return this.toUpperCase(Locale.getDefault())
}

fun String.lowerCased(): String {
    return this.toLowerCase(Locale.getDefault())
}

// Print Long Log
fun String.convertToLongLog() {
    val chunkSize = 2048
    var i = 0
    while (i < this.length) {
        Log.e("LONG LOG", this.substring(i, this.length.coerceAtMost(i + chunkSize)))
        i += chunkSize
    }
}

// View Visibility
fun View.setVisible(flag: Boolean?) {
    if (flag != null) {
        this.visibility = View.VISIBLE.takeIf { flag } ?: View.GONE
    } else {
        this.visibility = View.INVISIBLE
    }
}

fun View.isVisible(): Boolean {
    return this.visibility == View.VISIBLE
}

// Get Resources
fun Context.getColorRes(color: Int): Int {
    return ContextCompat.getColor(this, color)
}

fun Context.getDrawableRes(resID: Int): Drawable? {
    return ContextCompat.getDrawable(this, resID)
}

fun Context.getJsonFromAssets(path: String): String {
    return this.assets.open(path).bufferedReader().use { it.readText() }
}

// To HTML
@Suppress("DEPRECATION")
fun String.applyHTML(): Spanned {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY)
    } else {
        Html.fromHtml(this)
    }
}

// Random Light color
fun generateRandomColor(): Int {
    val colors = arrayOf("#7986CB", "#64B5F6", "#4FC3F7", "#4DD0E1", "#4DB6AC", "#81C784", "#AED581", " #FFB74D", "#FF8A65", "#A1887F", "#78909C")
    val mRandom = Random.nextInt(colors.size)
    return Color.parseColor(colors[mRandom])
}

// showToast
fun Context.showToast(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}
