# Gear Checkout System

A Spring Boot application for outdoor clubs (like the Climbing Club at UW) that loan gear. Allows officers to verify members when they join and check out gear to verified members.


### Features

- Officer based user verification
- User registration and auth
- Add, update, remove, list gear
- Checkout and return gear
- Email reminders for late gear returns

### Prerequisites

- JDK 11
- MySQL 5.7

### MySQL Config

1. Install MySQL and create a `Gear_Room` database
2. Create a user and grant it access to the database
3. Set environment variables `${DB_USERNAME}` and `${DB_PASSWORD}` in `application.properties`

### Application Configuration

Clone the repository:

```
git clone https://github.com/KeejayK/gear-system.git
```

Navigate to the project directory:
```
cd gear-system
```

Open src/main/resources/application.properties and update the database connection details:
```
spring.datasource.url=jdbc:mysql://localhost:3306/gear_room?useSSL=false&serverTimezone=UTC
spring.datasource.username=${db_username}
spring.datasource.password=${db_password}
```

Build the project using Gradle:
```
./gradlew bootRun
```

Run the application:
```
java -jar target/gear-checkout-system-0.0.1-SNAPSHOT.jar
```

### Usage

Once the application is running, REST API endpoints can be accessed at http://localhost:8080/api/....

Examples of operations you can perform:
```
Register a new user: POST /api/users/register
Verify a user: GET /api/users/verify/{email}
Add new gear: POST /api/gear
Check out gear: POST /api/checkout
Return gear: POST /api/checkout/return/{id}
```

### TODO:
- implement security config for user auth
- email notifications when gear is overdue
