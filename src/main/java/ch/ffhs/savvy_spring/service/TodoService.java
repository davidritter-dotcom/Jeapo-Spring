package ch.ffhs.savvy_spring.service;

import ch.ffhs.savvy_spring.jooq.model.Tables;
import ch.ffhs.savvy_spring.jooq.model.tables.pojos.Todo;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoService {
    private final DSLContext dsl;
    @Autowired
    public TodoService(DSLContext dsl){
        this.dsl = dsl;
    }

    public List<Todo> getAll() {
        return dsl.selectFrom(Tables.TODO)
                .fetchInto(Todo.class);
    }

    public List<Todo> getByTodolistId(int todolistId) {
        return dsl.selectFrom(Tables.TODO)
                .where(Tables.TODO.TODOLIST_ID.eq(todolistId))
                .fetchInto(Todo.class);
    }

    public Todo getById(int id) {
        return dsl.selectFrom(Tables.TODO)
                .where(Tables.TODO.ID.eq(id))
                .fetchOneInto(Todo.class);
    }

    public void create(Todo todo) {
        dsl.insertInto(Tables.TODO)
                .set(Tables.TODO.CONTENT, todo.getContent())
                .set(Tables.TODO.TODOLIST_ID, todo.getTodolistId())
                .execute();
    }

    public void delete(int id) {
        dsl.deleteFrom(Tables.TODO)
                .where(Tables.TODO.ID.eq(id))
                .execute();
    }

    public void update(int id, Todo todo) {
        dsl.update(Tables.TODO)
                .set(Tables.TODO.CONTENT, todo.getContent())
                .set(Tables.TODO.TODOLIST_ID, todo.getTodolistId())
                .where(Tables.TODO.ID.eq(id))
                .execute();
    }
}
