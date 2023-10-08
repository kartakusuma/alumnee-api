package id.my.garasikuzu.alumneeapi.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import id.my.garasikuzu.alumneeapi.dto.DataResponse;
import id.my.garasikuzu.alumneeapi.dto.JobInput;
import id.my.garasikuzu.alumneeapi.entities.Alumnee;
import id.my.garasikuzu.alumneeapi.entities.Job;
import id.my.garasikuzu.alumneeapi.services.AlumneeService;
import id.my.garasikuzu.alumneeapi.services.JobService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/jobs")
public class JobController {
    
    private final AlumneeService alumneeService;
    private final JobService jobService;

    public JobController(JobService jobService, AlumneeService alumneeService) {
        this.jobService = jobService;
        this.alumneeService = alumneeService;
    }

    @GetMapping
    public ResponseEntity<DataResponse> getJobs() {
        List<Job> jobs = jobService.findJobs();

        DataResponse dataResponse = new DataResponse(HttpStatus.OK.value(), "success", jobs);

        return new ResponseEntity<>(dataResponse, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DataResponse> getJobById(@PathVariable int id) {
        Optional<Job> job = jobService.findJobById(id);

        DataResponse dataResponse = new DataResponse();

        if (job.isPresent()) {
            dataResponse.setCode(HttpStatus.OK.value());
            dataResponse.setStatus("success");
            dataResponse.setData(job.get());

            return new ResponseEntity<>(dataResponse, HttpStatus.OK);
        } else {
            dataResponse.setCode(HttpStatus.BAD_REQUEST.value());
            dataResponse.setStatus("error");

            return new ResponseEntity<>(dataResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<DataResponse> createJob(@RequestBody @Valid JobInput jobInput) {
        Optional<Alumnee> existingAlumnee = alumneeService.findAlumneeById(jobInput.getAlumneeId());

        DataResponse dataResponse = new DataResponse();

        if (existingAlumnee.isPresent()) {
            Job job = new Job();
            job.setCompany(jobInput.getCompany());
            job.setPosition(jobInput.getPosition());
            job.setStartWork(jobInput.getStartWork());
            job.setEndWork(job.getEndWork());
            job.setAlumnee(existingAlumnee.get());
            job.setActive(true);

            Job storedJob = jobService.createJob(job);

            if (storedJob != null) {
                dataResponse.setCode(HttpStatus.CREATED.value());
                dataResponse.setStatus("success");
                dataResponse.setData(storedJob);

                return new ResponseEntity<>(dataResponse, HttpStatus.CREATED);
            } else {
                dataResponse.setCode(HttpStatus.NOT_FOUND.value());
                dataResponse.setStatus("error");
                
                return new ResponseEntity<>(dataResponse, HttpStatus.NOT_FOUND);
            }
        } else {
            dataResponse.setCode(HttpStatus.NOT_FOUND.value());
            dataResponse.setStatus("error");
            
            return new ResponseEntity<>(dataResponse, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<DataResponse> updateJob(@RequestBody @Valid JobInput jobInput, @PathVariable int id) {
        Optional<Alumnee> existingAlumnee = alumneeService.findAlumneeById(jobInput.getAlumneeId());

        DataResponse dataResponse = new DataResponse();

        if (existingAlumnee.isPresent()) {
            Job job = new Job();
            job.setCompany(jobInput.getCompany());
            job.setPosition(jobInput.getPosition());
            job.setStartWork(jobInput.getStartWork());
            job.setEndWork(jobInput.getEndWork());
            job.setAlumnee(existingAlumnee.get());

            Job updatedJob = jobService.updateJob(job, id);

            if (updatedJob != null) {
                dataResponse.setCode(HttpStatus.OK.value());
                dataResponse.setStatus("success");
                dataResponse.setData(updatedJob);
                return new ResponseEntity<>(dataResponse, HttpStatus.OK);
            } else {
                dataResponse.setCode(HttpStatus.NOT_FOUND.value());
                dataResponse.setStatus("error");
                
                return new ResponseEntity<>(dataResponse, HttpStatus.NOT_FOUND);
            }
        } else {
            dataResponse.setCode(HttpStatus.NOT_FOUND.value());
            dataResponse.setStatus("error");
                
            return new ResponseEntity<>(dataResponse, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<DataResponse> deleteJob(@PathVariable int id) {
        DataResponse dataResponse = new DataResponse();

        boolean deleted = jobService.softDeleteJob(id);

        if (deleted) {
            dataResponse.setCode(HttpStatus.OK.value());
            dataResponse.setStatus("success");

            return new ResponseEntity<>(dataResponse, HttpStatus.OK);
        } else {
            dataResponse.setCode(HttpStatus.NOT_FOUND.value());
            dataResponse.setStatus("error");

            return new ResponseEntity<>(dataResponse, HttpStatus.NOT_FOUND);
        }
    }
}
