package com.ramazanbatbay.security_webapp.controller;

import com.ramazanbatbay.security_webapp.model.dto.NoteDto;
import com.ramazanbatbay.security_webapp.service.NoteService;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/notes")
public class NoteController {

    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping
    public String getNotes(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails != null) {
            model.addAttribute("notes", noteService.getNotesForUser(userDetails.getUsername()));
            model.addAttribute("noteDto", new NoteDto());
        }
        return "notes";
    }

    @PostMapping
    public String createNote(@Valid @ModelAttribute("noteDto") NoteDto noteDto,
            BindingResult bindingResult,
            @AuthenticationPrincipal UserDetails userDetails,
            Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("notes", noteService.getNotesForUser(userDetails.getUsername()));
            return "notes";
        }
        noteService.createNote(userDetails.getUsername(), noteDto.getTitle(), noteDto.getContent());
        return "redirect:/notes";
    }

    @GetMapping("/delete/{id}")
    public String deleteNote(@PathVariable Integer id, @AuthenticationPrincipal UserDetails userDetails) {
        try {
            noteService.deleteNote(userDetails.getUsername(), id);
        } catch (RuntimeException e) {
            return "redirect:/notes?error=" + e.getMessage();
        }
        return "redirect:/notes";
    }
}
