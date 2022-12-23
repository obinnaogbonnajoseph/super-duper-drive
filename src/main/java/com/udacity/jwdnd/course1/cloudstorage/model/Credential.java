package com.udacity.jwdnd.course1.cloudstorage.model;

public class Credential {

    private Integer id;
    private String url;
    private String username;
    private String password;
    private String encodedKey;
    private String decryptedPassword;
    private int userId;

    public Credential(Integer id, String url, String username,
                      String encodedKey, String password, Integer userId) {
        this.id = id;
        this.url = url;
        this.username = username;
        this.encodedKey = encodedKey;
        this.password = password;
        this.userId = userId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEncodedKey() {
        return encodedKey;
    }

    public void setEncodedKey(String encodedKey) {
        this.encodedKey = encodedKey;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getDecryptedPassword() {
        return decryptedPassword;
    }

    public void setDecryptedPassword(String decryptedPassword) {
        this.decryptedPassword = decryptedPassword;
    }
}
