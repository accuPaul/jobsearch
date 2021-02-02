package com.paulfolio.jobsearch.repos;

import com.paulfolio.jobsearch.models.Job;
import com.paulfolio.jobsearch.models.Update;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JobRepository extends MongoRepository<Job, String> {


    public List<Job> findByTitleContainsAllIgnoreCase(String title);

}
