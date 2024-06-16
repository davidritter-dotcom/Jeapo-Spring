package ch.ffhs.todo2.repository;

import ch.ffhs.todo2.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteRepo extends JpaRepository<Note, Long> {
}
