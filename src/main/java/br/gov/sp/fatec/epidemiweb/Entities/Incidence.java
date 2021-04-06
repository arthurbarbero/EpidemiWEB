package br.gov.sp.fatec.epidemiweb.Entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.FetchType;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonView;

import br.gov.sp.fatec.epidemiweb.Controller.View;

import java.time.LocalDate;

@Entity
@Table(name = "inc_incidence", schema = "business")
public class Incidence {

    @JsonView({View.DiseaseIncidences.class, View.IncidenceResumed.class})
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_incidence", columnDefinition = "Serial")
    private Integer id;

    @JsonView({View.DiseaseIncidences.class, View.IncidenceResumed.class})
    @Column(name="dt_incidence", nullable=true)
    private LocalDate incidenceDate;

    @Column(name="created_at", nullable=true)
    private LocalDate createdAt;

    @Column(name="updated_at", nullable=true)
    private LocalDate updateAt;

    @JsonView(View.IncidenceComplete.class)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_disease")
    private Disease disease;

    @JsonView({View.DiseaseIncidences.class, View.IncidenceComplete.class})
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_user")
    private User user;
    
    public Incidence() {

    }

    public Incidence(LocalDate incidenceDate, Disease disease, User user) {
        this.incidenceDate = incidenceDate;
        this.createdAt = LocalDate.now();
        this.updateAt = LocalDate.now();
        this.disease = disease;
        this.user = user;
    }


    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getIncidenceDate() {
        return this.incidenceDate;
    }

    public void setIncidenceDate(LocalDate incidenceDate) {
        this.incidenceDate = incidenceDate;
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

    public Disease getDisease() {
        return this.disease;
    }

    public void setDisease(Disease disease) {
        this.disease = disease;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
