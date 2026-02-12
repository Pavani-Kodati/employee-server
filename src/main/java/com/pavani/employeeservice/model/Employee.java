package com.pavani.employeeservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "First name is required")
    @Column(nullable = false)
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Column(nullable = false)
    private String lastName;

    @Email(message = "Valid email is required")
    @Column(nullable = false, unique = true)
    private String email;

    @NotBlank(message = "Job title is required")
    private String jobTitle;

    @DecimalMin(value = "0.0", message = "Salary must be positive")
    @Column(precision = 10, scale = 2)
    private BigDecimal salary;

   @ManyToOne(fetch = FetchType.EAGER)
@JoinColumn(name = "department_id")
@JsonIgnoreProperties("employees")
private Department department;
    public Employee() {}

    public Employee(String firstName, String lastName, String email,
                    String jobTitle, BigDecimal salary, Department department) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.jobTitle = jobTitle;
        this.salary = salary;
        this.department = department;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getJobTitle() { return jobTitle; }
    public void setJobTitle(String jobTitle) { this.jobTitle = jobTitle; }
    public BigDecimal getSalary() { return salary; }
    public void setSalary(BigDecimal salary) { this.salary = salary; }
    public Department getDepartment() { return department; }
    public void setDepartment(Department department) { this.department = department; }
}
