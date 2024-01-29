package ru.skypro.collections.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.skypro.collections.model.Employee;
import ru.skypro.collections.service.DepartmentService;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/departments")
public class DepartmentController {
    private final DepartmentService service;

    public DepartmentController(DepartmentService service) {
        this.service = service;
    }

    @GetMapping("/max-salary")
    public Employee max(@RequestParam int department) {
        return service.findMaxSalary(department);
    }

    @GetMapping("/min-salary")
    public Employee min(@RequestParam int department) {
        return service.findMinSalary(department);
    }

    @GetMapping("/all-department")
    public Collection<Employee> findAllByDepartment(@RequestParam int department) {
        return service.findByDepartment(department);
    }

    @GetMapping("/all")
    public Map<Integer, List<Employee>> groupBy() {
        return service.groupByDepartment();
    }
}
