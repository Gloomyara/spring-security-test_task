package ru.antonovmikhail.book;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@SpringBootTest
@SqlGroup({
		@Sql(value = "classpath:schema.sql", executionPhase = BEFORE_TEST_METHOD),
		@Sql(value = "classpath:data.sql", executionPhase = BEFORE_TEST_METHOD)
})
class LibraryAppTests {

	@Test
	void contextLoads() {
	}

}
