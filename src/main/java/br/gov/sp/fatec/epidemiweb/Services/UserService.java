package br.gov.sp.fatec.epidemiweb.Services;

import java.time.LocalDate;

import br.gov.sp.fatec.epidemiweb.Entities.*;

public interface UserService {

    public User saveUser(String name, String email, String password, LocalDate createdAt, LocalDate updateAt, String address, int number, String complement, String district, String city, String state, String country, String role);

    public User getUser(String email, String password);

}
