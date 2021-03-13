package br.gov.sp.fatec.epidemiweb.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.gov.sp.fatec.epidemiweb.Entities.Incidence;

public interface IncidenceRepository extends JpaRepository<Incidence, Integer>  {
    
}
