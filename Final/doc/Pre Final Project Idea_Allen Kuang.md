# Project Title & Brief Description

**Smart Traffic Assistant**

Smart Traffic Assistant is a mobile application that provides users with real-time traffic information and public transportation updates based on their current location. By integrating geolocation, external APIs (such as Google Maps API and public transit APIs), and device sensors, the app helps users make informed travel decisions quickly and efficiently.

---

## Covered Topics

This project will incorporate at least three topics covered in the course:
1. **GeoLocation** – To obtain the user's current position and display relevant traffic and transit information.
2. **External APIs** – To fetch real-time traffic data and public transportation updates (e.g., Google Maps API, public transit APIs).
3. **Device Sensors ** – To detect user movement (e.g., using the accelerometer) and automatically refresh traffic information.

---

## Project Scope, Features, and APIs

- **Location Detection:** Use the device's location services to get the user's current position.
- **Traffic Information:** Integrate Google Maps API to display a map with real-time traffic overlays.
- **Public Transit Updates:** Use public transportation APIs to show nearby bus/subway stations and estimated arrival times.
- **Auto-Refresh (optional):** Use the accelerometer to detect when the user is moving and automatically refresh traffic and transit data.
- **History:** Maintain a simple history of recently searched routes or stations for quick access.
- **User Feedback:** Provide clear loading indicators, error messages, and refresh options.

**Native APIs:** Android Location API, Sensor API (optional)  
**External APIs:** Google Maps API, public transit APIs (e.g., city-specific open data)

---

## User Interface & User Experience

- **Main Screen:** Map view with real-time traffic information and a search bar for routes or stations.
- **Transit Info Screen:** List of nearby public transit stations and real-time arrival information.
- **History Screen:** List of recently searched routes or stations.
- **Navigation:** Simple bottom navigation bar for switching between main features.
- **Feedback Mechanisms:** Loading spinners, error dialogs, and refresh buttons for a smooth user experience.

*A simple Figma prototype will be provided to illustrate the layout and navigation flow.*

---

## Technical Challenges & Solutions

- **API Key Management:** Ensure secure handling of API keys and use free quota efficiently.
- **External API Integration:** Choose APIs with clear documentation and stable endpoints; handle different data formats robustly.
- **Location Permissions:** Clearly request and explain location permissions to users.
- **Sensor Data Handling:** If using accelerometer, filter noise and avoid unnecessary refreshes.
- **UI/UX Simplicity:** Focus on clear, minimal design to reduce development and design workload.
