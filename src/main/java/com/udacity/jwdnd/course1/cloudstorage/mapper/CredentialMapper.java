package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.dao.CredentialDao;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialMapper {

    @Select("select * from CREDENTIALS where userid = #{userId}")
    List<Credential> getAllCredentials(Integer userId);

    @Select("select * from CREDENTIALS where credentialid = #{id}")
    Credential getCredential(int id);

    @Insert("insert into CREDENTIALS (url, username, key, password, userid) " +
            "values (#{url}, #{username}, #{encodedKey}, #{password}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialId")
    int insertCredential(CredentialDao credential);

    @Update("update CREDENTIALS SET url=#{url}, username=#{username}, key=#{encodedKey}, password=#{password} " +
            "where credentialid = #{credentialId}")
    int updateCredential(CredentialDao credential);

    @Delete("delete from CREDENTIALS where credentialid = #{credentialId}")
    int deleteCredential(int credentialId);
}
