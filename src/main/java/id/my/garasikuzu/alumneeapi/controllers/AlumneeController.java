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

import id.my.garasikuzu.alumneeapi.dto.AlumneeInput;
import id.my.garasikuzu.alumneeapi.dto.DataResponse;
import id.my.garasikuzu.alumneeapi.entities.Alumnee;
import id.my.garasikuzu.alumneeapi.services.AlumneeService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/alumnees")
public class AlumneeController {

    private final AlumneeService alumneeService;

    public AlumneeController(AlumneeService alumneeService) {
        this.alumneeService = alumneeService;
    }
    
    @GetMapping
    public ResponseEntity<DataResponse> getAlumnee() {
        List<Alumnee> alumnees = alumneeService.findAlumnees();

        DataResponse dataResponse = new DataResponse(HttpStatus.OK.value(), "success", alumnees);

        return new ResponseEntity<>(dataResponse, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DataResponse> getAlumneeById(@PathVariable int id) {
        Optional<Alumnee> alumnee = alumneeService.findAlumneeById(id);

        DataResponse dataResponse = new DataResponse();

        if (alumnee.isPresent()) {
            dataResponse.setCode(HttpStatus.OK.value());
            dataResponse.setStatus("success");
            dataResponse.setData(alumnee.get());

            return new ResponseEntity<>(dataResponse, HttpStatus.OK);
        } else {
            dataResponse.setCode(HttpStatus.NOT_FOUND.value());
            dataResponse.setStatus("error");
            return new ResponseEntity<>(dataResponse, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<DataResponse> createAlumnee(@RequestBody @Valid AlumneeInput alumneeInput) {
        Alumnee alumnee = new Alumnee();
        alumnee.setName(alumneeInput.getName());
        alumnee.setBirthPlace(alumneeInput.getBirthPlace());
        alumnee.setDateOfBirth(alumneeInput.getDateOfBirth());
        alumnee.setPhone(alumneeInput.getPhone());
        alumnee.setEmail(alumneeInput.getEmail());
        alumnee.setGraduationYear(alumneeInput.getGraduationYear());
        alumnee.setActive(true);

        Alumnee storedAlumnee = alumneeService.createAlumnee(alumnee);

        DataResponse dataResponse = new DataResponse();

        if (storedAlumnee != null) {
            dataResponse.setCode(HttpStatus.CREATED.value());
            dataResponse.setStatus("success");
            dataResponse.setData(storedAlumnee);
            return new ResponseEntity<>(dataResponse, HttpStatus.CREATED);
        }

        dataResponse.setCode(HttpStatus.BAD_REQUEST.value());
        dataResponse.setStatus("error");
        return new ResponseEntity<>(dataResponse, HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<DataResponse> updateAlumnee(@RequestBody @Valid AlumneeInput alumneeInput, @PathVariable int id) {
        Alumnee alumnee = new Alumnee();
        alumnee.setName(alumneeInput.getName());
        alumnee.setBirthPlace(alumneeInput.getBirthPlace());
        alumnee.setDateOfBirth(alumneeInput.getDateOfBirth());
        alumnee.setPhone(alumneeInput.getPhone());
        alumnee.setEmail(alumneeInput.getEmail());
        alumnee.setGraduationYear(alumneeInput.getGraduationYear());

        Alumnee updatedAlumnee = alumneeService.updateAlumnee(alumnee, id);

        DataResponse dataResponse = new DataResponse();

        if (updatedAlumnee != null) {
            dataResponse.setCode(HttpStatus.OK.value());
            dataResponse.setStatus("success");
            dataResponse.setData(updatedAlumnee);
            return new ResponseEntity<>(dataResponse, HttpStatus.OK);
        }

        dataResponse.setCode(HttpStatus.BAD_REQUEST.value());
        dataResponse.setStatus("error");
        return new ResponseEntity<>(dataResponse, HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<DataResponse> deleteAlumnee(@PathVariable int id) {
        DataResponse dataResponse = new DataResponse();

        boolean deleted = alumneeService.softDeleteAlumnee(id);

        if (deleted) {
            dataResponse.setCode(HttpStatus.OK.value());
            dataResponse.setStatus("success");
            return new ResponseEntity<>(dataResponse, HttpStatus.OK);
        }

        dataResponse.setCode(HttpStatus.NOT_FOUND.value());
        dataResponse.setStatus("error");
        return new ResponseEntity<>(dataResponse, HttpStatus.NOT_FOUND);
    }
}
