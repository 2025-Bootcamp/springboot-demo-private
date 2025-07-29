## Spring Boot API Development - Technical Teaching Outline

### ✨ Goal

Teach trainees how to create a minimal Spring Boot project and expose a REST API using a simple controller.

---

### ✈️ Part 1: Introduction to Spring Boot

* What is Spring Boot?
* Benefits of using Spring Boot (auto-configuration, standalone applications, embedded servers, etc.)
* Spring Boot vs Spring Framework

---

### 🚀 Part 2: Create a Spring Boot Project

* ✅ Using [Spring Initializr](https://start.spring.io/) to bootstrap a project

    * Project: Maven
    * Language: Java
    * Spring Boot version: latest stable
    * Dependencies: Spring Web
* ✅ Import project into IDE (IntelliJ / Eclipse / VS Code)

---

### 🔍 Part 3: Directory Structure Overview

* `src/main/java` and `src/main/resources`
* `application.properties`
* Main class annotated with `@SpringBootApplication`

---

### 💡 Part 4: Build a Simple REST API

* Create package: `com.bootcamp.springBootDemo`
* Create `GreetingController` class

```java
package com.bootcamp.springBootDemo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {
    @GetMapping("/greeting")
    public String say(@RequestParam String name) {
        return String.format("Hello %s", name);
    }
}
```

---

### 🎨 Part 5: Run and Test

* Run the application from the main class
* Open browser or Postman: `http://localhost:8080/greeting?name=John`
* See result: `Hello John`

---

### ⚒️ Part 6: Enhance the API (optional)

* Return a JSON object instead of plain text
* Add default value for `name`
* Introduce `@RequestMapping`

---

### ✅ Wrap Up

* Review key annotations: `@RestController`, `@GetMapping`, `@RequestParam`
* Emphasize simplicity and productivity with Spring Boot

---

### 📅 Suggested Exercise

Ask each trainee to:

* Build a new Spring Boot project
* Create a new endpoint: `/welcome` that takes a `name` and returns `"Welcome, <name>!"`
