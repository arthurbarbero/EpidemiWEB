package br.gov.sp.fatec.epidemiweb.Services;

import java.time.LocalDate;
import java.util.List;

import br.gov.sp.fatec.epidemiweb.Entities.Disease;
import br.gov.sp.fatec.epidemiweb.Entities.Incidence;
import br.gov.sp.fatec.epidemiweb.Entities.Users;

public interface IncidenceService {
    
    public Incidence saveIncidence(LocalDate incidenceDate, Disease disease, Users user);

    public List<Incidence> getAllIncidences();

    public Incidence getById(int id);

    public List<Incidence> getAllIncidenceByUser(Users user);

    public List<Incidence> getAllIncidenceByDisease(Disease disease);

    public List<Incidence> getAllIncidenceByUserAndDisease(Users user, Disease disease);

    public Incidence update(Incidence newIncidence);

    public void deleteById(Incidence incidence);
    
}
