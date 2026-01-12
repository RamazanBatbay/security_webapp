package com.ramazanbatbay.security_webapp.service;

import com.ramazanbatbay.security_webapp.model.Note;
import com.ramazanbatbay.security_webapp.repository.NoteRepository;
import com.ramazanbatbay.security_webapp.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    private final NoteRepository noteRepository;
    private final UserRepository userRepository;

    public NoteService(NoteRepository noteRepository, UserRepository userRepository) {
        this.noteRepository = noteRepository;
        this.userRepository = userRepository;
    }

    public List<Note> getNotesForUser(String email) {
        Integer userId = getUserIdByEmail(email);
        // Using the native query requirement
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
            throw new RuntimeException("Access Denied: You cannot delete this note");
        }
        noteRepository.delete(note);
    }

    private Integer getUserIdByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"))
                .getId();
    }
}
