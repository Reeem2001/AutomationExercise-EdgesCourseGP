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
public class PaymentPage {
        // Locators
        private final By nameOnCardLocator = By.cssSelector("input[data-qa=\"name-on-card\"]");
        private final By cardNumberLocator = By.cssSelector("input[data-qa=\"card-number\"]");
        private final By cvcLocator = By.cssSelector("input[data-qa=\"cvc\"]");
        private final By expMonthLocator = By.cssSelector("input[data-qa=\"expiry-month\"]");
        private final By expYearLocator = By.cssSelector("input[data-qa=\"expiry-year\"]");
        private final By payAndConfirmButtonLocator = By.cssSelector("button.submit-button");
        private final By orderPlacedTextLocator = By.cssSelector("h2.title b");
        private final By footerLocator = By.cssSelector("footer div.footer-bottom");
        private final SelenuimFramework framework;
    
    
    public PaymentPage(SelenuimFramework framework) {
        this.framework = framework;
    }
    
    // Actions 
    public void sendNameOnCard(String nameOnCard){
        framework.sendKeys(nameOnCardLocator, nameOnCard);
    }
    public void sendCardNumber(String cardNumber){
        framework.sendKeys(cardNumberLocator, cardNumber);
    }
    public void sendCVC(String cvc){
        framework.sendKeys(cvcLocator, cvc);
    }
    public void sendExpMonth(String expMonth){
        framework.sendKeys(expMonthLocator, expMonth);
    }
    public void sendExpYear(String expYear){
        framework.sendKeys(expYearLocator, expYear);
    }
    public void clickPayAndConfirmButton(){
        framework.click(payAndConfirmButtonLocator);
    }
    public String getOrderPlacedText(){
        return framework.getText(orderPlacedTextLocator);
    }
    
    public void scrollToFooter(){
        framework.scrollToElement(footerLocator);
    }
}
