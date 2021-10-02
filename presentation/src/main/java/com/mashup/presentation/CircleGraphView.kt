package com.mashup.presentation

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlin.math.cos
import kotlin.math.sin


class CircleGraphView : View {
    constructor (context: Context) : super(context)
    constructor (context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor (context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context, attrs, defStyleAttr
    )

    private val paint = Paint()
    private val paintLine = Paint()

    private val linePaint = Paint()

    var delayDegree = 0.0
    private val distance = 800f
    private val strokeWidth = 40f
    private val shader: Shader = LinearGradient(
        0f, 0f, 0f, distance,
        Color.rgb(96, 29, 206),
        Color.rgb(198, 163, 255),
        Shader.TileMode.CLAMP
    )

    private var maskPaint: Paint? = null
    private var imagePaint: Paint? = null

    init {
        val colorLine: Int = ContextCompat.getColor(context, R.color.color_line)

        paint.isAntiAlias = true
        paintLine.color = colorLine
        paint.strokeWidth = strokeWidth
        paint.style = Paint.Style.STROKE
        paint.strokeCap = Paint.Cap.ROUND
        paintLine.strokeWidth = strokeWidth
        paintLine.style = Paint.Style.STROKE
        paintLine.strokeCap = Paint.Cap.ROUND

        maskPaint = Paint()
        maskPaint!!.xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)

        imagePaint = Paint()
        imagePaint!!.xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_OVER)


        linePaint.isAntiAlias = true
        linePaint.color = Color.WHITE
        linePaint.strokeWidth = 50F
    }


    override fun onMeasure(widthwidthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(width, heightMeasureSpec)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawSubtitle(canvas)
    }


    private fun getTargetX(degree: Int, r: Int): Float {
        return (cos(Math.toRadians(degree.toDouble())) * r).toFloat()
    }

    private fun getTargetY(degree: Int, r: Int): Float {
        return (sin(Math.toRadians(degree.toDouble())) * r).toFloat()
    }

    private fun drawSubtitle(canvas: Canvas) {
        val width = (distance * 2).toInt()
        val height = (distance * 2).toInt()
        val radius = (distance / 2.2).toInt()

        val conf = Bitmap.Config.ARGB_8888 // See other config types
        val mImage = Bitmap.createBitmap(width, height, conf) // This creates a mutable bitmap
        paint.shader = shader

        val mMask = Bitmap.createBitmap(distance.toInt(), distance.toInt(), conf)
        val maskCanvas = Canvas(mMask)

        val oval = RectF()
        val centerX = distance / 2
        val centerY = distance / 2
        oval[centerX - radius, centerY - radius, centerX + radius] = centerY + radius
        canvas.drawArc(oval, 135f, 270f, false, paintLine);
        canvas.drawArc(oval, 135f, ((delayDegree % 270).toFloat()), false, paint);

        // /Mask
        canvas.save()
        maskCanvas.drawBitmap(mMask, 0f, 0f, maskPaint)
        maskCanvas.drawBitmap(mImage, 0f, 0f, imagePaint) // Notice the imagePaint instead of null
        canvas.restore()

        //circle
        for (i in 3..5) {
            canvas.drawCircle(getTargetX(70*i, radius)+radius+32, getTargetY(70*i, radius)
                + radius+30, 20f, linePaint)
        }
    }

    private suspend fun cycle() {
        for (i in 0..540) {
             coroutineScope {
                delay(20)
                delayDegree+=0.4
            }
            this.post {
                invalidate()
            }
        }
    }


    suspend fun setCueList(
    ) {
        cycle()
    }
}
