package br.gov.sp.fatec.epidemiweb.Services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import br.gov.sp.fatec.epidemiweb.Entities.Disease;
import br.gov.sp.fatec.epidemiweb.Entities.Incidence;
import br.gov.sp.fatec.epidemiweb.Entities.Users;
import br.gov.sp.fatec.epidemiweb.Exceptions.BadRequestException;
import br.gov.sp.fatec.epidemiweb.Exceptions.NotFoundException;
import br.gov.sp.fatec.epidemiweb.Repositories.IncidenceRepository;

@Service("incidenceService")
@Transactional
public class IncidenceServiceImpl implements IncidenceService{

    @Autowired
    private IncidenceRepository incidenceRepo;


    @Override
    @PreAuthorize("hasRole('HEALTH_AGENT')")
    public Incidence saveIncidence(LocalDate incidenceDate, Disease disease, Users user) {
        try {
            Incidence newIncidence = new Incidence(incidenceDate, disease, user);
            incidenceRepo.save(newIncidence);
            if (newIncidence.getId() == null) {
                throw new Exception("Ocorreu um erro ao tentar salva a nova incidencia, tente novamente.");
            }
            return newIncidence;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    @PreAuthorize("isAuthenticated()")
    public List<Incidence> getAllIncidences() {
        try {
            List<Incidence> allIncidence = new ArrayList<Incidence>(incidenceRepo.findAll());
            if (allIncidence.size() <= 0) {
                throw new Exception("Não foram encontradas incidências cadastradas.");
            }
            return allIncidence;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    @PreAuthorize("isAuthenticated()")
    public List<Incidence> getAllIncidenceByUser(Users user) {
        try {
            List<Incidence> allIncidence = new ArrayList<Incidence>(user.getIncidences());
            if (allIncidence.size() <= 0) {
                throw new Exception("Não foram encontradas incidências para o usuário informado.");
            }
            return allIncidence;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    @PreAuthorize("isAuthenticated()")
    public List<Incidence> getAllIncidenceByDisease(Disease disease) {
        try {
            List<Incidence> allIncidence = new ArrayList<Incidence>(disease.getIncidences());
            if (allIncidence.size() <= 0) {
                throw new Exception("Não foram encontradas incidências para a doença informada.");
            }
            return allIncidence;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    @PreAuthorize("isAuthenticated()")
    public List<Incidence> getAllIncidenceByUserAndDisease(Users user, Disease disease) {
        try {
            List<Incidence> allIncidence = new ArrayList<Incidence>(
                incidenceRepo.findAllIncidencesByUserAndDisease(
                    user.getId(), disease.getId()
                )
            );
            
            if (allIncidence.size() <= 0) {
                throw new Exception("Não foram encontradas incidências para a doença informada.");
            }
            return allIncidence;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    @PreAuthorize("isAuthenticated()")
    public Incidence getById(int id) {
        Incidence foundIncidence = incidenceRepo.findById(id).get();
        if (foundIncidence == null) {
            throw new NotFoundException("Não foram encontradas incidências para o id informado.");
        }
        return foundIncidence;
    }

    @Override
    @PreAuthorize("hasRole('HEALTH_AGENT')")
    public Incidence update(Incidence newIncidence) {
        Incidence oldIncidence = incidenceRepo.findById(newIncidence.getId()).get();
        
        if (oldIncidence == null) {
            throw new NotFoundException("Não foi encontrado a incidencia para o id informado.");
        }
        if (newIncidence.getUser() != null && newIncidence.getDisease() != null && newIncidence.getIncidenceDate() != null) {
            oldIncidence.setUser(newIncidence.getUser());
            oldIncidence.setDisease(newIncidence.getDisease());
            oldIncidence.setIncidenceDate(newIncidence.getIncidenceDate());
            oldIncidence.setUpdateAt(LocalDate.now());
            return incidenceRepo.save(oldIncidence);
        }
        throw new BadRequestException("Por favor verifique se os campos estão preenchidos corretamente.");
    }

    @Override
    @PreAuthorize("hasRole('HEALTH_AGENT')")
    public void deleteById(Incidence incidence) {
        try{
            if (incidence == null) {
                throw new NotFoundException("Não foi encontrado a incidência para o id informado.");
            }
            incidenceRepo.deleteById(incidence.getId());
        } catch (Exception e) {
            throw new NotFoundException(e.getMessage());
        }
    }
    
}
