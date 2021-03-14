package br.gov.sp.fatec.epidemiweb.Entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "grp_groups", schema = "users")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_group", columnDefinition = "Serial")
    private Integer id;

    @Column(name="st_name", nullable=false)
    private String name;

    @Column(name="created_at", nullable=true)
    private LocalDate createdAt;

    @Column(name="updated_at", nullable=true)
    private LocalDate updateAt;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "groups")
    private Set<User> users;

    public Group() {
    }

    public Group(String name, LocalDate createdAt, LocalDate updateAt) {
        this.name = name;
        this.createdAt = createdAt;
        this.updateAt = updateAt;
        this.users = new HashSet<User>();
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

    public Set<User> getUsers() {
        return this.users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}
