package dam_A47478.coolweatherapp

import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import java.io.InputStreamReader
import java.net.URL

class MainActivity : AppCompatActivity() {
    private var day = false

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
        val lat: EditText = findViewById(R.id.latitude_text)
        val long: EditText = findViewById(R.id.longitude_text)

        button.setOnClickListener {
            fetchWeatherData(lat.text.toString().toFloat(), long.text.toString().toFloat()).start()
            println("Update! $day")
            day = !day
        }
    }

    private fun fetchWeatherData(lat: Float, long: Float) : Thread {
        return Thread {
            val weather = WeatherAPI_Call(lat, long)
            updateUI(weather)
        }
    }

    private fun updateUI(request: WeatherData) {
        println("UpdateUI")
        runOnUiThread {
            val weatherImage : ImageView = findViewById(R.id.weatherIcon)

            val windLabel : TextView = findViewById(R.id.windspeed_label)
            val wind : TextView = findViewById(R.id.windspeed_text)
            val windUnit : String = request.current_units.wind_speed_10m

            val tempLabel : TextView = findViewById(R.id.temperature_label)
            val temp : TextView = findViewById(R.id.temperature_text)
            val tempUnit : String = request.current_units.temperature_2m

            windLabel.visibility = View.VISIBLE
            tempLabel.visibility = View.VISIBLE
            wind.text = "${request.current.wind_speed_10m.toString()} $windUnit"
            temp.text = "${request.current.temperature_2m.toString()} $tempUnit"

            val mapt = getWeatherCodeMap();
            val wCode = mapt.get(request.current.weather_code)
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
        // https://api.open-meteo.com/v1/forecast?latitude=38.7167&longitude=-9.1333&current=temperature_2m,weather_code,wind_speed_10m&timezone=auto&forecast_days=3
        val reqString = buildString {
            append("https://api.open-meteo.com/v1/forecast?")
            append("latitude=${lat}&longitude=${long}&")
            append("current=temperature_2m,weather_code,wind_speed_10m&")
            append("timezone=auto&forecast_days=3")
        }
        val str = reqString.toString()
        val url = URL(reqString.toString());
        url.openStream().use {
            val request = Gson().fromJson(InputStreamReader(it,"UTF-8"),WeatherData::class.java)
            return request
        }
    }
}