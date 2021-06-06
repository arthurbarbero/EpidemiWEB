package br.gov.sp.fatec.epidemiweb.Services;

import java.util.List;

import br.gov.sp.fatec.epidemiweb.Entities.Disease;
import br.gov.sp.fatec.epidemiweb.Entities.Symptom;

public interface SymptomService {
    
    // CREATE
    public Symptom saveSymptom(String name, String description, int severity, List<Integer> listOfDiseases);

    // READ
    public Symptom getById(int id);

    public List<Symptom> getAllSymptoms();
    
    public List<Symptom> getAllSymptomsByDisease(Disease disease);
    
    // UPDATE
    public Symptom update(Symptom newSymptom);

    // DELETE
    public void deleteById(Symptom symptom);
}
