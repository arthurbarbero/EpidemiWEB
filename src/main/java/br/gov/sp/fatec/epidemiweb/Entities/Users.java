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
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonView;

import br.gov.sp.fatec.epidemiweb.Controller.View;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "usr_users", schema = "users")
public class Users {
    @Id
    @JsonView({View.Users.class, View.DiseaseIncidences.class, View.IncidenceComplete.class})
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user", columnDefinition = "Serial")
    private Integer id;

    @JsonView({View.Users.class, View.DiseaseIncidences.class, View.IncidenceComplete.class})
    @Column(name="st_name", nullable=false)
    private String name;

    @JsonView({View.Users.class, View.DiseaseIncidences.class, View.IncidenceComplete.class})
    @Column(name="st_email", nullable=false, unique=true)
    private String email;
    
    @Column(name="st_password", nullable=false)
    private String password;

    @JsonView(View.Users.class)
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_address")
    private Address address;

    @JsonView(View.Users.class)
    @Column(name="created_at", nullable=true)
    private LocalDate createdAt;

    @JsonView(View.Users.class)
    @Column(name="updated_at", nullable=true)
    private LocalDate updateAt;

    @JsonView(View.Users.class)
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_groups", schema = "users",
        joinColumns = { @JoinColumn(name = "id_user") },
        inverseJoinColumns = { @JoinColumn(name = "id_group")})
    private Set<Group> groups;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private Set<Incidence> incidences;

    public Users() {
    }

    public Users(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.address = new Address();
        this.createdAt = LocalDate.now();
        this.updateAt = LocalDate.now();
        this.groups = new HashSet<Group>();
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

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Address getAddress() {
        return this.address;
    }

    public void setAddress(Address address) {
        this.address = address;
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

    public Set<Group> getGroups() {
        return this.groups;
    }

    public void setGroups(Set<Group> Groups) {
        this.groups = Groups;
    }

    public Set<Incidence> getIncidences() {
        return this.incidences;
    }

    public void setIncidences(Set<Incidence> incidences) {
        this.incidences = incidences;
    }

}
