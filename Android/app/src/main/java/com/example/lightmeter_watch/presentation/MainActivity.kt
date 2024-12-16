/* While this template provides a good starting point for using Wear Compose, you can always
 * take a look at https://github.com/android/wear-os-samples/tree/main/ComposeStarter to find the
 * most up to date changes to the libraries and their usages.
 */

package com.example.lightmeter_watch.presentation

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.Bundle
import android.widget.Button
import android.widget.RelativeLayout
import android.widget.TextView
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
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.TimeText
import androidx.wear.tooling.preview.devices.WearDevices
import com.example.lightmeter_watch.R
import com.example.lightmeter_watch.presentation.theme.LightMeter_WatchTheme

class MainActivity : ComponentActivity() {
    private lateinit var button_increase: Button
    private lateinit var relativeLayout: RelativeLayout
    private var colorstate:Boolean = false
    private lateinit var sensorManager: SensorManager
    private var lightSensor: Sensor? = null
    private lateinit var lightvalueTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        installSplashScreen()

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main);
        relativeLayout = findViewById(R.id.relativelayout1)
        val view = CanvasView(this)
        relativeLayout.addView(view)

        button_increase = findViewById(R.id.increase_bright)
        button_increase.setOnClickListener {
            println("Button Pressed")
            if (colorstate == true){
                view.setText("1")
                view.invalidate()
            } else {
                view.setText("0")
                view.invalidate()
            }
            colorstate = !colorstate;
        }
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)
        if (lightSensor == null) {
            lightvalueTextView.text = "NO light Sensor"

        }


//        setContent {
//            WearApp("Android")
//        }
    }
}

//@Composable
//fun WearApp(greetingName: String) {
//    LightMeter_WatchTheme {
//        Box(
//            modifier = Modifier
//                .fillMaxSize()
//                .background(MaterialTheme.colors.background),
//            contentAlignment = Alignment.Center
//        ) {
//            TimeText()
//            Greeting(greetingName = greetingName)
//        }
//    }
//}
//
//@Composable
//fun Greeting(greetingName: String) {
//    Text(
//        modifier = Modifier.fillMaxWidth(),
//        textAlign = TextAlign.Center,
//        color = MaterialTheme.colors.primary,
//        text = stringResource(R.string.hello_world, greetingName)
//    )
//}
//
//@Preview(device = WearDevices.SMALL_ROUND, showSystemUi = true)
//@Composable
//fun DefaultPreview() {
//    WearApp("Preview Android")
//}