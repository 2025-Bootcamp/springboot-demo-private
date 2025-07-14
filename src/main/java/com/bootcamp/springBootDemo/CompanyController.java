package com.bootcamp.springBootDemo;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/companies")
public class CompanyController {

    private final HashMap<Long, Company> companies = new HashMap<>(Map.of(
            1L, new Company(1L, "Acme Corporation", List.of(
                    new Employee(1L, "John Smith", 32, Gender.MALE, 5000.0),
                    new Employee(2L, "Jane Johnson", 28, Gender.FEMALE, 6000.0)
            )),
            2L, new Company(2L, "TechCom Solutions", List.of(
                    new Employee(3L, "David Williams", 35, Gender.MALE, 5500.0),
                    new Employee(4L, "Emily Brown", 23, Gender.FEMALE, 4500.0),
                    new Employee(5L, "Michael Jones", 40, Gender.MALE, 7000.0)
            )),
            3L, new Company(3L, "Global Innovators"),
            4L, new Company(4L, "Stellar Enterprises"),
            5L, new Company(5L, "Nexus Industries")
    ));

    @GetMapping("/{id}")
    public Company getById(@PathVariable Long id) {
        return companies.get(id);
    }

    @GetMapping
    public List<Company> getAll() {
        return companies.values().stream().toList();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Company create(@RequestBody Company request) {
        final var newId = (long) (companies.size() + 1);
        Company newCompany = new Company(newId, request.getName(), request.getEmployees());
        companies.put(newId, newCompany);
        return newCompany;
    }

    @PutMapping("/{id}")
    public Company update(@PathVariable Long id, @RequestBody Company request) {
        if (id == null) {
            return null;
        }
        Company preCompany = companies.get(id);
        String name = request.getName() == null ? preCompany.getName() : request.getName();
        List<Employee> employees = request.getEmployees() == null ? preCompany.getEmployees()
                : request.getEmployees();
        Company newCompany = new Company(id, name, employees);
        companies.replace(id, newCompany);
        return newCompany;
    }

    @DeleteMapping(path = {"/{id}"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        companies.remove(id);
    }

    @GetMapping("/{id}/employees")
    public List<Employee> getEmployeesById(@PathVariable Long id) {
        return companies.get(id).getEmployees();
    }

    @GetMapping(params = {"pageNumber", "pageSize"})
    public List<Company> getAllByPageSize(@RequestParam Integer pageNumber,
                                          @RequestParam Integer pageSize) {
        return companies.values().stream().skip((long) (pageNumber - 1) * pageSize)
                .limit(pageSize).toList();
    }
}
