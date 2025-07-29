# EEP 523: Mobile Applications for Sensing and Control 

## Course Overview

This repository contains the complete coursework for the **Mobile Application Development** course (EEP523), which focuses on Android app development using Kotlin. The course covers fundamental concepts of mobile application development, including UI design, API integration, device sensors, location services, and modern Android development practices.

## Course Structure

The course is organized into weekly lectures and hands-on projects that progressively build Android development skills:

### Course Materials
- **Week 1-8**: Comprehensive lecture slides covering Android fundamentals to advanced topics
  - Week 1: Introduction to Android Development and Kotlin
  - Week 2: Android UI Fundamentals and Layouts
  - Week 3: Activities, Fragments, and Navigation
  - Week 4: User Input and Event Handling
  - Week 5: Data Storage and Persistence
  - Week 6: Networking and API Integration
  - Week 7: Device Sensors and Hardware Integration
  - Week 8: Advanced Topics and Best Practices
- **Hands-on Projects**: Practical implementation of concepts learned in lectures
- **Final Project**: A comprehensive Android application demonstrating all learned skills

## Projects Overview

### In-Class Tutorial Exercises (ICTE)
*Hands-on practice sessions during lectures*

#### 1. ICTE2 - Hello Button App
**Skills Demonstrated:**
- Basic Android UI components (Button, TextView)
- Event handling and click listeners
- Simple user interaction

**Key Features:**
- Interactive button that changes text when pressed
- Dynamic text updates based on user actions
- Basic Android activity lifecycle

#### 2. ICTE4 - ListApp Task Tracker
**Skills Demonstrated:**
- Text input and editing
- Time picker functionality
- Task completion toggling
- Material Design components

**Key Features:**
- Editable task text field with default content
- Time picker for task scheduling
- Task completion with strike-through effect
- Material Design UI elements

#### 3. ICTE5 - Drawing App
**Skills Demonstrated:**

- Custom view implementation
- Canvas drawing and graphics
- Color selection and brush controls
- Touch event handling

**Key Features:**
- Interactive drawing canvas
- Color selection buttons (black, red, blue)
- Clear canvas functionality
- Custom DrawingView implementation

#### 4. ICTE6 - Navigation and Drawing App
**Skills Demonstrated:**
- Bottom navigation implementation
- Fragment-based UI architecture
- Custom drawing functionality
- Settings management

**Key Features:**
- Multi-tab interface with Home, Drawing, and Settings fragments
- Interactive drawing canvas
- Navigation between different app sections

#### 5. ICTE7 - Bluetooth Search App
**Skills Demonstrated:**
- Bluetooth API integration
- Device discovery and scanning
- Permission handling for Bluetooth
- Broadcast receivers for device detection

**Key Features:**
- Bluetooth device discovery
- Real-time device list updates
- Permission management for Bluetooth access
- Device information display (name and address)

#### 6. ICTE8 - Google Maps Integration
**Skills Demonstrated:**
- Google Maps API integration
- Map markers and overlays
- Location-based features
- Camera controls and map customization

**Key Features:**
- Interactive map with custom markers
- Hidden gems discovery feature
- Seattle-based location showcase
- Map camera controls

#### 7. ICTE9 - Smart News Fetcher
**Skills Demonstrated:**
- Network connectivity management
- Shared preferences for user settings
- RecyclerView implementation
- Background thread processing
- User preference management

**Key Features:**
- News data fetching with network restrictions
- User preference for cellular data usage
- RecyclerView for news display
- Network type detection (WiFi vs Cellular)
- Settings persistence with SharedPreferences

### Homework Assignments (HW)
*Comprehensive projects demonstrating advanced concepts*

#### 1. HW1 - Todo List Application
**Skills Demonstrated:**
- MVVM architecture implementation
- RecyclerView for list management
- LiveData for reactive data handling
- Material Design components
- Task state management

**Key Features:**
- Add, edit, complete, and delete tasks
- Deadline management with time picker
- Task completion tracking
- Material Design interface
- ViewModel for data persistence

#### 2. HW2 - SwapSense Multi-Feature App
**Skills Demonstrated:**
- Advanced navigation components with bottom navigation
- Camera integration using CameraX API
- Multiple device sensors (magnetic, proximity, gyroscope)
- Custom drawing functionality with image processing
- Permission handling for camera and sensors

