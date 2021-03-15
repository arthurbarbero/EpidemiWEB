package br.gov.sp.fatec.epidemiweb.Services;

import java.time.LocalDate;
import java.util.List;

import br.gov.sp.fatec.epidemiweb.Entities.Disease;
import br.gov.sp.fatec.epidemiweb.Entities.Incidence;
import br.gov.sp.fatec.epidemiweb.Entities.User;

public interface IncidenceService {
    
    public Incidence saveIncidence(LocalDate incidenceDate, Disease disease, User user);

    public List<Incidence> getAllIncidences();

    public List<Incidence> getAllIncidenceByUser(User user);

    public List<Incidence> getAllIncidenceByDisease(Disease disease);

    public List<Incidence> getAllIncidenceByUserAndDisease(User user, Disease disease);
    
}
