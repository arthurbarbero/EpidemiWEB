package br.gov.sp.fatec.epidemiweb.Services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import br.gov.sp.fatec.epidemiweb.Entities.Disease;
import br.gov.sp.fatec.epidemiweb.Exceptions.BadRequestException;
import br.gov.sp.fatec.epidemiweb.Exceptions.NotFoundException;
import br.gov.sp.fatec.epidemiweb.Repositories.DiseaseRepository;

@Service("diseaseService")
@Transactional
public class DiseaseServiceImpl implements DiseaseService {
    
    @Autowired
    private DiseaseRepository diseaseRepo;

    @Override
    @PreAuthorize("hasRole('HEALTH_AGENT')")
    public Disease saveDisease(String name) {
        try {
            Disease newDisease = new Disease(name);
            diseaseRepo.save(newDisease);
            if (newDisease.getId() == null) {
                throw new Exception("Ocorreu um erro ao tentar salvar a nova doença");
            }
            return newDisease;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    @PreAuthorize("isAuthenticated()")
    public List<Disease> getAllDisease() {
        try {
            List<Disease> allDiseases = new ArrayList<Disease>(diseaseRepo.findAll());
            if (allDiseases.size() <= 0) {
                throw new NotFoundException("Ocorreu um erro ao tentar solicitar todas as doenças");
            }
            return allDiseases;
        } catch (NotFoundException e) {
            throw e;
        }
    }

    @Override
    @PreAuthorize("isAuthenticated()")
    public Disease getDiseaseByName(String name) {
        try {
            Disease foundDisease = diseaseRepo.findByName(name);
            if (foundDisease.getId() == null) {
                throw new NotFoundException("Não foi encontrada doença com o nome solicitado, tente novamente.");
            }
            return foundDisease;
            
        } catch (NotFoundException e) {
            throw e;
        }
    }

    @Override
    @PreAuthorize("isAuthenticated()")
    public Disease getById(int id) {
        Disease foundDisease = diseaseRepo.findById(id).get();
        if (foundDisease == null) {
            throw new NotFoundException("Não foi encontrada doença com o id solicitado");
        }
        return foundDisease;
    }

    @Override
    @PreAuthorize("hasRole('HEALTH_AGENT')")
    public Disease update(Disease newDisease) {
        Disease oldDisease = diseaseRepo.findById(newDisease.getId()).get();
        if (oldDisease == null) {
            throw new NotFoundException("Não foi encontrado o doença para o id informado.");
        }
        if (newDisease.getName() != null) {
            oldDisease.setName(newDisease.getName());
            oldDisease.setUpdateAt(LocalDate.now());
            return diseaseRepo.save(oldDisease);
        }
        throw new BadRequestException("Por favor verifique se os campos estão preenchidos corretamente.");
    }

    @Override
    @PreAuthorize("hasRole('HEALTH_AGENT')")
    public void deleteById(Disease disease) {
        try{
            if (disease == null) {
                throw new NotFoundException("Não foi encontrado o sintoma para o id informado.");
            }
            diseaseRepo.deleteById(disease.getId());
        } catch (Exception e) {
            throw new NotFoundException(e.getMessage());
        }
    }
    
}
