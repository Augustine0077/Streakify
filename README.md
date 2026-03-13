# Streakify Backend

Backend MVP for the Streakify habit tracking application.

## Tech Stack
- Java
- Spring Boot
- PostgreSQL
- JPA / Hibernate
- Maven
- Postman

---

## Setup Steps

1. Clone the repository

git clone https://github.com/Augustine0077/Streakify.git

cd Streakify

2. Create PostgreSQL database

streakify_db

3. Configure database in application.properties

spring.datasource.url=jdbc:postgresql://localhost:5432/streakify_db
spring.datasource.username=postgres
spring.datasource.password=yourpassword

4. Run the application

./mvnw spring-boot:run

Server runs on:
http://localhost:8080

---

## Database Schema

Located in:

database/schema.sql

---

## API Endpoints

Users
POST /users  
GET /users/{id}  
DELETE /users/{id}

Habits
POST /habits  
GET /users/{userId}/habits  
DELETE /habits/{id}

Habit Logs
POST /habits/{habitId}/logs  
PUT /habits/{habitId}/logs/{date}  
GET /habits/{habitId}/logs

Streak
GET /habits/{habitId}/streak

Dashboard
GET /users/{userId}/dashboard

---

## Sample Request

POST /users

{
"name": "Augustine",
"email": "augustine@gmail.com"
}

---

## Postman Collection

Located in:
postman/streakify_api_collection.json

---


## Screenshots

### Create User
![Create User](screenshots/create_user.png)

### Get User
![Get User](screenshots/get_user.png)

### Delete User
![Delete User](screenshots/delete_user.png)

---

### Create Habit
![Create Habit](screenshots/create_habit.png)

### Get User Habits
![Get User Habits](screenshots/get_user_habit.png)

### Trying to Add Same Habit
![Duplicate Habit](screenshots/trying_to_add%20same_habit.png)

---

### Log Multiple Days

Day 1  
![Log Day 1](screenshots/log_multiple_days_day_1.png)

Day 2  
![Log Day 2](screenshots/log_multiple_days_day_2.png)

Day 3 (Faild)  
![Log Day 3 Fail](screenshots/log_multiple_days_day_3_fasle_.png)

---

### Fetch Streak
![Fetch Streak](screenshots/fetch_streak.png)

---

### Dashboard
![Dashboard](screenshots/fetch_dashbord.png)

---

### Negative Cases

Duplicate Log  
![Duplicate Log](screenshots/duplicate_log_false.png)

Future Date Cannot Be Added  
![Future Date](screenshots/future_date_log_cannot_be_added)

Non Existing User  
![User Not Found](screenshots/non_existing_user_user_not_found.png)