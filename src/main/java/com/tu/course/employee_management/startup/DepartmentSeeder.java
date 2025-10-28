package com.tu.course.employee_management.startup;

import com.tu.course.employee_management.model.Department;
import com.tu.course.employee_management.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Order(0)
public class DepartmentSeeder implements CommandLineRunner {
    private final DepartmentRepository departmentRepository;

    @Override
    public void run(String... args) throws Exception {
        Department department1 = new Department("construction");
        Department department2 = new Department("development");
        Department department3 = new Department("accounting");

        departmentRepository.save(department1);
        departmentRepository.save(department2);
        departmentRepository.save(department3);

        System.out.println("--- 3 Departments Were Seeded ---");
    }
}
