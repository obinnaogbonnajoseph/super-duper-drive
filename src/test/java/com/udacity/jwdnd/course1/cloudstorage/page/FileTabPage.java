package com.udacity.jwdnd.course1.cloudstorage.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;
import java.util.stream.Collectors;

public class FileTabPage {

    @FindBy(id = "fileUpload")
    private WebElement fileUploadInput;

    @FindBy(id = "uploadButton")
    private WebElement uploadBtn;

    @FindAll({@FindBy(css = "tr[id *= 'file'")})
    private List<WebElement> fileElems;

    public FileTabPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void uploadFile(String filePath) {
        fileUploadInput.sendKeys(filePath);
        uploadBtn.click();
    }

    public List<String> fileNames() {
        return fileElems.stream()
                .map(webElement -> {
                    var elem = webElement.findElement(By.className("file-name"));
                    return elem.getText();
                }).collect(Collectors.toList());
    }

    public List<WebElement> deleteButtons() {
        return fileElems.stream()
                .map(webElement -> webElement.findElement(By.className("btn-danger")))
                .collect(Collectors.toList());
    }

    public List<WebElement> viewButtons() {
        return fileElems.stream()
                .map(webElement -> webElement.findElement(By.className("btn-success")))
                .collect(Collectors.toList());
    }
}
