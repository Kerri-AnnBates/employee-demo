package com.lambda.apidemo.services;

import com.lambda.apidemo.models.Employee;
import com.lambda.apidemo.views.EmpNameCountJobs;

import java.util.List;

public interface EmployeeService {
    Employee save(Employee employee);
    List<Employee> findAllEmployees();
    List<Employee> findEmployeeNameContaining(String subname);
    List<Employee> findEmployeeEmailContaining(String subemail);
    List<EmpNameCountJobs> getEmpNameCountJobs();
}
