package com.lambda.apidemo.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "employees")
@JsonIgnoreProperties(value = "hasvalueforsalary")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long employeeid;

    @Column(nullable = false, unique = true)
    private String name;

    @Transient
    public boolean hasvalueforsalary;
    private double salary;

    @ManyToMany()
    @JoinTable(name = "employeetitles",
            joinColumns = @JoinColumn(name = "employeeid"),
            inverseJoinColumns = @JoinColumn(name = "jobtitleid"))
    @JsonIgnoreProperties(value = "employees")
    Set<JobTitle> jobtitles = new HashSet<>();

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties(value = "employee")
    private List<Email> emails = new ArrayList<>();

    public Employee() {}

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

    public Set<JobTitle> getJobtitles() {
        return jobtitles;
    }

    public void setJobtitles(Set<JobTitle> jobtitles) {
        this.jobtitles = jobtitles;
    }

    public List<Email> getEmails() {
        return emails;
    }

    public void setEmails(List<Email> emails) {
        this.emails = emails;
    }
}
