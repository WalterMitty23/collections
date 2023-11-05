package ru.skypro.collections.service.implementation;

import org.springframework.stereotype.Service;
import ru.skypro.collections.exception.EmployeeAlreadyAddedException;
import ru.skypro.collections.exception.EmployeeNotFoundException;
import ru.skypro.collections.exception.EmployeeStorageIsFullException;
import ru.skypro.collections.model.Employee;
import ru.skypro.collections.service.EmployeeService;

import java.util.ArrayList;
import java.util.List;
@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final int STORAGE_SIZE = 5;

    private final List<Employee> employees = new ArrayList<>();

    @Override
    public Employee add(String firstName, String lastName) {
        if (employees.size() >= STORAGE_SIZE) {
            throw new EmployeeStorageIsFullException("Хранилище уже полное");
        }

        Employee employee = new Employee(firstName, lastName);
        if (employees.contains(employee)) {
            throw new EmployeeAlreadyAddedException("Сотрудник " + firstName + lastName + " уже в хранилище");
        }

        employees.add(employee);
        return employee;
    }

    @Override
    public Employee remove(String firstName, String lastName) {
        Employee employee = new Employee(firstName, lastName);
        if (!employees.contains(employee)) {
            throw new EmployeeNotFoundException("Сотрудник " + firstName + lastName + " не найден");
        }
        employees.remove(employee);
        return employee;
    }

    @Override
    public Employee find(String firstName, String lastName) {
        Employee requestedEmployee = new Employee(firstName, lastName);
        for (Employee employee : employees) {
            if (employee.equals(requestedEmployee)) {
                return requestedEmployee;
            }
        }
        throw new EmployeeNotFoundException("Сотрудник " + firstName + lastName + " не найден");

    }

    @Override
    public List<Employee> getAll() {
        return new ArrayList<>(employees);
    }

}
