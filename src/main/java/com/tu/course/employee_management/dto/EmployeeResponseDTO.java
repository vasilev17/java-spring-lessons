package com.tu.course.employee_management.dto;

public record EmployeeResponseDTO(
        Long id,
        String firstName,
        String lastName,
        String phoneNumber,
        Long departmentId,
        String departmentName
) {}
