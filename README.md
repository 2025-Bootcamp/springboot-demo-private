# 📘 Spring Boot RESTful Employee API

This demo project showcases how to build a simple RESTful API using **Spring Boot**. It demonstrates how to:

* Create a basic Spring Boot project
* Define a simple `Employee` model
* Use `@RestController` to expose RESTful endpoints
* Handle basic HTTP methods like `POST` and `GET`
* Use `@RequestParam`, `@PathVariable`, and `@RequestBody`

## 🚀 Technologies Used

* Java 17+
* Spring Boot 3+
* Maven

---

## ✨ Goal

Expose RESTful endpoints for managing employees.

## 📁 Project Structure

```
src/main/java/com/bootcamp/springBootDemo
├── Employee.java
├── EmployeeController.java
├── Gender.java
└── GreetingController.java
```

## 🧑‍💻 Step-by-step Implementation

```

### 2️⃣ Employee.java - Model Class

```java
package com.bootcamp.springBootDemo;

public class Employee {

    private int id;
    private String name;
    private Integer age;
    private Gender gender;
    private Double salary;

    public Employee(int id, String name, Integer age, Gender gender, Double salary) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.salary = salary;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Integer getAge() { return age; }
    public void setAge(Integer age) { this.age = age; }
    public Gender getGender() { return gender; }
    public void setGender(Gender gender) { this.gender = gender; }
    public Double getSalary() { return salary; }
    public void setSalary(Double salary) { this.salary = salary; }
}
```

### 3️⃣ Gender.java - Enum

```java
package com.bootcamp.springBootDemo;

public enum Gender {
    MALE, FEMALE
}
```

### 4️⃣ EmployeeController.java - REST API

```java
package com.bootcamp.springBootDemo;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final Map<Integer, Employee> employees = new HashMap<>(Map.of(
            1, new Employee(1, "John Smith", 32, Gender.MALE, 5000.0),
            2, new Employee(2, "Jane Johnson", 28, Gender.FEMALE, 6000.0),
            3, new Employee(3, "David Williams", 35, Gender.MALE, 5500.0),
            4, new Employee(4, "Emily Brown", 23, Gender.FEMALE, 4500.0),
            5, new Employee(5, "Michael Jones", 40, Gender.MALE, 7000.0)));

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employee create(@RequestBody Employee request) {
        int id = employees.size() + 1;
        Employee newEmployee = new Employee(id, request.getName(), request.getAge(), request.getGender(),
                request.getSalary());
        employees.put(id, newEmployee);
        return newEmployee;
    }

    @GetMapping("/{id}")
    public Employee getById(@PathVariable Long id) {
        return employees.get(id);
    }

    @GetMapping
    public List<Employee> getByGender(@RequestParam("gender") String genderParam) {
        Gender gender = Gender.valueOf(genderParam.toUpperCase());
        return employees.values().stream().filter(employee -> employee.getGender().equals(gender))
                .toList();
    }
}
```

## 📬 API Usage Examples

### ➕ Create Employee

```
POST /employees
Content-Type: application/json

{
  "name": "Alice",
  "age": 30,
  "gender": "FEMALE",
  "salary": 5800
}
```

### 🔍 Get Employee by ID

```
GET /employees/1
```

### 🧑‍🤝‍🧑 Get Employees by Gender

```
GET /employees?gender=male
```

## ✅ Run the Application

```
Run SpringBootDemoApplication
```

---

## 🌐 API Endpoints

### ➕ POST `/employees`

Create a new employee.

**Request Body:**

```json
{
  "name": "Alice",
  "age": 30,
  "gender": "FEMALE",
  "salary": 6000.0
}
```

**Response:**

```json
{
  "id": 6,
  "name": "Alice",
  "age": 30,
  "gender": "FEMALE",
  "salary": 6000.0
}
```

---

### 🔍 GET `/employees/{id}`

Get employee by ID.

**Example:**

```
GET /employees/1
```

**Response:**

```json
{
  "id": 1,
  "name": "John Smith",
  "age": 32,
  "gender": "MALE",
  "salary": 5000.0
}
```

---

### 📋 GET `/employees?gender=male`

Get all employees by gender.

**Example:**

```
GET /employees?gender=male
```

**Response:**

```json
[
  {
    "id": 1,
    "name": "John Smith",
    "age": 32,
    "gender": "MALE",
    "salary": 5000.0
  },
  ...
]
```

---

## 📁 Folder Structure

```
src
└── main
    └── java
        └── com.bootcamp.springBootDemo
            ├── Employee.java
            ├── EmployeeController.java
            └── Gender.java
```

## ✅ Next Steps

* Add `PUT` and `DELETE` endpoints
* Add persistence using Spring Data JPA and a database (e.g., H2/MySQL)
* Implement global exception handling

---

## 🧑‍🏫 For Trainers

This is an ideal starting point for teaching trainees how to:

* Create Spring Boot projects
* Build REST APIs
* Understand Java annotations and HTTP mapping

