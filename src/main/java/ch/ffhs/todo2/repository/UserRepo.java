package ch.ffhs.todo2.repository;

import ch.ffhs.todo2.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {
}
