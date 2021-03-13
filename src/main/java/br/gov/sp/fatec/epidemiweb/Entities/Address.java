package br.gov.sp.fatec.epidemiweb.Entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Set;

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

    @OneToMany(mappedBy = "address")
    private Set<User> users;


    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSt_address() {
        return this.st_address;
    }

    public void setSt_address(String st_address) {
        this.st_address = st_address;
    }

    public int getNm_number() {
        return this.nm_number;
    }

    public void setNm_number(int nm_number) {
        this.nm_number = nm_number;
    }

    public String getSt_complement() {
        return this.st_complement;
    }

    public void setSt_complement(String st_complement) {
        this.st_complement = st_complement;
    }

    public String getSt_district() {
        return this.st_district;
    }

    public void setSt_district(String st_district) {
        this.st_district = st_district;
    }

    public String getSt_city() {
        return this.st_city;
    }

    public void setSt_city(String st_city) {
        this.st_city = st_city;
    }

    public String getSt_state() {
        return this.st_state;
    }

    public void setSt_state(String st_state) {
        this.st_state = st_state;
    }

    public String getSt_country() {
        return this.st_country;
    }

    public void setSt_country(String st_country) {
        this.st_country = st_country;
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
