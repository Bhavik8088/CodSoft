# Student Management System

A robust and modern Java Swing application for managing student records. This application allows users to store, retrieve, update, and delete student information with a convenient Excel-like interface.

![Student Management System UI](screenshot_placeholder.png) 
*(Note: You can add a screenshot here)*

## 🚀 Features

- **CRUD Operations**: properties
    - **Add**: Create new student records with Name, Roll Number, and Grade.
    - **Edit**: Update existing student details.
    - **Delete**: Remove students from the database.
- **Search**: Quickly find students by their Roll Number.
- **Excel Integration**:
    - **Open in Excel**: One-click button to open your data in Microsoft Excel or your default CSV editor.
    - **Excel-like Grid**: Clean, visible grid lines for better readability.
- **Data Persistence**: Automatically saves all data to a local `students.csv` file, so your data is safe even after closing the app.
- **Modern UI**:
    - "Modern Indigo" color theme.
    - Gradient headers.
    - Responsive and clean layout.

## 🛠️ Prerequisites

- **Java Development Kit (JDK) 8 or higher**.
  - Ensure `java` and `javac` are added to your system's PATH.

## 📦 Installation & Running

You don't need any complex installation. The project comes with a handy script to run it.

### Option 1: Using the Run Script (Windows)
1. Navigate to the project folder.
2. Double-click on **`run.bat`**.
   - *This script will automatically compile the code and start the application.*

### Option 2: Manual Compilation (Terminal)
If you prefer using the command line:

1. Open a terminal/command prompt in the project directory.
2. **Compile** the source code:
   ```bash
   javac -d out -sourcepath src src/Main.java
   ```
3. **Run** the application:
   ```bash
   java -cp out Main
   ```

## 📂 Project Structure

```
Student Management System/
├── src/
│   ├── core/
│   │   ├── Student.java                 # Student data model
│   │   └── StudentManagementSystem.java # Logic controller (CRUD operations)
│   ├── ui/
│   │   ├── MainFrame.java               # Main dashboard UI
│   │   └── StudentDialog.java           # Dialog for Adding/Editing students
│   └── Main.java                        # Application Entry Point
├── out/                                 # Compiled .class files
├── students.csv                         # Data storage file (Created automatically)
├── run.bat                              # One-click run script
└── README.md                            # Project documentation
```

## 💡 Usage Guide

1. **Adding a Student**: Click the **Green "Add Student" button**. Fill in the details and click Save.
2. **Editing**: Select a row in the table and click the **Blue "Edit Student" button**.
3. **Deleting**: Select a row and click the **Red "Delete Student" button**. Confirm the action.
4. **Exporting**: Click **"Open in Excel"** to view your data in a spreadsheet format.

## 💾 Data Storage

All data is stored in `students.csv` in the project root.
- **Format**: `Name,RollNumber,Grade`
- You can backup this file to save your data.

## 🎨 Theme Details
- **Primary Color**: Indigo (`#3F51B5`)
- **Accent Color**: Emerald Green (`#2ECC71`)
- **Background**: Light Grayish Blue (`#F5F7FA`)

---
**Developed with Java Swing**
