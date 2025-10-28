package com.tu.course.employee_management.startup;

import com.tu.course.employee_management.model.Department;
import com.tu.course.employee_management.model.Employee;
import com.tu.course.employee_management.repository.DepartmentRepository;
import com.tu.course.employee_management.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

@Profile("dev")
@Component
@RequiredArgsConstructor
@Order(1)
public class EmployeeSeeder implements CommandLineRunner {
    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;

    @Override
    public void run(String... args) {

        Employee employee1 = Employee.builder()
                .firstName("Valentin")
                .lastName("Vasilev")
                .phoneNumber("0882471592")
                .department(departmentRepository.findById(2L).orElseThrow(() -> new NoSuchElementException("Couldn't set seeded employee department!")))
                .build();

        Employee employee2 = Employee.builder()
                .firstName("Ivan")
                .lastName("Petrov")
                .phoneNumber("0895821747")
                .build();

        Employee employee3 = Employee.builder()
                .firstName("Johnny")
                .lastName("Bravo")
                .phoneNumber("0885721654")
                .build();

        employeeRepository.save(employee1);
        employeeRepository.save(employee2);
        employeeRepository.save(employee3);

        System.out.println("--- 3 Employees Were Seeded ---");

    }
}
