# NWS Weather (Android)

A modern Android weather app built on official National Weather Service data, with support for both address search and real-time device location.

This project focuses on clean architecture, reliable public data sources, and a simple user experience without unnecessary complexity.

---

## Overview

NWS Weather pulls forecast data directly from weather.gov and uses U.S. Census geocoding to translate addresses into usable coordinates. It supports both manual address search and device-based location, making it flexible for different use cases.

The app is designed to be a clean, maintainable starting point for anyone building an Android weather application.

---

## Features

- Search U.S. street addresses using the Census geocoder  
- Retrieve current location using Google Play Services  
- Display forecast data from the National Weather Service API  
- Save and manage favorite locations locally using Room  
- Built with a modern Material 3 UI using Jetpack Compose  

---

## Project Structure

The project is organized to separate responsibilities clearly and keep things maintainable:

- `data/`  
  Handles API communication, data models, Room database, and repository logic  

- `location/`  
  Provides a wrapper around Google Play Services location APIs via `DeviceLocationClient`  

- `presentation/`  
  Manages UI state and user actions through `WeatherViewModel`  

- `ui/`  
  Contains Compose screens, reusable UI components, and theming  

- `di/`  
  Lightweight dependency container without introducing a full DI framework  

---

## Getting Started

### Prerequisites

- Android Studio (latest recommended)
- Android device or emulator

### Setup

```bash
git clone https://github.com/fa1sepr0phet/NWS-Weather.git
cd nws-weather-starter
