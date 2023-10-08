package id.my.garasikuzu.alumneeapi.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class JobInput {
    @NotBlank
    private String company;
    @NotBlank
    private String position;
    @NotNull
    @JsonProperty("start_work")
    private LocalDate startWork;
    @JsonProperty("end_work")
    private LocalDate endWork;
    @Positive
    @JsonProperty("alumnee_id")
    private int alumneeId;
}
