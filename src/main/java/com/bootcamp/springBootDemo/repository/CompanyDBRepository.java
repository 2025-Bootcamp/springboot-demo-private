package com.bootcamp.springBootDemo.repository;

import com.bootcamp.springBootDemo.model.Company;
import com.bootcamp.springBootDemo.model.Employee;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CompanyDBRepository implements CompanyRepository {

    private final JpaCompanyRepository repository;

    public CompanyDBRepository(JpaCompanyRepository repository) {
        this.repository = repository;
    }

    @Override
    public Company save(Company company) {
        return repository.save(company);
    }

    @Override
    public Company getById(Integer id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<Company> getAll() {
        return repository.findAll();
    }

    @Override
    public Company update(Company newCompany) {
        return repository.save(newCompany);
    }

    @Override
    public void delete(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public List<Employee> getEmployeesById(Integer id) {
        return List.of();
    }

    @Override
    public List<Company> getAllByPageSize(Integer pageNumber, Integer pageSize) {
        return repository.findAll(PageRequest.of(pageNumber - 1, pageSize)).getContent();
    }
}
