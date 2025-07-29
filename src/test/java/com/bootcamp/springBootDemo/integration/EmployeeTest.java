package com.bootcamp.springBootDemo.integration;

import com.bootcamp.springBootDemo.model.Employee;
import com.bootcamp.springBootDemo.model.Gender;
import com.bootcamp.springBootDemo.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeTest {

    @Autowired
    private MockMvc client;

    @Autowired
    private EmployeeRepository employeeRepository;

    @BeforeEach
    public void setup() {
        employeeRepository.getAll().clear();
        employeeRepository.save(new Employee(1, "John Smith", 32, Gender.MALE, 5000.0));
        employeeRepository.save(new Employee(2, "Jane Johnson", 28, Gender.FEMALE, 6000.0));
        employeeRepository.save(new Employee(3, "David Williams", 35, Gender.MALE, 5500.0));
        employeeRepository.save(new Employee(4, "Emily Brown", 23, Gender.FEMALE, 4500.0));
        employeeRepository.save(new Employee(5, "Michael Jones", 40, Gender.MALE, 7000.0));
    }

    @Test
    public void should_return_employees_when_get_all_employees_exist() throws Exception {
        // given
        List<Employee> givenEmployees = employeeRepository.getAll();

        // when
        ResultActions perform = client.perform(MockMvcRequestBuilders.get("/employees"));

        // then
        perform.andExpect(MockMvcResultMatchers.status().isOk());
        perform.andExpect(MockMvcResultMatchers.jsonPath("$.[0].id").value(givenEmployees.get(0).getId()));
        perform.andExpect(MockMvcResultMatchers.jsonPath("$.[0].name").value(givenEmployees.get(0).getName()));
        perform.andExpect(MockMvcResultMatchers.jsonPath("$.[0].age").value(givenEmployees.get(0).getAge()));
        perform.andExpect(MockMvcResultMatchers.jsonPath("$.[0].gender").value(givenEmployees.get(0).getGender().name()));
        perform.andExpect(MockMvcResultMatchers.jsonPath("$.[0].salary").value(givenEmployees.get(0).getSalary()));
        perform.andExpect(MockMvcResultMatchers.jsonPath("$.[1].id").value(givenEmployees.get(1).getId()));
        perform.andExpect(MockMvcResultMatchers.jsonPath("$.[2].id").value(givenEmployees.get(2).getId()));
        perform.andExpect(MockMvcResultMatchers.jsonPath("$.[3].id").value(givenEmployees.get(3).getId()));
        perform.andExpect(MockMvcResultMatchers.jsonPath("$.[4].id").value(givenEmployees.get(4).getId()));
    }

    // please implement the rest of employee apis

    @Test
    public void should_create_employee_when_post_employee_given_valid_request() throws Exception {
        // given
        String newEmployeeJson = """
                    {
                        "name": "Alice Cooper",
                        "age": 30,
                        "gender": "FEMALE",
                        "salary": 25000.0
                    }
                """;

        // when
        ResultActions result = client.perform(MockMvcRequestBuilders.post("/employees")
                .contentType("application/json")
                .content(newEmployeeJson));

        // then
        result.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Alice Cooper"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(30))
                .andExpect(MockMvcResultMatchers.jsonPath("$.gender").value("FEMALE"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.salary").value(25000.0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.active").value(true));
    }

    @Test
    public void should_update_employee_when_put_employee_given_valid_id() throws Exception {
        // given
        String updateJson = """
                    {
                        "name": "John Smith",
                        "age": 35,
                        "gender": "MALE",
                        "salary": 5500.0
                    }
                """;

        // when
        ResultActions result = client.perform(MockMvcRequestBuilders.put("/employees/1")
                .contentType("application/json")
                .content(updateJson));

        // then
        result.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(35))
                .andExpect(MockMvcResultMatchers.jsonPath("$.salary").value(5500.0));
    }

    @Test
    public void should_delete_employee_when_delete_employee_given_valid_id() throws Exception {
        // when
        ResultActions result = client.perform(MockMvcRequestBuilders.delete("/employees/1"));

        // then
        result.andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}