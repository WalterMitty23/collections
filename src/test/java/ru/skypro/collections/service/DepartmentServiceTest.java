package ru.skypro.collections.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.skypro.collections.exception.EmployeeNotFoundException;
import ru.skypro.collections.model.Employee;
import ru.skypro.collections.service.implementation.EmployeeService;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DepartmentServiceTest {
    @Mock
    EmployeeService employeeService;

    @InjectMocks
    DepartmentService departmentService;

    @BeforeEach
    void setUp() {
        var employees = List.of(
                new Employee("test1", "test1", 1_000, 1),
                new Employee("test2", "test2", 10_000, 1),
                new Employee("test3", "test3", 2_000, 2),
                new Employee("test4", "test4", 20_000, 2),
                new Employee("test5", "test5", 200_000, 2),
                new Employee("test6", "test6", 300_000, 3)
        );
        when(employeeService.getAll()).thenReturn(employees);
    }

    @Test
    void testDepartmentMaxSalary() {
        assertThat(departmentService.findMaxSalary(1)).isEqualTo(new Employee("test2", "test2", 10_000, 1));
        assertThat(departmentService.findMaxSalary(3)).isEqualTo(new Employee("test6", "test6", 300_000, 3));
        assertThrows(EmployeeNotFoundException.class, () -> departmentService.findMaxSalary(1111));
    }

    @Test
    void testDepartmentMinSalary() {
        Employee actual1 = departmentService.findMinSalary(1);
        assertThat(actual1).isEqualTo(new Employee("test1", "test1", 1_000, 1));

        Employee actual2 = departmentService.findMinSalary(3);
        assertThat(actual2).isEqualTo(new Employee("test6", "test6", 300_000, 3));

        assertThrows(EmployeeNotFoundException.class, () -> departmentService.findMinSalary(1111));
    }

    @Test
    void testFindByDepartment() {
        var actual = departmentService.findByDepartment(2);
        assertThat(actual).containsExactlyInAnyOrder(
                new Employee("test3", "test3", 2_000, 2),
                new Employee("test4", "test4", 20_000, 2),
                new Employee("test5", "test5", 200_000, 2)
        );
    }

    @Test
    void testGroupByDepartment() {
        var actual = departmentService.groupByDepartment();
        var expected = Map.of(
                1, List.of(new Employee("test1", "test1", 1_000, 1),
                        new Employee("test2", "test2", 10_000, 1)),
                2, List.of(new Employee("test3", "test3", 2_000, 2),
                        new Employee("test4", "test4", 20_000, 2),
                        new Employee("test5", "test5", 200_000, 2)),
                3, List.of(new Employee("test6", "test6", 300_000, 3))
        );
        assertThat(actual).isEqualTo(expected);
    }




}