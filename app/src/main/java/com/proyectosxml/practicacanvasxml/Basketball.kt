package com.proyectosxml.practicacanvasxml

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.BlurMaskFilter
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class Basketball(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    val paint: Paint = Paint()
    val animation: ValueAnimator
    val ball: Bitmap
    val basket: Bitmap

    var ballX = 0
    var ballY = 0
    var radius = 200f

    init {
        paint.color = Color.WHITE

        paint.maskFilter = BlurMaskFilter(20f, BlurMaskFilter.Blur.NORMAL)

        animation = ValueAnimator.ofFloat(200f, 100f)
        animation.duration = 300
        animation.addUpdateListener {
            radius = it.animatedValue as Float
            invalidate()
        }
        animation.repeatCount = 1
        animation.repeatMode = ValueAnimator.REVERSE

        ball = BitmapFactory.decodeResource(resources, R.drawable.basketball)
        basket = BitmapFactory.decodeResource(resources, R.drawable.basket)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        // Inicializar ballX y ballY con las coordenadas del centro de la pantalla
        ballX = w / 2
        ballY = h / 2
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        canvas.drawColor(Color.WHITE)
        canvas.drawCircle((width / 2).toFloat(), (height / 2).toFloat(), radius, paint)
        canvas.drawBitmap(
            ball, Rect(0, 0, ball.width, ball.height), Rect(
                (ballX - radius).toInt(), (ballY - radius).toInt(), (ballX + radius).toInt(),
                (ballY + radius).toInt()
            ), paint
        )

        canvas.drawBitmap(
            basket, Rect(0, 0, basket.width, basket.height), Rect(
                width / 2 - 200, 0, width / 2 + 200, 400
            ), null
        )

        canvas.drawBitmap(
            basket, Rect(0, 0, basket.width, basket.height), Rect(
                width / 2 - 200, height - 400, width / 2 + 200, height
            ), null
        )

    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_MOVE -> {
                ballX = event.x.toInt()
                ballY = event.y.toInt()
                animation.start()
            }
        }
        invalidate()  // Solicitar que se vuelva a dibujar la vista
        return true
    }
}