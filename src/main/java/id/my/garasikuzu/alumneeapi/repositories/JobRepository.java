package id.my.garasikuzu.alumneeapi.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import id.my.garasikuzu.alumneeapi.entities.Job;

public interface JobRepository extends JpaRepository<Job, Integer> {
    List<Job> findAllByIsActiveTrue();
    Optional<Job> findByIdAndIsActiveTrue(int id);
}
