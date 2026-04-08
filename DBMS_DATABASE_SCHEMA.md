# Database Schema Updated to dbms_database ✅

## Database Configuration

Your application is now connected to the **dbms_database** with a comprehensive, well-structured schema designed for mental health information sharing.

### Connection Details
- **Host**: localhost
- **Port**: 3306
- **Database Name**: `dbms_database`
- **Username**: Tharuniga
- **Password**: Tharuniga123!@#

---

## Database Schema - 6 Tables

### 1. **users** Table
User profiles and authentication records.

**Attributes**:
- `user_id` (INT, PK, Auto-increment) - Unique user identifier
- `username` (VARCHAR 100, UNIQUE) - Login username
- `email` (VARCHAR 150, UNIQUE) - User email
- `password_hash` (VARCHAR 255) - Hashed password
- `full_name` (VARCHAR 200) - User's full name
- `date_of_birth` (DATE) - Date of birth
- `phone_number` (VARCHAR 20) - Contact number
- `profile_picture_url` (VARCHAR 500) - Profile picture URL
- `bio` (TEXT) - User biography
- `location` (VARCHAR 150) - User location
- `is_active` (BOOLEAN, DEFAULT: TRUE) - Account active status
- `account_status` (ENUM: 'ACTIVE', 'INACTIVE', 'SUSPENDED') - Account status
- `created_at` (TIMESTAMP) - Account creation time
- `updated_at` (TIMESTAMP) - Last update time
- `last_login` (TIMESTAMP) - Last login timestamp

**Sample Data**:
```
admin | admin@dbms.local | System Administrator
testuser | testuser@dbms.local | Test User
```

---

### 2. **categories** Table
Content categories for organizing shared information and resources.

**Attributes**:
- `category_id` (INT, PK, Auto-increment) - Unique category identifier
- `category_name` (VARCHAR 100, UNIQUE) - Category name
- `description` (TEXT) - Category description
- `category_icon` (VARCHAR 200) - Icon URL or name
- `is_active` (BOOLEAN, DEFAULT: TRUE) - Category status
- `created_at` (TIMESTAMP) - Creation time

**Sample Categories**:
- Mental Health
- Wellness
- Fitness
- Nutrition
- Meditation
- Stress Management
- Support Groups
- Professional Help

---

### 3. **resources** Table
Educational and helpful resources (articles, videos, etc.).

**Attributes**:
- `resource_id` (INT, PK, Auto-increment) - Unique resource ID
- `title` (VARCHAR 255) - Resource title
- `description` (TEXT) - Resource description
- `resource_url` (VARCHAR 1000) - Link to resource
- `resource_type` (ENUM: 'ARTICLE', 'VIDEO', 'PODCAST', 'BOOK', 'WEBSITE', 'OTHER')
- `author_id` (INT, FK → users.user_id) - Creator of resource
- `category_id` (INT, FK → categories.category_id) - Resource category
- `view_count` (INT, DEFAULT: 0) - Number of views
- `like_count` (INT, DEFAULT: 0) - Number of likes
- `is_published` (BOOLEAN, DEFAULT: TRUE) - Publication status
- `created_at` (TIMESTAMP) - Creation time
- `updated_at` (TIMESTAMP) - Last update time

**Indexes**: author_id, category_id, created_at

---

### 4. **share_your_information** Table
User-shared mental health information and experiences.

**Attributes**:
- `share_id` (INT, PK, Auto-increment) - Unique share ID
- `user_id` (INT, FK → users.user_id) - Sharing user
- `title` (VARCHAR 255) - Share title
- `content` (LONGTEXT) - Detailed content
- `category_id` (INT, FK → categories.category_id) - Content category
- `is_anonymous` (BOOLEAN, DEFAULT: FALSE) - Anonymous sharing flag
- `mood_status` (ENUM: 'HAPPY', 'SAD', 'ANXIOUS', 'NEUTRAL', 'STRESSED', 'HOPEFUL')
- `privacy_level` (ENUM: 'PUBLIC', 'PRIVATE', 'FRIENDS_ONLY')
- `like_count` (INT, DEFAULT: 0) - Number of likes
- `comment_count` (INT, DEFAULT: 0) - Number of comments
- `is_featured` (BOOLEAN, DEFAULT: FALSE) - Featured status
- `created_at` (TIMESTAMP) - Creation time
- `updated_at` (TIMESTAMP) - Last update time

**Indexes**: user_id, category_id, privacy_level, created_at

---

### 5. **comments** Table
Comments on shared information.

**Attributes**:
- `comment_id` (INT, PK, Auto-increment) - Unique comment ID
- `share_id` (INT, FK → share_your_information.share_id) - Parent share
- `user_id` (INT, FK → users.user_id) - Comment author
- `comment_text` (TEXT) - Comment content
- `likes` (INT, DEFAULT: 0) - Comment likes
- `is_approved` (BOOLEAN, DEFAULT: TRUE) - Approval status
- `created_at` (TIMESTAMP) - Creation time
- `updated_at` (TIMESTAMP) - Last update time

**Indexes**: share_id, user_id

---

### 6. **likes** Table
Tracks likes on shares and resources.

**Attributes**:
- `like_id` (INT, PK, Auto-increment) - Unique like ID
- `user_id` (INT, FK → users.user_id) - User who liked
- `share_id` (INT, FK → share_your_information.share_id) - Liked share (nullable)
- `resource_id` (INT, FK → resources.resource_id) - Liked resource (nullable)
- `created_at` (TIMESTAMP) - Like timestamp

**Unique Constraints**:
- One like per user per share
- One like per user per resource

**Indexes**: user_id

---

## Relationships Diagram

```
users
├─→ 1:N ─→ resources (author_id)
├─→ 1:N ─→ share_your_information (user_id)
├─→ 1:N ─→ comments (user_id)
├─→ 1:N ─→ likes (user_id)
│
categories
├─→ 1:N ─→ resources (category_id)
└─→ 1:N ─→ share_your_information (category_id)

share_your_information
├─→ 1:N ─→ comments (share_id)
└─→ 1:N ─→ likes (share_id)

resources
└─→ 1:N ─→ likes (resource_id)
```

---

## Test Login Credentials

**Admin Account**:
- Username: `admin`
- Password: `admin`
- Email: admin@dbms.local

**Test Account**:
- Username: `testuser`
- Password: `testuser123`
- Email: testuser@dbms.local

---

## Verify Database

### Check all tables:
```bash
mysql -u Tharuniga -pTharuniga123!@# -e "USE dbms_database; SHOW TABLES;"
```

### View users:
```bash
mysql -u Tharuniga -pTharuniga123!@# -e "USE dbms_database; SELECT * FROM users;"
```

### View categories:
```bash
mysql -u Tharuniga -pTharuniga123!@# -e "USE dbms_database; SELECT * FROM categories;"
```

### Check table structure:
```bash
mysql -u Tharuniga -pTharuniga123!@# -e "USE dbms_database; DESCRIBE users;"
```

---

## Files Modified

1. **schema.sql** - Updated with complete dbms_database schema
2. **DBConnection.java** - Updated connection URL to dbms_database

---

## Application Status

✅ Database: `dbms_database` created
✅ All 6 tables created with proper relationships
✅ Sample data inserted (2 users, 8 categories)
✅ Indexes and constraints configured
✅ Application rebuilt and running
✅ Ready for data insertion

---

**Your application is now fully configured with a comprehensive, production-ready database schema!**

Generated: April 7, 2026
