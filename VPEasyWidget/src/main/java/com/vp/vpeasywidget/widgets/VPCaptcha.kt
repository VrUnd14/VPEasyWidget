package com.vp.vpeasywidget.widgets

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import com.vp.vpeasywidget.utils.px
import java.util.*
import kotlin.math.min

class VPCaptcha @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : AppCompatImageView(context, attrs, defStyleAttr) {

    private var generatedCaptcha: CaptchaGenerator.Captcha? = null
    private var captchaLength = 6
    private var captchaType = CaptchaGenerator.NUMBERS
    private var _width: Int = 0
    private var _height: Int = 0
    private var isDot: Boolean = false
    private var isRedraw: Boolean = false
    private var isItalic = true

    val captchaBitmap: Bitmap?
        get() = generatedCaptcha!!.bitmap

    val captchaCode: String?
        get() = generatedCaptcha!!.captchaCode

    private fun draw(width: Int, height: Int) {
        generatedCaptcha = CaptchaGenerator.regenerate(width, height, captchaLength, captchaType, isDot, isItalic)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val desiredWidth = 300
        val desiredHeight = 50

        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)

        //Measure Width
        val width: Int = when (widthMode) {
            MeasureSpec.EXACTLY -> widthSize
            MeasureSpec.AT_MOST -> min(desiredWidth, widthSize)
            else -> desiredWidth
        }
        //Measure Height
        val height: Int = when (heightMode) {
            MeasureSpec.EXACTLY -> heightSize
            MeasureSpec.AT_MOST -> min(desiredHeight, heightSize)
            else -> desiredHeight
        }
        //MUST CALL THIS
        setMeasuredDimension(width, height)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        _width = w
        _height = h
    }

    override fun onWindowFocusChanged(hasWindowFocus: Boolean) {
        super.onWindowFocusChanged(hasWindowFocus)
        if (!isRedraw) {
            reDraw()
            isRedraw = true
        }

    }

    fun regenerate() {
        reDraw()
    }

    fun setTextStyle(style: Int) {
        isItalic = when (style) {
            TYPE_ITALIC -> true
            TYPE_PLAIN_TEXT -> false
            else -> true
        }
    }

    fun setCaptchaLength(length: Int) {
        captchaLength = length
    }

    private fun reDraw() {
        post {
            if (_width != 0 && _height != 0) {
                draw(_width, _height)
                setImageBitmap(generatedCaptcha!!.bitmap)
            }
        }
    }

    fun setIsDotNeeded(isNeeded: Boolean) {
        isDot = isNeeded
    }

    fun setCaptchaType(type: Int) {
        captchaType = type
    }

    object CaptchaGenerator {
        const val ALPHABETS = 1
        const val NUMBERS = 2
        const val BOTH = 3

        internal fun regenerate(width: Int, height: Int, length: Int, type: Int, isDot: Boolean, isItalic: Boolean): Captcha {
            val border = Paint()
            border.style = Paint.Style.STROKE
            border.color = Color.parseColor("#CCCCCC")
            val paint = Paint()
            paint.color = Color.BLACK
            paint.typeface = Typeface.DEFAULT_BOLD.takeIf { isDot } ?: Typeface.MONOSPACE
            val bitMap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bitMap)
            canvas.drawColor(Color.parseColor("#F7F7FF"))
            val textX = generateRandomInt(width - width / 5 * 4, width / 3)
            val textY = generateRandomInt(height - height / 3, height - height / 4)
            val generatedText = drawRandomText(canvas, paint, textX, textY, length, type, isDot, isItalic)
            if (isDot) {
                canvas.drawLine(textX.toFloat(), (textY - generateRandomInt(7, 10)).toFloat(), (textX + length * 33).toFloat(), (textY - generateRandomInt(5, 10)).toFloat(), paint)
                canvas.drawLine(textX.toFloat(), (textY - generateRandomInt(7, 10)).toFloat(), (textX + length * 33).toFloat(), (textY - generateRandomInt(5, 10)).toFloat(), paint)
            } else {
                canvas.drawLine(textX.toFloat(), (textY - generateRandomInt(7, 10)).toFloat(), (textX + length * 23).toFloat(), (textY - generateRandomInt(5, 10)).toFloat(), paint)
                canvas.drawLine(textX.toFloat(), (textY - generateRandomInt(7, 10)).toFloat(), (textX + length * 23).toFloat(), (textY - generateRandomInt(5, 10)).toFloat(), paint)
            }
            canvas.drawRect(0f, 0f, (width - 1).toFloat(), (height - 1).toFloat(), border)
            if (isDot) {
                makeDots(bitMap, width, height, textX, textY)
            }
            return Captcha(generatedText, bitMap)
        }

        @Suppress("UNUSED_PARAMETER")
        private fun makeDots(bitMap: Bitmap, width: Int, height: Int, textX: Int, textY: Int) {
            val white = -526337
            @Suppress("UNUSED_VARIABLE") val black = -16777216
            val grey = -3355444
            val random = Random()
            for (x in 0 until width) {
                for (y in 0 until height) {
                    var pixel = bitMap.getPixel(x, y)
                    if (pixel == white) {
                        pixel = if (random.nextBoolean()) grey else white
                    }
                    bitMap.setPixel(x, y, pixel)
                }
            }
        }

        private fun drawRandomText(
                canvas: Canvas,
                paint: Paint,
                textX: Int,
                textY: Int,
                length: Int,
                type: Int,
                isDot: Boolean,
                isItalic: Boolean
        ): String {
            var generatedCaptcha = ""
            val scewRange = intArrayOf(-1, 1)
            val textSizeRange = intArrayOf(20.px, 20.px, 20.px, 20.px)
            val random = Random()
            paint.textSkewX =
                    (if (isItalic) scewRange[random.nextInt(scewRange.size)] else 0).toFloat()
            for (index in 0 until length) {
                val temp = generateRandomText(type)
                generatedCaptcha += temp
                paint.textSize = textSizeRange[random.nextInt(textSizeRange.size)].toFloat()
                if (isDot)
                    canvas.drawText(
                            temp,
                            (textX + index * 20.px).toFloat(),
                            textY.toFloat(),
                            paint
                    )
                else
                    canvas.drawText(temp, (textX + index * 20).toFloat(), textY.toFloat(), paint)
            }
            return generatedCaptcha
        }

        private fun generateRandomText(type: Int): String {
            val numbers = arrayOf("0", "1", "2", "3", "4", "5", "6", "7", "8", "9")
            val alphabets = arrayOf("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z")
            val random = Random()
            val mixedRandom = Random()
            return when (type) {
                ALPHABETS -> alphabets[random.nextInt(alphabets.size)]
                NUMBERS -> numbers[random.nextInt(numbers.size)]
                else -> if (mixedRandom.nextBoolean()) alphabets[random.nextInt(alphabets.size)] else numbers[random.nextInt(
                        numbers.size
                )]
            }
        }

        private fun generateRandomInt(length: Int): Int {
            val random = Random()
            val ran = random.nextInt(length)
            return if (ran == 0) random.nextInt(length) else ran
        }

        private fun generateRandomInt(min: Int, max: Int): Int {
            val rand = Random()
            return rand.nextInt(max - min + 1) + min
        }

        internal class Captcha internal constructor(captchaCode: String, bitmap: Bitmap) {
            internal var captchaCode: String? = null
            internal var bitmap: Bitmap? = null

            init {
                this.captchaCode = captchaCode
                this.bitmap = bitmap
            }
        }

    }

    companion object {
        const val TYPE_ITALIC = 100
        const val TYPE_PLAIN_TEXT = 101
    }
}