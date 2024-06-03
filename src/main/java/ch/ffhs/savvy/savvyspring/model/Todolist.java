package ch.ffhs.savvy.savvyspring.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "todoList")
@Getter
@Setter
public class Todolist {

    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    private Integer userId;

    public Todolist(Integer id, String name, Integer userId) {
        this.id = id;
        this.name = name;
        this.userId = userId;
    }
}
