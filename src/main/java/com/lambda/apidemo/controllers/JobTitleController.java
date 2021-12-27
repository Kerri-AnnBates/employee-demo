package com.lambda.apidemo.controllers;

import com.lambda.apidemo.models.JobTitle;
import com.lambda.apidemo.services.JobTitleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/jobtitles")
public class JobTitleController {
    @Autowired
    JobTitleService jobTitleService;

    @PutMapping(value = "/jobtitle/{id}", consumes = {"application/json"})
    public ResponseEntity<?> putUpdateJobTitle(@PathVariable long id, @Valid @RequestBody JobTitle jobTitle) {
        jobTitle = jobTitleService.update(id, jobTitle);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping(value = "/jobtitle/{id}", consumes = {"application/json"})
    public ResponseEntity<?> patchUpdateJobTitle(@PathVariable long id, @Valid @RequestBody JobTitle jobTitle) {
        jobTitle = jobTitleService.update(id, jobTitle);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
