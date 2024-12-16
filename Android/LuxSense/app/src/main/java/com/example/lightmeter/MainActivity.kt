package com.example.lightmeter

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.Timer
import kotlin.concurrent.schedule
import kotlin.concurrent.timer


class MainActivity : AppCompatActivity() {

    private lateinit var flashingSquareSurfaceView: FlashingSquareSurfaceView

    private lateinit var relativeLayout: RelativeLayout

    private lateinit var flashbutton: Button
    private val updateFrequency = 30L
    private lateinit var lightvalueTextView1: TextView
    private var togglestate = true

//    private val handler = Handler(Looper.getMainLooper())
//    var i = 0
//    private val toggleRunnable = object : Runnable {
//        override fun run() {
//            val values = intArrayOf(1,     1,0,1,0,1,0,1,0,      0) // one start bit and one stop bit
//
//            if(values.get(9 - i) == 1)
//                lightvalueTextView1.setBackgroundColor(android.graphics.Color.RED)
//            else
//                lightvalueTextView1.setBackgroundColor(android.graphics.Color.BLACK)
//
//            i++
//            if(i<10) {
//                handler.postDelayed(this,updateFrequency)
//            }
//        }
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize and set the custom SurfaceView as the content view
        setContentView(R.layout.activity_main)
        //flashingSquareSurfaceView = FlashingSquareSurfaceView(this)
        relativeLayout = findViewById(R.id.relativelayout1)
        //flashingSquareSurfaceView.setBackgroundColor(Color.RED)
        //relativeLayout.addView(flashingSquareSurfaceView)
        //flashingSquareSurfaceView.setBackgroundColor(Color.RED)
        //flashingSquareSurfaceView = findViewById(R.id.flashing_square_surface_view)
        //flashingSquareSurface = findViewById(R.id.flashing_square_surface_view)
        lightvalueTextView1 = findViewById(R.id.light_level1)
        lightvalueTextView1.setBackgroundColor(Color.RED)
        flashbutton = findViewById(R.id.flash)
        flashbutton.setOnClickListener {
            //flashingSquareSurfaceView.stopFlashing()
            //flashingSquareSurfaceView.startFlashing()
            //flashingSquareSurface.startflashing()
            val thread = Thread {
                scheduletimer()
            }
            thread.start()

        }
    }


    fun scheduletimer() {
        val timer = Timer()
        val values = intArrayOf(1,   1, 1, 1, 0, 1, 0, 1, 1,     0) // one start bit and one stop bit
        var i = 0
        var values_2D = arrayOf(arrayOf(1, 1,0,0,0,0,0,0,0,0), arrayOf(1, 1,1,0,0,0,0,0,0,0),
            arrayOf(1, 1,0,1,0,0,0,0,0,0), arrayOf(1, 1,0,0,1,0,0,0,0,0)
            ,arrayOf(1, 1,0,0,0,1,0,0,0,0),arrayOf(1, 1,0,0,0,0,1,0,0,0),
            arrayOf(1, 1,0,0,0,0,0,1,0,0), arrayOf(1, 1,0,0,0,0,0,0,1,0))
        var j = 0
        //println(values_2D[][0])

            timer.schedule(0, 30) {

                if (values_2D[j][9 - i] == 1) {
                    runOnUiThread {
                        lightvalueTextView1.setBackgroundColor(android.graphics.Color.RED)

                    }
                } else {
                    runOnUiThread {
                        lightvalueTextView1.setBackgroundColor(android.graphics.Color.BLACK)
                    }
                }
                i++

                if (i == 10) {
                    i = 0;
                    j++
                    //timer.cancel()
                }
                if( j == 8) {
                    j = 0
                    timer.cancel()
                }
            }

    }

    override fun onResume() {
        super.onResume()
        // Start the flashing when the activity resumes
        //flashingSquareSurfaceView.startFlashing()
        println("Resume")
    }

    override fun onPause() {
        super.onPause()
        // Stop the flashing when the activity pauses
        //flashingSquareSurfaceView.stopFlashing()
        println("Pause")

    }

    override fun onDestroy() {
        super.onDestroy()
        // Ensure flashing stops completely
        //flashingSquareSurfaceView.stopFlashing()
        println("Destroy")

    }
}
//}

