package dam_A47478.coolweatherapp

import android.content.res.Configuration
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import java.io.InputStreamReader
import java.net.URL

class MainActivity : AppCompatActivity() {
    private var day = false

    private fun fetchWeatherData(lat: Float, long: Float) : Thread {
        return Thread {
            val weather = WeatherAPI_Call(lat, long)
            updateUI(weather)
        }
    }

    private fun updateUI(request: WeatherData) {
        runOnUiThread {
            val weatherImage : ImageView = findViewById(R.id.weatherIcon)
            val wind : TextView = findViewById(R.id.windspeed_text)

            //TODO ...

            wind.text = request.hourly.pressure_msl.get(12).toString() + " hPa"

            //TODO ...

            val mapt = getWeatherCodeMap();
            val wCode = mapt.get(request.current_weather.weathercode)
            val wImage = when(wCode) {
                WMO_WeatherCode.CLEAR_SKY,
                WMO_WeatherCode.MAINLY_CLEAR,
                WMO_WeatherCode.PARTLY_CLOUDY->if(day) wCode?.image+"day" else
                    wCode?.image+"night"
                else -> wCode?.image
            }
            val res = getResources()
            weatherImage.setImageResource(R.drawable.fog)
            val resID = res.getIdentifier(wImage, "drawable", getPackageName());
            val drawable = this.getDrawable(resID);
            weatherImage.setImageDrawable(drawable);

            //TODO ...
        }
    }

    private fun WeatherAPI_Call(lat: Float, long: Float) : WeatherData {
        val reqString = buildString {
            append("https://api.open-meteo.com/v1/forecast?")
            append("latitude=${lat}&longitude=${long}&")
            append("current_weather=true&")
            append("hourly=temperature_2m,weathercode,pressure_msl,windspeed_10m")
        }
        val str = reqString.toString()
        val url = URL(reqString.toString());
        url.openStream().use {
            val request = Gson().fromJson(InputStreamReader(it,"UTF-8"),WeatherData::class.java)
            return request
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        val themeId = when (resources.configuration.orientation) {
            Configuration.ORIENTATION_PORTRAIT -> if (day) R.style.Theme_Day else R.style.Theme_Night
            Configuration.ORIENTATION_LANDSCAPE -> if (day) R.style.Theme_Day_Land else R.style.Theme_Night_Land
            else -> R.style.Theme_Day // Default fallback
        }

        setTheme(themeId)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button: Button = findViewById(R.id.update)
        button.setOnClickListener {
            println("Update! $day")
            day = !day
        }
    }
}