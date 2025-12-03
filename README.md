# DisasterResponseSystem_OOP


DisasterRescue: Integrated Disaster Response and Management System
Overview / Description
DisasterRescue is a Java console-based application designed to simulate an integrated disaster management and response system.
 The program centralizes multiple modules that support emergency operations, including:
Supply and relief request management


Emergency contact directory


Priority-based disaster queue system


Evacuee request tracker


Disaster information and safety tips


The system aims to help users understand how disaster operations work by managing resources, tracking evacuees, processing emergency requests, and providing essential safety guidelines.
 Through this project, Object-Oriented Programming (OOP) concepts are applied and demonstrated while maintaining a structured, user-friendly interface.
OOP Concepts Applied
Abstraction
The system uses abstract design principles to hide the complexity of specific modules.
 While the user interacts with simple menus, the deeper logic of request handling, file access, data structures, and emergency processing remains hidden inside classes.
For example, each module (such as SupplyModule or PriorityModule) exposes only necessary methods like menu(), but keeps the detailed internal operations abstracted.
Inheritance
Several parts of the system follow the inheritance principle, using superclasses and subclasses to avoid repetition and enforce structure.
 Common structures such as emergency request data, evacuee information, and disaster safety information follow shared patterns that can be extended into specialized forms in the future.
This enables the system to grow without rewriting features—new disaster modules, new request types, and future expansions can inherit base characteristics easily.
Polymorphism
Polymorphism is demonstrated through overridden behaviors, where different modules implement their methods uniquely while still being accessed through common interfaces.
For example:
Each disaster type displays its own version of safety tips through overridden methods.


Priority queue requests behave differently depending on their assigned priority level, but are processed through the same interface.


Method calls such as menu() behave dynamically depending on which module is accessed.
Encapsulation
All sensitive data fields—such as quantities of supplies, evacuee information, contact numbers, and emergency requests—are encapsulated using:
Private fields


Getter and setter methods


Controlled input-validation


Exception handling


This ensures data integrity and prevents accidental misuse or corruption of the internal program state.

Program Structure
Main Class: DisasterResponseSystem
The main controller of the entire application. It handles:
Displaying the master menu


Navigating to each module


Saving and loading file-backed modules


Overall flow of the disaster management simulation


This class coordinates all other modules and acts as the backbone of the system.
Modules
1. SupplyModule
Manages all resources such as food, water, medical kits, and shelter supplies.
 Functions include:
Adding supplies


Viewing inventory


Submitting relief requests


Fulfilling requests


Tracking shortages


2. ContactDirectory
A file-backed directory of emergency agencies (PNP, hospitals, Red Cross, Coast Guard, etc.).
 Allows users to:
Add emergency contacts


Remove contacts


Search agencies


Sort contacts


Display all entries


3. PriorityModule
Manages emergency requests using a priority queue.
 The highest priority (1 = critical) is always processed first.
 Features include:
Adding new emergency tickets


Processing urgent cases


Viewing pending requests


Showing priority statistics


Displaying the last processed log


4. EvacueeModule
Tracks evacuee requests through file-based storage.
 Handles:
Adding evacuee records


Marking requests as completed


Searching by name or barangay


Sorting by name or group size


Displaying evacuee statistics


5. DisasterInfoModule
Displays informative summaries and safety tips for:
Earthquake


Flood


Typhoon


Fire


Each disaster includes:
Description


Causes


Risks


Safety procedures
 Following the proper formatting and simple navigation design.


Menu Navigation Flow
The system uses layered menus that follow this logical structure:
1. Master Menu
Supply & Relief Requests


Emergency Contact Directory


Priority Request Queue


Evacuee Request Tracker


Disaster Safety Tips


Save & Exit


This acts as the central hub.
2. Submenus
Each module provides:
Add


Delete


Search


Sort


Display


Back


Ensuring both beginners and advanced users can navigate easily.
How to Run the Program
1. Open Command Prompt or Terminal
2. Compile the Program
javac DisasterResponseSystem.java

3. Run the Program
java DisasterResponseSystem

4. Access the Master Menu
Upon running, the first screen introduces the system and displays the main navigation.
5. Choose Actions
Each menu leads to its own functional area where users can add, search, update, sort, or view records.
6. Save and Exit
Choose 0 from the main menu to safely store updates and terminate the system.
Sample Output
=== Disaster Response Master Menu ===
1. Supply & Relief Requests
2. Emergency Contact Directory
3. Priority Request Queue
4. Evacuee Request Tracker
5. Disaster Info & Safety Tips
0. Save & Exit
Enter choice:


Authors
This project was conceptualized, designed, and developed by: Celo, Tristan Wyatt V. , Garcia, Sherwin lei, Perez, Justin. All aspects of coding, structuring, debugging, and documentation were handled with the intent to apply and demonstrate core Java OOP principles.
Acknowledgement

This project was completed under the instruction and guidance of Grace Alib, whose discussions and lessons formed the foundation of this work. We also thank classmates who contributed feedback, and family members who continually supported and motivated the creation of this project.


