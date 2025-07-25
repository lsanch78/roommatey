# 🏠 RoomMatey

**RoomMatey** is a lightweight, all-in-one roommate management dashboard. It's designed for households that want to stay organized and keep things fair without the stress. With RoomMatey, you can assign chores, split bills, and manage your household easily, all in one place.

---

## Features

- 📋 **Chore Management**: Create, assign, and schedule recurring chores (daily, weekly, monthly).
- 💰 **Bill Tracking**: Manage shared expenses with per-user breakdowns and due dates.
- 🧍‍♂️ **User Dashboard**: View all your chores and bills assigned to you at a glance.
- 🏡 **Household Overview**: Central dashboard with all important info per user.
- ✨ **Clean & Responsive UI**: Built with Bootstrap 5 and Thymeleaf for a simple, elegant experience.

---

## 🖼️ Screenshots

### Dashboard
![Dashboard](screenshots/dashboard.png)

### Manage Bills
![Manage Bills](screenshots/managebills.png)

### Manage Chores
![Manage Chores](screenshots/managechores.png)

### Manage Roommates
![Manage Roommates](screenshots/manageroommates.png)

---

## Tech Stack

- **Backend**: Java 17+, Spring Boot 3
- **Frontend**: Thymeleaf, Bootstrap 5
- **Persistence**: Spring Data JPA, H2 (default) or MySQL
- **Build Tool**: Maven

---

## Getting Started

### Prerequisites

- Java 17+
- Maven 3.8+

### Run Locally

```bash
git clone https://github.com/lsanch78/roommatey.git
cd roommatey
./mvnw spring-boot:run
Visit the app in your browser:
http://localhost:8080
