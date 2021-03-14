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
    private String address;

    @Column(name="users.nm_number", nullable=false)
    private int number;

    @Column(name="users.st_complement")
    private String complement;

    @Column(name="users.st_district", nullable=false, unique=true)
    private String district;
    
    @Column(name="users.st_city", nullable=false)
    private String city;

    @Column(name="users.st_state", nullable=false)
    private String state;
    
    @Column(name="users.st_country", nullable=false)
    private String country;
    
    @Column(name="users.created_at")
    private LocalDateTime createdAt;

    @Column(name="users.updated_at")
    private LocalDateTime updateAt;

    public Address() {

    }

    public Address(String address, int number, String complement, String district, String city, String state, String country, LocalDateTime createdAt, LocalDateTime updateAt) {
        this.address = address;
        this.number = number;
        this.complement = complement;
        this.district = district;
        this.city = city;
        this.state = state;
        this.country = country;
        this.createdAt = createdAt;
        this.updateAt = updateAt;
    }
    

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getNumber() {
        return this.number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getComplement() {
        return this.complement;
    }

    public void setComplement(String complement) {
        this.complement = complement;
    }

    public String getDistrict() {
        return this.district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return this.state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return this.country;
    }

    public void setCountry(String country) {
        this.country = country;
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

}
