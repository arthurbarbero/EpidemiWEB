package br.gov.sp.fatec.epidemiweb;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import br.gov.sp.fatec.epidemiweb.Entities.Disease;
import br.gov.sp.fatec.epidemiweb.Services.DiseaseService;

@SpringBootTest
@Transactional
@Rollback
class DiseaseTests {

	@Autowired
	private DiseaseService diseaseBO;

	@Test
	void saveDiseaseTest() {
		Disease newDisease = diseaseBO.saveDisease("Nova_doença");
		assertNotNull(newDisease.getId());
	}

    @Test
    void getAllDiseaseTest() {
        List<Disease> allDiseases = diseaseBO.getAllDisease();
        assertNotNull(allDiseases.get(0));
    }
}