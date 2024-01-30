package ru.skypro.collections.service.implementation;

import org.springframework.stereotype.Service;
import ru.skypro.collections.exception.EmployeeAlreadyAddedException;
import ru.skypro.collections.exception.EmployeeNotFoundException;
import ru.skypro.collections.exception.EmployeeStorageIsFullException;
import ru.skypro.collections.model.Employee;

import java.util.Collection;

import java.util.*;

@Service
public class EmployeeService {

    private static final int MAX_COUNT = 10;
    private final Map<String, Employee> employees = new HashMap<>(MAX_COUNT);
    public Employee add(String firstName, String lastName, int salary, int department) throws EmployeeAlreadyAddedException {
        if (employees.size() >= MAX_COUNT) {
            throw new EmployeeStorageIsFullException("Employee storage is full");
        }
        var key = makeKey(firstName, lastName);
        if (employees.containsKey(key)) {
            throw new EmployeeAlreadyAddedException("Employee has already added");
        }
        Employee employee = new Employee(firstName, lastName, salary, department);
        employees.put(key, employee);
        return employee;
    }

    public void remove(String firstName, String lastName) {
        var key = makeKey(firstName, lastName);
        var removed = employees.remove(key);
        if (removed == null) {
            throw new EmployeeNotFoundException("Employee is not found");
        }
    }

    public Employee find(String firstName, String lastName) {

        var key = makeKey(firstName, lastName);
        var employee = employees.get(key);
        if (employee == null) {
            throw new EmployeeNotFoundException("Сотрудник " + firstName + " " + lastName + " не найден");
        }
        return employee;
    }

    private static String makeKey(String firstName, String lastName) {
        return (firstName + "_" + lastName).toLowerCase();

    }


    public Collection<Employee> getAll() {
        return Collections.unmodifiableCollection(employees.values());
    }

    private static String getKey(String firstName, String lastName) {
        return firstName + lastName;
    }

    private static String getKey(Employee employee) {
        return employee.getFirstName() + employee.getLastName();
    }
}
