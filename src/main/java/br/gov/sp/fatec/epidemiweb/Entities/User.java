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
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users.usr_users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    private Integer id;

    @Column(name="st_name", nullable=false)
    private String name;

    @Column(name="st_email", nullable=false, unique=true)
    private String email;
    
    @Column(name="st_password", nullable=false)
    private String password;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_address", referencedColumnName = "id_address")
    private Address address;

    @Column(name="created_at", nullable=true)
    private LocalDateTime createdAt;

    @Column(name="updated_at", nullable=true)
    private LocalDateTime updateAt;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users.users_groups",
        joinColumns = { @JoinColumn(name = "id_user", referencedColumnName = "fk_user") },
        inverseJoinColumns = { @JoinColumn(name = "id_group", referencedColumnName = "fk_group")})
    private Set<Group> groups;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private Set<Incidence> incidences;


    public User(String name, String email, String password, LocalDateTime createdAt, LocalDateTime updateAt) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.address = new Address();
        this.createdAt = createdAt;
        this.updateAt = updateAt;
        this.groups = new HashSet<Group>();
        this.incidences = new HashSet<Incidence>();
    }


    public User() {
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