//import android.annotation.SuppressLint
//import androidx.activity.ComponentActivity
//import androidx.activity.compose.setContent
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Surface
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.tooling.preview.Preview
//import com.example.lightmeter.ui.theme.LightMeterTheme
//
//import android.app.Activity
//import android.content.Context
//import android.hardware.Sensor
//import android.hardware.SensorEvent
//import android.hardware.SensorEventListener
//import android.hardware.SensorManager
//import android.os.Bundle
//import android.util.Log
//import android.view.MotionEvent
//import android.view.View
//import android.widget.TextView
//import androidx.constraintlayout.widget.ConstraintSet.Motion
//
//import android.content.ContentResolver
//import android.content.Intent
//import android.content.pm.PackageManager
//import android.graphics.Color
//import android.hardware.SensorDirectChannel
//import android.net.Uri
//import android.provider.Settings
//import android.view.WindowManager
//import android.widget.Button
//import android.widget.RelativeLayout
//import android.widget.SeekBar
//import androidx.appcompat.app.AppCompatActivity
//import kotlinx.coroutines.newFixedThreadPoolContext
//import java.util.Timer
//import java.util.TimerTask
//import java.util.concurrent.Executors
//import java.util.concurrent.ScheduledExecutorService
//import java.util.concurrent.TimeUnit
//import kotlin.concurrent.schedule
//import kotlin.concurrent.thread
//
//
//class MainActivity : Activity(), SensorEventListener {
//    private lateinit var sensorManager: SensorManager
//    private var lightSensor: Sensor? = null
//    private lateinit var lightvalueTextView: TextView
//    private lateinit var lightvalueTextView1: TextView
//
//    private lateinit var button_increase: Button
//    private lateinit var button_decrease: Button
//    private lateinit var relativeLayout: RelativeLayout
//    private lateinit var color_button: Button
//    private lateinit var flashbutton: Button
//
//    private var colorstate:Boolean? = null
//    private var colorstate_auto:Boolean? = null
//    private lateinit var scheduler:ScheduledExecutorService
//
//    private var brightval = 255
//    private var flashspeed:Long = 100
//    private var timestart:Boolean?= null
//
//
//    @SuppressLint("MissingInflatedId")
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//        button_increase = findViewById(R.id.increase_bright)
//        button_decrease = findViewById(R.id.decrease_bright)
//        relativeLayout = findViewById(R.id.relativelayout1)
//        lightvalueTextView1 = findViewById(R.id.light_level1)
//        flashbutton = findViewById(R.id.flash);
//        val view = CanvasView(this)
//        relativeLayout.addView(view)
//
//
//
//        flashbutton.setOnClickListener {
//            val thread = Thread {
//                sendPulse(view)
//            }
//            thread.start()
//
//        }
//        relativeLayout.setBackgroundColor(android.graphics.Color.WHITE)
//
//        sensorList()
//
//
//
//
//        button_increase.setOnClickListener {
//            // Handle button click
//            brightval += 10
//            if (brightval >= 255)
//                brightval = 255
//
//            Log.d("Brightness","pressed")
//            setBrightness(brightval) // Modify brightness only if permission is granted
//        }
//
//        button_decrease.setOnClickListener {
//            // Handle button click
//            brightval -= 10
//            if (brightval <= 255)
//                brightval = 0
//
//            Log.d("Brightness","pressed")
//            setBrightness(brightval) // Modify brightness only if permission is granted
//        }
//
//        lightvalueTextView = findViewById(R.id.light_level)
//        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
//        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)
//        if (lightSensor == null) {
//            lightvalueTextView.text = "NO light Sensor"
//
//        }
//
////        val channel = sensorManager.createDirectChannel(lightSensor,SensorDirectChannel.RATE_FAST)
////        channel?.let {
////            sensorManager.registerListener(this, lightSensor, it)
////        }
//
//        val time = lightSensor!!.minDelay
//        checkWriteSettingsPermission()
//        colorstate = true
//        colorstate_auto = true
//
//        color_button = findViewById(R.id.background_change)
//        color_button.setOnClickListener {
//            if(colorstate == true) {
//                //relativeLayout.setBackgroundColor(android.graphics.Color.WHITE)
//                //lightvalueTextView.setTextColor(Color.BLACK)
//                lightvalueTextView1.setBackgroundColor(android.graphics.Color.RED)
//
//                colorstate = false
//                view.setText("1")
//                view.invalidate()
//            }else{
//                //relativeLayout.setBackgroundColor(android.graphics.Color.BLACK)
//                //lightvalueTextView.setTextColor(Color.WHITE)
//                colorstate = true
//                lightvalueTextView1.setBackgroundColor(android.graphics.Color.BLACK)
//
//                view.setText("0")
//                view.invalidate()
//            }
//        }
//        if (Settings.System.canWrite(this)) {
//            setBrightness(brightval) // Modify brightness only if permission is granted
//        } else {
//            checkWriteSettingsPermission() // Ask for permission if not granted
//        }
//        Log.d("delaytime", "$time")
////        val timer = Timer()
////        timestart = true
////        if (timestart as Boolean) {
////            timer.schedule(0, flashspeed) {


