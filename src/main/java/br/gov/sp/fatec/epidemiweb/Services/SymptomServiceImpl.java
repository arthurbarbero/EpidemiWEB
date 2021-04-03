package br.gov.sp.fatec.epidemiweb.Services;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.gov.sp.fatec.epidemiweb.Entities.Disease;
import br.gov.sp.fatec.epidemiweb.Entities.Symptom;
import br.gov.sp.fatec.epidemiweb.Exceptions.NotFoundException;
import br.gov.sp.fatec.epidemiweb.Repositories.SymptomRepository;

@Service("symptomService")
@Transactional
public class SymptomServiceImpl implements SymptomService {

    @Autowired
    private SymptomRepository symptomRepo;

    @Override
    public Symptom saveSymptom(String name, String description, int severity) {
        try {
            Symptom newSymptom = new Symptom(name, description, severity);
            symptomRepo.save(newSymptom);
            if (newSymptom.getId() == null) {
                throw new Exception("Ocorreu um erro ao tentar salvar o novo sintoma");
            }
            return newSymptom;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public List<Symptom> getAllSymptoms() {
        try {
            List<Symptom> allSymptoms = new ArrayList<Symptom>(symptomRepo.findAll());
            if (allSymptoms.size() <= 0) {
                throw new Exception("Não foram encontradas sintomas cadastrados");
            }
            return allSymptoms;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public List<Symptom> getAllSymptomsByDisease(Disease disease) {
        try {
            List<Symptom> allSymptoms = new ArrayList<Symptom>(disease.getSymptoms());
            if (allSymptoms.size() <= 0) {
                throw new Exception("Não foram encontradas sintomas para a doença cadastrada");
            }
            return allSymptoms;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public Symptom getById(int id) {
        Symptom foundSymptom = symptomRepo.findById(id).get();
        if (foundSymptom == null) {
            throw new NotFoundException("Não foi encontrado o sintoma para o id informado.");
        }
        return foundSymptom;
    }

    @Override
    public void deleteById(Symptom symptom) {
        try{
            if (symptom == null) {
                throw new NotFoundException("Não foi encontrado o sintoma para o id informado.");
            }
            symptomRepo.deleteById(symptom.getId());
        } catch (Exception e) {
            throw new NotFoundException(e.getMessage());
        }
    }
    
}
