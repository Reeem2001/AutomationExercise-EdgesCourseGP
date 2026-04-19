/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pages;

import org.openqa.selenium.By;
import selenuim.SelenuimFramework;

/**
 *
 * @author Reem
 */
public class ContactUsPage {
    private final SelenuimFramework framework;
    
    // Locators
    private final By getInTouchTextLocator = By.cssSelector("div.contact-form h2.title ");
    private final By contactUsNameLocator = By.cssSelector("input[data-qa=\"name\"]");
    private final By contactUsEmailLocator = By.cssSelector("input[data-qa=\"email\"]");
    private final By contactUsSubjectLocator = By.cssSelector("input[data-qa=\"subject\"]");
    private final By contactUsMessageLocator = By.cssSelector("textarea[data-qa=\"message\"]");
    private final By submitButtonLocator = By.cssSelector("input[data-qa=\"submit-button\"]");
    private final By successMessageTextLocator = By.cssSelector("div.status");
    private final By homeButtonLocator = By.cssSelector("div#form-section a[href=\"/\"]");
    private final By uploadFileLocator  = By.cssSelector("input[type='file'][name='upload_file']");
    private final By footerLocator = By.cssSelector("footer div.footer-bottom");

   

    // Constructor takes framework
    public ContactUsPage(SelenuimFramework framework) {
        this.framework = framework;
    }
    
    
    public void clickHomeButton (){
        framework.click(homeButtonLocator);
    }
    
    public void uploadFile(String filePath) {
        framework.findElement(uploadFileLocator).sendKeys(filePath);
        
    }
    
    public void clickSubmitButton (){
        framework.click(submitButtonLocator);
    }
    
    public void sendContactUsName (String name ){
        framework.sendKeys(contactUsNameLocator, name);
    }
    
    public void sendContactUsEmail (String email){
        framework.sendKeys(contactUsEmailLocator, email);
    }
    
    public void sendContactUsSubject (String subject ){
        framework.sendKeys(contactUsSubjectLocator, subject);
    }
    
    public void sendContactUsMessage (String message ){
        framework.sendKeys(contactUsMessageLocator, message);
    }
    
    public String getGetInTouchText(){
       return framework.getText(getInTouchTextLocator);
    }
    
    public String getSuccessMessageText(){
        return framework.getText(successMessageTextLocator);
    }
    
    public void clickOkButton(){
        framework.acceptAlert();
    }
    
    public void scrollToFooter(){
        framework.scrollToElement(footerLocator);
    }
}
