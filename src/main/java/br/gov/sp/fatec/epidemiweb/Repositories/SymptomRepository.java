package br.gov.sp.fatec.epidemiweb.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.gov.sp.fatec.epidemiweb.Entities.Symptom;

public interface SymptomRepository extends JpaRepository<Symptom, Integer>  {
    
}