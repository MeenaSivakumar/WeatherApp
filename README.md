# WeatherApp
WeatherApp is a simple Android application that allows users to get real-time weather information based on their location or a specific city.

Features:

1.Displays current temperature, weather condition, and city name.

2.Provides hourly weather forecast for the current day.

3.Dynamic background and weather condition icons based on the current weather.

4.Uses WeatherAPI to fetch weather data.

Installation
1.To run the WeatherApp on your local machine, follow these steps:

   Clone the repository:
   
   git clone https://github.com/MeenaSivakumar/WeatherApp.git
   
2.Open the project in Android Studio.

3.Build and run the project on an Android device or emulator.

Usage

1.Upon launching the app, it automatically fetches weather data based on the user's location (if permitted).

2.Alternatively, users can manually enter a city name in the search bar to get weather information for that city.

3.Scroll through the hourly weather forecast in the RecyclerView below.

Permissions

The following permissions are required to run the app:

ACCESS_FINE_LOCATION: Used to retrieve the user's current location for weather data.

ACCESS_COARSE_LOCATION: Used as an alternative for location retrieval.

Technologies Used

1.Android SDK

2.Java

3.Volley Library for network requests

4.Picasso Library for image loading

API Reference

The app uses the WeatherAPI to fetch weather data.

Acknowledgements

1.Thanks to WeatherAPI for providing weather data.

2.Icons made by Freepik from www.flaticon.com.
