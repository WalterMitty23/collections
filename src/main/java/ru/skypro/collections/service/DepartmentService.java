package ru.skypro.collections.service;

import org.springframework.stereotype.Service;
import ru.skypro.collections.exception.EmployeeNotFoundException;
import ru.skypro.collections.model.Employee;
import ru.skypro.collections.service.implementation.EmployeeService;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class DepartmentService {
    private final EmployeeService employeeService;

    public DepartmentService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    public Employee findMaxSalary(int department) {
        return employeeService.getAll().stream()
                .filter(e -> e.getDepartment() == department)
                .max(Comparator.comparingInt(Employee::getSalary))
                .orElseThrow(() -> new EmployeeNotFoundException("Employee is not found"));
    }

    public Employee findMinSalary(int department) {
        return employeeService.getAll().stream()
                .filter(e -> e.getDepartment() == department)
                .min(Comparator.comparingInt(Employee::getSalary))
                .orElseThrow(() -> new EmployeeNotFoundException("Employee is not found"));
    }

    public Collection<Employee> findByDepartment(int department) {
        return employeeService.getAll().stream()
                .filter(e -> e.getDepartment() == department)
                .collect(Collectors.toList());

    }

    public Map<Integer, List<Employee>> groupByDepartment() {
        return employeeService.getAll().stream()
                .collect(Collectors.groupingBy(e -> e.getDepartment()));
    }

}
