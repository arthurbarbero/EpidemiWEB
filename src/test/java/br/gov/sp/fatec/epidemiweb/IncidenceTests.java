package br.gov.sp.fatec.epidemiweb;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import br.gov.sp.fatec.epidemiweb.Entities.Disease;
import br.gov.sp.fatec.epidemiweb.Entities.Incidence;
import br.gov.sp.fatec.epidemiweb.Entities.User;
import br.gov.sp.fatec.epidemiweb.Repositories.DiseaseRepository;
import br.gov.sp.fatec.epidemiweb.Repositories.UserRepository;
import br.gov.sp.fatec.epidemiweb.Services.IncidenceService;

@SpringBootTest
@Transactional
@Rollback
class IncidenceTests {

	@Autowired
	private IncidenceService incidenceBO;
    @Autowired
	private DiseaseRepository diseaseRepo;
    @Autowired
	private UserRepository userRepo;

	@Test
    void saveIncidenceTest() {
        User tempUser = userRepo.findById(1).get();
        Disease tempDisease = diseaseRepo.findById(1).get();
        Incidence newIncidence = incidenceBO.saveIncidence(LocalDate.now(), tempDisease, tempUser);
		assertNotNull(newIncidence.getId());
	}

    @Test
    void getAllIncidenceTest() {
        List<Incidence> allIncidence = incidenceBO.getAllIncidences();
        assertNotNull(allIncidence.get(0));
    }

    @Test
    void getAllIncidenceByUserTest() {
        User tempUser = userRepo.findById(1).get();
        List<Incidence> allIncidence = incidenceBO.getAllIncidenceByUser(tempUser);
        assertNotNull(allIncidence.get(0));
    }

    @Test
    void getAllIncidenceByDiseaseTest() {
        Disease tempDisease = diseaseRepo.findById(1).get();
        List<Incidence> allIncidence = incidenceBO.getAllIncidenceByDisease(tempDisease);
        assertNotNull(allIncidence.get(0));
    }

}