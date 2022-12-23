package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileMapper {

    @Select("select * from FILES where userid = #{userId}")
    List<File> getAllFiles(Integer userId);

    @Select("select * from FILES where fileId = #{id}")
    File getFile(Integer id);

    @Select("select * from FILES where filename = #{fileName}")
    File getFileByName(String name);

    @Insert("insert into FILES (filename, contenttype, filesize, userid, filedata) " +
            "values (#{fileName}, #{contentType}, #{fileSize}, #{userId}, #{fileData})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    int insertFile(File file);

    @Delete("delete from FILES where fileId = #{fileId}")
    int deleteFile(Integer fileId);

}
