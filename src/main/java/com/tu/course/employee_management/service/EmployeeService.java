package com.tu.course.employee_management.service;

import com.tu.course.employee_management.dto.EmployeeRequestDTO;
import com.tu.course.employee_management.model.Department;
import com.tu.course.employee_management.model.Employee;
import com.tu.course.employee_management.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final DepartmentService departmentService;

    @Transactional
    public Employee saveEmployee(Employee employee) {
        Department requestDepartment = employee.getDepartment();

        if (requestDepartment != null) {
            Optional<Department> fetchedDepartment = departmentService.getDepartmentByName(requestDepartment.getName());
            Department department = fetchedDepartment.orElseGet(() -> departmentService.saveDepartment(requestDepartment));
            employee.setDepartment(department);
        }

        return employeeRepository.save(employee);
    }

    public Optional<Employee> getEmployeeById(Long id) {
        return employeeRepository.findById(id);
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Transactional
    public void deleteEmployee(Long employeeId) {
        if (employeeRepository.existsById(employeeId)) {
            employeeRepository.deleteById(employeeId);
        } else {
            throw new EmptyResultDataAccessException("Employee not found with ID: " + employeeId, 1);
        }
    }

    @Transactional
    public Employee patchEmployeeFirstName(Long employeeId, String newFirstName) {
        Employee employee = getEmployeeOrThrow(employeeId);
        employee.setFirstName(newFirstName);
        return employeeRepository.save(employee);
    }

    @Transactional
    public Employee putEmployee(Long employeeId, EmployeeRequestDTO employeeRequestDTO) {
        Employee existingEmployee = getEmployeeOrThrow(employeeId);
        String requestDepartmentName = employeeRequestDTO.departmentName();

        existingEmployee.setFirstName(employeeRequestDTO.firstName());
        existingEmployee.setLastName(employeeRequestDTO.lastName());
        existingEmployee.setPhoneNumber(employeeRequestDTO.phoneNumber());

        if (requestDepartmentName != null) {
            Optional<Department> fetchedDepartment = departmentService.getDepartmentByName(requestDepartmentName);
            Department department = fetchedDepartment.orElseGet(() -> departmentService.saveDepartment(new Department(requestDepartmentName)));
            existingEmployee.setDepartment(department);
        }

        return employeeRepository.save(existingEmployee);
    }

    @Transactional
    public void deleteEmployeeDepartmentViaQuery(Long employeeId) {
        int rowsAffected = employeeRepository.removeDepartment(employeeId);
        if (rowsAffected == 0) throw new NoSuchElementException("Employee with ID: " + employeeId + " not found!");
    }

    @Transactional
    public Employee assignEmployeeDepartment(Long employeeId, Long departmentId) {
        Employee employee = getEmployeeOrThrow(employeeId);
        Department department = departmentService.getDepartmentOrThrow(departmentId);

        employee.setDepartment(department);
        return employeeRepository.save(employee);
    }

    private Employee getEmployeeOrThrow(Long id) {
        return employeeRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Employee with ID: " + id + " not found!"));
    }

    public List<Employee> getEmployeesWithPhoneNumberStartingWith(String phoneNumberStartSequence) {
        return employeeRepository.findByPhoneNumberStartingWith(phoneNumberStartSequence);
    }

    public List<Employee> getEmployeesWithSameLastNameAndDepartment(String searchedLastName) {
        return employeeRepository.findEmployeesWithSameLastNameAndDepartment(searchedLastName);
    }
}
