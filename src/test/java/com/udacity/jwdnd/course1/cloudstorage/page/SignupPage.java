package com.udacity.jwdnd.course1.cloudstorage.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SignupPage {

    @FindBy(id = "success-msg")
    private WebElement successMsgElem;

    @FindBy(id = "signup-error-msg")
    private WebElement signupErrorMsgElem;

    @FindBy(id = "inputFirstName")
    private WebElement firstNameElem;

    @FindBy(id = "inputLastName")
    private WebElement lastNameElem;

    @FindBy(id = "inputUsername")
    private WebElement usernameElem;

    @FindBy(id = "inputPassword")
    private WebElement passwordElem;

    @FindBy(id = "buttonSignUp")
    private WebElement submitBtn;

    public SignupPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void signup(String firstName, String lastName, String username, String password) {
        firstNameElem.sendKeys(firstName);
        lastNameElem.sendKeys(lastName);
        usernameElem.sendKeys(username);
        passwordElem.sendKeys(password);
        submitBtn.click();
    }

    public boolean hasError() {
        return signupErrorMsgElem != null;
    }

    public boolean hasSuccess() {
        return successMsgElem != null;
    }
}