**Key Features:**
- Camera functionality with image capture and lens switching
- Custom drawing canvas with image overlay and editing
- Real-time sensor data monitoring (magnetic field, proximity, gyroscope)
- Bottom navigation with three main sections (Camera, Draw, Sensors)
- Image processing with EXIF data handling and rotation correction

#### 3. HW3 - Weather Application
**Skills Demonstrated:**
- REST API integration (OpenWeatherMap)
- Location services and GPS integration
- Network connectivity handling and error management
- Async operations with Thread and JSON parsing using Gson
- Permission handling for location access
- Input validation and user feedback

**Key Features:**
- Real-time weather data retrieval from OpenWeatherMap API
- Current location weather detection using GPS
- City search functionality with input validation
- Comprehensive weather information display (temperature, humidity, wind, pressure, sunrise/sunset)
- Network error handling with user-friendly messages
- Background thread processing for API calls
- Keyboard management and UI responsiveness

### Final Project - Smart Traffic Assistant
**Skills Demonstrated:**
- Advanced API integration (Google Maps, Google Places)
- Device sensor utilization (accelerometer)
- Real-time data processing
- Modern UI with Jetpack Compose
- Location-based services

**Key Features:**
- Real-time traffic information
- Nearby transit station discovery
- Accelerometer-based auto-refresh
- History management system
- Interactive map with traffic overlays
- Tabbed interface (Live/History views)

## Technologies & Tools Used

### Core Technologies
- **Kotlin**: Primary programming language
- **Android SDK**: Native Android development
- **Jetpack Compose**: Modern UI toolkit (Final Project)
- **XML Layouts**: Traditional Android UI design
- **CameraX**: Modern camera API for image capture
- **Gson**: JSON parsing and serialization
- **Retrofit**: HTTP client for API integration (Final Project)
- **MVVM Architecture**: Model-View-ViewModel pattern
- **LiveData**: Reactive data handling
- **RecyclerView**: Efficient list display
- **SharedPreferences**: Local data persistence

### APIs & Services
- **Google Maps API**: Map integration and location services
- **Google Places API**: Location-based data retrieval
- **OpenWeatherMap API**: Weather data integration
- **Android Location API**: Device location services
- **Android Sensor API**: Device sensors (magnetic, proximity, gyroscope, accelerometer)
- **Camera API**: Device camera integration
- **Bluetooth API**: Device discovery and connectivity
- **Network Connectivity API**: Network type detection and management

### Development Tools
- **Android Studio**: Primary IDE
- **Gradle**: Build system
- **Git**: Version control
- **Figma**: UI/UX design prototyping

## Key Learning Outcomes

### Technical Skills
1. **Android Development Fundamentals**
   - Activity and Fragment lifecycle management
   - UI design with XML layouts and Jetpack Compose
   - Navigation components and patterns

2. **API Integration**
   - REST API consumption with Retrofit and HttpURLConnection
   - JSON parsing and data handling with Gson
   - Error handling and network connectivity
   - Background thread processing for network operations

3. **Device Features**
   - Camera integration and image processing with CameraX
   - Sensor data utilization (magnetic, proximity, gyroscope, accelerometer, location)
   - Permission handling and user privacy
   - Image processing with EXIF data handling

4. **Modern Android Development**
   - Jetpack Compose for declarative UI
   - Material Design principles
   - Responsive and adaptive layouts

### Project Management
- **Version Control**: Git workflow and collaboration
- **Documentation**: Comprehensive project documentation
- **Testing**: Multi-device testing and debugging
- **User Experience**: Intuitive UI/UX design

## Repository Structure

```
EEP523_25Spring/
├── slides/                 # Course lecture materials (Week 1-8)
├── ICTE2/                  # Hello Button App
├── ICTE4/                  # ListApp Task Tracker
├── ICTE5/                  # Drawing App
├── ICTE6/                  # Navigation and Drawing App
├── ICTE7/                  # Bluetooth Search App
├── ICTE8/                  # Google Maps Integration
├── ICTE9/                  # Smart News Fetcher
├── hw1/                    # Todo List Application
├── hw2/                    # SwapSense Multi-Feature App
├── hw3/                    # Weather Application
├── Final/                  # Smart Traffic Assistant (Final Project)
└── README.md              # This file
```
