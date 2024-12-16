package com.example.lightmeter

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class CanvasView(context: Context, attrs: AttributeSet? = null) : View(context, attrs) {

    private val paint = Paint()
    private val paint1 = Paint()

    var canvas1 = Canvas()
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

            //println("2")
            canvas.drawRect(300f, 1400f, 500f, 1600f, paint1)
        } else{

            //println("1")
            canvas.drawRect(300f, 1400f, 500f, 1600f, paint)

        }

        // Set paint color and text size for text
//        paint.color = Color.BLACK
//        paint.style = Paint.Style.FILL
//        paint.textSize = 60f
//        canvas.drawText(text1, 100f, 450f, paint)
        //text?.let { canvas.drawText(it, 100f, 450f, paint) }
        //println("Draw")
    }

    fun setText(text:String) {
        //this.text = "A"
        text1 = text
        //println(text)
    }
}


