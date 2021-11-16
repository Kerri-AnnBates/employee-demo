package com.lambda.apidemo.repositories;

import com.lambda.apidemo.models.JobTitle;
import org.springframework.data.repository.CrudRepository;

public interface JobTitleRepository extends CrudRepository<JobTitle, Long> {
}
