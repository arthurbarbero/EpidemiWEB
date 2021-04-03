package br.gov.sp.fatec.epidemiweb.Services;

import java.time.LocalDate;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.gov.sp.fatec.epidemiweb.Entities.Address;
import br.gov.sp.fatec.epidemiweb.Entities.Group;
import br.gov.sp.fatec.epidemiweb.Entities.User;
import br.gov.sp.fatec.epidemiweb.Exceptions.NotFoundException;
import br.gov.sp.fatec.epidemiweb.Exceptions.BadRequestException;
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

    @Override
    public User update(User newUser) {
        User oldUser = userRepo.findById(newUser.getId()).get();
        Address oldAddress = addressRepo.findById(newUser.getAddress().getId()).get();
        if (oldUser == null) {
            throw new NotFoundException("Não foi encontrado o usuário para o id informado.");
        }
        if (newUser.getName() != null && newUser.getEmail() != null && newUser.getPassword() != null) {
            oldUser.setName(newUser.getName());
            oldUser.setEmail(newUser.getEmail());
            oldUser.setPassword(newUser.getPassword());
            oldUser.setUpdateAt(LocalDate.now());
            if (newUser.getAddress() != null) {
                oldAddress.setAddress(newUser.getAddress().getAddress());
                oldAddress.setNumber(newUser.getAddress().getNumber());
                oldAddress.setComplement(newUser.getAddress().getComplement());
                oldAddress.setDistrict(newUser.getAddress().getDistrict());
                oldAddress.setCity(newUser.getAddress().getCity());
                oldAddress.setState(newUser.getAddress().getState());
                oldAddress.setCountry(newUser.getAddress().getCountry());
                oldAddress = addressRepo.save(oldAddress);
            }
            oldUser.setAddress(oldAddress);
            return userRepo.save(oldUser);
        }
        throw new BadRequestException("Por favor verifique se os campos estão preenchidos corretamente.");
    }

    @Override
    public void deleteById(User user) {
        try{
            if (user == null) {
                throw new NotFoundException("Não foi encontrado o usuário para o id informado.");
            }
            userRepo.deleteById(user.getId());
        } catch (Exception e) {
            throw new NotFoundException(e.getMessage());
        }
    }

}
