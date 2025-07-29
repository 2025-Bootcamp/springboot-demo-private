# 🧪 Spring Boot Demo: Create a RESTful API

This project demonstrates how to build a simple RESTful API using Spring Boot. It covers:

* Creating a Spring Boot project
* Defining model classes
* Implementing a REST controller
* Handling HTTP requests (POST, GET, PUT, DELETE)

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

### 2️⃣ Employee.java - Model Class

```java
package com.bootcamp.springBootDemo;

public class Employee {
//    ...
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

    //    using memory to host the data
    private final Map<Integer, Employee> employees = new HashMap<>(Map.of(
            1, new Employee(1, "John Smith", 32, Gender.MALE, 5000.0),
            2, new Employee(2, "Jane Johnson", 28, Gender.FEMALE, 6000.0),
            3, new Employee(3, "David Williams", 35, Gender.MALE, 5500.0),
            4, new Employee(4, "Emily Brown", 23, Gender.FEMALE, 4500.0),
            5, new Employee(5, "Michael Jones", 40, Gender.MALE, 7000.0)));

    @GetMapping
    public List<Employee> getAll() {
        return employees.values().stream().toList();
    }

    @PutMapping("/{id}")
    public Employee update(@PathVariable int id, @RequestBody Employee request) {
        //get original employee
        Employee preEmployee = employees.get(id);
        if (preEmployee == null) {
            return null;
        }
        String name = request.getName() == null ? preEmployee.getName() : request.getName();
        Integer age = request.getAge() == null ? preEmployee.getAge() : request.getAge();
        Gender gender = request.getGender() == null ? preEmployee.getGender() : request.getGender();
        Double salary = request.getSalary() == null ? preEmployee.getSalary() : request.getSalary();
        Employee newEmployee = new Employee(id, name, age, gender, salary);
        return employees.replace(id, newEmployee);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Integer id) {
        employees.remove(id);
    }

    @GetMapping(params = {"pageNumber", "pageSize"})
    public List<Employee> getAllByPageSize(@RequestParam Integer pageNumber,
                                           @RequestParam Integer pageSize) {
        return employees.values().stream().skip((long) (pageNumber - 1) * pageSize).limit(pageSize)
                .toList();
    }
}
```

## 📬 API Usage Examples (Step 2)

### 📄 Get All Employees

```
GET /employees
```

### 📝 Update Employee

```
PUT /employees/1
Content-Type: application/json

{
  "age": 36,
  "salary": 6200
}
```

### ❌ Delete Employee

```
DELETE /employees/1
```

### 📃 Paginated Query

```
GET /employees?page=1&size=5
```

## ✅ Run the Application

```
mvn spring-boot:run
```

---

This example provides a clean and simple starting point for teaching REST APIs in Spring Boot. ✅
