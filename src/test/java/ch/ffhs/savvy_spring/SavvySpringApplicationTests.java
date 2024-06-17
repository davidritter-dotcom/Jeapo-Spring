package ch.ffhs.savvy_spring;

import ch.ffhs.savvy_spring.jooq.model.Tables;
import ch.ffhs.savvy_spring.jooq.model.tables.pojos.AppUser;
import ch.ffhs.savvy_spring.jooq.model.tables.pojos.Todo;
import ch.ffhs.savvy_spring.service.TodoService;
import org.jooq.DSLContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

// for rollback use @Transactional
@SpringBootTest
class SavvySpringApplicationTests {

	@Autowired
	private DSLContext dsl;
	@Autowired
	private TodoService todoService;
	@Test
	void find_all_Users() {
		List<AppUser> users = dsl.selectFrom(Tables.APP_USER).fetchInto(AppUser.class);
		assertThat(users.size()).isEqualTo(4);
	}
	@Test
	void find_all_Todos() {
		List<Todo> users = todoService.getAll();
		assertThat(users.size()).isEqualTo(4);
	}

	@Test
	void create_Todo() {
		List<Todo> users = todoService.getAll();
		assertThat(users.size()).isEqualTo(4);
	}

}
