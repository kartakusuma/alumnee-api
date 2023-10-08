package id.my.garasikuzu.alumneeapi.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import id.my.garasikuzu.alumneeapi.entities.Alumnee;
import id.my.garasikuzu.alumneeapi.repositories.AlumneeRepository;

@Service
public class AlumneeService {
    
    private final AlumneeRepository alumneeRepository;

    public AlumneeService(AlumneeRepository alumneeRepository) {
        this.alumneeRepository = alumneeRepository;
    }

    public List<Alumnee> findAlumnees() {
        return alumneeRepository.findAllByIsActiveTrue();
    }

    public Optional<Alumnee> findAlumneeById(int id) {
        return alumneeRepository.findByIdAndIsActiveTrue(id);
    }

    public Alumnee createAlumnee(Alumnee alumnee) {
        return alumneeRepository.save(alumnee);
    }

    public Alumnee updateAlumnee(Alumnee alumnee, int id) {
        Optional<Alumnee> existingAlumnee = alumneeRepository.findByIdAndIsActiveTrue(id);

        if (existingAlumnee.isPresent()) {
            Alumnee updateAlumnee = existingAlumnee.get();
            updateAlumnee.setName(alumnee.getName());
            updateAlumnee.setBirthPlace(alumnee.getBirthPlace());
            updateAlumnee.setDateOfBirth(alumnee.getDateOfBirth());
            updateAlumnee.setPhone(alumnee.getPhone());
            updateAlumnee.setEmail(alumnee.getEmail());
            updateAlumnee.setGraduationYear(alumnee.getGraduationYear());
            return alumneeRepository.save(updateAlumnee);
        } else {
            return null;
        }
    }

    public boolean softDeleteAlumnee(int id) {
        Optional<Alumnee> existingAlumnee = alumneeRepository.findByIdAndIsActiveTrue(id);
        if (existingAlumnee.isPresent()) {
            Alumnee alumnee = existingAlumnee.get();
            alumnee.setActive(false);
            alumneeRepository.save(alumnee);
            return true;
        }

        return false;
    }
}
