# Tutorial 3 - JPCompose

**Student:** João Fernandes  
**Student Number:** 47478  
**Course:** Desenvolvimento de Aplicações Móveis (DAM)  
**Deadline:** May 3rd, 2026

---

## Overview

This project is divided into two parts:

1. **Annotation Processor** — a custom Kotlin annotation processor built in IntelliJ IDEA that generates wrapper classes at compile time using KotlinPoet.
2. **Cool Jetpack Weather App** — a rebuild of the WeatherApp from Tutorial 2, now following the **MVVM architecture** and using **Jetpack Compose** for the UI instead of XML layouts.

---

## Part 1 — Annotation Processor

A multi-module Kotlin JVM project implementing a custom `@Greeting` annotation processor.

### Project Structure

```
GreetingProcessorProject/
├── annotations/   # Defines the @Greeting annotation
├── processor/     # Implements the GreetingProcessor using KotlinPoet
└── app/           # Uses the annotation and the generated wrapper classes
```

### How It Works

1. The `@Greeting` annotation is applied to methods in the `app` module.
2. At compile time, `GreetingProcessor` scans for annotated methods and generates a wrapper class for each annotated class.
3. The wrapper class prints the greeting message before delegating the call to the original method.

### Example

```kotlin
class MyClass {
    @Greeting("Hello from MyClass!")
    fun sayHello() {
        println("Executing sayHello method")
    }
}
```

Generated wrapper:
```kotlin
class MyClassWrapper(val original: MyClass) {
    fun sayHello() {
        println("Hello from MyClass!")
        original.sayHello()
    }
}
```

### Dependencies

- `com.google.auto.service:auto-service:1.1.1`
- `com.squareup:kotlinpoet:1.14.2`

---

## Part 2 — Cool Jetpack Weather App

A weather application built with **Jetpack Compose** following the **MVVM architecture**.

### Architecture

```
UI (Compose) → ViewModel → Repository → WeatherApiClient (Ktor)
```

The project is organized into three packages:

```
com.example.cooljetpackweatherapp/
├── data/          # WeatherData.kt, WeatherApiClient.kt
├── viewmodel/     # WeatherViewModel.kt
└── ui/            # WeatherScreen.kt, CoordinatesCard.kt, WeatherCard.kt, WeatherRow.kt
```

### Features

- Real-time weather data from the [Open-Meteo API](https://open-meteo.com/)
- Displays: **Temperature**, **Wind Speed**, **Time**, and **Timezone**
- Weather icon reflecting current conditions using WMO weather codes
- Portrait and Landscape layouts via Jetpack Compose
- Multilingual support: **English** and **Portuguese**
- MVVM with `StateFlow` and `collectAsState` for reactive UI updates

### API

**Provider:** [Open-Meteo](https://open-meteo.com/en/docs) — free, no API key required

```
https://api.open-meteo.com/v1/forecast?latitude={lat}&longitude={lon}&current_weather=true&hourly=temperature_2m,weathercode,pressure_msl,windspeed_10m&timezone=auto
```

### Key Technologies

| Technology | Purpose |
|---|---|
| Jetpack Compose | Declarative UI |
| ViewModel + StateFlow | State management |
| Ktor | HTTP client for API calls |
| Kotlinx Serialization | JSON parsing |
| Material 3 | UI components and theming |

### How to Run

1. Clone the repository
2. Open the project in **Android Studio**
3. Sync Gradle dependencies
4. Run on a **Google Pixel 3** AVD or any compatible device (API 24+)
5. Enter latitude and longitude coordinates and press **Update Weather** to fetch data

### Project Structure

```
app/
├── manifests/
│   └── AndroidManifest.xml
├── kotlin+java/
│   └── com.example.cooljetpackweatherapp/
│       ├── data/
│       │   ├── WeatherApiClient.kt
│       │   └── WeatherData.kt
│       ├── ui/
│       │   ├── theme/
│       │   ├── CoordinatesCard.kt
│       │   ├── WeatherCard.kt
│       │   ├── WeatherRow.kt
│       │   ├── WeatherScreen.kt
│       │   └── WeatherUIState.kt
│       ├── viewmodel/
│       │   └── WeatherViewModel.kt
│       └── MainActivity.kt
└── res/
    ├── drawable/       # Weather icons
    ├── values/         # Strings (EN), colors, themes
    └── values-pt/      # Strings (PT)
```

### Permissions

```xml
<uses-permission android:name="android.permission.INTERNET" />
```
