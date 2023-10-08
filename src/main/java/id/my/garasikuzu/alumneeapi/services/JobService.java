package id.my.garasikuzu.alumneeapi.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import id.my.garasikuzu.alumneeapi.entities.Job;
import id.my.garasikuzu.alumneeapi.repositories.JobRepository;

@Service
public class JobService {
    
    private final JobRepository jobRepository;

    public JobService(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    public List<Job> findJobs() {
        return jobRepository.findAllByIsActiveTrue();
    }

    public Optional<Job> findJobById(int id) {
        return jobRepository.findByIdAndIsActiveTrue(id);
    }

    public Job createJob(Job job) {
        return jobRepository.save(job);
    }

    public Job updateJob(Job job, int id) {
        Optional<Job> existingJob = jobRepository.findByIdAndIsActiveTrue(id);

        if (existingJob.isPresent()) {
            Job updateJob = existingJob.get();
            updateJob.setCompany(job.getCompany());
            updateJob.setPosition(job.getPosition());
            updateJob.setStartWork(job.getStartWork());
            updateJob.setEndWork(job.getEndWork());
            return jobRepository.save(updateJob);
        } else {
            return null;
        }
    }

    public boolean softDeleteJob(int id) {
        Optional<Job> existingJob = jobRepository.findByIdAndIsActiveTrue(id);

        if (existingJob.isPresent()) {
            Job job = existingJob.get();
            job.setActive(false);
            jobRepository.save(job);
            return true;
        }

        return false;
    }
}
