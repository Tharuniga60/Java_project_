# Database Setup Guide for Java Project

## Step 1: Connect to MySQL and Execute Schema

### Option A: Using MySQL Command Line
```bash
mysql -u Tharuniga -p
```
Enter your password: `Tharuniga123!@#`

Then run:
```sql
-- Drop and recreate the database
DROP DATABASE IF EXISTS java_project;
CREATE DATABASE java_project CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE java_project;

-- Create users table
CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(100) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    full_name VARCHAR(200),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create resources table
CREATE TABLE resources (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    url VARCHAR(1000),
    author_id INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (author_id) REFERENCES users(id) ON DELETE SET NULL
);

-- Create share_your_information table
CREATE TABLE share_your_information (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    title VARCHAR(255) NOT NULL,
    content TEXT NOT NULL,
    category VARCHAR(100),
    is_anonymous BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Insert admin user
INSERT INTO users (username, password_hash, full_name)
VALUES ('admin', 'admin', 'Admin User');
```

### Option B: Using schema.sql from PowerShell
```powershell
mysql -u Tharuniga -p java_project < schema.sql
```

## Step 2: Verify Database Creation
```sql
USE java_project;
SHOW TABLES;
DESCRIBE users;
DESCRIBE resources;
DESCRIBE share_your_information;
SELECT * FROM users;
```

## Step 3: Update connection details in Java if needed
The application is configured to connect to:
- **Host**: localhost
- **Port**: 3306
- **Database**: java_project
- **User**: Tharuniga
- **Password**: Tharuniga123!@#

If your MySQL instance is named "mental_health", update the HOST variable in DBConnection.java

## Step 4: Rebuild and Run the Application
```powershell
cd "c:\Users\tharu\Downloads\Java_project_-main\Java_project_-main"
mvn clean package -DskipTests
java -jar target/java-project-1.0-SNAPSHOT.jar
```

## Expected Tables

### users table
- id (PK, auto-increment)
- username (unique)
- password_hash
- full_name
- created_at

### resources table
- id (PK, auto-increment)
- title
- description
- url
- author_id (FK → users.id)
- created_at

### share_your_information table
- id (PK, auto-increment)
- user_id (FK → users.id)
- title
- content
- category
- is_anonymous
- created_at
- updated_at
