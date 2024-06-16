package ch.ffhs.todo2.controller;

import ch.ffhs.todo2.service.NoteService;
import ch.ffhs.todo2.service.UserService;
import ch.ffhs.todo2.model.Note;
import ch.ffhs.todo2.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/notes")
public class NoteController {

    @Autowired
    private NoteService noteService;

    @Autowired
    private UserService userService;

    @GetMapping
    public List<Note> getAllNotes() {
        return noteService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Note> getNoteById(@PathVariable Long id) {
        Optional<Note> note = noteService.findById(id);
        if (note.isPresent()) {
            return ResponseEntity.ok(note.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Note> createNote(@RequestBody Note note) {
        Optional<User> user = userService.findById(note.getUser().getUserId());
        if (user.isPresent()) {
            note.setUser(user.get());
            return ResponseEntity.ok(noteService.save(note));
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Note> updateNote(@PathVariable Long id, @RequestBody Note noteDetails) {
        Optional<Note> note = noteService.findById(id);
        if (note.isPresent()) {
            Note updatedNote = note.get();
            updatedNote.setTitle(noteDetails.getTitle());
            updatedNote.setContent(noteDetails.getContent());
            updatedNote.setUser(noteDetails.getUser());
            noteService.save(updatedNote);
            return ResponseEntity.ok(updatedNote);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNote(@PathVariable Long id) {
        noteService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
