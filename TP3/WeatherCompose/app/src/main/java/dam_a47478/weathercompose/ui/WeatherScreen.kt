package dam_a47478.weathercompose.ui

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dam_a47478.weathercompose.viewmodel.WeatherViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import dam_a47478.weathercompose.R
import dam_a47478.weathercompose.data.WMO_WeatherCode
import dam_a47478.weathercompose.data.getWeatherCodeMap

@Preview(showBackground = true)
@Composable
fun WeatherUI(weatherViewModel: WeatherViewModel = viewModel()) {
    val weatherUIState by weatherViewModel.uiState.collectAsState()
    val latitude = weatherUIState.latitude
    val longitude = weatherUIState.longitude
    val temperature = weatherUIState.temperature
    val windSpeed = weatherUIState.windspeed
    val windDirection = weatherUIState.winddirection
    val weathercode = weatherUIState.weathercode
    val seaLevelPressure = weatherUIState.seaLevelPressure
    val time = weatherUIState.time
    val timezone = weatherUIState.timezone
    val configuration = LocalConfiguration.current
    val day = true // Must change this in the future
    val mapt = getWeatherCodeMap()
    val wCode = mapt.get(weathercode)
    val wImage = when (wCode) {
        WMO_WeatherCode.CLEAR_SKY,
        WMO_WeatherCode.MAINLY_CLEAR,
        WMO_WeatherCode.PARTLY_CLOUDY -> if (day) wCode?.image + "day"
        else wCode?.image + "night"

        else -> wCode?.image
    }
    val context = LocalContext.current
    val wIcon = context.resources.getIdentifier(
        wImage, "drawable",
        context.packageName
    )
    if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
        LandscapeWeatherUI(
            wIcon,
            latitude,
            longitude,
            temperature,
            windSpeed,
            windDirection,
            weathercode,
            seaLevelPressure,
            time,
            timezone,
            onLatitudeChange = { newValue ->
                weatherViewModel.updateLatitude(newValue)
            },
            onLongitudeChange = { newValue ->
                weatherViewModel.updateLongitude(newValue)
            },
            onUpdateButtonClick = {
                weatherViewModel.fetchWeather()
            }
        )
    } else {
        PortraitWeatherUI(
            wIcon,
            latitude,
            longitude,
            temperature,
            windSpeed,
            windDirection,
            weathercode,
            seaLevelPressure,
            time,
            timezone,
            onLatitudeChange = { newValue ->
                weatherViewModel.updateLatitude(newValue)
            },
            onLongitudeChange = { newValue ->
                weatherViewModel.updateLongitude(newValue)
            },
            onUpdateButtonClick = {
                weatherViewModel.fetchWeather()
            }
        )
    }
}

@Composable
fun PortraitWeatherUI(
    wIcon: Int,
    latitude: String,
    longitude: String,
    temperature: Float,
    windSpeed: Float,
    windDirection: Int,
    weathercode: Int,
    seaLevelPressure: Float,
    time: String,
    timezone: String,
    onLatitudeChange: (String) -> Unit,
    onLongitudeChange: (String) -> Unit,
    onUpdateButtonClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Image(
                painter = painterResource(id = wIcon),
                contentDescription = "Weather Icon",
                modifier = Modifier
                    .size(150.dp)
                    .padding(16.dp),
            )
        }

        CoordinateBox(
            modifier = Modifier.fillMaxWidth(),
            latitude,
            longitude,
            onLatitudeChange,
            onLongitudeChange
        )

        Spacer(modifier = Modifier.height(16.dp))

        InfoBox(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            rowMod = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 8.dp),
            temp = temperature.toString(),
            wind = windSpeed.toString(),
            time = time,
            timezone = timezone
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 4. Button (Sits at the bottom because InfoBox pushed it there)
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = onUpdateButtonClick
        ) {
            Text(stringResource(R.string.update_weather))
        }
    }
}

@Composable
fun LandscapeWeatherUI(
    wIcon: Int,
    latitude: String,
    longitude: String,
    temperature: Float,
    windSpeed: Float,
    windDirection: Int,
    weathercode: Int,
    seaLevelPressure: Float,
    time: String,
    timezone: String,
    onLatitudeChange: (String) -> Unit,
    onLongitudeChange: (String) -> Unit,
    onUpdateButtonClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            if (wIcon != 0) {
                Image(
                    painter = painterResource(id = wIcon),
                    contentDescription = "Weather Icon",
                    modifier = Modifier
                        .size(120.dp)
                        .weight(.2f)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = onUpdateButtonClick
            ) {
                Text(stringResource(R.string.update_weather))
            }
        }

        CoordinateBox(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
            latitude = latitude,
            longitude = longitude,
            onLatitudeChange = onLatitudeChange,
            onLongitudeChange = onLongitudeChange
        )

        InfoBox(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
            rowMod = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 4.dp),
            temp = temperature.toString(),
            wind = windSpeed.toString(),
            time = time,
            timezone = timezone
        )
    }
}

@Composable
fun CoordinateBox(
    modifier: Modifier,
    latitude: String,
    longitude: String,
    onLatitudeChange: (String) -> Unit,
    onLongitudeChange: (String) -> Unit
) {
    Card(modifier = modifier) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Coordinates",
                style = typography.titleLarge
            )
            OutlinedTextField(
                value = latitude.toString(),
                onValueChange = onLatitudeChange,
                label = {
                    Text(
                        stringResource(R.string.latitude)
                    )
                },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = longitude.toString(),
                onValueChange = onLongitudeChange,
                label = {
                    Text(
                        stringResource(R.string.longitude)
                    )
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun InfoBox(
    modifier: Modifier,
    rowMod: Modifier,
    temp: String,
    wind: String,
    time: String,
    timezone: String
) {
    Card(modifier) {
        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            InfoRow(rowMod, infoType = stringResource(R.string.temperature), temp)
            InfoRow(rowMod, infoType = stringResource(R.string.wind_speed), wind)
            InfoRow(rowMod, infoType = stringResource(R.string.time), time)
            InfoRow(rowMod, infoType = stringResource(R.string.timezone), timezone)
        }
    }
}

@Composable
fun InfoRow(modifier: Modifier, infoType: String, info: String) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(infoType)
        Text(info)
    }
}