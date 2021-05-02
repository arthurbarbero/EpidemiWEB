package br.gov.sp.fatec.epidemiweb.Services;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.gov.sp.fatec.epidemiweb.Entities.Address;
import br.gov.sp.fatec.epidemiweb.Entities.Group;
import br.gov.sp.fatec.epidemiweb.Entities.Users;
import br.gov.sp.fatec.epidemiweb.Exceptions.BadRequestException;
import br.gov.sp.fatec.epidemiweb.Exceptions.NotFoundException;
import br.gov.sp.fatec.epidemiweb.Repositories.AddressRepository;
import br.gov.sp.fatec.epidemiweb.Repositories.GroupRepository;
import br.gov.sp.fatec.epidemiweb.Repositories.UserRepository;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private GroupRepository groupRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private AddressRepository addressRepo;

    @Autowired
    private PasswordEncoder passEncoder;

    @Override
    @PreAuthorize("isAuthenticated()")
    public Users getUserById(int id) {
        Users foundUser = userRepo.findById(id).get();
        if (foundUser == null) {
            throw new NotFoundException("Não foi possível encontrar o usuário.");
        }
        return foundUser;
    }

    @Override
    @PreAuthorize("hasRole('HEALTH_AGENT')")
    public Users saveUser(String name, String email, String password, Address newAddress, String role) {
        // Creating Address
        addressRepo.save(newAddress);
        if (newAddress == null) {
            throw new NotFoundException("Não foi possível salvar o endereço informado.");
        }

        // Getting the informed role
        Group group = groupRepo.findByName(role);
        if (group == null) {
            throw new NotFoundException("Não foi possível encontrar o grupo de usuário informado.");
        }
        ;

        // Creating Users
        Users newUser = new Users(name, email, passEncoder.encode(password));
        newUser.setAddress(newAddress);
        newUser.getGroups().add(group);
        userRepo.save(newUser);

        return newUser;
    }

    @Override
    @PreAuthorize("hasRole('HEALTH_AGENT')")
    public List<Users> getAllUsers() {
        return userRepo.findAll();
    }

    @Override
    @PreAuthorize("hasAnyRole('PATIENT', 'HEALTH_AGENT')")
    public Users update(Users newUser) {
        Users oldUser = userRepo.findById(newUser.getId()).get();
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
    @PreAuthorize("isAuthenticated()")
    public void deleteById(Users user) {
        try {
            if (user == null) {
                throw new NotFoundException("Não foi encontrado o usuário para o id informado.");
            }
            userRepo.deleteById(user.getId());
        } catch (Exception e) {
            throw new NotFoundException(e.getMessage());
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Users user = userRepo.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("Users " + email + " not found");
        }
        
        return User.builder().username(email).password(user.getPassword()).authorities(user.getGroups().stream()
                        .map(Group::getName).collect(Collectors.toList()).toArray(new String[user.getGroups().size()])
                    ).build();
    }

}
