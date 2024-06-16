package ch.ffhs.todo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public String home(){
        return "Welcome!!!! add /api/users/me   to path to test login functionality  :) ";
    }
}
