package br.gov.sp.fatec.epidemiweb.Controller;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonView;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.gov.sp.fatec.epidemiweb.Entities.Disease;
import br.gov.sp.fatec.epidemiweb.Entities.Symptom;
import br.gov.sp.fatec.epidemiweb.Entities.RequestModel.SymptomRequest;
import br.gov.sp.fatec.epidemiweb.Services.SymptomService;

@RestController
@RequestMapping(value = "/symptom")
@CrossOrigin
public class SymptomController {
    
    @Autowired
    private SymptomService symptomService;

    @JsonView(View.SymptomCompleted.class)
    @GetMapping(value = "/getById/{id}")
    public Symptom GetSymptomById(@PathVariable(value = "id") int id) {
        return symptomService.getById(id);
    }

    @JsonView(View.SymptomResumed.class)
    @GetMapping(value = "/getAll")
    public List<Symptom> GetAllSymptoms() {
        return symptomService.getAllSymptoms();
    }

    @JsonView(View.SymptomResumed.class)
    @GetMapping(value = "/getByDisease/{diseaseId}")
    public List<Symptom> GetAllSymptomsByDisease(@PathVariable(value = "diseaseId") Disease disease) {
        return symptomService.getAllSymptomsByDisease(disease);
    }

    @JsonView(View.SymptomResumed.class)
    @PostMapping(value = "/register")
    public ResponseEntity<Symptom> RegisterSymptom(@RequestBody SymptomRequest symptom, UriComponentsBuilder uriComponentsBuilder) {
        Symptom newSymptom = symptomService.saveSymptom(symptom.getName(), symptom.getDescription(), symptom.getSeverity(), symptom.getDiseases());

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(
            uriComponentsBuilder.path(
                "/symptom/getById/" + newSymptom.getId()
            ).build().toUri()
        );

        return new ResponseEntity<Symptom>(newSymptom, headers, HttpStatus.CREATED);
    }

    @JsonView(View.SymptomResumed.class)
    @PutMapping(value = "/update")
    public ResponseEntity<Symptom> UpdateSymptom(@RequestBody Symptom symptom) {
        Symptom updatedSymptom = symptomService.update(symptom);
        return new ResponseEntity<Symptom>(updatedSymptom, HttpStatus.OK);
    }

    @DeleteMapping(value = "/deleteById/{symptomId}")
    public ResponseEntity<String> DeleteSymptomById(@PathVariable(value = "symptomId") Symptom symptom) {
        symptomService.deleteById(symptom);
        return new ResponseEntity<String>("Ok", HttpStatus.OK);
    }


}
