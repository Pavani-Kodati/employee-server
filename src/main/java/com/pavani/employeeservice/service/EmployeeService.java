package com.pavani.employeeservice.service;

import com.pavani.employeeservice.model.Employee;
import com.pavani.employeeservice.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Optional<Employee> getEmployeeById(Long id) {
        return employeeRepository.findById(id);
    }

    public Employee createEmployee(Employee employee) {
        if (employeeRepository.findByEmail(employee.getEmail()).isPresent()) {
            throw new RuntimeException("Employee with email already exists: " + employee.getEmail());
        }
        return employeeRepository.save(employee);
    }

    public Employee updateEmployee(Long id, Employee updated) {
        return employeeRepository.findById(id).map(emp -> {
            emp.setFirstName(updated.getFirstName());
            emp.setLastName(updated.getLastName());
            emp.setEmail(updated.getEmail());
            emp.setJobTitle(updated.getJobTitle());
            emp.setSalary(updated.getSalary());
            emp.setDepartment(updated.getDepartment());
            return employeeRepository.save(emp);
        }).orElseThrow(() -> new RuntimeException("Employee not found: " + id));
    }

    public void deleteEmployee(Long id) {
        if (!employeeRepository.existsById(id)) {
            throw new RuntimeException("Employee not found: " + id);
        }
        employeeRepository.deleteById(id);
    }

    public List<Employee> getEmployeesByDepartment(Long departmentId) {
        return employeeRepository.findByDepartmentId(departmentId);
    }

    public List<Employee> searchEmployees(String query) {
        return employeeRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(query, query);
    }
}
