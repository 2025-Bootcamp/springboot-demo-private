package com.bootcamp.springBootDemo.service;

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
