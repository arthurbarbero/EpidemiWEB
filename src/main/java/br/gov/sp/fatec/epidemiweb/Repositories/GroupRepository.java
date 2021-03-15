package br.gov.sp.fatec.epidemiweb.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.gov.sp.fatec.epidemiweb.Entities.Group;

public interface GroupRepository extends JpaRepository<Group, Integer>  {
    
    public Group findByName(String name);

}
