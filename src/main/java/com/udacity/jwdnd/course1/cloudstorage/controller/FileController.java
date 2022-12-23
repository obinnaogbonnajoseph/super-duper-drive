package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.exception.FileExistsException;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.service.FileService;
import com.udacity.jwdnd.course1.cloudstorage.service.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/file")
public class FileController {

    private final FileService fileService;
    private final UserService userService;

    public FileController(FileService fileService, UserService userService) {
        this.fileService = fileService;
        this.userService = userService;
    }

    @PostMapping("upload")
    public String uploadFile(@RequestParam("fileUpload")MultipartFile file, Model model,
                             Authentication authentication) throws IOException {
        if(file.getSize() == 0) {
            model.addAttribute("error", true);
            model.addAttribute("errorMsg", "No file selected");
        } else {
            var user = userService.getUser(authentication.getName());
            var fileToUpload = new File(null, file.getOriginalFilename(),
                    file.getContentType(),  file.getSize(), user.getId(), file.getBytes());
            try {
                fileService.addFile(fileToUpload);
                model.addAttribute("success", true);
            } catch(FileExistsException e) {
                model.addAttribute("error", true);
                model.addAttribute("errorMsg", "File with name already exists");
            }
        }
        return "result";
    }

    @GetMapping("url")
    public ResponseEntity<?> downloadFile(@RequestParam("id") Integer fileId) {
        var file = fileService.getFile(fileId);
        if(file != null) {
            return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                    "attachment; filename=\"" + file.getFileName() + "\"").body(file.getFileData());
        }
        return ResponseEntity.ok(ResponseEntity.EMPTY);
    }

    @GetMapping("delete")
    public String deleteFile(@RequestParam("id") Integer fileId, Model model) {
        var file = fileService.getFile(fileId);
        if(file != null) {
            fileService.deleteFile(file.getFileId());
            model.addAttribute("success", true);
        } else {
            model.addAttribute("error", true);
            model.addAttribute("errorMsg", "File not found");
        }
        return "result";
    }
}
