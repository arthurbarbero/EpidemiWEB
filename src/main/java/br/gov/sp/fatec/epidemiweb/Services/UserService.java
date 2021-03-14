package br.gov.sp.fatec.epidemiweb.Services;

import java.time.LocalDateTime;

import br.gov.sp.fatec.epidemiweb.Entities.*;

public interface UserService {

    public User saveUser(String name, String email, String password, LocalDateTime createdAt, LocalDateTime updateAt, String address, int number, String complement, String district, String city, String state, String country, String role);

}
