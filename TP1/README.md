# Assignment 1 — Hello Kotlin. Hello Android World!

Course: Desenvolvimento de Aplicações Móveis (DAM)
Student(s): João Fernandes (a47478)
Date: 2026-03-05
Repository URL: https://github.com/Joao01F/GitHub.git

> **Note:** This is a personal repository shared across multiple courses. The DAM project for this assignment is located at the path `sem2_2526 / DAM` within the repository. Starting from the next assignment, a dedicated repository will be created exclusively for DAM.

---

## 1. Introduction

The purpose of this assignment is to introduce the fundamentals of Kotlin and Android
development. The objectives were to:

- Set up IntelliJ IDEA and get familiar with the Kotlin language through practical exercises covering Basic Types, Control Flow, and Object-Oriented Programming.
- Install and configure Android Studio, including the Android SDK and virtual devices (AVDs).
- Build a first Android application ("Hello, World!") and progressively improve it.
- Practice Kotlin OOP concepts through a Virtual Library Management System.
- Use Logcat for debugging and monitoring application state.

---

## 2. System Overview

The assignment is divided across four main areas:

1. **Kotlin Exercises (Section 2)** — Three console-based programs covering integer arrays, a calculator, and a ball-bounce sequence, implemented in IntelliJ IDEA using a Maven-based Kotlin project.
2. **Hello World Android App (Section 4)** — A simple Android application built in Android Studio with a `TextView`, image, and `CalendarView`, progressively improved from v1 to v2.
3. **Logcat & System Info App (Section 5)** — Exploration of Android Studio's Logcat, debug mode, and a dedicated application that reads and displays device build information using `android.os.Build`.
4. **Virtual Library (Section 6)** — A console-based Kotlin program implementing a full object-oriented library management system, including abstract classes, inheritance, custom getters/setters, companion objects, and data classes.

---

## 3. Architecture and Design

### Kotlin Project (DAM_TP1)
The IntelliJ project follows a Maven structure with packages organised as:
- `dam.exer_1` — Perfect squares array
- `dam.exer_2` — Console calculator
- `dam.exer_3` — Ball bounce sequence
- `dam.virtual_library` — Virtual Library Management System

### Android Projects
Each Android application is a separate Android Studio project with its own repository. The Hello World application uses:
- **Empty Views Activity** template
- `ConstraintLayout` for the UI
- `strings.xml` for all string resources
- `colors.xml` for colour definitions
- `drawable` folder for image resources

### Key Design Decisions
- Strings are externalised to `strings.xml` to support future internationalisation.
- The Virtual Library uses an abstract base class `Book` with two subclasses (`DigitalBook`, `PhysicalBook`), following the Open/Closed Principle.
- The `Library` companion object tracks the total number of books created across all instances.

---

## 4. Implementation

### Exercise 1 — Perfect Squares Array
Three approaches were used to create an array of the first 50 perfect squares:
- `IntArray` constructor with explicit initialisation
- A range combined with `map()` to transform indices into squares
- `Array` constructor with a lambda

### Exercise 2 — Console Calculator
Implemented using a `when` expression to dispatch operations, `try/catch` for error handling (division by zero, invalid input), and string templates for formatted output. Supports arithmetic, boolean, and bitwise operations, displaying results in decimal, hexadecimal, and boolean formats.

### Exercise 3 — Ball Bounce Sequence
Used `generateSequence` to model successive bounce heights (60% of the previous), filtered to keep only bounces ≥ 1 metre, taking the first 15 qualifying values and printing them rounded to 2 decimal places.

### Hello World App
- Created using **Empty Views Activity** with package `dam_a47478.helloworld`
- Minimum API level: API 24 (Android 7.0 Nougat)
- v1: Single `TextView` with `@string/hello_string`, app name set to "Hello World V1"
- v2: Added a second `TextView`, an image from drawable, a `CalendarView`, a landscape layout variant, and a custom app icon

### Logcat & System Info App
- Added `println` inside `onCreate` to observe lifecycle events in Logcat
- Used `getString()` with a dynamic string resource (`%1$s` placeholder)
- Set a breakpoint and stepped through execution in Debug Mode
- Built a new app using `android.os.Build` to display device information in a `MultiLine Text Widget`

