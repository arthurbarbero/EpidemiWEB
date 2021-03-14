package br.gov.sp.fatec.epidemiweb;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

@SpringBootTest
@Transactional
@Rollback
class EpidemiwebApplicationTests {

	@Test
	void contextLoads() {
	}

}
