package com.paulfolio.jobsearch.controllers;


import com.paulfolio.jobsearch.models.Job;
import com.paulfolio.jobsearch.models.Update;
import com.paulfolio.jobsearch.repos.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/jobs")
public class JobController {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private List<Job> jobsList;

    @Autowired
    private Job job;

    @ApiIgnore
    @GetMapping("/api")
    public void redirect(HttpServletResponse response) throws IOException {
        response.sendRedirect("/swagger-ui.html");
    }

    @GetMapping("/")
    public ResponseEntity<List<Job>> getAllJobs() {
        jobsList = jobRepository.findAll();
        if (jobsList.isEmpty())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

        return ResponseEntity.status(HttpStatus.CREATED).body(jobsList);

    }

    @GetMapping("/{id}/show")
    public ResponseEntity<Job> showById(@PathVariable String id) {
        Optional<Job> foundJob = jobRepository.findById(id);

        return foundJob.isPresent()?
                ResponseEntity.status(HttpStatus.CREATED).body(foundJob.get()) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build() ;
    }

    @GetMapping("/findNameLike/{name}")
    public ResponseEntity<List<Job>> findByName(@PathVariable String name) {
        jobsList = jobRepository.findByTitleContainsAllIgnoreCase(name);

        return jobsList.isEmpty()?
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build() :
                ResponseEntity.status(HttpStatus.CREATED).body(jobsList);
    }

    @PostMapping("/new")
    public ResponseEntity<Job> addJob(@RequestBody Job jobPost) {

        job = new Job(jobPost.getTitle(), jobPost.getLink());
        if (!jobPost.getUpdates().isEmpty()) job.setUpdates(jobPost.getUpdates());

        job = jobRepository.save(job);
        if (job != null)
            return ResponseEntity.status(HttpStatus.CREATED).body(job);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

    }


    @PutMapping("/{id}/edit")
    public ResponseEntity<Job> editJob(@PathVariable String id, @RequestBody Job job) {
        Optional<Job> foundJob = jobRepository.findById(id);
        foundJob.ifPresent(j  -> j.setTitle(job.getTitle().length()>1?job.getTitle():j.getTitle()) );
        foundJob.ifPresent(j  -> j.setLink(job.getLink().length()>1?job.getLink():j.getLink()) );
        foundJob.ifPresent(j  -> jobRepository.save(j) );
        return foundJob.isPresent()?
                ResponseEntity.status(HttpStatus.CREATED).body(foundJob.get()) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build() ;
    }

    @PutMapping("/{id}/addUpdate")
    public ResponseEntity<Job> updateJob(@PathVariable String id, @RequestBody Update update) {
        Optional<Job> foundJob = jobRepository.findById(id);
        foundJob.ifPresent(job -> job.addUpdate(update.getDescription()));
        foundJob.ifPresent(job -> jobRepository.save(job));
        return foundJob.isPresent()?
                ResponseEntity.status(HttpStatus.CREATED).body(foundJob.get()) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build() ;
    }

    @PutMapping("/{id}/markInactive")
    public ResponseEntity<Job> inactive(@PathVariable String id) {
        Optional<Job> foundJob = jobRepository.findById(id);
        foundJob.ifPresent(job -> {
            job.addUpdate("Marked inactive");
            job.setActive(false);
            jobRepository.save(job);
        });

        return foundJob.isPresent()?
                ResponseEntity.status(HttpStatus.CREATED).body(foundJob.get()) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build() ;
    }

    @DeleteMapping("/{id}/delete")
    public String deleteJob(@PathVariable String id) {
        jobRepository.deleteById(id);
        return "Job listing deleted";
    }
}
