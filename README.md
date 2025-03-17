# BookNook Web Application - README

## Project Overview
The BookNook Web Application is a JSP-based online bookstore that allows users to browse, search, and purchase books and accessories while providing administrators with tools to manage products, users, and orders. The system uses Java, JSP, Servlets, and SQLite for backend processing and data management.

## How to Set Up & Run the Project

### Prerequisites
- NetBeans IDE
- Java JDK 11 or compatible version
- Apache Tomcat 10.1

### Configure the Database
The application uses SQLite as the database, and the path must be set correctly in `DatabaseConnection.java`.

1. Locate the file:
   ```
   src/dao/DatabaseConnection.java
   ```
2. Modify the database path in the `getConnection()` method:
   ```java
   private static final String URL = "jdbc:sqlite:C:/path/to/your/database/booknook.db";
   ```
3. Ensure the database (`booknook.db`) exists in the specified location.

### Deploy & Run the Application
Right-click the project in NetBeans and select "Run".

## Troubleshooting
- Ensure the database path is correct in `DatabaseConnection.java`.
- If Tomcat does not start, check that port 8081 is free.

## Default Admin Credentials
```
Username: admin  
Password: admin123  
```

