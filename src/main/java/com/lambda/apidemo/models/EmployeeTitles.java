package com.lambda.apidemo.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "employeetitles")
@IdClass(EmployeeTitlesId.class)
public class EmployeeTitles implements Serializable {

    @Id
    @ManyToMany
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EmployeeTitles)) return false;

        // Check to see if the ids are equal.
        // if one of the objects happens to have a null for one of the fields
        // set the id to 0
        EmployeeTitles that = (EmployeeTitles) o;
        return ((emp == null) ? 0 : emp.getEmployeeid()) == ((that.jobname == null) ? 0 : that.jobname.getJobtitleid());
    }

    @Override
    public int hashCode() {
//        return Objects.hash(getEmp(), getJobname(), getManager());
        return 34;
    }
}
