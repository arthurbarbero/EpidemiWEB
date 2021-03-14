package br.gov.sp.fatec.epidemiweb.Services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.gov.sp.fatec.epidemiweb.Entities.Address;
import br.gov.sp.fatec.epidemiweb.Entities.Group;
import br.gov.sp.fatec.epidemiweb.Entities.User;
import br.gov.sp.fatec.epidemiweb.Repositories.AddressRepository;
import br.gov.sp.fatec.epidemiweb.Repositories.GroupRepository;
import br.gov.sp.fatec.epidemiweb.Repositories.UserRepository;

@Service("userService")
public class UserServiceImpl implements UserService  {

    @Autowired
    private GroupRepository groupRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private AddressRepository addressRepo;

    @Override
    @Transactional
    public User saveUser(String name, String email, String password, String address, int number, String complement, String district, String city, String state, String country, String role) {
        try {
            // Creating Address
            Address newAddress = new Address(address, number, complement, district, city, state, country);
            addressRepo.save(newAddress);
            if (newAddress.getId() == null) {
                throw new Exception("Não foi possível salvar o endereço informado.");
            }

            //Getting the informed role
            Group group = groupRepo.findByName(role);
            if (group.getName() == null) {
                throw new Exception("Não foi possível encontrar o grupo de usuário informado.");
            };
            
            // Creating User
            User newUser = new User(name, email, password);
            newUser.setAddress(newAddress);
            newUser.getGroups().add(group);
            userRepo.save(newUser);

            return newUser;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public User getUser(String email, String password) {
        try {
            User foundUser = userRepo.findByEmailAndPassword(email, password);
            if (foundUser.getId() == null) {
                throw new Exception("Não foi possível encontrar o usuário a partir do email/senha informados, tente novamente.");
            }
            return foundUser;
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    
}
