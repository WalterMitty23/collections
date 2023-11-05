package ru.skypro.collections.service;

import ru.skypro.collections.model.Employee;

public interface EmployeeService {

    Employee add(String firstName, String lastName);
    void remove(String firstName, String lastName);
    Employee find(String firstName, String lastName);
}
