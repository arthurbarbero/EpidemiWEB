package br.gov.sp.fatec.epidemiweb.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.gov.sp.fatec.epidemiweb.Entities.Incidence;

public interface IncidenceRepository extends JpaRepository<Incidence, Integer>  {
    
    @Query("select i from Incidence i inner join i.user u inner join i.disease d where u.id = :userId and d.id = :diseaseId")
    public List<Incidence> findAllIncidencesByUserAndDisease(int userId, int diseaseId);

}
