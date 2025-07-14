package com.bootcamp.springBootDemo.repository;

import com.bootcamp.springBootDemo.model.Employee;
import com.bootcamp.springBootDemo.model.Gender;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class EmployeeRepository {

    private final List<Employee> employees = new ArrayList<>();

    public EmployeeRepository() {
        initData();
    }

    public void initData() {
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
        return employees.stream().filter(employee -> employee.getId().equals(id))
                .findFirst().orElse(null);
    }

    public List<Employee> getByGender(Gender gender) {
        return employees.stream().filter(employee -> employee.getGender().equals(gender)).toList();
    }

    public List<Employee> getAll() {
        return employees;
    }

    public Employee update(Employee newEmployee) {
        return employees.stream().filter(employee -> employee.getId().equals(newEmployee.getId()))
                .findFirst()
                .map(employee -> updateEmployeeAttr(newEmployee, employee))
                .orElse(null);
    }

    private Employee updateEmployeeAttr(Employee newEmployee, Employee employee) {
        if (newEmployee.getName() != null) {
            employee.setName(newEmployee.getName());
        }
        if (newEmployee.getAge() != null) {
            employee.setAge(newEmployee.getAge());
        }
        if (newEmployee.getGender() != null) {
            employee.setGender(newEmployee.getGender());
        }
        if (newEmployee.getSalary() != null) {
            employee.setSalary(newEmployee.getSalary());
        }
        return employee;
    }

    public void deleteById(Integer id) {
        employees.removeIf(employee -> employee.getId().equals(id));
    }


    public List<Employee> getByPageSize(Integer pageNumber, Integer pageSize) {
        return employees.stream().skip((long) (pageNumber - 1) * pageSize).limit(pageSize).toList();
    }
}
