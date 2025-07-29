package com.bootcamp.springBootDemo.service;

import com.bootcamp.springBootDemo.exception.InactiveEmployeeException;
import com.bootcamp.springBootDemo.exception.InvalidEmployeeException;
import com.bootcamp.springBootDemo.model.Employee;
import com.bootcamp.springBootDemo.model.Gender;
import com.bootcamp.springBootDemo.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class EmployeeServiceTest {

    @Mock
    EmployeeRepository employeeRepository;

    @InjectMocks
    EmployeeService employeeService;


    @Test
    public void should_throw_exception_when_create_employee_with_age_below_18() {
        // given
        Employee employee = new Employee(null, "Tooyang", 16, Gender.MALE, 3000.0);

        // when/then
        assertThrows(InvalidEmployeeException.class, () -> employeeService.saveEmployee(employee));
    }

    @Test
    public void should_throw_exception_when_create_employee_with_age_above_65() {
        // given
        Employee employee = new Employee(null, "Tooyang", 66, Gender.MALE, 3000.0);

        // when/then
        InvalidEmployeeException ex = assertThrows(InvalidEmployeeException.class, () -> employeeService.saveEmployee(employee));
        assertEquals("Employee age must be between 18 and 65.", ex.getMessage());
    }

    @Test
    public void should_throw_exception_when_salary_below_20000_for_age_30_or_above() {
        // given
        Employee employee = new Employee(null, "Tooyang", 33, Gender.MALE, 15000.0);

        // when/then
        InvalidEmployeeException ex = assertThrows(InvalidEmployeeException.class, () -> employeeService.saveEmployee(employee));
        assertEquals("Employees over 30 must have a salary >= 20000.", ex.getMessage());
    }

    @Test
    public void should_create_employee_successfully_when_valid() {
        // given
        Employee employee = new Employee(null, "valid", 33, Gender.MALE, 30000.0);
        when(employeeRepository.save(employee)).thenReturn(employee);
        
        // when
        Employee savedEmployee = employeeService.saveEmployee(employee);

        // then
        assertNotNull(savedEmployee);
        assertEquals(employee.getId(), savedEmployee.getId());
    }

    @Test
    public void should_set_active_true_on_creation() {
        // given
        Employee employee = new Employee(null, "activeCheck", 33, Gender.MALE, 30000.0);

        //when
        when(employeeRepository.save(employee)).thenAnswer(invocation -> {
              Employee e = (Employee) invocation.getArguments()[0];
              assertTrue(e.getActive());
            return employee;
        });
        employeeService.saveEmployee(employee);
    }

    @Test
    public void should_set_employee_inactive_on_delete() {
        // given
        Employee employee = new Employee(1, "ToBeDeleted", 32, Gender.MALE, 25000.0);
        employee.setActive(true);
        when(employeeRepository.getById(1)).thenReturn(employee);

        // when
        employeeService.deleteEmployeeById(1);

        // then
        assertFalse(employee.getActive());
    }

    @Test
    void should_throw_exception_when_updating_inactive_employee() {
        // given
        Employee employee = new Employee(1, "InactiveEmp", 40, Gender.FEMALE, 26000.0);
        employee.setActive(false);
        when(employeeRepository.getById(1)).thenReturn(employee);

        Employee update = new Employee(null, "NewName", 40, Gender.FEMALE, 26000.0);

        // when
        // then
        InactiveEmployeeException ex = assertThrows(InactiveEmployeeException.class,
                () -> employeeService.updateEmployee(1, update));
        assertEquals("Cannot update inactive employee.", ex.getMessage());
    }

    @Test
    void should_update_active_employee_successfully() {
        // given
        Employee oldEmp = new Employee(1, "OldName", 29, Gender.MALE, 25000.0);
        oldEmp.setActive(true);

        when(employeeRepository.getById(1)).thenReturn(oldEmp);
        when(employeeRepository.update(any())).thenAnswer(invocation -> invocation.getArgument(0));

        Employee update = new Employee(null, "NewName", 29, Gender.MALE, 25000.0);
        Employee result = employeeService.updateEmployee(1, update);

        assertEquals("NewName", result.getName());}
}
