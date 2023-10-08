package id.my.garasikuzu.alumneeapi.entities;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "alumnees")
public class Alumnee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    @JsonProperty("birth_place")
    private String birthPlace;
    @JsonProperty("date_of_birth")
    private LocalDate dateOfBirth;
    private String phone;
    private String email;
    @JsonProperty("graduation_year")
    private int graduationYear;

    @OneToMany(mappedBy = "alumnee", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Job> jobs;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false, updatable = false)
    @JsonProperty("created_at")
    private Date createdAt;
    
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    @JsonProperty("updated_at")
    private Date updatedAt;

    @Column(name = "is_active", columnDefinition = "boolean not null default true")
    @JsonProperty("is_active")
    private boolean isActive;
}
