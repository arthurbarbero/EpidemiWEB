package br.gov.sp.fatec.epidemiweb.Services;

import java.util.List;

import br.gov.sp.fatec.epidemiweb.Entities.Disease;
import br.gov.sp.fatec.epidemiweb.Entities.Symptom;

public interface SymptomService {
    
    public Symptom saveSymptom(String name, String description, int severity);

    public Symptom getById(int id);

    public void deleteById(Symptom symptom);

    public List<Symptom> getAllSymptoms();

    public List<Symptom> getAllSymptomsByDisease(Disease disease);
}
