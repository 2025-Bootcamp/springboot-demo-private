package com.bootcamp.springBootDemo.repository;

import com.bootcamp.springBootDemo.model.Employee;
import com.bootcamp.springBootDemo.model.Gender;

import java.util.List;

public interface EmployeeRepository {

    Employee save(Employee employee);

    Employee getById(Integer id);

    List<Employee> getByGender(Gender gender);

    List<Employee> getAll();

    Employee update(Employee newEmployee);

    void deleteById(Integer id);

    List<Employee> getByPageSize(Integer pageNumber, Integer pageSize);
}
