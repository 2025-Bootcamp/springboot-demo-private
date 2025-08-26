package com.bootcamp.springBootDemo;

import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

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
    public Employee getById(@PathVariable Integer id) {
        return employees.get(id);
    }

    @GetMapping(params = {"gender"})
    public List<Employee> getByGender(@RequestParam("gender") String genderParam) {
        if (genderParam == null || genderParam.trim().isEmpty()) {
            return new ArrayList<>(employees.values());
        }
        Gender gender = Gender.valueOf(genderParam.toUpperCase());
        return employees.values().stream().filter(employee -> employee.getGender().equals(gender))
                .toList();
    }

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
