package br.gov.sp.fatec.epidemiweb.Services;

import java.util.List;

import br.gov.sp.fatec.epidemiweb.Entities.*;

public interface UserService {

    public User saveUser(String name, String email, String password, Address address, String role);

    public User getUser(String email, String password);

    public User getUserById(int id);

    public List<User> getAllUsers();

    public User update(User newUser);

    public void deleteById(User user); 

}
