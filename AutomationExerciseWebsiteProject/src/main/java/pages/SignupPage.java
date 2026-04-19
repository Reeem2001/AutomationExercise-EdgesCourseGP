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
public class SignupPage {
    // Locators
    private final By signupRadioButtonTitleMrLocator = By.cssSelector("input#id_gender1");
    private final By signupRadioButtonTitleMrsLocator = By.cssSelector("input#id_gender2");
    private final By signupNameLocator = By.cssSelector("input#name");
    private final By signupEmailLocator = By.cssSelector("input#email");
    private final By signupPasswordLocator = By.cssSelector("input#password");
    private final By signupFirstNameLocator = By.cssSelector("input#first_name");
    private final By signupLastNameLocator = By.cssSelector("input#last_name");
    private final By signupDateOfBirthDayLocator = By.cssSelector("select#days");
    private final By signupDateOfBirthMonthLocator = By.cssSelector("select#months");
    private final By signupDateOfBirthYearLocator = By.cssSelector("select#years");
    private final By enterAccountInformationTextLocator = By.cssSelector("h2.title  b");
    private final By signupForNewsletterCheckBoxLocator = By.cssSelector("input#newsletter");
    private final By receiveSpecialOffersCheckBoxLocator = By.cssSelector("input#optin");
    private final By signupComanyLocator = By.cssSelector("input#company");
    private final By signupAddressLocator = By.cssSelector("input#address1");
    private final By signupAddress2Locator = By.cssSelector("input#address2");
    private final By signupCountryLocator = By.cssSelector("select#country");
    private final By signupStateLocator = By.cssSelector("input#state");
    private final By signupCityLocator = By.cssSelector("input#city");
    private final By signupZipcodeLocator = By.cssSelector("input#zipcode");
    private final By signupMobileNumberLocator = By.cssSelector("input#mobile_number");
    private final By createAccountButtonLocator = By.cssSelector("button[data-qa=\"create-account\"]");
    private final By accountCreatedTextLocator = By.cssSelector("h2.title b");
    private final By continueButtonLocator = By.cssSelector("a[data-qa=\"continue-button\"]");
    private final By subscriptionAdTextLocator = By.cssSelector("div.col-sm-3.col-sm-offset-1 p");
    private final SelenuimFramework framework;

     
    public SignupPage(SelenuimFramework framework) {
        this.framework = framework;
    }
    
    // Actions
    public void sendName (String Name){
        framework.sendKeys(signupNameLocator, Name);
    }
    
    public void sendEmail (String Email){
        framework.sendKeys(signupEmailLocator, Email);
    }
    
    public void sendPassword (String Password ){
        framework.sendKeys(signupPasswordLocator, Password);
    }
    
    
    public void sendFirstname (String Firstname){
        framework.sendKeys(signupFirstNameLocator, Firstname);
    }
    
    
    public void sendLastName (String LastName){
        framework.sendKeys(signupLastNameLocator, LastName);
    }
    
    public void sendCompany (String Company){
        framework.sendKeys(signupComanyLocator, Company);
    }
    public void sendAddress (String Address){
        framework.sendKeys(signupAddressLocator, Address);
    }
    
    public void sendAddress2 (String Address2){
        framework.sendKeys(signupAddress2Locator, Address2);
    }
    public void selectCountry (String Country){
        framework.selectDropdownByVisibleText(signupCountryLocator, Country);
    }
    
    
    public void sendState (String State){
        framework.sendKeys(signupStateLocator, State);
    }
    public void sendCity (String City){
        framework.sendKeys(signupCityLocator, City);
    }public void sendZipcode (String Zipcode){
        framework.sendKeys(signupZipcodeLocator, Zipcode);
    }
    public void sendMobileNumber  (String MobileNumber){
        framework.sendKeys(signupMobileNumberLocator, MobileNumber);
    }
    
    public void clickCreateAccountButton (){
        framework.click(createAccountButtonLocator);
    }
    
    public void clickContinueButton (){
        framework.click(continueButtonLocator);
    }
    
    
    public String getAccountCreatedText (){
        return framework.getText(accountCreatedTextLocator);
    }
    
    
    public String getEnterAccountInformationText(){
        return framework.getText(enterAccountInformationTextLocator);
    }
    
    
    public void checkSignupForNewsletterCheckBox(){
        framework.checkCheckbox(signupForNewsletterCheckBoxLocator);
    }
    
    public void checkReceiveSpecialOffersCheckBox(){
        framework.checkCheckbox(receiveSpecialOffersCheckBoxLocator);
    }
    
    public void selectSignupRadioButtonTitleMr(){
        framework.selectRadioButton(signupRadioButtonTitleMrLocator);
    }
    
    public void selectSignupRadioButtonTitleMrs(){
        framework.selectRadioButton(signupRadioButtonTitleMrsLocator);
    }
    
    public void selectDateOfBirthDay(String day){
        framework.selectDropdownByVisibleText(signupDateOfBirthDayLocator, day);
    }
    
    public void selectDateOfBirthMonth(String month){
        framework.selectDropdownByVisibleText(signupDateOfBirthMonthLocator, month);
    }
    
    public void selectDateOfBirthYear(String year){
        framework.selectDropdownByVisibleText(signupDateOfBirthYearLocator, year);
    }
    
    public void scrollToAddress2(){
        framework.scrollToElement(signupAddress2Locator);
    }
      public void scrollToCreateAccountButton(){
        framework.scrollToElement(createAccountButtonLocator);
    }
      
    public void scrollToSubscriptionAdText(){
        framework.scrollToElement(subscriptionAdTextLocator);
    }   
    
    public void waitHelper(){
        framework.implicitWait(15);
    }
    
}
