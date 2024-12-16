package com.example.lightmeter

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.Choreographer
import android.view.View

class FlashingSquareView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : View(context, attrs) {

    private val paint = Paint().apply { color = Color.RED }
    private val paint1 = Paint().apply { color = Color.BLACK }

    private var running = false
    private val updateInterval = 20L // 20 milliseconds for 50 FPS (50 frames per second)
    var i = 0
    var toggle = true
    var time1 = System.currentTimeMillis()
    // This variable will hold the frame callback for Choreographer
    private val frameCallback = object : Choreographer.FrameCallback {
        override fun doFrame(frameTimeNanos: Long) {

            //val values = intArrayOf(1,     1,0,1,0,1,0,1,0,      0) // one start bit and one stop bit



            // Toggle the color every frame


            // Request a redraw of the view
            if(running) {
                var elapsed = System.currentTimeMillis() - time1
                //println("Time,$elapsed")
                invalidate()
            }
            Choreographer.getInstance().postFrameCallback(this)

            // Post the next frame callback
        }
    }

    init {

        // Start the flashing effect by posting the first frame callback
        //Choreographer.getInstance().postFrameCallback(frameCallback)
    }

    fun startflashing() {
        time1 = System.currentTimeMillis()
        Choreographer.getInstance().postFrameCallback(frameCallback)
        running = true

    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // Set the color of the square based on the toggle
        //paint.color = if (isRed) Color.RED else Color.BLUE
        val values = intArrayOf(1,     0,0,0,1,1,0,1,0,      0) // one start bit and one stop bit
        if(running) {
            //toggle = !toggle
            if (values.get(9 - i) == 1) {
                //var time1 = System.nanoTime()
                canvas.drawRect(100f, 100f, 300f, 300f, paint)
                //var time2 = System.nanoTime()
                //var elapsed = time2 - time1
                //println("Time$elapsed")
            } else
                canvas.drawRect(100f, 100f, 300f, 300f, paint1)
            i++

            if (i == 10) {
                running = false
                i = 0
            }
            //println("Started")
        }
    }


    // Optional method to stop the flashing
    fun stopFlashing() {
        Choreographer.getInstance().removeFrameCallback(frameCallback)
    }
}
