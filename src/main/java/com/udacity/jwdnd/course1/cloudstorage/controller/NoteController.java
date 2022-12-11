package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.dao.NoteDao;
import com.udacity.jwdnd.course1.cloudstorage.service.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/note")
public class NoteController {

    private final NoteService noteService;
    private final UserService userService;

    public NoteController(NoteService noteService, UserService userService) {
        this.noteService = noteService;
        this.userService = userService;
    }

    @PostMapping
    public String addOrEdit(NoteDao note, Model model, Authentication authentication) {
        var user = userService.getUser(authentication.getName());
        note.setUserId(user.getId());
        var addNote = -1;
        if(note.getNoteId() == null) {
            addNote = noteService.addNote(note);
        } else {
            var noteFromDB = noteService.getNote(note.getNoteId());
            if(noteFromDB != null) {
                addNote = noteService.updateNote(note);
            } else {
                model.addAttribute("errorMsg", "Note not found");
            }
        }
        if(addNote != -1) {
            model.addAttribute("success", true);
        } else {
            model.addAttribute("error", true);
        }
        return "result";
    }

    @GetMapping("delete")
    public String deleteNote(@RequestParam("id") Integer noteId, Model model) {
        var note = noteService.getNote(noteId);
        if(note != null) {
            noteService.deleteNote(noteId);
            model.addAttribute("success", true);
        } else {
            model.addAttribute("error", true);
            model.addAttribute("errorMsg", "Note not found");
        }
        return "result";
    }
}
