package com.tu.course.employee_management.dto;

public record EmployeeRequestDTO(
        String firstName,
        String lastName,
        String phoneNumber,
        String departmentName
) {}
