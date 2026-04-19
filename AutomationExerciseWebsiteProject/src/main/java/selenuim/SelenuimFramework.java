
package selenuim;

import java.io.File;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SelenuimFramework {

    private WebDriver browser;
    private WebDriverWait explicitWait;
    private final int DEFAULT_TIMEOUT = 10;
    

    // ------------------- Initialization -------------------
    public void initializeBrowser() {
        // Build the relative path to Project-1/Downloads
        String downloadDir = Paths.get(System.getProperty("user.dir"))
                                  .getParent()   
                                  .resolve("Downloads")
                                  .toString();

        // Setup Chrome options
        ChromeOptions options = new ChromeOptions();
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("download.default_directory", downloadDir); // set custom download directory
        prefs.put("download.prompt_for_download", false); // auto-download without prompt
        prefs.put("download.directory_upgrade", true);
        prefs.put("safebrowsing.enabled", true);

        options.setExperimentalOption("prefs", prefs);

        browser = new ChromeDriver(options);
        browser.manage().window().maximize();
        explicitWait = new WebDriverWait(browser, Duration.ofSeconds(DEFAULT_TIMEOUT));
        System.out.println("Browser initialized and maximized with download dir: " + downloadDir);
    }


    public void closeBrowser() {
        if (browser != null) {
            browser.quit();
            browser = null;
            System.out.println("Browser closed.");
        }
    }

    // ------------------- Helper Functions -------------------
    private Actions createAction() {
        return new Actions(browser);
    }

    public WebElement findElement(By locator) {
        return explicitWait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }
    
    public List<WebElement> findElements(By locator) {
        List<WebElement> elements = browser.findElements(locator);
        return elements;
    }

    private WebElement waitUntilClickable(By locator, int timeoutSeconds) {
        return new WebDriverWait(browser, Duration.ofSeconds(timeoutSeconds))
                .until(ExpectedConditions.elementToBeClickable(locator));
    }

    // ------------------- Navigation -------------------
    public void navigateToURL(String url) {
        browser.navigate().to(url);
        System.out.println("Navigated to URL: " + url);
    }

    public void navigateBack() {
        browser.navigate().back();
        System.out.println("Navigated back.");
    }

    public void navigateForward() {
        browser.navigate().forward();
        System.out.println("Navigated forward.");
    }

    public void refreshPage() {
        browser.navigate().refresh();
        System.out.println("Page refreshed.");
    }

    public String getPageTitle() {
        String title = browser.getTitle();
        System.out.println("Page title: " + title);
        return title;
    }

    public String getCurrentURL() {
        String url = browser.getCurrentUrl();
        System.out.println("Current URL: " + url);
        return url;
    }

    // ------------------- Waits -------------------
    public void implicitWait(int seconds) {
        browser.manage().timeouts().implicitlyWait(Duration.ofSeconds(seconds));
        System.out.println("Implicit wait set to " + seconds + " seconds.");
    }

    public void explicitWait(By locator, int timeoutSeconds) {
        new WebDriverWait(browser, Duration.ofSeconds(timeoutSeconds))
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
        System.out.println("Explicit wait finished for: " + locator);
    }

    public void fluentWait(By locator, int timeoutSeconds, int pollingMillis, String timeoutMessage) {
        new FluentWait<>(browser)
                .withTimeout(Duration.ofSeconds(timeoutSeconds))
                .pollingEvery(Duration.ofMillis(pollingMillis))
                .withMessage(timeoutMessage)
                .ignoring(NoSuchElementException.class)
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
        System.out.println("Fluent wait finished for: " + locator);
    }

    // ------------------- Element Interactions -------------------
    public void click(By locator) {
        waitUntilClickable(locator, DEFAULT_TIMEOUT).click();
        System.out.println("Clicked element: " + locator);
    }

    public void sendKeys(By locator, String text) {
        WebElement element = waitUntilClickable(locator, DEFAULT_TIMEOUT);
        element.clear();
        element.sendKeys(text);
        System.out.println("Sent keys to element: " + locator + " | Value: " + text);
    }

    public String getText(By locator) {
        String text = findElement(locator).getText();
        System.out.println("Got text from element " + locator + ": " + text);
        return text;
    }

    public void rightClick(By locator) {
        createAction().contextClick(findElement(locator)).perform();
        System.out.println("Right-clicked on element: " + locator);
    }

    public void scrollToElement(By locator) {
        createAction().scrollToElement(findElement(locator)).perform();
        System.out.println("Scrolled to element: " + locator);
    }

    // ------------------- Dropdowns -------------------
    public void selectDropdownByVisibleText(By locator, String visibleText) {
        Select select = new Select(waitUntilClickable(locator, DEFAULT_TIMEOUT));
        select.selectByVisibleText(visibleText);
        System.out.println("Selected dropdown by visible text: " + visibleText);
    }

    public void selectDropdownByValue(By locator, String value) {
        Select select = new Select(waitUntilClickable(locator, DEFAULT_TIMEOUT));
        select.selectByValue(value);
        System.out.println("Selected dropdown by value: " + value);
    }

    public void selectDropdownByIndex(By locator, int index) {
        Select select = new Select(waitUntilClickable(locator, DEFAULT_TIMEOUT));
        select.selectByIndex(index);
        System.out.println("Selected dropdown by index: " + index);
    }

    // ------------------- Mouse Actions -------------------
    public void dragAndDrop(By sourceLocator, By targetLocator) {
        WebElement source = waitUntilClickable(sourceLocator, DEFAULT_TIMEOUT);
        WebElement target = waitUntilClickable(targetLocator, DEFAULT_TIMEOUT);
        createAction().dragAndDrop(source, target).perform();
        System.out.println("Dragged element " + sourceLocator + " and dropped on " + targetLocator);
    }

    public void checkCheckbox(By locator) {
        WebElement element = waitUntilClickable(locator, DEFAULT_TIMEOUT);
        if (!element.isSelected()) {
            element.click();
            System.out.println("Checked checkbox: " + locator);
        } else {
            System.out.println("Checkbox already checked: " + locator);
        }
    }

    public void uncheckCheckbox(By locator) {
        WebElement element = waitUntilClickable(locator, DEFAULT_TIMEOUT);
        if (element.isSelected()) {
            element.click();
            System.out.println("Unchecked checkbox: " + locator);
        } else {
            System.out.println("Checkbox already unchecked: " + locator);
        }
    }

    public void selectRadioButton(By locator) {
        WebElement element = waitUntilClickable(locator, DEFAULT_TIMEOUT);
        if (!element.isSelected()) {
            element.click();
            System.out.println("Selected radio button: " + locator);
        } else {
            System.out.println("Radio button already selected: " + locator);
        }
    }

    // ------------------- Windows & Alerts -------------------
    public void switchToWindowByTitle(String windowTitle) {
        String currentHandle = browser.getWindowHandle();
        for (String handle : browser.getWindowHandles()) {
            browser.switchTo().window(handle);
            if (browser.getTitle().equals(windowTitle)) {
                System.out.println("Switched to window with title: " + windowTitle);
                return;
            }
        }
        browser.switchTo().window(currentHandle);
        System.out.println("Window with title '" + windowTitle + "' not found.");
    }

    public void switchToWindowByHandle(String windowHandle) {
        browser.switchTo().window(windowHandle);
        System.out.println("Switched to window with handle: " + windowHandle);
    }

    public void closeCurrentWindow() {
        browser.close();
        System.out.println("Closed current window.");
        if (browser.getWindowHandles().isEmpty()) {
            browser.quit();
            browser = null;
            System.out.println("All windows closed. Browser quit.");
        }
    }

    public void acceptAlert() {
        Alert alert = explicitWait.until(ExpectedConditions.alertIsPresent());
        alert.accept();
        System.out.println("Alert accepted.");
    }

    public void dismissAlert() {
        Alert alert = explicitWait.until(ExpectedConditions.alertIsPresent());
        alert.dismiss();
        System.out.println("Alert dismissed.");
    }

    public String getAlertText() {
        Alert alert = explicitWait.until(ExpectedConditions.alertIsPresent());
        String text = alert.getText();
        System.out.println("Alert text: " + text);
        return text;
    }

    public void sendTextToAlert(String text) {
        Alert alert = explicitWait.until(ExpectedConditions.alertIsPresent());
        alert.sendKeys(text);
        alert.accept();
        System.out.println("Sent text to alert and accepted: " + text);
    }
    
    public boolean isDisplayed(By locator){
       return browser.findElement(locator).isDisplayed();
    }
    
    public void clearAndSendKeys(By locator,String text ){
        WebElement element = browser.findElement(locator);
        element.clear();
        element.sendKeys(text);
        
    
    }
    
    public void hoverOnElement(By locator) {
        WebElement hoverElement = findElement(locator);
        createAction().moveToElement(hoverElement).perform();
    }
    
    
    public void waitForElementToBeInvesible(By locator){
         WebDriverWait wait = new WebDriverWait(browser, Duration.ofSeconds(10));
         wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }
    
    
    public boolean waitForFile(String downloadDir, String fileExtension, int timeoutSeconds) {
        File dir = new File(downloadDir);
        long endTime = System.currentTimeMillis() + (timeoutSeconds * 1000);

        while (System.currentTimeMillis() < endTime) {
            File[] files = dir.listFiles((d, name) -> name.toLowerCase().endsWith(fileExtension));

            if (files != null && files.length > 0) {
                // Find the most recently modified file
                File latestFile = Arrays.stream(files)
                                        .max((f1, f2) -> Long.compare(f1.lastModified(), f2.lastModified()))
                                        .orElse(null);

                if (latestFile != null) {
                    long lastModified = latestFile.lastModified();
                    long now = System.currentTimeMillis();

                    // Check if file was modified (downloaded) within the last few seconds
                    if ((now - lastModified) <= (timeoutSeconds * 1000)) {
                        return true;
                    }
                }
            }

            try {
                TimeUnit.SECONDS.sleep(1); // wait 1 sec before checking again
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return false;
            }
        }

        return false; // no new file found within timeout
    }
    
   
}
