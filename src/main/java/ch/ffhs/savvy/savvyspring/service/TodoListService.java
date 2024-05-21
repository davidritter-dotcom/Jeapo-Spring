package ch.ffhs.savvy.savvyspring.service;

import ch.ffhs.savvy.savvyspring.jooq.tables.records.TodolistRecord;
import ch.ffhs.savvy.savvyspring.model.Todolist;
import org.jooq.DSLContext;
import org.springframework.stereotype.Service;
import static ch.ffhs.savvy.savvyspring.jooq.tables.Todolist.TODOLIST;
import java.util.List;



@Service
public class TodoListService {

    private final DSLContext jooq;

    public TodoListService(DSLContext jooq){
        this.jooq = jooq;
    }

    public List<Todolist> getAll() {
        List<TodolistRecord> todolistRecords = jooq.selectFrom(TODOLIST).fetchInto(TodolistRecord.class);
        return todolistRecords.stream().map(e -> new Todolist(e.getId(), e.getName(), e.getUserId())).toList();
    }

    /**
     * todo try / catch blc
     */
    public Todolist getById(Integer id) {
        TodolistRecord  todolistRecord=  jooq.fetchOne(TODOLIST, TODOLIST.ID.eq(id));
        return new Todolist(todolistRecord.getId(), todolistRecord.getName(), todolistRecord.getUserId());
    }

    public Todolist create(Todolist todolist) {
   TodolistRecord todolistRecord=    jooq.insertInto(TODOLIST).columns(TODOLIST.NAME, TODOLIST.USER_ID).values(todolist.getName(), todolist.getUserId())
             .returningResult(TODOLIST.ID, TODOLIST.NAME, TODOLIST.USER_ID).fetchOneInto(TodolistRecord.class);
        return new Todolist(todolistRecord.getId(), todolistRecord.getName(), todolistRecord.getUserId());

    }

    public List<Todolist> getByUserId(Integer id) {
        List<TodolistRecord> todolistRecords = jooq.selectFrom(TODOLIST).where(TODOLIST.USER_ID.eq(id)).fetchInto(TodolistRecord.class);
        return todolistRecords.stream().map(e -> new Todolist(e.getId(), e.getName(), e.getUserId())).toList();
    }

}
