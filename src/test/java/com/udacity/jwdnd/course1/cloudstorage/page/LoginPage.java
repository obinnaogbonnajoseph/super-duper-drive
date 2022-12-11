package com.udacity.jwdnd.course1.cloudstorage.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {

    @FindBy(id = "inputUsername")
    private WebElement usernameElem;

    @FindBy(id = "inputPassword")
    private WebElement passwordElem;

    @FindBy(id = "login-error")
    private WebElement loginErrorElem;

    @FindBy(id = "login-button")
    private WebElement loginBtn;

    @FindBy(id = "signup-link")
    private WebElement signupLinkElem;

    public LoginPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    private void login(String username, String password) {
        usernameElem.sendKeys(username);
        passwordElem.sendKeys(password);
        loginBtn.click();
    }

    private boolean hasLoginError() {
        return loginErrorElem != null;
    }

    private boolean hasSignupLink() {
        return signupLinkElem != null;
    }
}
