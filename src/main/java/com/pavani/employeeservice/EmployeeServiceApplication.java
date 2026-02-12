package com.pavani.employeeservice;

import com.pavani.employeeservice.model.Department;
import com.pavani.employeeservice.model.Employee;
import com.pavani.employeeservice.repository.DepartmentRepository;
import com.pavani.employeeservice.repository.EmployeeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;

@SpringBootApplication
public class EmployeeServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmployeeServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner loadData(DepartmentRepository deptRepo, EmployeeRepository empRepo) {
        return args -> {
            Department engineering = deptRepo.save(new Department("Engineering", "Boston, MA"));
            Department finance = deptRepo.save(new Department("Finance", "New York, NY"));
            Department hr = deptRepo.save(new Department("Human Resources", "Chicago, IL"));

            empRepo.save(new Employee("Alice", "Johnson", "alice.j@company.com", "Senior Software Engineer", new BigDecimal("145000"), engineering));
            empRepo.save(new Employee("Bob", "Smith", "bob.s@company.com", "Backend Developer", new BigDecimal("120000"), engineering));
            empRepo.save(new Employee("Carol", "Williams", "carol.w@company.com", "Financial Analyst", new BigDecimal("110000"), finance));
            empRepo.save(new Employee("David", "Brown", "david.b@company.com", "HR Manager", new BigDecimal("95000"), hr));
            empRepo.save(new Employee("Eva", "Davis", "eva.d@company.com", "DevOps Engineer", new BigDecimal("135000"), engineering));
        };
    }
}
