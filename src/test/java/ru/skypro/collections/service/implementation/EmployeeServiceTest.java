package ru.skypro.collections.service.implementation;

import org.junit.jupiter.api.Test;
import ru.skypro.collections.exception.EmployeeAlreadyAddedException;
import ru.skypro.collections.exception.EmployeeNotFoundException;
import ru.skypro.collections.exception.EmployeeStorageIsFullException;
import ru.skypro.collections.exception.WrongNameException;
import ru.skypro.collections.model.Employee;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class EmployeeServiceTest {

    EmployeeService employeeService = new EmployeeService();
    void testAdd() {
        var actual1 = employeeService.find("test", "TESTTEST");
        assertThat(actual1).isNotNull();
        assertThat(actual1.getFirstName()).isEqualTo("Test");
        assertThat(actual1.getLastName()).isEqualTo("Testtest");
        assertThat(actual1.getDepartment()).isEqualTo(1);
        assertThat(actual1.getSalary()).isEqualTo(1_000);

        var actual2 = employeeService.find("TESTTEST", "TESTESTtest");
        assertThat(actual2).isNotNull();
        assertThat(actual2.getFirstName()).isEqualTo("TEsTEST");
        assertThat(actual2.getLastName()).isEqualTo("TEstteStTest");
        assertThat(actual2.getDepartment()).isEqualTo(3);
        assertThat(actual2.getSalary()).isEqualTo(2_000);
    }

    @Test
    void testAddDuplicate() {
        employeeService.add("test", "testtest", 1_000, 1);
        assertThrows(EmployeeAlreadyAddedException.class, () -> employeeService.add("test", "testtest", 2_000, 2));
    }

    @Test
    void testFull() {
        employeeService.add("test", "testtest", 1_000, 1);
        employeeService.add("testt", "testtest", 1_000, 1);
        employeeService.add("testtt", "testtest", 1_000, 1);
        employeeService.add("testttt", "testtest", 1_000, 1);
        employeeService.add("testtttt", "testtest", 1_000, 1);
        employeeService.add("testttttt", "testtest", 1_000, 1);
        employeeService.add("testtttttt", "testtest", 1_000, 1);
        employeeService.add("testttttttt", "testtest", 1_000, 1);
        employeeService.add("testtttttttt", "testtest", 1_000, 1);
        employeeService.add("testttttttttt", "testtest", 1_000, 1);
        assertThrows(EmployeeStorageIsFullException.class, () ->
                employeeService.add("testtest", "testtest", 1_000, 1));
    }

    @Test
    void testWrongName() {
        assertThrows(WrongNameException.class, () ->
                employeeService.add("test666", "test_test", 1_000, 1));
        assertThrows(WrongNameException.class, () ->
                employeeService.add("666test",
                "test_test", 1_000, 1));
        assertThrows(WrongNameException.class, () ->
                employeeService.add("test",
                "999testtest", 1_000, 1));
        assertThrows(WrongNameException.class, () ->
                employeeService.add("test*",
                "testtest999", 1_000, 1));
    }

    @Test
    void testGetAll() {
        employeeService.add("testt", "testtest", 1_000, 1);
        employeeService.add("testtt", "testtest", 2_000, 2);

        var actual = employeeService.getAll();
        assertThat(actual).containsExactlyInAnyOrder(
                new Employee("Testt", "Testtest", 1_000, 1),
                new Employee("Testtt", "Testtest", 2_000, 2)
        );

    }
    @Test
    void testNotFound() {
        assertThrows(EmployeeNotFoundException.class, () -> employeeService.find("foo", "bar"));
    }

    @Test
    void testRemove() {
        assertThrows(EmployeeNotFoundException.class, () -> employeeService.remove("foo", "bar"));

        employeeService.add("testt", "testtest", 1_000, 1);
        employeeService.add("testtt", "testtest", 2_000, 2);
        var actual = employeeService.find("testt", "testtest");
        assertThat(actual).isNotNull();
        employeeService.remove("testt", "testtest");
        assertThrows(EmployeeNotFoundException.class, () -> employeeService.find("testt", "testtest"));
    }

}