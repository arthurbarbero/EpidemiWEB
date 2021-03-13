package br.gov.sp.fatec.epidemiweb.Entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "users.adr_address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "users.id_address")
    private Integer id;

    @Column(name="users.st_address", nullable=false)
    private String st_address;

    @Column(name="users.nm_number", nullable=false)
    private int nm_number;

    @Column(name="users.st_complement")
    private String st_complement;

    @Column(name="users.st_district", nullable=false, unique=true)
    private String st_district;
    
    @Column(name="users.st_city", nullable=false)
    private String st_city;

    @Column(name="users.st_state", nullable=false)
    private String st_state;
    
    @Column(name="users.st_country", nullable=false)
    private String st_country;
    
    @Column(name="users.created_at")
    private LocalDateTime createdAt;

    @Column(name="users.updated_at")
    private LocalDateTime updateAt;
}
