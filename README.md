# Spring Boot Teaching Outline
## Requirements
1. Employees those who are not 18 ~ 65 years old cannot be created.
2. Employees over 30 years of age (inclusive) with a salary below 20000 cannot be created.
3. Creating a new employee will set the employee's active status to true by default.
4. Deleting an employee simply sets the employee's active status to false.
5. When updating an employee, you need to verify whether the employee is active or not, if he/she has already left the company, you can't update him/her.


## Steps
### 1. tasking

| Task ID | Description                                                                                                       |
   | ------- |-------------------------------------------------------------------------------------------------------------------|
| T1      | Employee must be between 18 and 65 years old (inclusive); otherwise, creation should be rejected.                 |
| T2      | Employees aged 30 or above must have a salary ≥ 20000; otherwise, creation should be rejected.                    |
| T3      | New employees must have their `active` status set to `true` by default upon creation.                             |
| T4      | Deleting an employee should only mark them as inactive (`active = false`) instead of removing them from the list. |
| T5      | Updating an employee should only be allowed if they are active; updating an inactive employee should return error |
| T6      | Active employees should be updatable, and the updated values must be correctly saved.                             |

### 2. update Employee class to add `active` field
``` java
private Boolean active = true;
```
please remember to add getter/setter。

2. add unit test using junit5
> Use @ExtendWith(SpringExtension.class) that means please using Spring to run the test class, like auto wire bean and load spring config
> Use @InjectMocks is a Mockito annotation used in unit testing to create an instance of the class under test and automatically inject mock dependencies into it.


## Implementation
```java

package com.bootcamp.springBootDemo.service;

import com.bootcamp.springBootDemo.exception.InactiveEmployeeException;
import com.bootcamp.springBootDemo.exception.InvalidEmployeeException;
import com.bootcamp.springBootDemo.model.Employee;
import com.bootcamp.springBootDemo.model.Gender;
import com.bootcamp.springBootDemo.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

   private final EmployeeRepository employeeRepository;

   public EmployeeService(EmployeeRepository employeeRepository) {
      this.employeeRepository = employeeRepository;
   }

   public Employee saveEmployee(Employee employee) {
      if (employee.getAge() < 18 || employee.getAge() > 65) {
         throw new InvalidEmployeeException("Employee age must be between 18 and 65.");
      }

      if (employee.getAge() > 30 && employee.getSalary() < 20000.0) {
         throw new InvalidEmployeeException("Employees over 30 must have a salary >= 20000.");
      }

      return employeeRepository.save(employee);
   }

   public Employee getEmployeeById(Integer id) {
      return employeeRepository.getById(id);
   }

   public List<Employee> getEmployeeByGender(Gender gender) {
      return employeeRepository.getByGender(gender);
   }

   public List<Employee> getAllEmployee() {
      return employeeRepository.getAll();

   }

   public Employee updateEmployee(Integer id, Employee request) {
      //get original employee
      Employee preEmployee = employeeRepository.getById(id);
      if (preEmployee == null) {
         return null;
      }
      if (!preEmployee.getActive()) {
         throw new InactiveEmployeeException("Cannot update inactive employee.");
      }

      String name = request.getName() == null ? preEmployee.getName() : request.getName();
      Integer age = request.getAge() == null ? preEmployee.getAge() : request.getAge();
      Gender gender = request.getGender() == null ? preEmployee.getGender() : request.getGender();
      Double salary = request.getSalary() == null ? preEmployee.getSalary() : request.getSalary();
      Employee newEmployee = new Employee(id, name, age, gender, salary);
      return employeeRepository.update(newEmployee);

   }

   public void deleteEmployeeById(Integer id) {
      Employee employee = employeeRepository.getById(id);
      employee.setActive(false);
      employeeRepository.update(employee);
   }

   public List<Employee> getAllByPageSize(Integer pageNumber, Integer pageSize) {
      return employeeRepository.getByPageSize(pageNumber, pageSize);
   }
}
```

### Unit Test
```java

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
```
