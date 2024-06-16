package ch.ffhs.todo.service;

import ch.ffhs.todo.model.Note;
import ch.ffhs.todo.repository.NoteRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NoteService {

    @Autowired
    private NoteRepo noteRepository;

    public List<Note> findAll() {
        return noteRepository.findAll();
    }

    public Optional<Note> findById(Long noteId) {
        return noteRepository.findById(noteId);
    }

    public Note save(Note note) {
        return noteRepository.save(note);
    }

    public void deleteById(Long noteId) {
        noteRepository.deleteById(noteId);
    }

}
