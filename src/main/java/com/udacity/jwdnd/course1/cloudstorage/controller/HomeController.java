package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.dao.CredentialDao;
import com.udacity.jwdnd.course1.cloudstorage.dao.NoteDao;
import com.udacity.jwdnd.course1.cloudstorage.service.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.service.FileService;
import com.udacity.jwdnd.course1.cloudstorage.service.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class HomeController {

    private final FileService fileService;
    private final UserService userService;
    private final NoteService noteService;
    private final CredentialService credentialService;

    public HomeController(FileService fileService, UserService userService,
                          NoteService noteService, CredentialService credentialService) {
        this.fileService = fileService;
        this.userService = userService;
        this.noteService = noteService;
        this.credentialService = credentialService;
    }

    @GetMapping()
    public String loadHome(Model model, Authentication authentication) {
        var user = userService.getUser(authentication.getName());
        var files = fileService.getFiles(user.getId());
        var notes = noteService.getNotes(user.getId());
        var credentials = credentialService.getCredentials(user.getId());
        model.addAttribute("files", files);
        model.addAttribute("notes", notes);
        model.addAttribute("credentials", credentials);
        // requests
        model.addAttribute("note", new NoteDao());
        model.addAttribute("credential", new CredentialDao());
        return "home";
    }
}
