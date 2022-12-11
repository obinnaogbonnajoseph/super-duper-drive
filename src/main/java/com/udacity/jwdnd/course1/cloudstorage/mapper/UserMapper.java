package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;


@Mapper
public interface UserMapper {

    @Select("select * from USERS where username = #{username}")
    User getUser(String username);

    @Select("select * from USERS")
    List<User> getAllUsers();

    @Insert("insert into USERS (username, salt, password, firstname, lastname) " +
    "values (#{username}, #{salt}, #{password}, #{firstName}, #{lastName})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertUser(User user);
}
