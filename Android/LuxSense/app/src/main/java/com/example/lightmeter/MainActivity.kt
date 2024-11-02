package com.example.lightmeter

import android.annotation.SuppressLint
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.lightmeter.ui.theme.LightMeterTheme

import android.app.Activity
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintSet.Motion

import android.content.ContentResolver
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.hardware.SensorDirectChannel
import android.net.Uri
import android.provider.Settings
import android.view.WindowManager
import android.widget.Button
import android.widget.RelativeLayout
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.newFixedThreadPoolContext
import java.util.Timer
import kotlin.concurrent.schedule
import kotlin.concurrent.thread


class MainActivity : Activity(), SensorEventListener {
    private lateinit var sensorManager: SensorManager
    private var lightSensor: Sensor? = null
    private lateinit var lightvalueTextView: TextView
    private lateinit var button_increase: Button
    private lateinit var button_decrease: Button
    private lateinit var relativeLayout: RelativeLayout
    private lateinit var color_button: Button
    private lateinit var flashbutton: Button

    private var colorstate:Boolean? = null
    private var colorstate_auto:Boolean? = null


    private var brightval = 255
    private var flashspeed:Long = 100
    private var timestart:Boolean?= null


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button_increase = findViewById(R.id.increase_bright)
        button_decrease = findViewById(R.id.decrease_bright)
        relativeLayout = findViewById(R.id.relativelayout1)
        flashbutton = findViewById(R.id.flash);
        val view = CanvasView(this)
        relativeLayout.addView(view)



        flashbutton.setOnClickListener {
            val thread = Thread {
                sendPulse(view)
            }
            thread.start()

        }
        relativeLayout.setBackgroundColor(android.graphics.Color.WHITE)

        sensorList()




        button_increase.setOnClickListener {
            // Handle button click
            brightval += 10
            if (brightval >= 255)
                brightval = 255

            Log.d("Brightness","pressed")
            setBrightness(brightval) // Modify brightness only if permission is granted
        }

        button_decrease.setOnClickListener {
            // Handle button click
            brightval -= 10
            if (brightval <= 255)
                brightval = 0

            Log.d("Brightness","pressed")
            setBrightness(brightval) // Modify brightness only if permission is granted
        }

        lightvalueTextView = findViewById(R.id.light_level)
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)
        if (lightSensor == null) {
            lightvalueTextView.text = "NO light Sensor"

        }

//        val channel = sensorManager.createDirectChannel(lightSensor,SensorDirectChannel.RATE_FAST)
//        channel?.let {
//            sensorManager.registerListener(this, lightSensor, it)
//        }

        val time = lightSensor!!.minDelay
        checkWriteSettingsPermission()
        colorstate = true
        colorstate_auto = true

        color_button = findViewById(R.id.background_change)
        color_button.setOnClickListener {
            if(colorstate == true) {
                //relativeLayout.setBackgroundColor(android.graphics.Color.WHITE)
                //lightvalueTextView.setTextColor(Color.BLACK)
                colorstate = false
                view.setText("1")
                view.invalidate()
            }else{
                //relativeLayout.setBackgroundColor(android.graphics.Color.BLACK)
                //lightvalueTextView.setTextColor(Color.WHITE)
                colorstate = true
                view.setText("2")
                view.invalidate()
            }
        }
        if (Settings.System.canWrite(this)) {
            setBrightness(brightval) // Modify brightness only if permission is granted
        } else {
            checkWriteSettingsPermission() // Ask for permission if not granted
        }
        Log.d("delaytime", "$time")
//        val timer = Timer()
//        timestart = true
//        if (timestart as Boolean) {
//            timer.schedule(0, flashspeed) {
//                if (colorstate_auto as Boolean) {
//
//                    view.setText("1")
//                    view.invalidate()
////                    runOnUiThread {
////                    setBrightness(255)
////                    }
//                    colorstate_auto = false
//                } else {
//                    view.setText("2")
//                    view.invalidate()
////                    runOnUiThread {
////                        setBrightness(0)
////                    }
//                    colorstate_auto = true
//                }
//
//            }
//        }
    }


    fun sendPulse(view: CanvasView) {
        val timer = Timer()
         var colorstate_auto:Boolean = false
        val values = intArrayOf(1,     1,0,1,0,1,0,1,1,      0) // one start bit and one stop bit
        var i =0
        val currenttime = System.currentTimeMillis()
        timer.schedule(0, 50) {
            if (values.get(9 - i) == 1){
                view.setText("1")
                view.invalidate()
            } else {
                view.setText("2")
                view.invalidate()
            }
            //val elapsed = System.currentTimeMillis() - currenttime
            //println("value:${values.get(i)}, Time:$elapsed")
            i++
            if(i ==10) {
                timer.cancel()

            }

//            if(colorstate_auto) {
//                view.setText("1")
//                view.invalidate()
//            } else {
//                view.setText("2")
//                view.invalidate()
//            }
//            colorstate_auto = !colorstate_auto

        }

    }
    override fun onResume() {
        super.onResume()
        lightSensor?.also {
            sensorManager.registerListener(this, it,SensorManager.SENSOR_DELAY_FASTEST)
        }
    }
    private fun checkWriteSettingsPermission() {
        if (!Settings.System.canWrite(this)) {
            //Toast.makeText(this, "Please allow write settings permission", Toast.LENGTH_LONG).show()

            // Send user to system settings to grant the permission
            val intent = Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS)
            intent.data = Uri.parse("package:com.example.lightmeter")
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when(event!!.action){
            MotionEvent.ACTION_DOWN ->{
                val x =event.x
                Log.d("TouchPoint","$x")
            }
        }
        return super.onTouchEvent(event)
    }

    fun sensorList(){
        val sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager

        val sensorList: List<Sensor> = sensorManager.getSensorList(Sensor.TYPE_ALL)
        sensorList.forEach{sensor: Sensor ->
            println(sensor)
        }
    }
    override fun onSensorChanged(event: SensorEvent?) {


        if (event!!.sensor.type  == Sensor.TYPE_LIGHT) {
            val lightval = event.values[0]
            lightvalueTextView.text = "$lightval"
            Log.d("LightVal","$lightval")
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }

    fun setBrightness(brightness:Int?) {
        val contentResolver:ContentResolver = contentResolver
        Settings.System.putInt(contentResolver, Settings.System.SCREEN_BRIGHTNESS, brightness!!)

        // Apply the brightness change to the current window
        val layoutParams: WindowManager.LayoutParams = window.attributes
        val brightval = brightness / 255.0f
        Log.d("Brightnessval", "$brightval")
        layoutParams.screenBrightness = brightness / 255.0f
        window.attributes = layoutParams

    }
}




