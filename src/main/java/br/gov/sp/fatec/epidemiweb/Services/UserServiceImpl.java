package br.gov.sp.fatec.epidemiweb.Services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.gov.sp.fatec.epidemiweb.Entities.Address;
import br.gov.sp.fatec.epidemiweb.Entities.Group;
import br.gov.sp.fatec.epidemiweb.Entities.User;
import br.gov.sp.fatec.epidemiweb.Exceptions.NotFoundException;
import br.gov.sp.fatec.epidemiweb.Repositories.AddressRepository;
import br.gov.sp.fatec.epidemiweb.Repositories.GroupRepository;
import br.gov.sp.fatec.epidemiweb.Repositories.UserRepository;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService  {

    @Autowired
    private GroupRepository groupRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private AddressRepository addressRepo;

    @Override
    public User getUserById(int id) {
        User foundUser = userRepo.findById(id).get();
        if (foundUser == null) {
            throw new NotFoundException("Não foi possível encontrar o usuário.");
        }
        return foundUser;
    }

    @Override
    public User saveUser(String name, String email, String password, Address newAddress, String role) {
        // Creating Address
        addressRepo.save(newAddress);
        if (newAddress == null) {
            throw new NotFoundException("Não foi possível salvar o endereço informado.");
        }

        //Getting the informed role
        Group group = groupRepo.findByName(role);
        if (group == null) {
            throw new NotFoundException("Não foi possível encontrar o grupo de usuário informado.");
        };
        
        // Creating User
        User newUser = new User(name, email, password);
        newUser.setAddress(newAddress);
        newUser.getGroups().add(group);
        userRepo.save(newUser);

        return newUser;
    }

    @Override
    public User getUser(String email, String password) {
        User foundUser = userRepo.findByEmailAndPassword(email, password);
        if (foundUser == null) {
            throw new NotFoundException("Não foi possível encontrar o usuário a partir do email/senha informados, tente novamente.");
        }
        return foundUser;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }
}
