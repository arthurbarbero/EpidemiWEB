package br.gov.sp.fatec.epidemiweb.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.gov.sp.fatec.epidemiweb.Entities.Address;

public interface AddressRepository extends JpaRepository<Address, Integer>  {
    
}
