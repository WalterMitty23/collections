package ru.skypro.collections.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.skypro.collections.model.Employee;
import ru.skypro.collections.service.implementation.EmployeeService;

import java.util.Collection;


@RestController
@RequestMapping("/employee")
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/add")
    public void add(@RequestParam String firstName,
                    @RequestParam String lastName,
                    @RequestParam int salary,
                    @RequestParam int department) {
        employeeService.add(firstName, lastName, salary, department);
    }

    @GetMapping("/remove")
    public void remove(@RequestParam String firstName, @RequestParam String lastName) {
        employeeService.remove(firstName, lastName);
    }

    @GetMapping("/find")
    public void find(@RequestParam String firstName, @RequestParam String lastName) {
        employeeService.find(firstName, lastName);
    }

    @GetMapping("/all")
    public Collection<Employee> getAll() {
        return employeeService.getAll();
    }
}
