package br.gov.sp.fatec.epidemiweb.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.gov.sp.fatec.epidemiweb.Entities.Users;

public interface UserRepository extends JpaRepository<Users, Integer>  {

    public Users findByEmail(String email);

}
