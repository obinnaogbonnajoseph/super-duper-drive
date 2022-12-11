package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.dao.CredentialDao;
import com.udacity.jwdnd.course1.cloudstorage.service.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/credential")
public class CredentialController {

    private final CredentialService credentialService;
    private final UserService userService;

    public CredentialController(CredentialService credentialService, UserService userService) {
        this.credentialService = credentialService;
        this.userService = userService;
    }

    @PostMapping
    public String addOrEdit(CredentialDao credential, Model model, Authentication authentication) {
        var user = userService.getUser(authentication.getName());
        credential.setUserId(user.getId());
        var addCredential = -1;
        if(credential.getCredentialId() == null) {
            addCredential = credentialService.addCredential(credential);
        } else {
            var credentialFromDB = credentialService.getCredential(credential.getCredentialId());
            if(credentialFromDB != null) {
                addCredential = credentialService.updateCredential(credential);
            } else {
                model.addAttribute("errorMsg", "Credential not found");
            }
        }
        if(addCredential != -1) {
            model.addAttribute("success", true);
        } else {
            model.addAttribute("error", true);
        }
        return "result";
    }

    @GetMapping("delete")
    public String deleteCredential(@RequestParam("id") Integer noteId, Model model) {
        var note = credentialService.getCredential(noteId);
        if(note != null) {
            credentialService.deleteCredential(noteId);
            model.addAttribute("success", true);
        } else {
            model.addAttribute("error", true);
            model.addAttribute("errorMsg", "Note not found");
        }
        return "result";
    }
}
