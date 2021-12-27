package com.lambda.apidemo.services;

import com.lambda.apidemo.models.JobTitle;
import com.lambda.apidemo.repositories.EmployeeRepository;
import com.lambda.apidemo.repositories.JobTitleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

@Transactional
@Service(value = "jobtitleService")
public class JobTitleServiceImpl implements JobTitleService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private JobTitleRepository jobTitleRepository;

    @Transactional
    @Override
    public JobTitle update(long id, JobTitle jt) {
        if(jt.getTitle() == null) {
            throw new EntityNotFoundException("No Title to update");
        }

        if(jt.getEmpnames().size() > 0) {
            throw new EntityExistsException("Employees are not updated through Job Titles");
        }

        jobTitleRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Job Title id " + id + " not found!"));

        jobTitleRepository.updateJobTitle("SYSTEM", id, jt.getTitle());

        return jobTitleRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Job Title id " + id + " not found!"));
    }
}
