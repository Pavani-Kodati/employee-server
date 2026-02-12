package com.pavani.employeeservice;

import com.pavani.employeeservice.model.Department;
import com.pavani.employeeservice.model.Employee;
import com.pavani.employeeservice.repository.EmployeeRepository;
import com.pavani.employeeservice.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeService employeeService;

    private Employee sampleEmployee;
    private Department sampleDept;

    @BeforeEach
    void setUp() {
        sampleDept = new Department("Engineering", "Boston");
        sampleDept.setId(1L);
        sampleEmployee = new Employee("Alice", "Johnson", "alice@company.com",
                "Software Engineer", new BigDecimal("120000"), sampleDept);
        sampleEmployee.setId(1L);
    }

    @Test
    void getAllEmployees_ShouldReturnList() {
        when(employeeRepository.findAll()).thenReturn(Arrays.asList(sampleEmployee));
        List<Employee> result = employeeService.getAllEmployees();
        assertEquals(1, result.size());
        assertEquals("Alice", result.get(0).getFirstName());
    }

    @Test
    void getEmployeeById_WhenExists_ShouldReturn() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(sampleEmployee));
        Optional<Employee> result = employeeService.getEmployeeById(1L);
        assertTrue(result.isPresent());
        assertEquals("alice@company.com", result.get().getEmail());
    }

    @Test
    void createEmployee_WhenEmailUnique_ShouldSave() {
        when(employeeRepository.findByEmail(any())).thenReturn(Optional.empty());
        when(employeeRepository.save(any())).thenReturn(sampleEmployee);
        Employee result = employeeService.createEmployee(sampleEmployee);
        assertNotNull(result);
        verify(employeeRepository).save(sampleEmployee);
    }

    @Test
    void createEmployee_WhenEmailExists_ShouldThrow() {
        when(employeeRepository.findByEmail(any())).thenReturn(Optional.of(sampleEmployee));
        assertThrows(RuntimeException.class, () -> employeeService.createEmployee(sampleEmployee));
        verify(employeeRepository, never()).save(any());
    }

    @Test
    void deleteEmployee_WhenExists_ShouldDelete() {
        when(employeeRepository.existsById(1L)).thenReturn(true);
        doNothing().when(employeeRepository).deleteById(1L);
        assertDoesNotThrow(() -> employeeService.deleteEmployee(1L));
        verify(employeeRepository).deleteById(1L);
    }

    @Test
    void deleteEmployee_WhenNotExists_ShouldThrow() {
        when(employeeRepository.existsById(99L)).thenReturn(false);
        assertThrows(RuntimeException.class, () -> employeeService.deleteEmployee(99L));
    }

    @Test
    void searchEmployees_ShouldReturnMatches() {
        when(employeeRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase("ali", "ali"))
                .thenReturn(Arrays.asList(sampleEmployee));
        List<Employee> result = employeeService.searchEmployees("ali");
        assertEquals(1, result.size());
    }
}
