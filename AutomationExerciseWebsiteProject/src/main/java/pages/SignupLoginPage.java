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
public class SignupLoginPage {
    
    // Locators
    private final By emailLocator = By.cssSelector("input[data-qa='login-email']");
    private final By passwordLocator = By.cssSelector("input[data-qa='login-password']");
    private final By loginButtonLocator = By.cssSelector("button[data-qa='login-button']");
    private final By loginToYourAccountTextLocator = By.cssSelector("div.login-form > h2");
    private final By incorrectLoginTextLocator = By.cssSelector("div.login-form form p");
    private final By newUserSignupLocator = By.cssSelector("div.signup-form h2");
    private final By signupNameLocator = By.cssSelector("input[data-qa=\"signup-name\"]");
    private final By signupEmailLocator = By.cssSelector("input[data-qa=\"signup-email\"]");
    private final By signupButtonLocator = By.cssSelector("button[data-qa=\"signup-button\"]");
    private final By signupErrorMessageLocator = By.cssSelector("form[action=\"/signup\"]  p");
    private final SelenuimFramework framework;

    public SignupLoginPage(SelenuimFramework framework) {
        this.framework = framework;
    }

    // Actions
    public void sendLoginEmail(String email) {
        framework.sendKeys(emailLocator, email);
    }

    public void sendLoginPassword(String password) {
        framework.sendKeys(passwordLocator, password);
    }

    public void clickLoginButton() {
        framework.click(loginButtonLocator);
    }

   
    public String getLoginToYourAccountText() {
        return framework.getText(loginToYourAccountTextLocator);
    }

    public String getIncorrectLoginText() {
        return framework.getText(incorrectLoginTextLocator);
    }
    
    public void sendSignupEmail(String email) {
        framework.sendKeys(signupEmailLocator, email);
    }

    public void sendSignupName(String name) {
        framework.sendKeys(signupNameLocator, name);
    }
    
    public void clickSignupButton() {
        framework.click(signupButtonLocator);
    }
    
    public String getNewUserSignupText() {
        return framework.getText(newUserSignupLocator);
    }
    
    public String getSignupErrorMessageText() {
        return framework.getText(signupErrorMessageLocator);
    }

}
