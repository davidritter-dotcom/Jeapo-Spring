package ch.ffhs.todo.repository;

import ch.ffhs.todo.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteRepo extends JpaRepository<Note, Long> {
}
