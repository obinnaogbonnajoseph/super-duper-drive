package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.dao.NoteDao;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoteMapper {

    @Select("select * from NOTES where userid = #{userId}")
    List<Note> getAllNotes(Integer userId);

    @Select("select * from NOTES where noteid = #{id}")
    Note getNote(int id);

    @Insert("insert into NOTES (notetitle, notedescription, userid) " +
    "values (#{noteTitle}, #{noteDescription}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "noteId")
    int insertNote(NoteDao note);

    @Update("update NOTES SET notetitle=#{noteTitle}, notedescription=#{noteDescription} " +
    "where noteid = #{noteId}")
    int updateNote(NoteDao note);

    @Delete("delete from NOTES where noteid = #{noteId}")
    int deleteNote(int noteId);
}
