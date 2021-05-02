package br.gov.sp.fatec.epidemiweb.Services;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import br.gov.sp.fatec.epidemiweb.Entities.*;

public interface UserService extends UserDetailsService {

    public Users saveUser(String name, String email, String password, Address address, String role);

    public Users getUserById(int id);

    public List<Users> getAllUsers();

    public Users update(Users newUser);

    public void deleteById(Users user); 

}
