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
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "users.usr_users")
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "users.id_user")
    private Integer id;

    @Column(name="users.st_name", nullable=false)
    private String name;

    @Column(name="users.st_email", nullable=false, unique=true)
    private String email;
    
    @Column(name="users.st_password", nullable=false)
    private String password;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "users.adr_address",
        joinColumns = { @JoinColumn(name = "users.id_address") },
        inverseJoinColumns = { @JoinColumn(name = "users.id_user")})
    private Set<Address> addresses;

    @Column(name="users.created_at", nullable=true)
    private LocalDateTime createdAt;

    @Column(name="users.updated_at", nullable=true)
    private LocalDateTime updateAt;
    
}
