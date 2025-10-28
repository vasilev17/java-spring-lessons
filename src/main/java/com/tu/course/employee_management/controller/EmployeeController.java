package com.tu.course.employee_management.controller;

import com.tu.course.employee_management.dto.EmployeeRequestDTO;
import com.tu.course.employee_management.dto.EmployeeResponseDTO;
import com.tu.course.employee_management.mapper.EmployeeMapper;
import com.tu.course.employee_management.model.Employee;
import com.tu.course.employee_management.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;
    private final EmployeeMapper employeeMapper;

    @PostMapping
    public ResponseEntity<Employee> saveEmployee(@RequestBody Employee employee) {
        return new ResponseEntity<>(employeeService.saveEmployee(employee), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponseDTO> getEmployeeById(@PathVariable Long id) {
        Optional<Employee> result = employeeService.getEmployeeById(id);

        return result.isPresent() ? new ResponseEntity<>(employeeMapper.toEmployeeResponseDTO(result.get()), HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> results = employeeService.getAllEmployees();
        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployeeById(@PathVariable Long id) {
        try {
            employeeService.deleteEmployee(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/{id}/firstName")
    public ResponseEntity<Employee> patchEmployeeFirstName(@PathVariable Long id, @RequestParam String newFirstName) {
        try {
            Employee updatedEmployee = employeeService.patchEmployeeFirstName(id, newFirstName);
            return new ResponseEntity<>(updatedEmployee, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeResponseDTO> putEmployee(@PathVariable Long id, @RequestBody EmployeeRequestDTO employeeRequestDTO) {
        try {
            Employee updatedEmployee = employeeService.putEmployee(id, employeeRequestDTO);
            return new ResponseEntity<>(employeeMapper.toEmployeeResponseDTO(updatedEmployee), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{employeeId}/departmentViaQuery")
    public ResponseEntity<Void> deleteEmployeeDepartmentViaQuery(@PathVariable Long employeeId) {
        try {
            employeeService.deleteEmployeeDepartmentViaQuery(employeeId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{employeeId}/department/{departmentId}")
    public ResponseEntity<Employee> assignEmployeeDepartment(@PathVariable Long employeeId, @PathVariable Long departmentId) {
        try {
            return new ResponseEntity<>(employeeService.assignEmployeeDepartment(employeeId, departmentId), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/filter/byPhoneNumberStartSequence")
    public ResponseEntity<List<Employee>> getEmployeesByPhoneNumberSequence(@RequestParam String phoneNumberStartSequence) {
        return new ResponseEntity<>(employeeService.getEmployeesWithPhoneNumberStartingWith(phoneNumberStartSequence), HttpStatus.OK);
    }

    @GetMapping("/filter/bySameLastNameAndDepartment")
    public ResponseEntity<List<Employee>> getEmployeesWithSameLastNameAndDepartment(@RequestParam String searchedLastName) {
        return new ResponseEntity<>(employeeService.getEmployeesWithSameLastNameAndDepartment(searchedLastName), HttpStatus.OK);
    }
}
