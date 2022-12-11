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
import java.util.stream.Collectors;

public class NoteTabPage {

    @FindAll({@FindBy(className = "note-description")})
    private List<WebElement> noteDescriptionElems;

    @FindAll({@FindBy(css = "tr[id ^= 'note']")})
    private List<WebElement> noteElems;

    @FindBy(id = "add-note")
    private WebElement addNoteBtn;

    @FindBy(id = "nav-notes-tab")
    private WebElement noteTabBtn;

    @FindBy(id = "note-id")
    private WebElement noteIdElem;

    @FindBy(id = "note-title")
    private WebElement noteTitleElem;

    @FindBy(id = "note-description")
    private WebElement noteDescriptionElem;

    @FindBy(id = "save-changes-btn")
    private WebElement noteSubmitBtn;

    private final WebDriver driver;

    public NoteTabPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    public void createNote(String title, String description) {
        var webDriverWait = new WebDriverWait(driver, 2);
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-title")));
        noteTitleElem.clear();
        noteTitleElem.sendKeys(title);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-description")));
        noteDescriptionElem.clear();
        noteDescriptionElem.sendKeys(description);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("save-changes-btn")));
        noteSubmitBtn.click();
    }

    public void editNote(String id, String title, String description) {
        String script = "return document.getElementById('note-id').setAttribute('value', " + id + ");";
        ((JavascriptExecutor) driver).executeScript(script);
        createNote(title, description);
    }

    public List<String> getNoteDescriptions() {
        return noteDescriptionElems.stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    public List<WebElement> getEditButtons() {
        return noteElems.stream()
                .map(webElement -> webElement.findElement(By.className("btn-success")))
                .collect(Collectors.toList());
    }

    public List<WebElement> getDeleteButtons() {
        return noteElems.stream()
                .map(webElement -> webElement.findElement(By.className("btn-danger")))
                .collect(Collectors.toList());
    }

    public void showAddNoteModal() {
        System.out.println();
        addNoteBtn.click();
    }

    public void goToNotesTab() { noteTabBtn.click(); }


    public WebElement getNoteTitleElem() { return noteTitleElem; }
}
