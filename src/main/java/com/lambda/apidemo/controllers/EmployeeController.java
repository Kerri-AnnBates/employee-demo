package com.lambda.apidemo.controllers;

import com.lambda.apidemo.models.Employee;
import com.lambda.apidemo.services.EmployeeService;
import com.lambda.apidemo.views.EmpNameCountJobs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping(value = "/employees", produces = "application/json")
    public ResponseEntity<?> listAllEmployees() {
        List<Employee> myEmployees = employeeService.findAllEmployees();

        return new ResponseEntity<>(myEmployees, HttpStatus.OK);
    }

    @GetMapping(value = "/employeename/{subname}", produces = "application/json")
    public ResponseEntity<?> listEmployeesWithName(@PathVariable String subname) {
        List<Employee> myEmployees = employeeService.findEmployeeNameContaining(subname);

        return new ResponseEntity<>(myEmployees, HttpStatus.OK);
    }

    @GetMapping(value = "/employeeemail/{subemail}", produces = "application/json")
    public ResponseEntity<?> listEmployeesWithEmail(@PathVariable String subemail) {
        List<Employee> myEmployees = employeeService.findEmployeeEmailContaining(subemail);

        return new ResponseEntity<>(myEmployees, HttpStatus.OK);
    }

    @GetMapping(value = "/job/counts", produces = "application/json")
    public ResponseEntity<?> getEmpJobCounts() {
        List<EmpNameCountJobs> myEmployees = employeeService.getEmpNameCountJobs();

        return  new ResponseEntity<>(myEmployees, HttpStatus.OK);
    }

    @PostMapping(value = "/employee", consumes= {"application/json"})
    public ResponseEntity<?> addNewEmployee(@Valid @RequestBody Employee newEmployee ) {
        newEmployee.setEmployeeid(0);
        newEmployee = employeeService.save(newEmployee);

        // Create the location header
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newEmployeeURI = ServletUriComponentsBuilder.fromCurrentRequestUri()// get the URI for this request
                .path("/{employeeid}") // add to it a path variable
                .buildAndExpand(newEmployee.getEmployeeid()) // populate that path variable with the newly created employee
                .toUri(); // convert that work into a human readable URI
        responseHeaders.setLocation(newEmployeeURI);

        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
    }

    @PutMapping(value = "/employee/{employeeid}", consumes = {"application/json"})
    public ResponseEntity<?> updateFullEmployee(@Valid @RequestBody Employee updateEmployee, @PathVariable long employeeid) {
        updateEmployee.setEmployeeid(employeeid);
        employeeService.save(updateEmployee);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
