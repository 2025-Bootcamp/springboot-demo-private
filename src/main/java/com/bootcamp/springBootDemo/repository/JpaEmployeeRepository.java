package com.bootcamp.springBootDemo.repository;

import com.bootcamp.springBootDemo.model.Employee;
import com.bootcamp.springBootDemo.model.Gender;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaEmployeeRepository extends JpaRepository<Employee, Integer> {

    List<Employee> getEmployeesByGender(Gender gender);

}
