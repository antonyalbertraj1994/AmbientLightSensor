/* While this template provides a good starting point for using Wear Compose, you can always
 * take a look at https://github.com/android/wear-os-samples/tree/main/ComposeStarter to find the
 * most up to date changes to the libraries and their usages.
 */

package com.example.watch_lightmeter.presentation

import android.annotation.SuppressLint
import kotlin.math.abs
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.net.Uri
import android.net.wifi.WifiManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.text.format.Formatter
import android.util.Log
import android.view.InputDevice
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.WindowManager
import android.widget.Button
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.MutableLiveData
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.TimeText
import androidx.wear.tooling.preview.devices.WearDevices
import com.example.watch_lightmeter.R
import com.example.watch_lightmeter.presentation.theme.Watch_LightMeterTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.w3c.dom.Text
import java.io.OutputStream
import java.io.PrintWriter
import java.util.Timer
import kotlin.concurrent.schedule
import java.io.*
import java.net.Socket
import kotlinx.coroutines.*
import kotlinx.coroutines.internal.synchronized
import java.util.ArrayList
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock
import kotlin.math.roundToInt

class MainActivity : ComponentActivity() {
    private lateinit var lightvalueTextView: TextView
    private lateinit var relativeLayout: RelativeLayout

    private lateinit var sensorManager: SensorManager
    private var lightSensor: Sensor? = null
    private val recentReadings = mutableListOf<Float>()
    private val movingAverageWindow = 5
    private var previousLightLevel: Float = -1f
    private var smoothedLightLevel: Float = -1f
    private lateinit var lightvalueTextView1: TextView
    private var recordstate:Boolean = false
    private var offbuttonstate:Boolean = false
    private var maxlightval = -1
    private val threshold = 100f
    private lateinit var flashbutton: Button
    private lateinit var offbutton: Button
    private var flashid = 0
    private var max_lightvalue = -1

    companion object {
        var lightvalue: Int = 0
    }
    var livedata = MutableLiveData<String>()
    private lateinit var socketClient: com.example.watch_lightmeter.presentation.Socket
    private val lock = ReentrantLock()
    private var sending = true

    override fun onCreate(savedInstanceState: Bundle?) {
        //installSplashScreen()

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        lightvalueTextView = findViewById(R.id.light_level)
        lightvalueTextView.setTextColor(Color.WHITE)

        lightvalueTextView.setText("AN")
        sensorList()

        lightvalueTextView1 = findViewById(R.id.light_level1)
        lightvalueTextView1.setBackgroundColor(Color.BLACK)


        relativeLayout = findViewById(R.id.relativelayout)
        relativeLayout.setBackgroundColor(Color.BLACK)
//        if (Settings.System.canWrite(this)) {
//            // Permission is granted, you can proceed with write settings
//            Toast.makeText(this, "Write Settings Permission Granted", Toast.LENGTH_SHORT).show()
//        } else {
//            // Permission is not granted, request the permission
//            checkWriteSettingsPermission()
//        }

        //println("Wifi:$wifiIp")
        setBrightness(0)
        get_screenbrightness()
        val host = "192.168.1.11" // Directly put the IP number shown by ipconfig command on Desktop
        val port = 9003
        socketClient = com.example.watch_lightmeter.presentation.Socket(this)



        //senddata_socket("ASntony")
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)
        if (lightSensor == null) {
            lightvalueTextView.text = "NO"
        }
        offbutton = findViewById(R.id.off)
        offbutton.setOnClickListener {
            flashid = 1
            offbuttonstate = true
//            val thread = Thread {
//                scheduletimer()
//            }
//            thread.start()
        }
        flashbutton = findViewById(R.id.flash)
        flashbutton.setOnClickListener {
            //flashingSquareSurfaceView.stopFlashing()
            //flashingSquareSurfaceView.startFlashing()
            //flashingSquareSurface.startflashing()
            recordstate = !recordstate
            socketClient.connectToServer(host, port)
            socketClient.sendData("Hello")
//            println("")
//            socketClient.sendData("Hello")
//            flashid = 0
//            val thread = Thread {
//                scheduletimer()
//            }
//            thread.start()

        }
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

    }

//     fun get_lightvalue(): Int {
//        return lightvalue
//    }

    fun send_lightvalue() {
            //sending = true
        socketClient.sendData(lightvalue.toString())

    }

    fun send_brightness() {
        val brightness = get_screenbrightness()
        socketClient.sendData(brightness.toString())

    }

    fun stop_sending() {
        println("Stop sending")
       sending = false
    }

