package com.bootcamp.springBootDemo.repository;

import com.bootcamp.springBootDemo.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaCompanyRepository extends JpaRepository<Company, Integer> {
}
