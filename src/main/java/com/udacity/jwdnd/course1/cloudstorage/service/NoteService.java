package com.udacity.jwdnd.course1.cloudstorage.service;

import com.udacity.jwdnd.course1.cloudstorage.dao.NoteDao;
import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    private final NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    public List<Note> getNotes(Integer userId) {
        return noteMapper.getAllNotes(userId);
    }

    public Integer addNote(NoteDao note) {
        return noteMapper.insertNote(note);
    }

    public Integer updateNote(NoteDao note) {
        return noteMapper.updateNote(note);
    }

    public Note getNote(int id) {
        return noteMapper.getNote(id);
    }

    public void deleteNote(int id) {
        noteMapper.deleteNote(id);
    }
}
