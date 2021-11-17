package com.lambda.apidemo.services;

import com.lambda.apidemo.models.Employee;
import com.lambda.apidemo.views.EmpNameCountJobs;

import java.util.List;

public interface EmployeeService {
    Employee save(Employee employee);
    Employee update(Employee employee, long id);
    void delete(long id);
    List<Employee> findAllEmployees();
    List<Employee> findEmployeeNameContaining(String subname);
    List<Employee> findEmployeeEmailContaining(String subemail);
    List<EmpNameCountJobs> getEmpNameCountJobs();
}
