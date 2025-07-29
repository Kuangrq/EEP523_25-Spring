# Android Todo List Application

## Project Overview
This is a Todo List application developed for EEP523 HW1. The application allows users to manage their tasks with features like adding, completing, and deleting tasks, as well as setting deadlines for each task.

## Features
1. **Task Management**
   - Add new tasks with title and optional description
   - Mark tasks as complete/incomplete
   - Delete tasks
   - Edit tasks
   - Display total number of tasks
   
2. **Deadline Management**
   - Set deadline time for tasks
   - Display deadline in 24-hour format
   - Visual indication of task deadlines

3. **User Interface**
   - Clean and intuitive Material Design interface
   - Visual feedback for task completion
   - Easy-to-use task creation form
   - Responsive layout that works on different screen sizes

## Technical Implementation
- Built using Kotlin for Android
- Implements MVVM (Model-View-ViewModel) architecture
- Uses Android Jetpack components:
  - ViewModel for managing UI data
  - LiveData for reactive data handling
  - RecyclerView for efficient list display
- Material Design components for modern UI elements

## Hours Spent
- Project Planning and Setup: 2 hours
- UI Design and Implementation: 3 hours
- Core Functionality Development: 4 hours
- Testing and Debugging: 2 hours
- Documentation: 1 hour
Total: 12 hours

## Challenges Faced and Solutions
1. **Task State Management**
   - Challenge: Maintaining consistent task states across different views
   - Solution: Implemented ViewModel with LiveData to ensure data consistency

2. **Deadline Implementation**
   - Challenge: Handling time selection and display in a user-friendly way
   - Solution: Used TimePickerDialog and formatted time display using DateTimeFormatter

3. **UI/UX Design**
   - Challenge: Creating an intuitive and responsive interface
   - Solution: Utilized Material Design components and guidelines for a modern, user-friendly experience

4. **Data Persistence**
   - Challenge: Managing task data during app lifecycle
   - Solution: Used ViewModel to retain data during configuration changes

## Acknowledgments
- Android Developer Documentation
- Material Design Guidelines
- Stack Overflow Community
- Course Materials and Lectures 