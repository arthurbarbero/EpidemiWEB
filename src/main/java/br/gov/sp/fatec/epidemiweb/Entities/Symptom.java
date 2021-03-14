package br.gov.sp.fatec.epidemiweb.Entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.FetchType;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "business.sym_symptoms")
public class Symptom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_symptoms")
    private Integer id;

    @Column(name="st_name", nullable=false)
    private String name;

    @Column(name="st_description", nullable=false)
    private String description;

    @Column(name="nm_severity", nullable=false)
    private int severity;

    @Column(name="created_at", nullable=true)
    private LocalDateTime createdAt;

    @Column(name="updated_at", nullable=true)
    private LocalDateTime updateAt;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "symptoms")
    private Set<Disease> diseases;

    public Symptom(String name, String description, int severity, LocalDateTime createdAt, LocalDateTime updateAt, Set<Disease> diseases) {
        this.name = name;
        this.description = description;
        this.severity = severity;
        this.createdAt = createdAt;
        this.updateAt = updateAt;
        this.diseases = diseases;
    }


    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getSeverity() {
        return this.severity;
    }

    public void setSeverity(int severity) {
        this.severity = severity;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdateAt() {
        return this.updateAt;
    }

    public void setUpdateAt(LocalDateTime updateAt) {
        this.updateAt = updateAt;
    }

    public Set<Disease> getDiseases() {
        return this.diseases;
    }

    public void setDiseases(Set<Disease> diseases) {
        this.diseases = diseases;
    }

}
