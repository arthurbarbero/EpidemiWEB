package br.gov.sp.fatec.epidemiweb.Services;

import java.time.LocalDateTime;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;

import br.gov.sp.fatec.epidemiweb.Entities.Address;
import br.gov.sp.fatec.epidemiweb.Entities.Group;
import br.gov.sp.fatec.epidemiweb.Entities.User;
import br.gov.sp.fatec.epidemiweb.Repositories.GroupRepository;
import br.gov.sp.fatec.epidemiweb.Repositories.UserRepository;

@Transactional
public class UserServiceImpl implements UserService  {

    @Autowired
    private GroupRepository groupRepo;

    @Autowired
    private UserRepository userRepo;

    @Override
    public User saveUser(String name, String email, String password, LocalDateTime createdAt, LocalDateTime updateAt, String address, int number, String complement, String district, String city, String state, String country, String role) {
        try {
            User newUser = new User(name, email, password, createdAt, updateAt);
            Address newAddress = new Address(address, number, complement, district, city, state, country, createdAt, updateAt);
            
            Group group = groupRepo.findByName(role);
            if (group.getName() == null) {
                throw new Exception("Não foi possível encontrar o grupo de usuário informado.");
            };
            
            newUser.setAddress(newAddress);
            newUser.getGroups().add(group);
            userRepo.save(newUser);
            return newUser;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
