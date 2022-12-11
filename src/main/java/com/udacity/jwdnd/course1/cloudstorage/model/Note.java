package com.udacity.jwdnd.course1.cloudstorage.model;

public class Note {

    private String title;
    private String description;
    private Integer noteId;
    private Integer userId;

    public Note(Integer noteId, String title, String description, Integer userId) {
        this.title = title;
        this.description = description;
        this.noteId = noteId;
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getNoteId() {
        return noteId;
    }

    public void setNoteId(int noteId) {
        this.noteId = noteId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
