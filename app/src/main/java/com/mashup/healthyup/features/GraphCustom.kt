package com.mashup.healthyup.features

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.mashup.healthyup.R
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlin.math.cos
import kotlin.math.sin


class GraphCustom : View {
    constructor (context: Context) : super(context)
    constructor (context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor (context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
        super(context, attrs, defStyleAttr)

    private val paintProgressLine = Paint()
    private val paintBasicLineShadow = Paint()
    private var paintBasicLine = Paint()
    private var paintProgressLineShadow = Paint()

    private val paintCirclePoint = Paint()

    private var delayDegree = 0.0f
    private val distance = 800f
    private val strokeWidth = 40f
    private val shaderGradient: Shader = LinearGradient(
        0f, 0f, 0f, distance,
        Color.rgb(96, 29, 206),
        Color.rgb(198, 163, 255),
        Shader.TileMode.CLAMP
    )
    val startAngle = 135f
    val serrpAngle = 270f


    private val colorBasicLineRight: Int = ContextCompat.getColor(context, R.color.color_line_r)

    init {
        val colorLine: Int = ContextCompat.getColor(context, R.color.color_line)

        paintProgressLine.isAntiAlias = true
        paintProgressLine.strokeWidth = strokeWidth
        paintProgressLine.style = Paint.Style.STROKE
        paintProgressLine.strokeCap = Paint.Cap.ROUND

        paintBasicLine.color = colorBasicLineRight
        paintBasicLine.strokeWidth = 15f
        paintBasicLine.style = Paint.Style.STROKE
        paintBasicLine.strokeCap = Paint.Cap.ROUND

        paintProgressLineShadow.color = Color.WHITE
        paintProgressLineShadow.strokeWidth = 10f
        paintProgressLineShadow.style = Paint.Style.STROKE
        paintProgressLineShadow.strokeCap = Paint.Cap.ROUND

        paintBasicLineShadow.color = colorLine
        paintBasicLineShadow.strokeWidth = strokeWidth
        paintBasicLineShadow.style = Paint.Style.STROKE
        paintBasicLineShadow.strokeCap = Paint.Cap.ROUND

        paintCirclePoint.isAntiAlias = true
        paintCirclePoint.color = Color.WHITE
        paintCirclePoint.strokeWidth = 50F
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawProgressLine(canvas)
        drawSubtitle(canvas)
    }

    private fun getTargetX(degree: Float, r: Int): Float {
        return (cos(Math.toRadians(degree.toDouble())) * r).toFloat()
    }

    private fun getTargetY(degree: Float, r: Int): Float {
        return (sin(Math.toRadians(degree.toDouble())) * r).toFloat()
    }

    private fun drawProgressLine(canvas: Canvas) {
        val oval = RectF()
        var radius = (distance / 2.2).toInt()
        val centerX = distance / 2
        val centerY = distance / 2

        oval[centerX - radius, centerY - radius, centerX + radius] = centerY + radius

        //프로그래스바 백그라운드
        canvas.drawArc(oval, startAngle, serrpAngle, false, paintBasicLineShadow)

        //프로그래스바 백그라운드 음영
        paintBasicLine.setShadowLayer(10f, 0f, 0f, colorBasicLineRight)
        canvas.drawArc(oval, startAngle - 5f, serrpAngle + 5f, false, paintBasicLine)
        paintBasicLine.setShadowLayer(30f, 0f, 0f, colorBasicLineRight)
        canvas.drawArc(oval, startAngle - 5f, serrpAngle + 5f, false, paintBasicLine)
        canvas.drawArc(oval, startAngle - 5f, serrpAngle + 5f, false, paintBasicLine)
        canvas.drawArc(oval, startAngle - 5f, serrpAngle + 5f, false, paintBasicLine)

        //그라데이션 프로그래스바
        paintProgressLine.shader = shaderGradient
        canvas.drawArc(
            oval, startAngle, (delayDegree % serrpAngle), false, paintProgressLine
        )
        //그라데이션 프로그래스바 내부 밝은 선
        radius = (distance / 2.37).toInt()
        oval[centerX - radius, centerY - radius, centerX + radius] = centerY + radius
        paintProgressLineShadow.setShadowLayer(25f, 0f, 0f, Color.WHITE)
        canvas.drawArc(
            oval, startAngle, (delayDegree % serrpAngle), false, paintProgressLineShadow
        )
        canvas.drawArc(
            oval, startAngle, (delayDegree % serrpAngle), false, paintProgressLineShadow
        )

        //routine point
        val routine = 3
        val size = routine - 1
        radius = (distance / 2.2).toInt()
        for (i in 1..size) {
            val degree = startAngle + (serrpAngle / (size + 1)) * i
            canvas.drawCircle(
                getTargetX(degree, radius) + centerY,
                getTargetY(degree, radius)
                    + centerY,
                13f,
                paintCirclePoint
            )
        }
    }

    private suspend fun cycle() {
        for (i in 0..540) {
            coroutineScope {
                delay(20)
                delayDegree += 0.4f
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
