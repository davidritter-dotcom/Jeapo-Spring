package ch.ffhs.savvy_spring.service;

import ch.ffhs.savvy_spring.jooq.model.Tables;
import ch.ffhs.savvy_spring.jooq.model.tables.pojos.Todolist;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoListService {
    private final DSLContext dsl;
    @Autowired
    public TodoListService(DSLContext dsl){
        this.dsl = dsl;
    }
    public List<Todolist> getAll() {
        return dsl.selectFrom(Tables.TODOLIST).fetchInto(Todolist.class);
    }

    public List<Todolist> getByUserId(String userId) {
        return dsl.selectFrom(Tables.TODOLIST)
                .where(Tables.TODOLIST.USER_ID.eq(userId))
                .fetchInto(Todolist.class);
    }

    public Todolist getById(int id) {
        return dsl.selectFrom(Tables.TODOLIST)
                .where(Tables.TODOLIST.ID.eq(id))
                .fetchOneInto(Todolist.class);
    }

    public void create(Todolist todolist) {
        dsl.insertInto(Tables.TODOLIST)
                .set(Tables.TODOLIST.NAME, todolist.getName())
                .set(Tables.TODOLIST.USER_ID, todolist.getUserId())
                .execute();
    }

    public void delete(int id) {
        dsl.deleteFrom(Tables.TODOLIST)
                .where(Tables.TODOLIST.ID.eq(id))
                .execute();
    }

    public void update(int id, Todolist todolist) {
        dsl.update(Tables.TODOLIST)
                .set(Tables.TODOLIST.NAME, todolist.getName())
                .set(Tables.TODOLIST.USER_ID, todolist.getUserId())
                .where(Tables.TODOLIST.ID.eq(id))
                .execute();
    }
}
