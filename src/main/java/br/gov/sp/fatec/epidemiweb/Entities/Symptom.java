package br.gov.sp.fatec.epidemiweb.Entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.FetchType;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "sym_symptoms", schema = "business")
public class Symptom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_symptom", columnDefinition = "Serial")
    private Integer id;

    @Column(name="st_name", nullable=false)
    private String name;

    @Column(name="st_description", nullable=false)
    private String description;

    @Column(name="nm_severity", nullable=false)
    private int severity;

    @Column(name="created_at", nullable=true)
    private LocalDate createdAt;

    @Column(name="updated_at", nullable=true)
    private LocalDate updateAt;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "symptoms")
    private Set<Disease> diseases;

    public Symptom() {

    }

    public Symptom(String name, String description, int severity) {
        this.name = name;
        this.description = description;
        this.severity = severity;
        this.createdAt = LocalDate.now();
        this.updateAt = LocalDate.now();
        this.diseases = new HashSet<Disease>();
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

    public LocalDate getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDate getUpdateAt() {
        return this.updateAt;
    }

    public void setUpdateAt(LocalDate updateAt) {
        this.updateAt = updateAt;
    }

    public Set<Disease> getDiseases() {
        return this.diseases;
    }

    public void setDiseases(Set<Disease> diseases) {
        this.diseases = diseases;
    }

}
