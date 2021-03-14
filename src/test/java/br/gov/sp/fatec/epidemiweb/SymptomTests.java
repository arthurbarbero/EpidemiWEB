package br.gov.sp.fatec.epidemiweb;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import br.gov.sp.fatec.epidemiweb.Entities.Symptom;
import br.gov.sp.fatec.epidemiweb.Services.SymptomService;

@SpringBootTest
@Transactional
@Rollback
class SymptomTests {

	@Autowired
	private SymptomService symptomBO;

	@Test
	void saveSymptomTest() {
		Symptom newSymptom = symptomBO.saveSymptom("name", "description", 2);
		assertNotNull(newSymptom.getId());
	}

    @Test
    void getAllSymptomTest() {
        List<Symptom> allSymptom = symptomBO.getAllSymptoms();
        assertNotNull(allSymptom.get(0));
    }
}