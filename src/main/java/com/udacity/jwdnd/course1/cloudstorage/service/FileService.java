package com.udacity.jwdnd.course1.cloudstorage.service;

import com.udacity.jwdnd.course1.cloudstorage.exception.FileExistsException;
import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileService {

    private final FileMapper fileMapper;

    public FileService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    public List<File> getFiles(Integer userId) {
        return fileMapper.getAllFiles(userId);
    }

    public void addFile(File file) throws FileExistsException {
        var existingFile = fileMapper.getFileByName(file.getFileName(), file.getUserId());
        if(existingFile != null) {
            throw new FileExistsException("File already exists");
        }
        fileMapper.insertFile(file);
    }

    public File getFile(int id) { return fileMapper.getFile(id); }

    public void deleteFile(int id) {
        fileMapper.deleteFile(id);
    }
}
