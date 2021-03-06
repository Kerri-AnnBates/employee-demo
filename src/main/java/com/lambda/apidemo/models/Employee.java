package com.lambda.apidemo.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "employees")
@JsonIgnoreProperties(value = "hasvalueforsalary")
public class Employee extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long employeeid;

    @Column(nullable = false, unique = true)
    private String name;

    @Transient
    public boolean hasvalueforsalary;
    private double salary;

//    @ManyToMany()
//    @JoinTable(name = "employeetitles",
//            joinColumns = @JoinColumn(name = "employeeid"),
//            inverseJoinColumns = @JoinColumn(name = "jobtitleid"))
//    @JsonIgnoreProperties(value = "employees")
//    Set<JobTitle> jobtitles = new HashSet<>();

    @OneToMany(mappedBy = "emp", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties(value = "emp", allowSetters = true)
    private Set<EmployeeTitles> jobnames = new HashSet<>();

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties(value = "employee")
    private List<Email> emails = new ArrayList<>();

    public Employee() {}

    public Employee(String name, double salary) {
        this.name = name;
        this.salary = salary;
    }

    public long getEmployeeid() {
        return employeeid;
    }

    public void setEmployeeid(long employeeid) {
        this.employeeid = employeeid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        hasvalueforsalary = true;
        this.salary = salary;
    }

    public Set<EmployeeTitles> getJobnames() {
        return jobnames;
    }

    public void setJobnames(Set<EmployeeTitles> jobnames) {
        this.jobnames = jobnames;
    }

    public List<Email> getEmails() {
        return emails;
    }

    public void setEmails(List<Email> emails) {
        this.emails = emails;
    }

    // Get created date from auditable, to display in json
    public Date getCreatedDate() {
        return createdDate;
    }
}
