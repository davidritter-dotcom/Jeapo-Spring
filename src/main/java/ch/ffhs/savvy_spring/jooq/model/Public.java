/*
 * This file is generated by jOOQ.
 */
package ch.ffhs.savvy_spring.jooq.model;


import ch.ffhs.savvy_spring.jooq.model.tables.AppUser;
import ch.ffhs.savvy_spring.jooq.model.tables.Diaryentry;
import ch.ffhs.savvy_spring.jooq.model.tables.Sportstuff;
import ch.ffhs.savvy_spring.jooq.model.tables.Todo;
import ch.ffhs.savvy_spring.jooq.model.tables.Todolist;

import java.util.Arrays;
import java.util.List;

import org.jooq.Catalog;
import org.jooq.Sequence;
import org.jooq.Table;
import org.jooq.impl.SchemaImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Public extends SchemaImpl {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>public</code>
     */
    public static final Public PUBLIC = new Public();

    /**
     * The table <code>public.app_user</code>.
     */
    public final AppUser APP_USER = AppUser.APP_USER;

    /**
     * The table <code>public.diaryentry</code>.
     */
    public final Diaryentry DIARYENTRY = Diaryentry.DIARYENTRY;

    /**
     * The table <code>public.sportstuff</code>.
     */
    public final Sportstuff SPORTSTUFF = Sportstuff.SPORTSTUFF;

    /**
     * The table <code>public.todo</code>.
     */
    public final Todo TODO = Todo.TODO;

    /**
     * The table <code>public.todoList</code>.
     */
    public final Todolist TODOLIST = Todolist.TODOLIST;

    /**
     * No further instances allowed
     */
    private Public() {
        super("public", null);
    }


    @Override
    public Catalog getCatalog() {
        return DefaultCatalog.DEFAULT_CATALOG;
    }

    @Override
    public final List<Sequence<?>> getSequences() {
        return Arrays.asList(
            Sequences.TODOLIST_ID_SEQ
        );
    }

    @Override
    public final List<Table<?>> getTables() {
        return Arrays.asList(
            AppUser.APP_USER,
            Diaryentry.DIARYENTRY,
            Sportstuff.SPORTSTUFF,
            Todo.TODO,
            Todolist.TODOLIST
        );
    }
}
