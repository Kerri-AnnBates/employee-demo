package com.lambda.apidemo.services;

import com.lambda.apidemo.models.Email;
import com.lambda.apidemo.models.Employee;
import com.lambda.apidemo.models.EmployeeTitles;
import com.lambda.apidemo.models.JobTitle;
import com.lambda.apidemo.repositories.EmployeeRepository;
import com.lambda.apidemo.repositories.JobTitleRepository;
import com.lambda.apidemo.views.EmpNameCountJobs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service(value = "employeeService") // needed to name this implementation as the service to use
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeerepos;

    @Autowired
    private JobTitleRepository jtrepos;

    @Transactional
    @Override
    public Employee save(Employee employee) {
        Employee newEmployee = new Employee();

        if (employee.getEmployeeid() != 0) {
            employeerepos.findById(employee.getEmployeeid())
                    .orElseThrow(() -> new EntityNotFoundException("Employee " + employee.getEmployeeid() + " not found!"));

            newEmployee.setEmployeeid(employee.getEmployeeid());
        }

        newEmployee.setName(employee.getName());
        newEmployee.setSalary(employee.getSalary());

        newEmployee.getJobnames().clear();
        for (EmployeeTitles jt : employee.getJobnames()) {
            JobTitle newJT = jtrepos.findById(jt.getJobname().getJobtitleid())
                    .orElseThrow(() -> new EntityNotFoundException("JobTitle " + jt.getJobname().getTitle() + " not found!"));

            newEmployee.getJobnames().add(new EmployeeTitles(newEmployee, newJT, "Stumps"));
        }

        newEmployee.getEmails().clear();
        for (Email e : employee.getEmails()) {
            Email newEmail = new Email();
            newEmail.setEmail(e.getEmail());
            newEmail.setEmployee(newEmployee);

            newEmployee.getEmails().add(newEmail);
        }

        return employeerepos.save(newEmployee);
    }

    @Transactional
    @Override
    public Employee update(Employee employee, long id) {
        Employee currentEmployee = employeerepos.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employee " + id + " not found!"));

        if (employee.getName() != null) {
           currentEmployee.setName(employee.getName());
        }

        if (employee.hasvalueforsalary) {
            currentEmployee.setSalary(employee.getSalary());
        }

        if (employee.getJobnames().size() > 0) {
            currentEmployee.getJobnames().clear();

            for (EmployeeTitles jt : employee.getJobnames()) {
                JobTitle newjt = jtrepos.findById(jt.getJobname().getJobtitleid())
                        .orElseThrow(() -> new EntityNotFoundException("Job Title " + jt.getJobname().getJobtitleid() + " not found!"));

                currentEmployee.getJobnames().add(new EmployeeTitles(currentEmployee, newjt, "Stump"));
            }
        }

        if (employee.getEmails().size() > 0) {
            currentEmployee.getEmails().clear();

            for (Email e : employee.getEmails()) {
                Email newEmail = new Email();
                newEmail.setEmployee(currentEmployee);
                newEmail.setEmail(e.getEmail());

                currentEmployee.getEmails().add(newEmail);
            }
        }

        return employeerepos.save(currentEmployee);
    }

    @Transactional
    @Override
    public void delete(long id) {
        if (employeerepos.findById(id).isPresent()) {
            employeerepos.deleteById(id);
        } else {
            throw new EntityNotFoundException("Employee " + id + " not found!");
        }
    }

    @Override
    public List<Employee> findAllEmployees() {
        List<Employee> list = new ArrayList<>();
        employeerepos.findAll().iterator().forEachRemaining(list::add);

        return list;
    }

    @Override
    public List<Employee> findEmployeeNameContaining(String subname) {
        return employeerepos.findByNameContainingIgnoreCase(subname);
    }

    @Override
    public List<Employee> findEmployeeEmailContaining(String subemail) {
        return employeerepos.findByEmails_EmailContainingIgnoreCase(subemail);
    }

    @Override
    public List<EmpNameCountJobs> getEmpNameCountJobs() {
        return employeerepos.getCountEmpJobs();
    }


}
