package com.udacity.jwdnd.course1.cloudstorage.service;

import com.udacity.jwdnd.course1.cloudstorage.dao.CredentialDao;
import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialService {

    private final CredentialMapper credentialMapper;

    private final EncryptionService encryptionService;

    public CredentialService(CredentialMapper credentialMapper, EncryptionService encryptionService) {
        this.credentialMapper = credentialMapper;
        this.encryptionService = encryptionService;
    }

    public List<Credential> getCredentials(Integer userId) {
        var credentials = credentialMapper.getAllCredentials(userId);
        credentials.forEach(credential -> credential.setEncodedKey(""));
        return credentials;
    }

    public Integer addCredential(CredentialDao credential) {
        updateKeyAndPassword(credential);
        return credentialMapper.insertCredential(credential);
    }

    public Integer updateCredential(CredentialDao credential)
    {
        updateKeyAndPassword(credential);
        return credentialMapper.updateCredential(credential);
    }

    public Credential getCredential(int id) {
        var credential = credentialMapper.getCredential(id);
        credential.setEncodedKey("");
        return credential;
    }

    public void deleteCredential(int id) {
        credentialMapper.deleteCredential(id);
    }

    private String generateRandomKey() {
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        return Base64.getEncoder().encodeToString(key);
    }

    private String encryptPassword(String password, String key) {
        return encryptionService.encryptValue(password, key);
    }

    private void updateKeyAndPassword(CredentialDao credential) {
        String key = generateRandomKey();
        String encryptedPassword = encryptPassword(credential.getPassword(), key);
        credential.setPassword(encryptedPassword);
        credential.setEncodedKey(key);
    }
}
