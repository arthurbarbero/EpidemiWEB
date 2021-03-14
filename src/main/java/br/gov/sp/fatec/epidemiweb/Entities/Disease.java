package br.gov.sp.fatec.epidemiweb.Entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.FetchType;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "business.dse_disease")
public class Disease {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_disease")
    private Integer id;

    @Column(name="st_name", nullable=false)
    private String name;

    @Column(name="created_at", nullable=true)
    private LocalDateTime createdAt;

    @Column(name="updated_at", nullable=true)
    private LocalDateTime updateAt;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="business.disease_symptoms",
        joinColumns = { @JoinColumn(name="id_disease", referencedColumnName = "fk_disease") },
        inverseJoinColumns = { @JoinColumn(name="id_symptom", referencedColumnName = "fk_symptom") })
    private Set<Symptom> symptoms;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "disease")
    private Set<Incidence> incidences;


    public Disease(String name, LocalDateTime createdAt, LocalDateTime updateAt, Set<Symptom> symptoms, Set<Incidence> incidences) {
        this.name = name;
        this.createdAt = createdAt;
        this.updateAt = updateAt;
        this.symptoms = symptoms;
        this.incidences = incidences;
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

    public Set<Symptom> getSymptoms() {
        return this.symptoms;
    }

    public void setSymptoms(Set<Symptom> symptoms) {
        this.symptoms = symptoms;
    }

    public Set<Incidence> getIncidences() {
        return this.incidences;
    }

    public void setIncidences(Set<Incidence> incidences) {
        this.incidences = incidences;
    }

}
