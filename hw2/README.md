# EEP523 HW2: Multi-Function Android App

## Project Overview
This project is the submission for EEP523 Homework 2. It is a multi-function Android application featuring a bottom navigation bar with three main modules: Sensor display, Image Drawing, and Camera. All features are implemented using native Android development, and the UI design follows the provided Figma prototype.

## Features

### 1. Bottom Navigation Bar
- Implements a bottom navigation bar with three tabs: "Sensors", "Draw", and "Camera".
- Users can seamlessly switch between tabs.

### 2. Sensors Tab
- Supports three hardware sensors (e.g., Magnetometer, Accelerometer, Light Sensor, etc.).
- Requests necessary permissions; displays a Toast message if permissions are denied or the sensor is not available.
- Real-time display of sensor data.
- UI design follows the Figma prototype.

### 3. Draw Tab
- Users can select an image from the gallery and draw over it.
- Supports switching brush colors, resetting to the original image, and saving the edited image back to the gallery.
- Simple and intuitive drawing interface.

### 4. Camera Tab
- Supports both front and back cameras for taking photos.
- Allows switching between cameras.
- Captured photos are saved to the device gallery and can be previewed immediately.
- Handles camera and storage permissions with appropriate error messages.

## Design Reference
- The UI design is based on the provided Figma prototype (`FigmaDesign.png`).

## Development Notes

- **Development Time:** Approximately 15 hours
- **Challenges Faced:**
  - Sensor integration: The main difficulty was in accessing and managing different hardware sensors, including handling cases where sensors were missing or failed to provide data.
  - Permission handling: Special care was taken to support Android 10+ permission models.
  - Saving and refreshing images in the gallery: Implemented media library refresh to ensure images appear promptly.

## File Structure

```
hw2/
├── app/
│   ├── src/
│   ├── build.gradle.kts
│   └── ...
├── FigmaDesign.png
├── README.md
```

## Acknowledgements
Thanks to the course staff for their guidance and resources.
