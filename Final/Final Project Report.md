# Smart Traffic Assistant

Author: Allen Kuang

# I. Introduction

Smart Traffic Assistant is an Android application developed in Kotlin that provides users with real-time traffic information and nearby public transit stations based on their current location. The app leverages Google Maps, Google Places API, and device sensors to deliver a seamless and intuitive experience for urban commuters and travelers. The main objective is to help users quickly find nearby bus and subway stations, view live traffic conditions, and keep a history of stations they have checked. This project demonstrates the integration of at least three core topics: device sensors, geolocation, and external APIs.

# II. Problem Statement

Urban commuters often struggle to quickly locate nearby public transit stations and get up-to-date traffic information, especially when in unfamiliar areas. Existing apps may be cluttered or lack real-time updates. The target audience for this application includes city residents, travelers, and anyone who relies on public transportation. The app aims to solve the problem of efficiently finding transit options and making informed travel decisions by providing a user-friendly, real-time, and reliable solution.

# III. Design and Implementation

## Architecture & Features
- **Jetpack Compose** is used for a modern, responsive UI.
- **Google Maps Compose** renders the map and traffic overlays.
- **Google Places API** fetches nearby transit stations (bus/subway).
- **Android Location API** obtains the user's current location.
- **Accelerometer Sensor** triggers auto-refresh when movement is detected.
- **Retrofit** is used for robust API integration and error handling.
- **Tabbed Interface** allows switching between Live and History views.
- **Custom Refresh Button** enables manual data updates.
- **History Management** records every station the user clicks, with unlimited entries and one-click clear.

## API Integration
- **Native APIs:** Android Location API, Sensor API (accelerometer)
- **External APIs:** Google Maps API, Google Places API
- The app demonstrates the ability to access native features and communicate with external APIs, as required.

## Error Handling & Comments
- All API/network errors and permission issues are handled with user-friendly feedback (snackbars, dialogs).
- The codebase is well-documented with clear, English comments explaining logic and API usage.

## Challenges & Solutions
- **Location Permission:** Solved by clear prompts and fallback handling.
- **API Quotas/Errors:** Handled with error messages and retry options.
- **UI Overlap/Responsiveness:** Resolved by using Compose layouts and testing on multiple devices.

# IV. Minimum UI Requirements

- Clear and intuitive layout with a top tab bar for navigation (Live/History).
- Consistent and visually appealing design using Material 3, Compose, and icons.
- Informative feedback: loading indicators, error snackbars, permission prompts.
- Responsive design: adapts to different screen sizes and orientations.
- All UI and messages are in English for international usability.

# V. Additional Features & Bonus Points

- **Accelerometer-based auto-refresh:** Automatically updates transit data when user movement is detected (device sensor integration).
- **Unlimited history:** Every station the user clicks is saved in the history tab for easy access, with one-click clear (innovative feature).
- **Custom refresh button:** A floating refresh icon in the bottom left corner for manual updates.
- **All code is well-commented and documented.**
- **Error handling:** All user actions have meaningful feedback (bonus for robust error handling).
- **Potential for further innovation:** The architecture allows easy integration of more external APIs (e.g., local transit APIs, weather, etc.).

# VI. Testing and Evaluation

The app was tested on multiple Android devices and emulators. Testing included:
- Location permission handling and fallback.
- Map interaction and marker display.
- API error handling and network failure scenarios.
- UI responsiveness and usability.
- Sensor-triggered auto-refresh and manual refresh.
Issues such as permission denial, network errors, and UI overlap were identified and resolved by improving error messages, adjusting layouts, and adding user prompts. All features were verified to work as intended.

# VII. Conclusion

Smart Traffic Assistant successfully addresses the need for real-time transit and traffic information in a user-friendly manner. The project provided valuable experience in integrating multiple APIs, handling device sensors, and designing modern Android UIs. All course requirements and bonus features were met. Future improvements could include integrating local transit APIs for line details, adding route planning, supporting more languages, and adding animations for enhanced user experience.

# VIII. Figma

The following image is a screenshot of the UI prototype for Smart Traffic Assistant:

![UI Prototype](../materials/Final_UI%20Design.png)

# IX. Demo Video

https://youtube.com/shorts/RwW1wd5Zqnc?feature=share

# X. References

- [Google Maps Platform Documentation](https://developers.google.com/maps/documentation)
- [Google Places API Documentation](https://developers.google.com/maps/documentation/places/web-service/overview)
- [Android Developers - Location](https://developer.android.com/training/location)
- [Jetpack Compose Documentation](https://developer.android.com/jetpack/compose)

# XI. Appendices

Below are screenshots of the Smart Traffic Assistant app in action:

<p align="center">
  <img src="../interface_1.jpg" alt="Map View" width="45%" style="border:4px solid black; margin-right:10px;"/>
  <img src="../interface_2.jpg" alt="History View" width="45%" style="border:4px solid black;"/>
</p>

- Left: Map view with real-time traffic and transit stations
- Right: History view showing clicked stations 