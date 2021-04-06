package br.gov.sp.fatec.epidemiweb;

import static org.junit.jupiter.api.Assertions.*;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import br.gov.sp.fatec.epidemiweb.Entities.Address;
import br.gov.sp.fatec.epidemiweb.Entities.User;
import br.gov.sp.fatec.epidemiweb.Services.UserService;

@SpringBootTest
@Transactional
@Rollback
class UserTests {

	@Autowired
	private UserService userBO;

	@Test
	void saveUserTest(){

		Address newAddress = new Address("address", 
		123, 
		"complement", 
		"district", 
		"city", 
		"state", 
		"country");

		User newUser = userBO.saveUser(
			"name", 
			"email", 
			"password", 
			newAddress,
			"PATIENT");
		assertNotNull(newUser.getId());
	}

	@Test
	void getUserTest(){
		User foundUser = userBO.getUser("first_user@email.com", "123456");
		assertNotNull(foundUser.getId());
	}

}