//    fun senddata_socket(message: String){
//        CoroutineScope(Dispatchers.IO).launch {
//            try {
//
//                // Create a socket and connect to the server
//
//                val socket = Socket(host, port)
//
//                // Send data to the server
//                val outputStream: OutputStream = socket.getOutputStream()
//                val printWriter = PrintWriter(outputStream, true)
//                printWriter.println(message)
//
//                // Log success
//                Log.d("SocketActivity", "Message sent: $message")
//
//                // Close the socket
//                socket.close()
//            } catch (e: Exception) {
//                // Handle any exceptions
//                Log.e("SocketActivity", "Error: ${e.message}", e)
//            }
//        }
//    }

    override fun onResume() {
        super.onResume()
        lightSensor?.also {
            sensorManager.registerListener(sensorEventListener, it,SensorManager.SENSOR_DELAY_FASTEST)
        }
    }
    fun scheduletimer() {
        val timer = Timer()
        val values = intArrayOf(1,   1, 1, 1, 0, 1, 0, 1, 1,     0) // one start bit and one stop bit
        var i = 0
        var values_2D = arrayOf(arrayOf(1,  1,0,0,0,1,1,0,0,  0),
                                arrayOf(1,  1,1,0,0,1,0,0,0,  0),
                                arrayOf(1,  1,0,1,0,0,0,0,0,  0),
                                arrayOf(1,  1,0,0,1,0,0,0,0,  0),
                                arrayOf(1,  1,0,0,0,1,0,0,0,  0),
                                arrayOf(1,  1,0,0,0,0,1,0,0,  0),
                                arrayOf(1,  1,0,0,0,0,0,1,0,  0),
                                arrayOf(1,  1,1,0,0,0,0,1,1,  0))
        var j = flashid
        //println(values_2D[][0])

        timer.schedule(0, 40) {

            if (values_2D[j][9 - i] == 1) {


                runOnUiThread {
                    lightvalueTextView1.alpha = 1f
//                    //lightvalueTextView1.setBackgroundColor(android.graphics.Color.RED)
                }
            } else {
                runOnUiThread {
                    //lightvalueTextView1.setBackgroundColor(android.graphics.Color.BLACK)
                    lightvalueTextView1.alpha = 0f
                }
            }
            i++

            if (i == 10) {
                i = 0;
                j++
                Thread.sleep(10)

                timer.cancel()
            }
            if( j == 8) {
                j = 0
                //timer.cancel()
            }
        }
    }

    fun setBrightness(brightness:Int?) {
        val contentResolver: ContentResolver = contentResolver
        Settings.System.putInt(contentResolver, Settings.System.SCREEN_BRIGHTNESS, brightness!!)

        // Apply the brightness change to the current window
        val layoutParams: WindowManager.LayoutParams = window.attributes
        val brightval = brightness / 100.0f
        Log.d("Brightnessval", "$brightval")
        layoutParams.screenBrightness = brightness / 100.0f
        window.attributes = layoutParams
    }

    private fun get_screenbrightness() : Int {
        try {
            // Access the system brightness setting
            val brightness = Settings.System.getInt(
                contentResolver,
                Settings.System.SCREEN_BRIGHTNESS
            )
            Log.d("BrighnessRecv", brightness.toString())
            return brightness
            //Toast.makeText(this, "Current Brightness: $brightness", Toast.LENGTH_LONG).show()
        } catch (e: Settings.SettingNotFoundException) {
            e.printStackTrace()
            return 0
            Toast.makeText(this, "Could not retrieve brightness level", Toast.LENGTH_LONG).show()
        }
    }
    private fun checkWriteSettingsPermission() {
        if (!Settings.System.canWrite(this)) {
            //Toast.makeText(this, "Please allow write settings permission", Toast.LENGTH_LONG).show()

            // Send user to system settings to grant the permission
            val intent = Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS)
            intent.data = Uri.parse("package:com.example.watch_lightmeter")
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
    }

    private val sensorEventListener = object : SensorEventListener {

        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
            println("Accuracy changed")
        }


        @SuppressLint("SetTextI18n")
        override fun onSensorChanged(event: SensorEvent?) {
                if (event!!.sensor.type  == Sensor.TYPE_LIGHT) {
                    var alpha = 0.7f
                    val lightval = event.values[0]
                    //println("lightvalue $lightval")
                    lightvalue = lightval.toInt()
//                    if(lightvalue > max_lightvalue) {
//                        max_lightvalue = lightvalue
//                        println("Fitted_maxlig$max_lightvalue")
//                    }else {
//                        // white light coefficient
////                        var coeff1 = 9.58368383e-08
////                        var coeff2 = 1.73587127e-02
////                        var coeff3 = 2.4894032
//
//                        var coeff1 =  -7.59158686e-05
//                        var coeff2 = 7.96724140e-01
//                        var coeff3 =  1.07727960e+01
//
//                        //70187 for white light
//                        var scaling:Double = 1457 / max_lightvalue.toDouble()
//                        var light_scaled = scaling * lightval
//                        var fitted_value =
//                            coeff1 * light_scaled * light_scaled + coeff2 * light_scaled + coeff3
//                        //socketClient.sendData(lightval.toString())
//                        fitted_value = ((fitted_value/10) *10).roundToInt()/10.0
//                        var outstring = "$fitted_value\u00B0 C"
//
//                        //lightvalueTextView.text = outstring
//                        println("fittedvalue:$fitted_value , actualvalue$lightval,scaling$scaling")
//                    }
//
                    if(offbuttonstate == false) {
                        if (lightval > maxlightval) {
                            maxlightval = lightval.toInt()
                            lightvalueTextView.text = " $maxlightval "

                        }
                    } else {
                        if (true) {
                            lightvalueTextView.text = " $lightval "
                        } else {
                            val glucose_array: IntArray =
                                intArrayOf(9040, 7178, 6368, 6162, 5572, 4555)

                            val error_list = ArrayList<Double>()
                            for (i in 0..5) {
                                var val1 = glucose_array[i]/2869.0
                                var val2 = lightval/maxlightval
                                //println("val2 $val2")

                                var error = abs( val1 - val2 )
                                error_list.add(error)
                            }
                            val minval = error_list.minOrNull()
                            val glucoseval = error_list.indexOf(minval)
                            lightvalueTextView.text = " $glucoseval "
                            var reference_glucose =glucose_array[glucoseval] / 2981
                            var mod_light = lightval / maxlightval
//                            if (0.9 * reference_glucose < mod_light && mod_light < 1.1 * reference_glucose ) {
//                                lightvalueTextView.text = " $glucoseval "
//                            } else {
//                                lightvalueTextView.text = " $glucoseval "
//
//                                //lightvalueTextView.text = " No Strip "
//
//                            }
                        }
                    }
                    println("lightval $lightval")
//                    lock.withLock {
//
//                        val lightval = event.values[0]
//                        lightvalueTextView.text = " $lightval "
//                        livedata.value = "$lightval"
//                        if(true) {
//                        }
//                    }
                    //println("lightvalue$lightval")
                    if(recordstate) {
                        //senddata_socket(lightval.toString())
                        //println("val:$lightval")
                    }
//                    if (previousLightLevel <0 || Math.abs(lightval - previousLightLevel) > 100 ){
//                        recentReadings.add(lightval)
//                        if (recentReadings.size > movingAverageWindow) {
//                            recentReadings.removeAt(0)
//                        }
//                    }


//                    if (smoothedLightLevel < 0) {
//                        smoothedLightLevel = lightval
//                    }
//                    val newSmoothedLightLevel = smoothedLightLevel + alpha * (lightval - smoothedLightLevel)


//                    runOnUiThread {
//                        if (Math.abs(newSmoothedLightLevel - smoothedLightLevel) > 20) {
//                        //if ( newSmoothedLightLevel < smoothedLightLevel * 0.8 || newSmoothedLightLevel > smoothedLightLevel * 1.2)   {
//                            smoothedLightLevel = newSmoothedLightLevel
//                            var level = (smoothedLightLevel/100).toInt()
//
//                            //lightvalueTextView.text = " $smoothedLightLevel "
////                            lightvalueTextView.text = " $smoothedLightLevel "
//
//                        }
//                }
            }
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_STEM_1) {
            // Crown button pressed
            Log.d("CrownPress", "Crown button pressed")
            //handleCrownPress()
             // Consume the event to stop default behavior
        }
        return true
        return super.onKeyDown(keyCode, event)
    }
    override fun onKeyUp(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_STEM_1) {
            // Crown button released
            Log.d("CrownPress", "Crown button released")
            return true // Consume the event
        }
        return true
        return super.onKeyUp(keyCode, event)
    }

    override fun onGenericMotionEvent(event: MotionEvent?): Boolean {
        if (event?.action == MotionEvent.ACTION_SCROLL &&
            event.source and InputDevice.SOURCE_ROTARY_ENCODER == InputDevice.SOURCE_ROTARY_ENCODER
        ) {
            // Get the rotation direction.
            val delta = -event.getAxisValue(MotionEvent.AXIS_SCROLL)

            // Positive delta = clockwise rotation, Negative delta = counterclockwise rotation.
            handleCrownRotation(delta)
            return true
        }
        return super.onGenericMotionEvent(event)
    }

    private fun handleCrownRotation(delta: Float) {
        if (delta > 0) {
            // Clockwise rotation
            Log.d("CrownRotation", "Rotating clockwise: $delta")
        } else {
            // Counterclockwise rotation
            Log.d("CrownRotation", "Rotating counterclockwise: $delta")
        }
    }


    private fun handleCrownPress() {
        // Add your custom behavior here
        Toast.makeText(this, "Crown Pressed!", Toast.LENGTH_SHORT).show()
    }

    fun findglucosevalue() {


    }
    @SuppressLint("ServiceCast")
    fun getWiFiIPAddress(context: Context): String? {
        val wifiManager = context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        val wifiInfo = wifiManager.connectionInfo
        val ipInt = wifiInfo.ipAddress
        return Formatter.formatIpAddress(ipInt)
    }

    override fun onPause() {
        super.onPause()
       sensorManager.unregisterListener(sensorEventListener)
   }

    override fun onDestroy() {
        super.onDestroy()
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    fun sensorList(){
        val sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager

        val sensorList: List<Sensor> = sensorManager.getSensorList(Sensor.TYPE_ALL)
        sensorList.forEach{sensor: Sensor ->
            println(sensor)
        }
    }
}

