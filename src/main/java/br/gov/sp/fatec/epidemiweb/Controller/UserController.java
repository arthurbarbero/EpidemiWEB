package br.gov.sp.fatec.epidemiweb.Controller;

import com.fasterxml.jackson.annotation.JsonView;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    @GetMapping(value = "/login")
    public User GetUserByEmailAndPassword(
        @RequestParam(value = "email") String email,
        @RequestParam(value = "password") String password) {
        return userService.getUser(email, password);
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
}
