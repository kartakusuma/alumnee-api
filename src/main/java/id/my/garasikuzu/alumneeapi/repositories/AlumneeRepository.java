package id.my.garasikuzu.alumneeapi.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import id.my.garasikuzu.alumneeapi.entities.Alumnee;

public interface AlumneeRepository extends JpaRepository<Alumnee, Integer> {
    List<Alumnee> findAllByIsActiveTrue();
    Optional<Alumnee> findByIdAndIsActiveTrue(int id);
}
