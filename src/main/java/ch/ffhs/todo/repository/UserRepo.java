package ch.ffhs.todo.repository;

import ch.ffhs.todo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {
}