### Virtual Library
Key classes:
- `Book` (abstract) — `title`, `author`, `publicationYear`, `availableCopies` with custom getter/setter and abstract `getStorageInfo()`
- `DigitalBook` — adds `fileSize` and `format`
- `PhysicalBook` — adds `weight` and `hasHardcover`
- `Library` — manages a `MutableList<Book>` with `addBook()`, `borrowBook()`, `returnBook()`, `showBooks()`, `searchByAuthor()`
- `LibraryMember` — data class with `name`, `membershipId`, `borrowedBooks`

---

## 5. Testing and Validation

- **Kotlin exercises**: Verified by running each file in IntelliJ IDEA and inspecting console output against expected results.
- **Hello World app**: Deployed and tested on the Pixel 9 Pro AVD (API 34) and on a Xiaomi Redmi Note 12, verifying UI elements, string updates, and layout correctness in both portrait and landscape modes.
- **Logcat**: Confirmed that lifecycle messages appear correctly in Logcat, both in normal and debug mode.
- **System Info App**: Verified that all `android.os.Build` fields are correctly displayed on the emulator.
- **Virtual Library**: Executed `main()` and compared the console output against the expected output provided in the assignment (Section 6.4), including edge cases such as borrowing a book with no copies remaining.

Known limitations:
- The landscape layout variant was not fully optimised for all screen sizes.
- The Virtual Library does not persist data between runs.

---

## 6. Usage Instructions

### Kotlin Project (DAM_TP1)
**Requirements:** IntelliJ IDEA, JDK 17+, Maven

1. Clone the repository: `git clone https://github.com/Joao01F/GitHub.git`
2. Open the `sem2_2526\IntelliJCode\DAM_TP1` folder in IntelliJ IDEA.
3. Navigate to the desired exercise package (e.g., `dam/exer_1/exer_1.kt`).
4. Run the file using the green play button or `Shift + F10`.

### Android Projects
**Requirements:** Android Studio (Ladybug/Panda), Android SDK API 24+, a configured AVD (Pixel 9 Pro) or physical device with USB debugging enabled.

1. Open the relevant Android project folder (`sem2_2526\DAM\code`) in Android Studio.
2. Wait for Gradle to sync.
3. Select the target device (AVD or physical).
4. Run via `Run > Run 'app'` or `Shift + F10`.

---

# Development Process

## 12. Version Control and Commit History

Version control was managed using Git with a local repository in IntelliJ (for the Kotlin project) and GitHub for remote storage. Commits were made incrementally at the end of each exercise and each significant development step, reflecting a continuous workflow rather than a single final commit. Commit messages follow a descriptive convention (e.g., `initialize project structure with Kotlin and Maven configuration`).

---

## 13. Difficulties and Lessons Learned

- **ConstraintLayout**: Managing constraints in the layout editor required careful attention, particularly when removing and re-adding constraints using `CTRL + click`.
- **generateSequence**: Understanding how to seed the sequence and reference the previous value took some iteration with the Kotlin documentation.
- **Custom getters returning non-primitive types**: The `publicationYear` getter returning a `String` era label rather than the raw `Int` required careful design to avoid confusion in the rest of the code.

Key skills acquired: Kotlin OOP, Android layout system, Logcat debugging, AVD management, and Git version control.

---

## 14. Future Improvements

- Extend the Hello World app with user interaction (e.g., a button that updates the displayed text).
- Add unit tests for the Virtual Library using JUnit/Kotlin Test.
- Explore Jetpack Compose as an alternative to XML-based layouts.

---

## 15. AI Usage Disclosure (Mandatory)

- **Tool used:** Claude (Anthropic) — used to assist in drafting this report and to clarify Kotlin syntax and built-in methods (e.g., generateSequence, map, when expressions) during development. No code was generated by AI — all implementations were written independently, with AI used only as a reference/explanation tool, equivalent to consulting documentation.
- **How it was used:** The report structure and content were generated based on the assignment specification and details provided by the student. The AI was not used for any coding sections marked [AI NO].
- The student remains fully responsible for all content in this report and all submitted code.