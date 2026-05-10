package com.petwellnessplus;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ActiveProfiles({"test", "test-local"})
class PetWellnessPlusApplicationTests {

	@Test
	void contextLoads() {
	}

}
