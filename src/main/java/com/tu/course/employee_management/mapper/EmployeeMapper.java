package com.tu.course.employee_management.mapper;

import com.tu.course.employee_management.dto.EmployeeResponseDTO;
import com.tu.course.employee_management.model.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface EmployeeMapper {

    @Mapping(target = "departmentId", source = "department.id")
    @Mapping(target = "departmentName", source = "department.name")
    EmployeeResponseDTO toEmployeeResponseDTO (Employee employee);
}
