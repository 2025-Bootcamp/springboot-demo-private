# 🧪 Spring Boot Demo: Create a RESTful API

This project demonstrates how to build a simple RESTful API using Spring Boot. It covers:

* Creating a Spring Boot project
* Defining model classes
* Implementing a REST controller
* Handling HTTP requests (POST, GET, PUT, DELETE)

## ✨ Goal

Expose RESTful endpoints for managing employees and companies.

## 📁 Project Structure

```
src/main/java/com/bootcamp/springBootDemo
├── Company.java
├── CompanyController.java
├── Employee.java
├── EmployeeController.java
├── Gender.java
└── GreetingController.java
```

## 🧑‍💻 CompanyController.java - Company REST APIs

```java
package com.bootcamp.springBootDemo;

import java.util.ArrayList;
import java.util.List;

public class Company {

    private Integer id;
    private String name;
    private List<Employee> employees = new ArrayList<>();

    public Company() {
    }

    public Company(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Company(Integer id, String name, List<Employee> employees) {
        this.id = id;
        this.name = name;
        this.employees = employees;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }
}
```

## 🏢 Company.java - Model Class

```java
package com.bootcamp.springBootDemo;

import java.util.ArrayList;
import java.util.List;

public class Company {

    private Integer id;
    private String name;
    private List<Employee> employees = new ArrayList<>();

    public Company() {
    }

    public Company(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Company(Integer id, String name, List<Employee> employees) {
        this.id = id;
        this.name = name;
        this.employees = employees;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }
}
```

## 📬 API Usage Examples (Company APIs)

### 📄 Get All Companies

```
GET /companies
```

### 📃 Get Paginated Companies

```
GET /companies?page=1&size=5
```

### 🔍 Get Company By ID

```
GET /companies/1
```

### 👥 Get All Employees of a Company

```
GET /companies/1/employees
```

### ➕ Add New Company

```
POST /companies
Content-Type: application/json

{
  "name": "NewCo"
}
```

### 📝 Update Company

```
PUT /companies/1
Content-Type: application/json

{
  "name": "UpdatedCo"
}
```

### ❌ Delete Company

```
DELETE /companies/1
```
