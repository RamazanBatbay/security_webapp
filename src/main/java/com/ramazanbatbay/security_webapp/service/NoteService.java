package com.ramazanbatbay.security_webapp.service;

import com.ramazanbatbay.security_webapp.model.Note;
import com.ramazanbatbay.security_webapp.repository.NoteRepository;
import com.ramazanbatbay.security_webapp.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@lombok.extern.slf4j.Slf4j
public class NoteService {

    private final NoteRepository noteRepository;
    private final UserRepository userRepository;

    public NoteService(NoteRepository noteRepository, UserRepository userRepository) {
        this.noteRepository = noteRepository;
        this.userRepository = userRepository;
    }

    public List<Note> getNotesForUser(String email) {
        Integer userId = getUserIdByEmail(email);
        return noteRepository.findAllByUserIdOrderByCreatedAtDesc(userId);
    }

    public void createNote(String email, String title, String content) {
        Integer userId = getUserIdByEmail(email);
        Note note = new Note(title, content, userId);
        noteRepository.save(note);
    }

    public void deleteNote(String email, Integer noteId) {
        Integer userId = getUserIdByEmail(email);
        Note note = noteRepository.findById(noteId)
                .orElseThrow(() -> new RuntimeException("Note not found"));

        if (!note.getUserId().equals(userId)) {
            log.warn("Unauthorized data access attempt (DELETE) - User: {}, NoteID: {}, OwnerID: {}", email, noteId,
                    note.getUserId());
            throw new RuntimeException("Access Denied: You cannot delete this note");
        }
        noteRepository.delete(note);
    }

    private Integer getUserIdByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"))
                .getId();
    }

    public Note getNoteForUser(String email, Integer noteId) {
        Integer userId = getUserIdByEmail(email);
        Note note = noteRepository.findById(noteId)
                .orElseThrow(() -> new RuntimeException("Note not found"));

        if (!note.getUserId().equals(userId)) {
            log.warn("Unauthorized data access attempt (READ) - User: {}, NoteID: {}, OwnerID: {}", email, noteId,
                    note.getUserId());
            throw new RuntimeException("Access Denied: You cannot access this note");
        }
        return note;
    }

    public void updateNote(String email, Integer noteId, String title, String content) {
        Integer userId = getUserIdByEmail(email);
        Note note = noteRepository.findById(noteId)
                .orElseThrow(() -> new RuntimeException("Note not found"));

        if (!note.getUserId().equals(userId)) {
            log.warn("Unauthorized data access attempt (UPDATE) - User: {}, NoteID: {}, OwnerID: {}", email, noteId,
                    note.getUserId());
            throw new RuntimeException("Access Denied: You cannot update this note");
        }
        note.setTitle(title);
        note.setContent(content);
        noteRepository.save(note);
    }
}
