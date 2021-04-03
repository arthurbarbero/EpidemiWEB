package br.gov.sp.fatec.epidemiweb.Services;

import java.util.List;

import br.gov.sp.fatec.epidemiweb.Entities.Disease;

public interface DiseaseService {
    
    public Disease saveDisease(String name);

    public List<Disease> getAllDisease();

    public Disease getById(int id);

    public Disease getDiseaseByName(String name);

    public Disease update(Disease newDisease);

    public void deleteById(Disease disease);

}
