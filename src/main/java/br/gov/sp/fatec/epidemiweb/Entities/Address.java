package br.gov.sp.fatec.epidemiweb.Entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonView;

import br.gov.sp.fatec.epidemiweb.Controller.View;

import java.time.LocalDate;

@Entity
@Table(name = "adr_address", schema = "users")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_address", columnDefinition = "Serial")
    private Integer id;

    @JsonView(View.Users.class)
    @Column(name="st_address", nullable=false)
    private String address;

    @JsonView(View.Users.class)
    @Column(name="nm_number", nullable=false)
    private int number;

    @JsonView(View.Users.class)
    @Column(name="st_complement")
    private String complement;

    @JsonView(View.Users.class)
    @Column(name="st_district", nullable=false, unique=true)
    private String district;
    
    @JsonView(View.Users.class)
    @Column(name="st_city", nullable=false)
    private String city;

    @JsonView(View.Users.class)
    @Column(name="st_state", nullable=false)
    private String state;
    
    @JsonView(View.Users.class)
    @Column(name="st_country", nullable=false)
    private String country;
    
    @Column(name="created_at")
    private LocalDate createdAt;

    @Column(name="updated_at")
    private LocalDate updateAt;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "address")
    private Users userAddress; 

    public Address() {

    }

    public Address(String address, int number, String complement, String district, String city, String state, String country) {
        this.address = address;
        this.number = number;
        this.complement = complement;
        this.district = district;
        this.city = city;
        this.state = state;
        this.country = country;
        this.createdAt = LocalDate.now();
        this.updateAt = LocalDate.now();
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
}
