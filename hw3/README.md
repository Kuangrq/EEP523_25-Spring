# EEP523 Homework 3 - Android Weather App

## Project Overview
This project is an Android weather application developed for the EEP523 course. The app allows users to search for and display real-time weather information for any city using the OpenWeatherMap API. It demonstrates Android development concepts such as API integration, UI/UX design, error handling, and geolocation.

## Working Hours
- Total development time: Approximately 20 hours
- Testing and debugging: Approximately 5 hours
- Documentation and video recording: Approximately 2 hours

## Application Features
1. **City Weather Search**: Users can enter a city name to search for weather information.
2. **Weather Information Display**: Shows current temperature, weather conditions (e.g., cloudy, sunny, rainy), min/max temperature, sunrise/sunset time, wind speed, pressure, and humidity.
3. **Current Location Weather**: By default, displays weather for the device's current location using geolocation.
4. **Error Handling**: Displays error messages for invalid city names, blank input, or network issues (e.g., "Please connect to internet").
5. **Modern UI**: Designed with Material Design principles and Figma prototyping.
6. **Responsive Layout**: Supports different screen sizes and orientations.

## Technical Implementation
- Built with Kotlin and Android Studio
- MVVM architecture with Android Jetpack components
- Integrates OpenWeatherMap API (v2.5) for weather data
- Handles network calls gracefully and parses JSON responses
- Uses model classes for API data
- Updates UI on the main thread
- Implements geolocation to get device's current city
- Error scenarios handled with Toast messages and error screens
- Figma used for UI/UX design prototyping

## How to Run
1. Register and obtain an API key from [OpenWeatherMap](https://home.openweathermap.org/).
2. Add your API key to the app's configuration file.
3. Build and run the app on an Android device or emulator with internet access.
4. Enter a city name and tap the search button to view weather information.
5. On launch, the app will attempt to show weather for your current location (with permission).

## Challenges Faced
1. **API Integration**
   - Challenge: Parsing complex JSON data and handling API key security
   - Solution: Used data models and secure storage for API key
2. **Network Error Handling**
   - Challenge: Managing no internet, invalid input, and API errors
   - Solution: Implemented comprehensive error messages and fallback UI
3. **Geolocation**
   - Challenge: Requesting and handling location permissions
   - Solution: Used Android location APIs with runtime permission checks
4. **UI/UX Design**
   - Challenge: Making the interface intuitive and visually appealing
   - Solution: Followed Material Design and iterated with Figma

## Acknowledgments
- Professor and TAs for their guidance
- OpenWeatherMap for the free API
- Android and Kotlin documentation
- Stack Overflow and developer community
- Material Design guidelines

## References
- [OpenWeatherMap](https://home.openweathermap.org/)
- [API Example](https://openweathermap.org/api/one-call-3#example)
- [API Deprecation Notice](https://forum.arduino.cc/t/openweathermap-api-2-5-to-be-closed-this-june-2024/1250604/8)
- [API Tutorial](https://openweathermap.org/appid)
