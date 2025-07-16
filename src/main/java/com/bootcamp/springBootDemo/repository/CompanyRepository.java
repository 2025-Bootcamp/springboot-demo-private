package com.bootcamp.springBootDemo.repository;

import com.bootcamp.springBootDemo.model.Company;
import com.bootcamp.springBootDemo.model.Employee;

import java.util.List;

public interface CompanyRepository {

    Company save(Company company);

    Company getById(Integer id);

    List<Company> getAll();

    Company update(Company newCompany);

    void delete(Integer id);

    List<Employee> getEmployeesById(Integer id);

    List<Company> getAllByPageSize(Integer pageNumber, Integer pageSize);
}
