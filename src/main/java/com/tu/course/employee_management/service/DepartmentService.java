package com.tu.course.employee_management.service;

import com.tu.course.employee_management.model.Department;
import com.tu.course.employee_management.repository.DepartmentRepository;
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
public class DepartmentService {
    private final DepartmentRepository departmentRepository;

    public Optional<Department> getDepartmentByName(String name) {
        return departmentRepository.findByName(name);
    }

    public Optional<Department> getDepartmentById(Long id) {
        return departmentRepository.findById(id);
    }

    @Transactional
    public Department saveDepartment(Department department) {
        return departmentRepository.save(department);
    }

    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    @Transactional
    public void deleteDepartment(Long id) {
        if (departmentRepository.existsById(id)) departmentRepository.deleteById(id);
        else throw new EmptyResultDataAccessException("No department with ID: " + id + " was found!", 1);
    }

    public Department getDepartmentOrThrow(Long id) {
        return departmentRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Department with ID: " + id + " not found!"));
    }
}
