package br.gov.sp.fatec.epidemiweb.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.gov.sp.fatec.epidemiweb.Entities.Groups;

public interface GroupsRepository extends JpaRepository<Groups, Integer>  {
    
}
