package com.lambda.apidemo.services;

import com.lambda.apidemo.models.JobTitle;

public interface JobTitleService {
    JobTitle update(long id, JobTitle jt);
}
