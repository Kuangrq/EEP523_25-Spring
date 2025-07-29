# ListApp - Simple Task Tracker

A simple Android application that demonstrates basic task tracking functionality with time selection capabilities.

## Features

- Editable task text field with default text "Task: Buy groceries"
- Time picker functionality to select and display time
- Task completion toggling with strike-through effect
- Material Design UI elements

## Technical Details

### Project Structure
```
app/
├── src/
│   ├── main/
│   │   ├── java/com/example/listapp/
│   │   │   └── MainActivity.kt
│   │   ├── res/
│   │   │   ├── layout/
│   │   │   │   └── activity_main.xml
│   │   │   ├── values/
│   │   │   │   ├── colors.xml
│   │   │   │   └── themes.xml
│   │   │   └── ...
│   │   └── AndroidManifest.xml
│   └── ...
└── build.gradle.kts
```


## Usage

1. **Task Management**
   - The app opens with a default task "Task: Buy groceries"
   - Tap the text field to edit the task
   - Tap the task to toggle strike-through (completion status)

2. **Time Selection**
   - Tap the "Pick Time" button to open the time picker
   - Select your desired time
   - The selected time will be displayed below the button