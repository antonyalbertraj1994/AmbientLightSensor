package com.example.lightmeter_watch.presentation

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class CanvasView(context: Context, attrs: AttributeSet? = null) : View(context, attrs) {

    private val paint = Paint()
    private val paint1 = Paint()

    var text1:String = ""

    init {

        paint1.color = Color.BLACK
        paint1.style = Paint.Style.FILL_AND_STROKE
        paint1.strokeWidth = 8f

        paint.color = Color.WHITE
        paint.style = Paint.Style.FILL_AND_STROKE
        paint.strokeWidth = 8f
    }

    public
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        //canvas1 = canvas


        // Set paint color and style for rectangle
        if(text1=="0") {
            println("0")
            canvas.drawRect(300f, 1400f, 500f, 1600f, paint1)
        } else{
            println("1")
            canvas.drawRect(100f, 100f, 150f, 150f, paint)
        }

    }

    fun setText(text:String) {
        //this.text = "A"
        text1 = text
        //println(text)
    }
}

