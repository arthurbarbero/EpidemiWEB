package br.gov.sp.fatec.epidemiweb.Services;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.gov.sp.fatec.epidemiweb.Entities.Disease;
import br.gov.sp.fatec.epidemiweb.Repositories.DiseaseRepository;
@Service("diseaseService")
@Transactional
public class DiseaseServiceImpl implements DiseaseService {
    @Autowired
    private DiseaseRepository diseaseRepo;

    @Override
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
    public List<Disease> getAllDisease() {
        try {
            List<Disease> allDiseases = new ArrayList<Disease>();
            allDiseases.addAll(diseaseRepo.findAll());
            if (allDiseases.size() <= 0) {
                throw new Exception("Ocorreu um erro ao tentar solicitar todas as doenças");
            }
            return allDiseases;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
    
}
