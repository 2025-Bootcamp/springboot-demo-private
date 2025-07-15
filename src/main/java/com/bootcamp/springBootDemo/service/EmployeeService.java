package com.bootcamp.springBootDemo.service;

import com.bootcamp.springBootDemo.exception.InactiveEmployeeException;
import com.bootcamp.springBootDemo.exception.InvalidEmployeeException;
import com.bootcamp.springBootDemo.model.Employee;
import com.bootcamp.springBootDemo.model.Gender;
import com.bootcamp.springBootDemo.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Employee saveEmployee(Employee employee) {
        if (employee.getAge() < 18 || employee.getAge() > 65) {
            throw new InvalidEmployeeException("Employee age must be between 18 and 65.");
        }

        if (employee.getAge() > 30 && employee.getSalary() < 20000.0) {
            throw new InvalidEmployeeException("Employees over 30 must have a salary >= 20000.");
        }

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
        //get original employee
        Employee preEmployee = employeeRepository.getById(id);
        if (preEmployee == null) {
            return null;
        }
        if (!preEmployee.getActive()) {
            throw new InactiveEmployeeException("Cannot update inactive employee.");
        }

        String name = request.getName() == null ? preEmployee.getName() : request.getName();
        Integer age = request.getAge() == null ? preEmployee.getAge() : request.getAge();
        Gender gender = request.getGender() == null ? preEmployee.getGender() : request.getGender();
        Double salary = request.getSalary() == null ? preEmployee.getSalary() : request.getSalary();
        Employee newEmployee = new Employee(id, name, age, gender, salary);
        return employeeRepository.update(newEmployee);

    }

    public void deleteEmployeeById(Integer id) {
        Employee employee = employeeRepository.getById(id);
        employee.setActive(false);
        employeeRepository.update(employee);
    }

    public List<Employee> getAllByPageSize(Integer pageNumber, Integer pageSize) {
        return employeeRepository.getByPageSize(pageNumber, pageSize);
    }
}
