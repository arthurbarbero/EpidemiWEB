package br.gov.sp.fatec.epidemiweb.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.gov.sp.fatec.epidemiweb.Entities.User;

public interface UserRepository extends JpaRepository<User, Integer>  {
    
    public User findByEmailAndPassword(String email, String password);

}
