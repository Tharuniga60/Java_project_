-- Drop and recreate the database
DROP DATABASE IF EXISTS codingshare;
CREATE DATABASE codingshare CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE codingshare;

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

-- Insert admin user safely
INSERT INTO users (username, password_hash, full_name)
VALUES ('admin', 'admin', 'Admin User');
