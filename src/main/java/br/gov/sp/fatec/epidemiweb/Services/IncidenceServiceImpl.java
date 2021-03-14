package br.gov.sp.fatec.epidemiweb.Services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.gov.sp.fatec.epidemiweb.Entities.Disease;
import br.gov.sp.fatec.epidemiweb.Entities.Incidence;
import br.gov.sp.fatec.epidemiweb.Entities.User;
import br.gov.sp.fatec.epidemiweb.Repositories.IncidenceRepository;

@Service("incidenceService")
public class IncidenceServiceImpl implements IncidenceService{

    @Autowired
    private IncidenceRepository incidenceRepo;


    @Override
    public Incidence saveIncidence(LocalDate incidenceDate, Disease disease, User user) {
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
    public List<Incidence> getAllIncidenceByUser(User user) {
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
    public List<Incidence> getAllIncidenceByUserAndDisease(User user, Disease disease) {
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
    
}
