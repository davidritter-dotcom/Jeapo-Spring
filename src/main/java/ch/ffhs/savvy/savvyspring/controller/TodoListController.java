package ch.ffhs.savvy.savvyspring.controller;

import ch.ffhs.savvy.savvyspring.jooq.tables.Todo;
import ch.ffhs.savvy.savvyspring.model.Todolist;
import ch.ffhs.savvy.savvyspring.service.TodoListService;
import org.jooq.impl.QOM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/todolist")
public class TodoListController {

    private final TodoListService todoListService;

    @Autowired
    public TodoListController(TodoListService todoListService){
        this.todoListService = todoListService;
    }

    @GetMapping
    public List<Todolist> getAll(){
        return todoListService.getAll();
    }

    @GetMapping("/{id}")
    public Todolist getById(@PathVariable Integer id){
        return todoListService.getById(id);
    }

    @PostMapping
    public Todolist create(@RequestBody Todolist todolist){
        return  todoListService.create(todolist);
    }

    @GetMapping("/userid/{id}")
    public List<Todolist> getByUserId(@PathVariable Integer id){
        return todoListService.getByUserId(id);
    }

}
