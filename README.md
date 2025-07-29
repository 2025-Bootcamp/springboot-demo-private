# Spring Boot Bootcamp – Teaching Outline

## 🎯 Objective

Introduce trainees to the architecture and development of a Spring Boot RESTful API using a layered approach:

1. Controller Layer
2. Service Layer
3. Repository Layer (simulated with in-memory list)

---

## 🏗️ Project Structure Overview

```
com.bootcamp.springBootDemo
│
├── controller
│   └── EmployeeController.java
│
├── service
│   └── EmployeeService.java
│
├── repository
│   └── EmployeeRepository.java
│
├── model
│   ├── Employee.java
│   └── Gender.java
│
└── SpringBootDemoApplication.java
```

---

## 🔥 Step-by-Step Teaching Plan

### ✅ Step 1: Introduce Layered Architecture

* Controller: Accepts API requests
* Service: Handles business logic
* Repository: Manages data access (mocked with a list)

---

### ✅ Step 2: Explain the API Requirements

**Employee REST APIs**

* `POST /employees`: Create an employee
* `GET /employees/{id}`: Get employee by ID
* `GET /employees?gender=male`: Filter employees by gender
* `GET /employees`: Get all employees
* `PUT /employees/{id}`: Update employee
* `DELETE /employees/{id}`: Delete employee
* `GET /employees?pageNumber=1&pageSize=5`: Pagination query

---

### ✅ Step 3: Controller Layer

#### `EmployeeController.java`

```java
@RestController
@RequestMapping("/employees")
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employee create(@RequestBody Employee request) {
        return employeeService.saveEmployee(request);
    }

    @GetMapping("/{id}")
    public Employee getById(@PathVariable Integer id) {
        return employeeService.getEmployeeById(id);
    }

    @GetMapping(params = {"gender"})
    public List<Employee> getByGender(@RequestParam("gender") String genderParam) {
        Gender gender = Gender.valueOf(genderParam.toUpperCase());
        return employeeService.getEmployeeByGender(gender);
    }

    @GetMapping
    public List<Employee> getAll() {
        return employeeService.getAllEmployee();
    }

    @PutMapping("/{id}")
    public Employee update(@PathVariable Integer id, @RequestBody Employee request) {
        return employeeService.updateEmployee(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Integer id) {
        employeeService.deleteEmployeeById(id);
    }

    @GetMapping(params = {"pageNumber", "pageSize"})
    public List<Employee> getAllByPageSize(@RequestParam Integer pageNumber, @RequestParam Integer pageSize) {
        return employeeService.getAllByPageSize(pageNumber, pageSize);
    }
}
```

---

### ✅ Step 4: Service Layer

#### `EmployeeService.java`

```java
@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Employee getEmployeeById(Integer id) {
        return employeeRepository.getById(id);
    }

    public List<Employee> getEmployeeByGender(Gender gender) {
        return employeeRepository.getByGender(gender);
    }

    public List<Employee> getAllEmployee() {
        return employeeRepository.getAll();
    }

    public Employee updateEmployee(Integer id, Employee request) {
        Employee preEmployee = employeeRepository.getById(id);
        if (preEmployee == null) return null;
        String name = request.getName() == null ? preEmployee.getName() : request.getName();
        Integer age = request.getAge() == null ? preEmployee.getAge() : request.getAge();
        Gender gender = request.getGender() == null ? preEmployee.getGender() : request.getGender();
        Double salary = request.getSalary() == null ? preEmployee.getSalary() : request.getSalary();
        Employee newEmployee = new Employee(id, name, age, gender, salary);
        return employeeRepository.update(newEmployee);
    }

    public void deleteEmployeeById(Integer id) {
        employeeRepository.deleteById(id);
    }

    public List<Employee> getAllByPageSize(Integer pageNumber, Integer pageSize) {
        return employeeRepository.getByPageSize(pageNumber, pageSize);
    }
}
```

---

### ✅ Step 5: Repository Layer

#### `EmployeeRepository.java`

```java
@Repository
public class EmployeeRepository {
    private final List<Employee> employees = new ArrayList<>();

    public EmployeeRepository() {
        initData();
    }

    private void initData() {
        this.employees.add(new Employee(1, "John Smith", 32, Gender.MALE, 5000.0));
        this.employees.add(new Employee(2, "Jane Johnson", 28, Gender.FEMALE, 6000.0));
        this.employees.add(new Employee(3, "David Williams", 35, Gender.MALE, 5500.0));
        this.employees.add(new Employee(4, "Emily Brown", 23, Gender.FEMALE, 4500.0));
        this.employees.add(new Employee(5, "Michael Jones", 40, Gender.MALE, 7000.0));
    }

    public Employee save(Employee employee) {
        Employee newEmployee = new Employee(this.employees.size() + 1, employee.getName(), employee.getAge(),
                employee.getGender(), employee.getSalary());
        employees.add(newEmployee);
        return newEmployee;
    }

    public Employee getById(Integer id) {
        return employees.stream().filter(e -> e.getId().equals(id)).findFirst().orElse(null);
    }

    public List<Employee> getByGender(Gender gender) {
        return employees.stream().filter(e -> e.getGender().equals(gender)).toList();
    }

    public List<Employee> getAll() {
        return employees;
    }

    public Employee update(Employee newEmployee) {
        return employees.stream().filter(e -> e.getId().equals(newEmployee.getId()))
                .findFirst().map(e -> updateEmployeeAttr(newEmployee, e)).orElse(null);
    }

    private Employee updateEmployeeAttr(Employee newEmployee, Employee employee) {
        if (newEmployee.getName() != null) employee.setName(newEmployee.getName());
        if (newEmployee.getAge() != null) employee.setAge(newEmployee.getAge());
        if (newEmployee.getGender() != null) employee.setGender(newEmployee.getGender());
        if (newEmployee.getSalary() != null) employee.setSalary(newEmployee.getSalary());
        return employee;
    }

    public void deleteById(Integer id) {
        employees.removeIf(e -> e.getId().equals(id));
    }

    public List<Employee> getByPageSize(Integer pageNumber, Integer pageSize) {
        return employees.stream().skip((long)(pageNumber - 1) * pageSize).limit(pageSize).toList();
    }
}
```

---

## 🎯 Outcome

By the end of this demo, trainees should:

* Understand separation of concerns in Spring Boot.
* Be able to implement basic CRUD APIs using Controller, Service, Repository.
* Apply Java 8 streams and list manipulation.
* Build and run using `mvn spring-boot:run`.