////                if (colorstate_auto as Boolean) {
////
////                    view.setText("1")
////                    view.invalidate()
//////                    runOnUiThread {
//////                    setBrightness(255)
//////                    }
////                    colorstate_auto = false
////                } else {
////                    view.setText("2")
////                    view.invalidate()
//////                    runOnUiThread {
//////                        setBrightness(0)
//////                    }
////                    colorstate_auto = true
////                }
////
////            }
////        }
//    }
//
//
//    fun sendPulse(view: CanvasView) {
//        val timer = Timer()
//         var colorstate_auto:Boolean = false
//        val values = intArrayOf(1,     1,0,1,0,1,0,1,0,      0) // one start bit and one stop bit
//        //val values = intArrayOf(0,     0,0,0,0,0,0,0,0,      0) // one start bit and one stop bit
//
//        var i =0
//        val currenttime = System.currentTimeMillis()
//
//        scheduler = Executors.newScheduledThreadPool(2)
//        scheduler.scheduleWithFixedDelay({
//            if (values.get(9 - i) == 1) {
//                    lightvalueTextView1.setBackgroundColor(android.graphics.Color.RED)
//                    //val elapsed = System.nanoTime() - currenttime
//                    //println("1,$elapsed")
//                } else {
//                    lightvalueTextView1.setBackgroundColor(android.graphics.Color.BLACK)
//                    //val elapsed = System.nanoTime() - currenttime
//                    //println("0,$elapsed")
//                }
//                i++;
//                if(i==10) {
//                    scheduler.shutdown()
//                }
//
////            if (colorstate_auto == true) {
////                colorstate_auto = false
//////                view.setText("1")
//////                view.invalidate()
////
////                lightvalueTextView1.setBackgroundColor(android.graphics.Color.RED)
////
////            } else {
////                colorstate_auto = true
////                lightvalueTextView1.setBackgroundColor(android.graphics.Color.BLACK)
////
//////                view.setText("0")
//////                view.invalidate()
////            }
//        },0, 20, TimeUnit.MILLISECONDS)
//
////        timer.scheduleAtFixedRate(object : TimerTask(){
////            override fun run() {
////
////                if (colorstate_auto == true) {
////                    colorstate_auto = false
//////                view.setText("1")
//////                view.invalidate()
////                    runOnUiThread {
////                        lightvalueTextView1.setBackgroundColor(android.graphics.Color.RED)
////                    }
////
////                } else {
////                    colorstate_auto = true
////                    runOnUiThread {
////                        lightvalueTextView1.setBackgroundColor(android.graphics.Color.BLACK)
////
////                    }
////
//////                view.setText("0")
//////                view.invalidate()
////                }
////            }
////        },0,50)
//
////            if (colorstate_auto == true) {
////                colorstate_auto = false
//////                view.setText("1")
//////                view.invalidate()
////
////                lightvalueTextView1.setBackgroundColor(android.graphics.Color.RED)
////
////            } else {
////                colorstate_auto = true
////                lightvalueTextView1.setBackgroundColor(android.graphics.Color.BLACK)
////
//////                view.setText("0")
//////                view.invalidate()
////            }
////        },0,20)
////        scheduler = Executors.newScheduledThreadPool(1)
////        scheduler.scheduleWithFixedDelay({
////            runOnUiThread {
////                if (values.get(9 - i) == 1) {
////                    lightvalueTextView1.setBackgroundColor(android.graphics.Color.RED)
////                    //val elapsed = System.nanoTime() - currenttime
////                    //println("1,$elapsed")
////                } else {
////                    lightvalueTextView1.setBackgroundColor(android.graphics.Color.BLACK)
////                    //val elapsed = System.nanoTime() - currenttime
////                    //println("0,$elapsed")
////                }
////            }
////
////           //val elapsed = System.currentTimeMillis() - currenttime
////           //println("value:${values.get(i)}, Time:$elapsed")
////           i++
////           if (i == 10) {
////               scheduler.shutdown()
////           }
////        },0, 20000, TimeUnit.MICROSECONDS)
//
//
////        timer.schedule(0, 20) {
////
////
////            if (colorstate_auto == true) {
////                colorstate_auto = false
//////                view.setText("1")
//////                view.invalidate()
////
////                lightvalueTextView1.setBackgroundColor(android.graphics.Color.RED)
////
////            } else {
////                colorstate_auto = true
////                lightvalueTextView1.setBackgroundColor(android.graphics.Color.BLACK)
////
//////                view.setText("0")
//////                view.invalidate()
////            }
////        }
//
//
////       timer.schedule(0, 20) {
////
////           if (values.get(9 - i) == 1) {
////               view.setText("1")
////               view.invalidate()
////           } else {
////               view.setText("0")
////               view.invalidate()
////           }
////
////           //val elapsed = System.currentTimeMillis() - currenttime
////           //println("value:${values.get(i)}, Time:$elapsed")
////           i++
////           if (i == 10) {
////               timer.cancel()
////
////           }
////       }
////        for(i in 0..9) {
////        if (values.get(9 - i) == 1) {
////            //var currenttime = System.nanoTime()
////            lightvalueTextView1.setBackgroundColor(android.graphics.Color.WHITE)
////            //val elapsed = System.nanoTime() - currenttime
////            //println(elapsed)
////        } else {
////            lightvalueTextView1.setBackgroundColor(android.graphics.Color.BLACK)
////        }
////        Thread.sleep(30);
////        }
//
////       var currenttime = System.nanoTime()
////       timer.schedule(0, 20) {
////
////           if (values.get(9 - i) == 1) {
////               lightvalueTextView1.setBackgroundColor(android.graphics.Color.RED)
////               val elapsed = System.nanoTime() - currenttime
////               println("1,$elapsed")
////           } else {
////               lightvalueTextView1.setBackgroundColor(android.graphics.Color.BLACK)
////               val elapsed = System.nanoTime() - currenttime
////               println("0,$elapsed")
////           }
////
////           //val elapsed = System.currentTimeMillis() - currenttime
////           //println("value:${values.get(i)}, Time:$elapsed")
////           i++
////           if (i == 10) {
////               timer.cancel()
////
////           }
////       }
//
//
//    }
//    override fun onResume() {
//        super.onResume()
//        lightSensor?.also {
//            sensorManager.registerListener(this, it,SensorManager.SENSOR_DELAY_FASTEST)
//        }
//    }
//    private fun checkWriteSettingsPermission() {
//        if (!Settings.System.canWrite(this)) {
//            //Toast.makeText(this, "Please allow write settings permission", Toast.LENGTH_LONG).show()
//
//            // Send user to system settings to grant the permission
//            val intent = Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS)
//            intent.data = Uri.parse("package:com.example.lightmeter")
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//            startActivity(intent)
//        }
//    }
//
//    override fun onPause() {
//        super.onPause()
//        sensorManager.unregisterListener(this)
//    }
//
//    override fun onTouchEvent(event: MotionEvent?): Boolean {
//        when(event!!.action){
//            MotionEvent.ACTION_DOWN ->{
//                val x =event.x
//                Log.d("TouchPoint","$x")
//            }
//        }
//        return super.onTouchEvent(event)
//    }
//
//    fun sensorList(){
//        val sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
//
//        val sensorList: List<Sensor> = sensorManager.getSensorList(Sensor.TYPE_ALL)
//        sensorList.forEach{sensor: Sensor ->
//            println(sensor)
//        }
//    }
//    override fun onSensorChanged(event: SensorEvent?) {
//
//
//        if (event!!.sensor.type  == Sensor.TYPE_LIGHT) {
//            val lightval = event.values[0]
//            lightvalueTextView.text = "$lightval"
//            Log.d("LightVal","$lightval")
//        }
//    }
//
//    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
//    }
//
//    fun setBrightness(brightness:Int?) {
//        val contentResolver:ContentResolver = contentResolver
//        Settings.System.putInt(contentResolver, Settings.System.SCREEN_BRIGHTNESS, brightness!!)
//
//        // Apply the brightness change to the current window
//        val layoutParams: WindowManager.LayoutParams = window.attributes
//        val brightval = brightness / 255.0f
//        Log.d("Brightnessval", "$brightval")
//        layoutParams.screenBrightness = brightness / 255.0f
//        window.attributes = layoutParams
//
//    }
//}
//
//
//
//
