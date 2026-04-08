package dam_A47478.coolweatherapp

import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AdapterView
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import java.io.InputStreamReader
import java.net.URL

class MainActivity : AppCompatActivity() {
    private val day = false

    override fun onCreate(savedInstanceState: Bundle?) {

        val themeId = when (resources.configuration.orientation) {
            Configuration.ORIENTATION_LANDSCAPE -> R.style.Theme_CoolWeatherApp_Land
            else -> R.style.Theme_CoolWeatherApp
        }
        setTheme(themeId)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button: Button = findViewById(R.id.update)
        val lat: EditText = findViewById(R.id.latitude_text)
        val long: EditText = findViewById(R.id.longitude_text)

        button.setOnClickListener {
            fetchWeatherData(lat.text.toString().toFloat(), long.text.toString().toFloat()).start()
        }
    }

    private fun fetchWeatherData(lat: Float, long: Float): Thread {
        return Thread {
            val weather = WeatherAPI_Call(lat, long)
            updateUI(weather)
        }
    }

    private fun updateUI(request: WeatherData) {
        println("UpdateUI")
        runOnUiThread {
            val weatherImage: ImageView = findViewById(R.id.weatherIcon)

            // Labels
            val mintempLabel: TextView = findViewById(R.id.min_temp_label)
            val maxtempLabel: TextView = findViewById(R.id.max_temp_label)
            val precipLabel: TextView = findViewById(R.id.prec_prob_label)
            val windLabel: TextView = findViewById(R.id.wind_speed_label)

            // Text fields
            val min_temp: TextView = findViewById(R.id.min_temp_text)
            val max_temp: TextView = findViewById(R.id.max_temp_text)
            val precip: TextView = findViewById(R.id.prec_prob_text)
            val wind: TextView = findViewById(R.id.wind_speed_text)

            // Units
            val tempUnit: String = request.daily_units.temperature_2m_min
            val precipUnit: String = request.daily_units.precipitation_probability_max
            val windUnit: String = request.daily_units.wind_speed_10m_max

            mintempLabel.text = getString(R.string.min_temperature, tempUnit)
            maxtempLabel.text = getString(R.string.max_temperature, tempUnit)
            mintempLabel.visibility = View.VISIBLE
            maxtempLabel.visibility = View.VISIBLE
            precipLabel.visibility = View.VISIBLE
            windLabel.visibility = View.VISIBLE

            min_temp.text = "${request.daily.temperature_2m_min.get(0).toString()} $tempUnit"
            max_temp.text = "${request.daily.temperature_2m_max.get(0).toString()} $tempUnit"
            wind.text = "${request.daily.wind_speed_10m_max.get(0).toString()} $windUnit"
            precip.text =
                "${request.daily.precipitation_probability_max.get(0).toString()} $precipUnit"

            val mapt = getWeatherCodeMap();
            val wCode = mapt.get(request.daily.weather_code.get(0))
            val wImage = when (wCode) {
                WMO_WeatherCode.CLEAR_SKY,
                WMO_WeatherCode.MAINLY_CLEAR,
                WMO_WeatherCode.PARTLY_CLOUDY -> if (day) wCode?.image + "day" else
                    wCode?.image + "night"

                else -> wCode?.image
            }
            val res = getResources()
            weatherImage.setImageResource(R.drawable.fog)
            val resID = res.getIdentifier(wImage, "drawable", getPackageName());
            val drawable = this.getDrawable(resID);
            weatherImage.setImageDrawable(drawable);
        }
    }

    private fun WeatherAPI_Call(lat: Float, long: Float): WeatherData {
        // https://api.open-meteo.com/v1/forecast?latitude=38.7167&longitude=-9.1333&daily=precipitation_probability_max,temperature_2m_min,temperature_2m_max,weather_code,wind_speed_10m_max&timezone=auto&forecast_days=3
        val reqString = buildString {
            append("https://api.open-meteo.com/v1/forecast?")
            append("latitude=${lat}&longitude=${long}&")
            append("daily=precipitation_probability_max,temperature_2m_min,temperature_2m_max,weather_code,wind_speed_10m_max&")
            append("timezone=auto&forecast_days=3")
        }
        val str = reqString.toString()
        val url = URL(reqString.toString());
        url.openStream().use {
            val request = Gson().fromJson(InputStreamReader(it, "UTF-8"), WeatherData::class.java)
            return request
        }
    }
}