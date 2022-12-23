package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.page.CredentialsTabPage;
import com.udacity.jwdnd.course1.cloudstorage.page.FileTabPage;
import com.udacity.jwdnd.course1.cloudstorage.page.NoteTabPage;
import com.udacity.jwdnd.course1.cloudstorage.service.CredentialService;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.io.File;
import java.util.stream.Collectors;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private WebDriver driver;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	@Autowired
	private CredentialService credentialService;

	/**
	 * PLEASE DO NOT DELETE THIS method.
	 * Helper method for Udacity-supplied sanity checks.
	 **/
	private void doMockSignUp(String firstName, String lastName, String userName, String password) {
		// Create a dummy account for logging in later.

		// Visit the sign-up page.
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		driver.get("http://localhost:" + this.port + "/signup");
		webDriverWait.until(ExpectedConditions.titleContains("Sign Up"));

		// Fill out credentials
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputFirstName")));
		WebElement inputFirstName = driver.findElement(By.id("inputFirstName"));
		inputFirstName.click();
		inputFirstName.sendKeys(firstName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputLastName")));
		WebElement inputLastName = driver.findElement(By.id("inputLastName"));
		inputLastName.click();
		inputLastName.sendKeys(lastName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		WebElement inputUsername = driver.findElement(By.id("inputUsername"));
		inputUsername.click();
		inputUsername.sendKeys(userName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		WebElement inputPassword = driver.findElement(By.id("inputPassword"));
		inputPassword.click();
		inputPassword.sendKeys(password);

		// Attempt to sign up.
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("buttonSignUp")));
		WebElement buttonSignUp = driver.findElement(By.id("buttonSignUp"));
		buttonSignUp.click();

		/*
		 * Check that the sign-up was successful.
		 * // You may have to modify the element "success-msg" and the sign-up
		 * // success message below depending on the rest of your code.
		 */
		// Assertions.assertTrue(driver.findElement(By.id("success-msg")).getText().contains("You
		// successfully signed up!"));
	}

	/**
	 * PLEASE DO NOT DELETE THIS method.
	 * Helper method for Udacity-supplied sanity checks.
	 **/
	private void doLogIn(String userName, String password) {
		// Log in to our dummy account.
		driver.get("http://localhost:" + this.port + "/login");
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		WebElement loginUserName = driver.findElement(By.id("inputUsername"));
		loginUserName.click();
		loginUserName.sendKeys(userName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		WebElement loginPassword = driver.findElement(By.id("inputPassword"));
		loginPassword.click();
		loginPassword.sendKeys(password);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-button")));
		WebElement loginButton = driver.findElement(By.id("login-button"));
		loginButton.click();

		webDriverWait.until(ExpectedConditions.titleContains("Home"));

	}

	private WebDriverWait doUploadFile(String fileName) {
		// Create a test account
		doMockSignUp("Large File", "Test", "LFT", "123");
		doLogIn("LFT", "123");

		// Try to upload an arbitrary large file
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("fileUpload")));
		WebElement fileSelectButton = driver.findElement(By.id("fileUpload"));
		fileSelectButton.sendKeys(new File(fileName).getAbsolutePath());

		WebElement uploadButton = driver.findElement(By.id("uploadButton"));
		uploadButton.click();
		return webDriverWait;
	}

	private void doUploadEmptyFile() {
		// Create a test account
		doMockSignUp("Large File", "Test", "LFT", "123");
		doLogIn("LFT", "123");

		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("fileUpload")));

		// click on upload
		WebElement uploadButton = driver.findElement(By.id("uploadButton"));
		uploadButton.click();

		webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.id("error")));

	}

	private void goBackToHomeFromResultPage(WebDriverWait webDriverWait) {
		try {
			webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.id("success-back")));
		} catch (TimeoutException e) {
			System.out.println("Result page not visible");
		}
		// go back
		WebElement backButton = driver.findElement(By.id("success-back"));
		backButton.click();
	}

	private WebDriverWait doUploadFileAndGoBackToHomePage() {
		var webDriverWait = doUploadFile("TEST_TDS.pdf");
		webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.id("success")));
		goBackToHomeFromResultPage(webDriverWait);
		return webDriverWait;
	}

	private void goToNotesTabFromResultPage(WebDriverWait webDriverWait, NoteTabPage noteTabPage) {
		goBackToHomeFromResultPage(webDriverWait);
		noteTabPage.goToNotesTab();
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("tr[id ^= 'note']")));
	}

	private void goToCredentialsTabFromResultPage(WebDriverWait webDriverWait, CredentialsTabPage credentialsTabPage) {
		goBackToHomeFromResultPage(webDriverWait);
		credentialsTabPage.goToCredentialsTab();
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("tr[id ^= 'credential']")));
	}

	private WebDriverWait doAddNote(String title, String description) {
		// Create a test account
		doMockSignUp("Add Note", "Test", "TAN", "234");
		doLogIn("TAN", "234");
		// Try to add a note
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
		var noteTabPage = new NoteTabPage(driver);
		// go to notes tab
		noteTabPage.goToNotesTab();
		// click on add notes button;
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("add-note")));
		noteTabPage.showAddNoteModal();
		// add note
		noteTabPage.createNote(title, description);
		goToNotesTabFromResultPage(webDriverWait, noteTabPage);
		return webDriverWait;
	}

	private WebDriverWait doAddCredential(String url, String username, String password) {
		doMockSignUp("Add Credential", "Test Credential", "TCN", "456");
		doLogIn("TCN", "456");
		// add credential
		var webDriverWait = new WebDriverWait(driver, 2);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials-tab")));
		var credentialsTabPage = new CredentialsTabPage(driver);
		// go to credentials tab;
		credentialsTabPage.goToCredentialsTab();
		// show credential modal
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("add-credential")));
		credentialsTabPage.showCredentialModal();
		// add credential
		credentialsTabPage.createCredential(url, username, password);
		goToCredentialsTabFromResultPage(webDriverWait, credentialsTabPage);
		return webDriverWait;
	}

	private void cleanFileUpload() {
		var fileTabpage = new FileTabPage(driver);
		var webDriverWait = new WebDriverWait(driver, 2);
		fileTabpage.deleteButtons().get(0).click();
		goBackToHomeFromResultPage(webDriverWait);
	}

	@Test
	public void getLoginPage() {
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the
	 * rest of your code.
	 * This test is provided by Udacity to perform some basic sanity testing of
	 * your code to ensure that it meets certain rubric criteria.
	 *
	 * If this test is failing, please ensure that you are handling redirecting
	 * users
	 * back to the login page after a successful sign-up.
	 * Read more about the requirement in the rubric:
	 * https://review.udacity.com/#!/rubrics/2724/view
	 */
	@Test
	public void testRedirection() {
		// Create a test account
		doMockSignUp("Redirection", "Test", "RT", "123");

		// Check if we have been redirected to the login page.
		Assertions.assertEquals("http://localhost:" + this.port + "/login", driver.getCurrentUrl());
	}

	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the
	 * rest of your code.
	 * This test is provided by Udacity to perform some basic sanity testing of
	 * your code to ensure that it meets certain rubric criteria.
	 *
	 * If this test is failing, please ensure that you are handling bad URLs
	 * gracefully, for example with a custom error page.
	 *
	 * Read more about custom error pages at:
	 * https://attacomsian.com/blog/spring-boot-custom-error-page#displaying-custom-error-page
	 */
	@Test
	public void testBadUrl() {
		// Create a test account
		doMockSignUp("URL", "Test", "UT", "123");
		doLogIn("UT", "123");

		// Try to access a random made-up URL.
		driver.get("http://localhost:" + this.port + "/some-random-page");
		Assertions.assertFalse(driver.getPageSource().contains("Whitelabel Error Page"));
	}

	@Test
	public void testLoginFlow() {
		doMockSignUp("Signup", "Flow", "ST", "234");
		doLogIn("ST", "234");

		var webDriverWait = new WebDriverWait(driver, 2);
		Assertions.assertEquals("Home", driver.getTitle());
		driver.findElement(By.id("logout-btn")).click();
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		Assertions.assertEquals("Login", driver.getTitle());
	}

	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the
	 * rest of your code.
	 * This test is provided by Udacity to perform some basic sanity testing of
	 * your code to ensure that it meets certain rubric criteria.
	 *
	 * If this test is failing, please ensure that you are handling uploading large
	 * files (>1MB),
	 * gracefully in your code.
	 *
	 * Read more about file size limits here:
	 * https://spring.io/guides/gs/uploading-files/ under the "Tuning File Upload
	 * Limits" section.
	 */
	@Test
	public void testLargeUpload() {
		var webDriverWait = doUploadFile("upload5m.zip");
		try {
			webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.id("success")));
		} catch (org.openqa.selenium.TimeoutException e) {
			System.out.println("Large File upload failed");
		}
		Assertions.assertFalse(driver.getPageSource().contains("HTTP Status 403 – Forbidden"));
	}

	@Test
	public void testUploadSuccess() {
		doUploadFileAndGoBackToHomePage();
		// check that file exists on the table
		var fileTabPage = new FileTabPage(driver);
		Assertions.assertTrue(fileTabPage.fileNames().size() > 0);
		cleanFileUpload();
	}

	@Test
	public void testUploadAlreadyExistingFile() {
		var webDriverWait = doUploadFileAndGoBackToHomePage();
		// upload again
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("fileUpload")));
		WebElement fileSelectButton = driver.findElement(By.id("fileUpload"));
		fileSelectButton.sendKeys(new File("TEST_TDS.pdf").getAbsolutePath());

		WebElement uploadButton = driver.findElement(By.id("uploadButton"));
		uploadButton.click();

		webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.id("error")));

		Assertions.assertEquals("File with name already exists",
				driver.findElement(By.id("error-msg")).getText());
		// go back
		driver.findElement(By.id("error-msg-back")).click();
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("fileUpload")));

		cleanFileUpload();
	}

	@Test
	public void testUploadEmptyFile() {
		doUploadEmptyFile();
		Assertions.assertEquals(driver.findElement(By.id("error-msg")).getText(), "No file selected");
	}

	@Test
	public void testFileDeleteSuccess() {
		var webDriverWait = doUploadFileAndGoBackToHomePage();
		var fileTabPage = new FileTabPage(driver);
		// delete file;
		fileTabPage.deleteButtons().get(0).click();
		// back to home page
		goBackToHomeFromResultPage(webDriverWait);
		// assert
		Assertions.assertEquals(0, fileTabPage.fileNames().size());
	}

	@Test
	public void testFileView() {
		doUploadFileAndGoBackToHomePage();
		var fileTabPage = new FileTabPage(driver);
		// view file;
		fileTabPage.viewButtons().get(0).click();
		// assert
		Assertions.assertTrue(driver.getCurrentUrl().contains("home"));
		cleanFileUpload();
	}

	@Test
	public void testAddNote() {
		String title = "Test Add Note";
		String description = "This is a test to add note";
		doAddNote(title, description);
		// assert
		var noteTabPage = new NoteTabPage(driver);
		Assertions.assertTrue(noteTabPage.getNoteDescriptions().size() > 0);
	}

	@Test
	public void testEditNote() {
		String title = "Test Edit Note";
		String description = "This is a test to edit note";
		var webDriverWait = doAddNote(title, description);
		var noteTabPage = new NoteTabPage(driver);
		// click on edit button
		noteTabPage.getEditButtons().get(0).click();
		webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.id("note-id")));
		// edit note
		String editDescription = "This is an edited test note";
		String script = "return document.getElementById('note-id').getAttribute('value');";
		String noteId = ((JavascriptExecutor) driver).executeScript(script).toString();
		noteTabPage.editNote(noteId,
				title, editDescription);
		goToNotesTabFromResultPage(webDriverWait, noteTabPage);
		Assertions.assertTrue(noteTabPage.getNoteDescriptions().contains(editDescription));
	}

	@Test
	public void testDeleteNote() {
		String title = "Test Delete Note";
		String description = "This is a test to delete note";
		var webDriverWait = doAddNote(title, description);
		var noteTabPage = new NoteTabPage(driver);
		// notes size after upload
		var sizeBeforeDelete = noteTabPage.getNoteDescriptions().size();
		// click on delete button
		noteTabPage.getDeleteButtons().get(0).click();
		goBackToHomeFromResultPage(webDriverWait);
		// assert
		Assertions.assertEquals(sizeBeforeDelete - 1, noteTabPage.getNoteDescriptions().size());
	}

	@Test
	public void testAddCredential() {
		String url = "https://google.com";
		String username = "addCredential";
		String password = "234123";
		// do
		var credentialTabPage = new CredentialsTabPage(driver);
		doAddCredential(url, username, password);
		// assert
		Assertions.assertTrue(credentialTabPage.getCredentialRows().size() > 0);
		var credentialUrls = credentialTabPage.getCredentialRows().stream()
				.map(webElem -> webElem.findElement(By.className("credential-url")).getText())
				.collect(Collectors.toList());
		Assertions.assertTrue(credentialUrls.contains(url));
	}

	@Test
	public void testEncryptedPasswordIsDisplayed() {
		String url = "https://google.com";
		String username = "addCredential";
		String password = "234123";
		// do
		var credentialTabPage = new CredentialsTabPage(driver);
		var webDriverWait = doAddCredential(url, username, password);
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.className("credential-url")));
		var credentialRow = credentialTabPage.getCredentialRows().get(0);
		var encryptedPassword = credentialRow.findElement(By.className("credential-password")).getText();
		// get credential id from edit view
		credentialRow.findElement(By.className("btn-success")).click();
		webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.id("credential-id")));
		String script = "return document.getElementById('credential-id').getAttribute('value');";
		String credentialId = ((JavascriptExecutor) driver).executeScript(script).toString();

		var encryptedPasswordFromDB = credentialService.getCredential(Integer.parseInt(credentialId)).getPassword();
		Assertions.assertEquals(encryptedPasswordFromDB, encryptedPassword);

	}

	@Test
	public void testEditCredential() {
		String url = "https://google.com";
		String username = "editCredential";
		String password = "123456";
		// do
		var credentialTabPage = new CredentialsTabPage(driver);
		var webDriverWait = doAddCredential(url, username, password);
		// go to edit view.
		String editedUsername = "editedCredential";
		var credentialRow = credentialTabPage.getCredentialRows()
				.get(credentialTabPage.getCredentialRows().size() - 1);
		credentialRow.findElement(By.className("btn-success"))
				.click();
		webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.id("credential-id")));

		// assert decrypted password
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-password")));
		var actualPassword = driver.findElement(By.id("credential-password")).getAttribute("value");
		Assertions.assertEquals(password, actualPassword);

		// edit
		String script = "return document.getElementById('credential-id').getAttribute('value');";
		String credentialId = ((JavascriptExecutor) driver).executeScript(script).toString();
		credentialTabPage.editCredential(credentialId,
				url, editedUsername, password);
		goToCredentialsTabFromResultPage(webDriverWait, credentialTabPage);

		// assert
		var credentialUsernames = credentialTabPage.getCredentialRows().stream()
				.map(webElem -> webElem.findElement(By.className("credential-username")).getText())
				.collect(Collectors.toList());
		Assertions.assertTrue(credentialUsernames.contains(editedUsername));
	}

	@Test
	public void testDeleteCredential() {
		String url = "https://google.com";
		String username = "deleteCredential";
		String password = "123456";
		var webDriverWait = doAddCredential(url, username, password);
		var credentialTabPage = new CredentialsTabPage(driver);
		var sizeBeforeDelete = credentialTabPage.getCredentialRows().size();
		// delete
		var credentialTabRow = credentialTabPage
				.getCredentialRows().get(0);
		credentialTabRow.findElement(By.className("btn-danger")).click();
		goToCredentialsTabFromResultPage(webDriverWait, credentialTabPage);
		// assert
		Assertions.assertEquals(sizeBeforeDelete - 1,
				credentialTabPage.getCredentialRows().size());
	}

}
