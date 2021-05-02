package br.gov.sp.fatec.epidemiweb.Controller;

import com.fasterxml.jackson.annotation.JsonView;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import java.util.List;

import br.gov.sp.fatec.epidemiweb.Entities.Users;
import br.gov.sp.fatec.epidemiweb.Entities.RequestModel.UserRequest;
import br.gov.sp.fatec.epidemiweb.Services.UserService;

@RestController
@RequestMapping(value = "/user")
@CrossOrigin
public class UserController {
    
    @Autowired
    private UserService userService;
    
    @JsonView(View.Users.class)
    @GetMapping(value = "/getById/{id}")
    public Users GetUserById(@PathVariable(value = "id") int id) {
        return userService.getUserById(id);
    }

    @JsonView(View.Users.class)
    @GetMapping(value = "/getAll")
    public List<Users> GetAllUser() {
        return userService.getAllUsers();
    }

    @JsonView(View.Users.class)
    @PostMapping(value = "/registerUser")
    public ResponseEntity<Users> RegisterUser(@RequestBody UserRequest userRequest, UriComponentsBuilder uriComponentsBuilder) {
        Users newUser = userService.saveUser(
            userRequest.getUser().getName(), 
            userRequest.getUser().getEmail(), 
            userRequest.getUser().getPassword(), 
            userRequest.getAddress(), 
            userRequest.getRole()
        );

        HttpHeaders header = new HttpHeaders();
        header.setLocation(
            uriComponentsBuilder.path(
                "/user/getById/" + newUser.getId()
            ).build().toUri()
        );

        return new ResponseEntity<Users>(newUser, header, HttpStatus.CREATED);
    }

    @JsonView(View.Users.class)
    @PutMapping(value = "/update")
    public ResponseEntity<Users> UpdateDisease(@RequestBody Users user) {
        Users updatedUser = userService.update(user);
        return new ResponseEntity<Users>(updatedUser, HttpStatus.OK);
    }

    @DeleteMapping(value = "/deleteById/{userId}")
    public ResponseEntity<String> DeleteSymptomById(@PathVariable(value = "userId") Users user) {
        userService.deleteById(user);
        return new ResponseEntity<String>("Ok", HttpStatus.OK);
    }
}
