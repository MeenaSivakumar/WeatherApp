# WeatherApp
WeatherApp is a simple Android application that allows users to get real-time weather information based on their location or a specific city.
Features
Displays current temperature, weather condition, and city name.
Provides hourly weather forecast for the current day.
Dynamic background and weather condition icons based on the current weather.
Uses WeatherAPI to fetch weather data.
Installation
To run the WeatherApp on your local machine, follow these steps:
Clone the repository:
bash
Copy code
git clone https://github.com/your_username/WeatherApp.git
Open the project in Android Studio.
Build and run the project on an Android device or emulator.
Usage
Upon launching the app, it automatically fetches weather data based on the user's location (if permitted).
Alternatively, users can manually enter a city name in the search bar to get weather information for that city.
Scroll through the hourly weather forecast in the RecyclerView below.
Permissions
The following permissions are required to run the app:
ACCESS_FINE_LOCATION: Used to retrieve the user's current location for weather data.
ACCESS_COARSE_LOCATION: Used as an alternative for location retrieval.
Technologies Used
Android SDK
Java
Volley Library for network requests
Picasso Library for image loading
API Reference
The app uses the WeatherAPI to fetch weather data.
Acknowledgements
Thanks to WeatherAPI for providing weather data.
Icons made by Freepik from www.flaticon.com.
