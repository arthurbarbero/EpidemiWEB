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

import br.gov.sp.fatec.epidemiweb.Entities.User;
import br.gov.sp.fatec.epidemiweb.Entities.RequestModel.UserRequest;
import br.gov.sp.fatec.epidemiweb.Services.UserService;

@RestController
@RequestMapping(value = "/user")
@CrossOrigin
public class UserController {
    
    @Autowired
    private UserService userService;
    
    @JsonView(View.User.class)
    @GetMapping(value = "/getById/{id}")
    public User GetUserById(@PathVariable(value = "id") int id) {
        return userService.getUserById(id);
    }

    @JsonView(View.User.class)
    @PostMapping(value = "/login")
    public User GetUserByEmailAndPassword(@RequestBody User user) {
        return userService.getUser(user.getEmail(), user.getPassword());
    }

    @JsonView(View.User.class)
    @PostMapping(value = "/registerUser")
    public ResponseEntity<User> RegisterUser(@RequestBody UserRequest userRequest, UriComponentsBuilder uriComponentsBuilder) {
        User newUser = userService.saveUser(
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

        return new ResponseEntity<User>(newUser, header, HttpStatus.CREATED);
    }

    @JsonView(View.User.class)
    @PutMapping(value = "/update")
    public ResponseEntity<User> UpdateDisease(@RequestBody User user) {
        User updatedUser = userService.update(user);
        return new ResponseEntity<User>(updatedUser, HttpStatus.OK);
    }

    @DeleteMapping(value = "/deleteById/{userId}")
    public ResponseEntity<String> DeleteSymptomById(@PathVariable(value = "userId") User user) {
        userService.deleteById(user);
        return new ResponseEntity<String>("Ok", HttpStatus.OK);
    }
}
