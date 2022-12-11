package com.udacity.jwdnd.course1.cloudstorage.page;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class CredentialsTabPage {

    @FindAll({@FindBy(css = "tr[id ^='credential']")})
    private List<WebElement> credentialRowElems;

    @FindBy(id = "credential-id")
    private WebElement credentialIdElem;

    @FindBy(id = "credential-url")
    private WebElement credentialUrlElem;

    @FindBy(id = "credential-username")
    private WebElement credentialUsernameElem;

    @FindBy(id = "credential-password")
    private WebElement credentialPasswordElem;

    @FindBy(id = "credentialSubmit")
    private WebElement submitBtn;

    @FindBy(id = "nav-credentials-tab")
    private WebElement credentialsTabBtn;

    @FindBy(id = "add-credential")
    private WebElement addCredentialBtn;

    private final WebDriver driver;

    public CredentialsTabPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    public void createCredential(String url, String username, String password) {
        var webDriverWait = new WebDriverWait(driver, 2);
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-url")));
        credentialUrlElem.clear();
        credentialUrlElem.sendKeys(url);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-username")));
        credentialUsernameElem.clear();
        credentialUsernameElem.sendKeys(username);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-password")));
        credentialPasswordElem.clear();
        credentialPasswordElem.sendKeys(password);

        String script = "return document.getElementById('credentialSubmit').click();";
        ((JavascriptExecutor) driver).executeScript(script);
    }

    public void editCredential(String id, String url, String username, String password) {
        String script = "return document.getElementById('credential-id').setAttribute('value', " + id + ");";
        ((JavascriptExecutor) driver).executeScript(script);
        createCredential(url, username, password);
    }

    public List<WebElement> getCredentialRows() {
        return credentialRowElems;
    }

    public void goToCredentialsTab() {
        credentialsTabBtn.click();
    }

    public void showCredentialModal() {
        addCredentialBtn.click();
    }
}
