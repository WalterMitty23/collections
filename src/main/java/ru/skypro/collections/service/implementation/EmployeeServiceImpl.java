package ru.skypro.collections.service.implementation;

import org.springframework.stereotype.Service;
import ru.skypro.collections.exception.EmployeeAlreadyAddedException;
import ru.skypro.collections.exception.EmployeeNotFoundException;
import ru.skypro.collections.exception.EmployeeStorageIsFullException;
import ru.skypro.collections.model.Employee;
import ru.skypro.collections.service.EmployeeService;

import java.util.*;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final int STORAGE_SIZE = 5;

    private final Map<String, Employee> employees = new HashMap<>();

    @Override
    public Employee add(String firstName, String lastName) {
        if (employees.size() >= STORAGE_SIZE) {
            throw new EmployeeStorageIsFullException("Хранилище уже полное");
        }

        if (employees.containsKey(getKey(firstName, lastName))) {

            throw new EmployeeAlreadyAddedException("Сотрудник " + firstName + lastName + " уже в хранилище");
        }

        Employee employee = new Employee(firstName, lastName);
        employees.put(getKey(employee), employee);
        return employee;
    }

    @Override
    public Employee remove(String firstName, String lastName) {
        if (!employees.containsKey(getKey(firstName, lastName))) {

            throw new EmployeeNotFoundException("Сотрудник " + firstName + lastName + " не найден");
        }
        return employees.remove(getKey(firstName, lastName));
    }

    @Override
    public Employee find(String firstName, String lastName) {

        Employee employee = employees.get(getKey(firstName, lastName));
        if (employee == null) {
            throw new EmployeeNotFoundException("Сотрудник " + firstName + lastName + " не найден");

        }
        return employee;
    }



    @Override
    public Map<String, Employee> getAll() {
        return Collections.unmodifiableMap(employees);
    }

    private static String getKey(String firstName, String lastName) {
        return firstName + lastName;
    }

    private static String getKey(Employee employee) {
        return employee.getFirstName() + employee.getLastName();
    }
}
