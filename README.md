#  NWS Weather

> A clean, ad-free, open-source weather app powered by the National Weather Service  
> No tracking. No subscriptions. No nonsense.

---

##  About

NWS Weather is a simple Android weather application built using official data from the National Weather Service (NWS).

The goal is straightforward:

> Provide accurate weather data in a clean, fast interface without ads, tracking, or monetization.

---

##  Philosophy

Weather is public information.

Many modern weather platforms rely on ads, subscriptions, and user tracking. This project is a small step in the opposite direction—keeping weather simple, transparent, and accessible.

- No ads  
- No analytics or tracking  
- No subscriptions  
- No paywalls  
- No data harvesting  

**Just weather.**

## Philosophy on Usage

This project is released under the GPLv3 License to encourage openness and collaboration.

However, the intent of this project is to keep weather information free, accessible, and free from monetization.

If you use this code, please respect that spirit:
- avoid adding ads or tracking
- avoid restricting access behind paywalls

This is not a legal requirement, but a request to preserve the purpose of the project.

---

##  Features

-  GPS-based weather lookup  
-  Address search using Android Geocoder  
-  Save favorite locations locally  
-  Forecast data directly from the National Weather Service  
-  Dynamic atmospheric UI based on current conditions  
-  Clean, modern Material 3 design  
-  Detailed forecast breakdowns  
-  Home screen widget support  

---

##  Privacy

This app is designed with privacy as a core principle.

- No analytics  
- No tracking  
- No advertising SDKs  
- No data collection beyond what is required for functionality  

### Location Usage

- Location is used only to retrieve weather data  
- Location data is never stored remotely  
- Location access is optional and user-controlled  

### Network Requests

- Weather data is retrieved from the National Weather Service API  
- Address lookup uses Android's built-in Geocoder  

No user data is sent to third-party tracking or analytics services.

---

##  Tech Stack

- Kotlin  
- Jetpack Compose  
- Material 3  
- Android Architecture Components (ViewModel)  
- Room (local storage)  
- WorkManager (background updates)  
- Retrofit + OkHttp  
- Glance (widgets)  

---

##  Building the App

### Requirements

- Android Studio (latest stable)
- Android SDK

### Steps

```bash
git clone https://github.com/fa1sepr0phet/NWS-Weather.git
cd NWS-Weather
