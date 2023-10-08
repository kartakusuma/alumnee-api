package id.my.garasikuzu.alumneeapi.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AlumneeInput {
    @NotBlank
    private String name;
    @NotBlank
    @JsonProperty("birth_place")
    private String birthPlace;
    @NotNull
    @JsonProperty("date_of_birth")
    private LocalDate dateOfBirth;
    @NotBlank
    private String phone;
    @NotBlank
    @Email
    private String email;
    @Positive
    @JsonProperty("graduation_year")
    private int graduationYear;
}
