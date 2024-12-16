package com.example.lightmeter

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.SurfaceHolder
import android.view.SurfaceView

class FlashingSquareSurfaceView(context: Context, attrs: AttributeSet? = null) : SurfaceView(context, attrs), SurfaceHolder.Callback {

    private val paint = Paint().apply { color = Color.WHITE }
    private val paint1 = Paint().apply { color = Color.BLACK }

    private var isSquareVisible = true
    private var flashInterval = 50L // Flash interval in milliseconds
    private var drawThread: DrawThread? = null
    private var running = false

    init {
        holder.addCallback(this)
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        // Start flashing when the surface is created
        //println("Surface created")
        val canvas: Canvas? = holder.lockCanvas()
        if(canvas!=null) {
            isSquareVisible = true
        }
        if (canvas != null) {
           // println("canvas initial")
            canvas.drawRect(300f, 1400f, 500f, 1600f, paint)
        }
        holder.unlockCanvasAndPost(canvas)

        //startFlashing()
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
        Log.d("gek","eg")
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        // Stop flashing when the surface is destroyed
        stopFlashing()
    }

    fun startFlashing() {
        Log.d("Flashing","Started")
        if (drawThread == null) {
            drawThread = DrawThread(holder).apply {
                running = true
                start()
            }
            Log.d("Flashing","Started1")

        }
    }

    fun stopFlashing() {
        Log.d("Flashing","Stopped")

        drawThread?.running = false
        //drawThread?.join()
        drawThread = null
        Log.d("Flashing","NUllset")

    }

    private inner class DrawThread(private val surfaceHolder: SurfaceHolder) : Thread() {
       @Volatile var running = false

        val values = intArrayOf(1,     1,1,1,0,1,0,1,1,      0) // one start bit and one stop bit

        private var lastFlashTime = System.currentTimeMillis()


        var i = 0
        override fun run() {


            while (running) {
                //println("Antony")
                val currentTime = System.currentTimeMillis()
                if (currentTime - lastFlashTime >= flashInterval) {
                    if (values.get(9 - i) == 1) {
                        isSquareVisible = true

                    } else {
                        isSquareVisible = false
                    }
                    var elapsed = currentTime - lastFlashTime

                    lastFlashTime = currentTime
                    i++
                    println("A$i, $elapsed")
                    if (i == 10) {
                        //running = false
                        i =0
                        stopFlashing()
                    }

                    val canvas: Canvas? = surfaceHolder.lockCanvas()
                    if(canvas!=null) {
                        drawSquare(canvas)
                        surfaceHolder.unlockCanvasAndPost(canvas)
                        //println("canvas")
                    }
//                    drawSquare(canvas!!)
//                    canvas?.let {
////                        print("helllo")
//                        drawSquare(it)
//                        surfaceHolder.unlockCanvasAndPost(it)
//                        //Log.d("DrawThread", "Canvas drawn") // Logging for debugging
//                    }
                }
                //sleep(16) // Small sleep to prevent overloading the CPU
            }
        }

        private fun drawSquare(canvas: Canvas) {
            //canvas.drawColor(Color.WHITE) // Clear the canvas
            if (isSquareVisible) {
                canvas.drawRect(300f, 1400f, 500f, 1600f, paint)
                //println("Val1")

//                val size = minOf(width, height) / 2f
//                val left = (width - size) / 2f
//                val top = (height - size) / 2f
//                canvas.drawRect(left, top, left + size, top + size, paint)
            } else{
                //println("Val2")
//                val size = minOf(width, height) / 2f
//                val left = (width - size) / 2f
//                val top = (height - size) / 2f
                canvas.drawRect(300f, 1400f, 500f, 1600f, paint1)
            }
        }
    }
}
