package com.lambda.apidemo.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "employeetitles")
public class EmployeeTitles implements Serializable {

    @Id
    @ManyToOne
    @JoinColumn(name = "employeeid")
    @JsonIgnoreProperties(value = "jobnames", allowSetters = true)
    private Employee emp;

    @Id
    @ManyToMany
    @JoinColumn(name = "jobtitles")
    @JsonIgnoreProperties(value = "empnames", allowSetters = true)
    private JobTitle jobname;

    @Column(nullable = false)
    private String manager;

    public EmployeeTitles() {
    }

    public EmployeeTitles(Employee emp, JobTitle jobname, String manager) {
        this.emp = emp;
        this.jobname = jobname;
        this.manager = manager;
    }

    public Employee getEmp() {
        return emp;
    }

    public void setEmp(Employee emp) {
        this.emp = emp;
    }

    public JobTitle getJobname() {
        return jobname;
    }

    public void setJobname(JobTitle jobname) {
        this.jobname = jobname;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }
}
