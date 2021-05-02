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
import br.gov.sp.fatec.epidemiweb.Services.DiseaseService;

@RestController
@RequestMapping(value = "/disease")
@CrossOrigin
public class DiseaseController {
    
    @Autowired
    private DiseaseService diseaseService;

    @JsonView(View.DiseaseResume.class)
    @GetMapping(value = "/getAll")
    public List<Disease> GetAllDisease() {
        return diseaseService.getAllDisease();
    }

    @JsonView(View.DiseaseSymptons.class)
    @GetMapping(value = "/getById/{id}")
    public Disease GetDiseaseById(@PathVariable(value = "id") int id) {
        return diseaseService.getById(id);
    }

    @JsonView(View.DiseaseResume.class)
    @PostMapping(value = "/register")
    public ResponseEntity<Disease> RegisterDisease(@RequestBody Disease disease, UriComponentsBuilder uriComponentsBuilder) {
        Disease newDisease = diseaseService.saveDisease(disease.getName());

        HttpHeaders header = new HttpHeaders();
        header.setLocation(
            uriComponentsBuilder.path(
                "/disease/getById/" + newDisease.getId()
            ).build().toUri()
        );

        return new ResponseEntity<Disease>(newDisease, header, HttpStatus.CREATED);
    }

    @JsonView(View.DiseaseResume.class)
    @PutMapping(value = "/update")
    public ResponseEntity<Disease> UpdateDisease(@RequestBody Disease disease) {
        Disease updatedSymptom = diseaseService.update(disease);
        return new ResponseEntity<Disease>(updatedSymptom, HttpStatus.OK);
    }

    @DeleteMapping(value = "/deleteById/{diseaseId}")
    public ResponseEntity<String> DeleteSymptomById(@PathVariable(value = "diseaseId") Disease disease) {
        diseaseService.deleteById(disease);
        return new ResponseEntity<String>("Ok", HttpStatus.OK);
    }

}
