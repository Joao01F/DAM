package dam_a47478.weathercompose.data

import kotlinx.serialization.Serializable

@kotlinx.serialization.InternalSerializationApi
@Serializable
data class WeatherData(
    val latitude: Float,
    val longitude: Float,
    val generationtime_ms: Double,
    val utc_offset_seconds: Int,
    val timezone: String,
    val timezone_abbreviation: String,
    val elevation: Float,
    val current_weather: CurrentWeather? = null,
    val hourly: HourlyData? = null
)

@kotlinx.serialization.InternalSerializationApi
@Serializable
data class CurrentWeather(
    val time: String,
    val interval: Int,
    val temperature: Float,
    val windspeed: Float,
    val winddirection: Int,
    val is_day: Int,
    val weathercode: Int
)

@kotlinx.serialization.InternalSerializationApi
@Serializable
data class HourlyData(
    val time: List<String>,
    val temperature_2m: List<Float>,
    val weathercode: List<Int>,
    val pressure_msl: List<Float>,
    val windspeed_10m: List<Float>
)

enum class WMO_WeatherCode(var code: Int, var image: String) {
    CLEAR_SKY(0, "clear_"),
    MAINLY_CLEAR(1, "mostly_clear_"),
    PARTLY_CLOUDY(2, "partly_cloudy_"),
    OVERCAST(3, "cloudy"),
    FOG(45, "fog"),
    DEPOSITING_RIME_FOG(48, "fog"),
    DRIZZLE_LIGHT(51, "drizzle"),
    DRIZZLE_MODERATE(53, "drizzle"),
    DRIZZLE_DENSE(55, "drizzle"),
    FREEZING_DRIZZLE_LIGHT(56, "freezing_drizzle"),
    FREEZING_DRIZZLE_DENSE(57, "freezing_drizzle"),
    RAIN_SLIGHT(61, "rain_light"),
    RAIN_MODERATE(63, "rain"),
    RAIN_HEAVY(65, "rain_heavy"),
    FREEZING_RAIN_LIGHT(66, "freezing_rain_light"),
    FREEZING_RAIN_HEAVY(67, "freezing_rain_heavy"),
    SNOW_FALL_SLIGHT(71, "snow_light"),
    SNOW_FALL_MODERATE(73, "snow"),
    SNOW_FALL_HEAVY(75, "snow_heavy"),
    SNOW_GRAINS(77, "snow"),
    RAIN_SHOWERS_SLIGHT(80, "rain_light"),
    RAIN_SHOWERS_MODERATE(81, "rain"),
    RAIN_SHOWERS_VIOLENT(82, "rain_heavy"),
    SNOW_SHOWERS_SLIGHT(85, "snow_light"),
    SNOW_SHOWERS_HEAVY(86, "snow_heavy"),
    THUNDERSTORM_SLIGHT_MODERATE(95, "tstorm"),
    THUNDERSTORM_HAIL_SLIGHT(96, "tstorm"),
    THUNDERSTORM_HAIL_HEAVY(99, "tstorm")
}

fun getWeatherCodeMap(): Map<Int, WMO_WeatherCode> {
    val weatherMap = HashMap<Int, WMO_WeatherCode>()
    WMO_WeatherCode.entries.forEach {
        weatherMap[it.code] = it
    }
    return weatherMap
}