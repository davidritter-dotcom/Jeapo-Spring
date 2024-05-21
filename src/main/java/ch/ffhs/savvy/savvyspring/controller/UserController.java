package ch.ffhs.savvy.savvyspring.controller;

import ch.ffhs.savvy.savvyspring.jooq.tables.records.AppUserRecord;
import ch.ffhs.savvy.savvyspring.model.User;
import ch.ffhs.savvy.savvyspring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/user")
public class UserController {

   private final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping
    public List<User> getAllUser(){
        return userService.getAll();
    }

    @GetMapping("/{id}")
    public User getUserByRecord(@PathVariable Integer id){
        return userService.getById(id);
    }
}
