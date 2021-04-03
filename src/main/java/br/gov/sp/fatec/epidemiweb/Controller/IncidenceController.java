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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.util.UriComponentsBuilder;

import br.gov.sp.fatec.epidemiweb.Entities.Disease;
import br.gov.sp.fatec.epidemiweb.Entities.Incidence;
import br.gov.sp.fatec.epidemiweb.Entities.User;
import br.gov.sp.fatec.epidemiweb.Entities.RequestModel.IncidenceRequest;
import br.gov.sp.fatec.epidemiweb.Services.IncidenceService;

@RestControllerAdvice
@RequestMapping(value = "/incidence")
@CrossOrigin
public class IncidenceController {
    
    @Autowired
    private IncidenceService incidenceService;

    @JsonView(View.IncidenceComplete.class)
    @GetMapping(value = "/getById/{id}")
    public Incidence GetIncidenceById(@PathVariable int id) {
        return incidenceService.getById(id);
    }

    @JsonView(View.IncidenceResumed.class)
    @GetMapping(value = "/getAll")
    public List<Incidence> GetAllIncidence() {
        return incidenceService.getAllIncidences();
    }

    @JsonView(View.IncidenceResumed.class)
    @PostMapping(value = "/register")
    public ResponseEntity<Incidence> RegisterIncidence(@RequestBody IncidenceRequest incidenceRequest, UriComponentsBuilder uriComponentsBuilder) {
        Incidence newIncidence = incidenceService.saveIncidence(
            incidenceRequest.getIncidenceDate(), 
            incidenceRequest.getDisease(), 
            incidenceRequest.getUser()
        );

        HttpHeaders header = new HttpHeaders();
        header.setLocation(
            uriComponentsBuilder.path(
                "/incidence/getById/" + newIncidence.getId()
            ).build().toUri()
        );

        return new ResponseEntity<Incidence>(newIncidence, header, HttpStatus.CREATED);
    }

    @JsonView(View.IncidenceComplete.class)
    @GetMapping(value = "/getAllByUser/{userId}")
    public List<Incidence> GetAllIncidenceByUser(@PathVariable(value = "userId") User user) {
        return incidenceService.getAllIncidenceByUser(user);
    }

    @JsonView(View.IncidenceComplete.class)
    @GetMapping(value = "/getAllByDisease/{diseaseId}")
    public List<Incidence> GetAllIncidenceByDisease(@PathVariable(value = "diseaseId") Disease disease) {
        return incidenceService.getAllIncidenceByDisease(disease);
    }

    @JsonView(View.IncidenceComplete.class)
    @GetMapping(value = "/getAllByDiseaseAndUser")
    public List<Incidence> GetAllIncidenceByDiseaseAndUser(@RequestParam(value = "disease") Disease disease, @RequestParam(value = "user") User user) {
        return incidenceService.getAllIncidenceByUserAndDisease(user, disease);
    }

    @PutMapping(value = "/update")
    public ResponseEntity<Incidence> UpdateIncidence(@RequestBody Incidence incidence) {
        Incidence updatedIncidence = incidenceService.update(incidence);
        return new ResponseEntity<Incidence>(updatedIncidence, HttpStatus.OK);
    }

    @DeleteMapping(value = "/deleteById/{incidenceId}")
    public ResponseEntity<String> DeleteSymptomById(@PathVariable(value = "incidenceId") Incidence incidence) {
        incidenceService.deleteById(incidence);
        return new ResponseEntity<String>("Ok", HttpStatus.OK);
    }

}
