package com.helphi.question;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;

@SpringBootTest
class QuestionApplicationTests {

	@Configuration
    static class ContextConfiguration {

        // this bean will be injected into the OrderServiceTest class
        // @Bean
        // public CqlSession session() throws ConfigurationException, IOException, InterruptedException {
		// 	EmbeddedCassandraServerHelper.startEmbeddedCassandra();
        // 	return EmbeddedCassandraServerHelper.getSession();
        // }
    }

	@Test
	void contextLoads() {
	}

	// @After
	// public void tearDown() throws Exception {
	// 	EmbeddedCassandraServerHelper.cleanEmbeddedCassandra();
	// }

}
