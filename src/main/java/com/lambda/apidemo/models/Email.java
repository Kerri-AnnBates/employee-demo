package com.lambda.apidemo.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "emails")
public class Email extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long emailid;

    private String email;

    @ManyToOne
    @JoinColumn(name = "employeeid", nullable = false)
    @JsonIgnoreProperties(value = "emails")
    private Employee employee;

    public Email() {}

    public Email(String email, Employee employee) {
        this.email = email;
        this.employee = employee;
    }

    public long getEmailid() {
        return emailid;
    }

    public void setEmailid(long emailid) {
        this.emailid = emailid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
