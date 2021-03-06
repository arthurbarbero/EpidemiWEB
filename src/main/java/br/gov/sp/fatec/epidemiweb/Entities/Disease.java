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

import com.fasterxml.jackson.annotation.JsonView;

import br.gov.sp.fatec.epidemiweb.Controller.View;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "dse_disease", schema = "business")
public class Disease {

    @JsonView({View.DiseaseResume.class, View.IncidenceComplete.class, View.SymptomCompleted.class})
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_disease", columnDefinition = "Serial")
    private Integer id;

    @JsonView({View.DiseaseResume.class, View.IncidenceComplete.class, View.SymptomCompleted.class})
    @Column(name="st_name", nullable=false)
    private String name;

    @Column(name="created_at", nullable=true)
    private LocalDate createdAt;

    @Column(name="updated_at", nullable=true)
    private LocalDate updateAt;

    @JsonView({View.DiseaseSymptons.class, View.IncidenceComplete.class})
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="disease_symptoms", schema = "business",
        joinColumns = { @JoinColumn(name="id_disease") },
        inverseJoinColumns = { @JoinColumn(name="id_symptom") })
    private Set<Symptom> symptoms;

    @JsonView(View.DiseaseIncidences.class)
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "disease")
    private Set<Incidence> incidences;

    public Disease() {
        
    }

    public Disease(String name) {
        this.name = name;
        this.createdAt = LocalDate.now();
        this.updateAt = LocalDate.now();
        this.symptoms = new HashSet<Symptom>();
        this.incidences = new HashSet<Incidence>();
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
