package br.gov.sp.fatec.epidemiweb.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.gov.sp.fatec.epidemiweb.Entities.Disease;

public interface DiseaseRepository extends JpaRepository<Disease, Integer>  {
    
}