package com.lambda.apidemo.repositories;

import com.lambda.apidemo.models.JobTitle;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

public interface JobTitleRepository extends CrudRepository<JobTitle, Long> {
    @Transactional
    @Modifying
    @Query(value = "UPDATE jobtitles SET title = :title, last_modified_by = :uname, last_modified_date = CURRENT_TIMESTAMP WHERE jobtitleid = :jobtitleid\n",
    nativeQuery = true)
    void updateJobTitle(String uname, long jobtitleid, String title);
}
