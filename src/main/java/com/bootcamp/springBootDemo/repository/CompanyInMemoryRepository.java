package com.bootcamp.springBootDemo.repository;

import com.bootcamp.springBootDemo.model.Company;
import com.bootcamp.springBootDemo.model.Employee;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Repository
public class CompanyInMemoryRepository implements CompanyRepository {

    private final List<Company> companies = new ArrayList<>();

    public CompanyInMemoryRepository() {
        initData();
    }

    private void initData() {
        this.companies.add(new Company(1, "Acme Corporation"));
        this.companies.add(new Company(2, "TechCom Solutions"));
        this.companies.add(new Company(3, "Global Innovators"));
        this.companies.add(new Company(4, "Stellar Enterprises"));
        this.companies.add(new Company(5, "Nexus Industries"));
    }

    @Override
    public Company save(Company company) {
        Company newCompany = new Company(this.companies.size() + 1, company.getName());
        companies.add(newCompany);
        return newCompany;
    }

    @Override
    public Company getById(Integer id) {
        return companies.stream().filter(company -> company.getId().equals(id))
                .findFirst().orElse(null);
    }

    @Override
    public List<Company> getAll() {
        return companies;
    }

    @Override
    public Company update(Company newCompany) {
        return companies.stream()
                .filter(company -> company.getId().equals(newCompany.getId()))
                .findFirst()
                .map(company -> {
                    if (newCompany.getName() != null) {
                        company.setName(newCompany.getName());
                    }
                    return company;
                })
                .orElse(null);
    }

    @Override
    public void delete(Integer id) {
        companies.removeIf(company -> company.getId().equals(id));
    }

    @Override
    public List<Employee> getEmployeesById(Integer id) {
        return Collections.emptyList();
    }

    @Override
    public List<Company> getAllByPageSize(Integer pageNumber, Integer pageSize) {
        return companies.stream().skip((long) (pageNumber - 1) * pageSize).limit(pageSize).toList();
    }
}
