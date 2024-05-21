package ch.ffhs.savvy.savvyspring.service;

import static ch.ffhs.savvy.savvyspring.jooq.Tables.APP_USER;
import ch.ffhs.savvy.savvyspring.jooq.tables.AppUser;
import ch.ffhs.savvy.savvyspring.jooq.tables.records.AppUserRecord;
import ch.ffhs.savvy.savvyspring.model.User;
import org.jooq.DSLContext;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService
{
    private final DSLContext jooq;

    public UserService(DSLContext jooq) {
        this.jooq = jooq;
    }

    /**
     *     Von Tabelle APP_USER wird
     *     umschreiben von stream wenn bei tabelle änderungen gemacht werden, dann nochmals jooq gen
     */
    public List<User> getAll() {
    List<AppUserRecord> appUserRecords =  jooq.selectFrom(APP_USER).fetchInto(AppUserRecord.class);
    return appUserRecords.stream().map(e -> new User(e.getId(), e.getUsername(), e.getEmail(), e.getPassword(), e.getRole(), e.getStatus())).toList();
    }


    public User getById(Integer id) {
        AppUserRecord  appUserRecord =  jooq.fetchOne(APP_USER, APP_USER.ID.eq(id));
        return new User(appUserRecord.getId(), appUserRecord.getUsername(), appUserRecord.getEmail(), appUserRecord.getPassword(), appUserRecord.getRole(), appUserRecord.getStatus());
    }
}
