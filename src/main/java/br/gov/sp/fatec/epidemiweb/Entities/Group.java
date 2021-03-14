package br.gov.sp.fatec.epidemiweb.Entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users.grp_groups")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "users.id_group")
    private Integer id;

    @Column(name="users.st_name", nullable=false)
    private String name;

    @Column(name="users.created_at", nullable=true)
    private LocalDateTime createdAt;

    @Column(name="users.updated_at", nullable=true)
    private LocalDateTime updateAt;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "groups")
    private Set<User> users;


    public Group(String name, LocalDateTime createdAt, LocalDateTime updateAt) {
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

    public Set<User> getUsers() {
        return this.users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}
