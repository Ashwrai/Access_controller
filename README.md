# Door Access Control System

This repository contains the source code and documentation for a Door Access Control System project. The system aims to manage access to various areas within a building by controlling the opening, closing, locking, and unlocking of doors based on user permissions and schedules.

## Project Overview

### Milestones

The project is divided into multiple milestones, each addressing specific development objectives:

- **First Milestone**: Defines system requirements, designs architecture, and implements core functionalities such as employee schedules, access permissions, and temporary user groups.

- **Second Milestone**: Focuses on code quality improvements, including enhanced comments, adherence to coding style guidelines, implementation of logging, and application of design patterns like Singleton, Observer, and Strategy.

- **Third Milestone**: Involves building a user interface for a mobile app to interact with the system, including navigation through the hierarchy of areas, displaying door and area status, and sending requests to the Access Control Unit (ACU).

## Technical Details
- **Languages**:
  - Java
  - Dart (for Flutter mobile app development)
- **Libraries**:
  - java.util.time: Used for date and time representation.
  - logback: Used for logging functionality.
- **Other tools**:
  - JSON: Utilized for data interchange between system components.
  - PlantUML: Utilized for creating UML diagrams to visualize system architecture.

### Design Patterns

- **Singleton**: Applied to certain classes to ensure proper instantiation and management.
- **Observer**: Utilized for implementing a clock that notifies doors about the current time.
- **State**: Employed for implementing various door states and transitions.
- **Composite**: Utilized for representing hierarchical structures of areas within the building.
- **Visitor**: Employed for traversing and performing operations on hierarchical area structures.
