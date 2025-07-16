package com.bootcamp.springBootDemo.repository;

import com.bootcamp.springBootDemo.model.Employee;
import com.bootcamp.springBootDemo.model.Gender;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EmployeeDBRepository implements EmployeeRepository {

    JpaEmployeeRepository repository;

    public EmployeeDBRepository(JpaEmployeeRepository repository) {
        this.repository = repository;
    }

    @Override
    public Employee save(Employee employee) {
        return repository.save(employee);
    }

    @Override
    public Employee getById(Integer id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<Employee> getByGender(Gender gender) {
        return repository.getEmployeesByGender(gender);
    }

    @Override
    public List<Employee> getAll() {
        return repository.findAll();
    }

    @Override
    public Employee update(Employee newEmployee) {
        return repository.save(newEmployee);
    }

    @Override
    public void deleteById(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public List<Employee> getByPageSize(Integer pageNumber, Integer pageSize) {
        return repository.findAll(PageRequest.of(pageNumber - 1, pageSize)).getContent();
    }
}
