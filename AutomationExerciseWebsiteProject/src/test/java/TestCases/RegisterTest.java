/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/EmptyTestNGTest.java to edit this template
 */
package TestCases;

import Helper.HelperClass;
import Helper.FullUserData;
import Helper.RegisteredUser;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.SignupLoginPage;
import pages.SignupPage;
import selenuim.SelenuimFramework;
import io.qameta.allure.*;


/**
 *
 * @author Reem
 */
@Epic("Automation Exercise Website")
@Feature("User Registration")
@Owner("Reem Adel")
public class RegisterTest {
    SelenuimFramework framework;
    static FullUserData[] ListOfUsers;
    static RegisteredUser[] ListOfRegisteredUser ;
    SignupLoginPage signupLoginPage;
    HomePage homePage;
    SignupPage signupPage;
    
    @BeforeClass
    public void beforeClass() throws FileNotFoundException {
        ListOfUsers = HelperClass.ReadFullUser("FullUserData.json");
        System.out.println("Number of users loaded: " + ListOfUsers.length);
        ListOfRegisteredUser = HelperClass.ReadRegisteredUsers("RegisteredUser.json");
        System.out.println("Number of registered users loaded: " + ListOfRegisteredUser.length);
    }
    @DataProvider(name = "registerUserData")
    public FullUserData[] registerUserDataProvider() {
        return ListOfUsers;
    }
    
    @DataProvider(name = "registeredUserData")
    public RegisteredUser[] registeredUserDataProvider() {
        return ListOfRegisteredUser;
    }
    @BeforeMethod
    public void beforeMethod() {
        framework = new SelenuimFramework();
        framework.initializeBrowser();
        framework.navigateToURL("https://automationexercise.com/");
        signupLoginPage = new SignupLoginPage(framework);
        homePage = new HomePage(framework);
        signupPage = new SignupPage(framework) ;
    }

    @AfterMethod
    public void afterMethod() {
        framework.closeBrowser();
    }
    
    // Test Case 1
    @Test(dataProvider = "registerUserData")
    @Story("Register a new user")
    @Severity(SeverityLevel.BLOCKER)
    @Description("Verify that a new user can successfully register with valid data and delete the account.")
    public void registerUser_TC1(FullUserData TestUser) {
        // verify in home page
        String ActualTitle =  homePage.getHomePageTitle();
        Assert.assertTrue(ActualTitle.contains("Automation Exercise"),
                "User is not in home page");
        
        //Click on 'Signup / Login' button
        homePage.clickSignupLoginButton();
        
        // Verify 'New User Signup!' is visible
        String ExpectedNewUserText = signupLoginPage.getNewUserSignupText();
        String ActualNewUserText = "New User Signup!";
        Assert.assertEquals(ActualNewUserText, ExpectedNewUserText, "Not in Signup/Login Page");

        // Enter name 
        signupLoginPage.sendSignupName(TestUser.name);
       
        // Enter email address
        signupLoginPage.sendSignupEmail(TestUser.email);

        // Click 'Signup' button
        signupLoginPage.clickSignupButton();
        
        // Verify that 'ENTER ACCOUNT INFORMATION' is visible
        String ExpectedEnterAccountInformationText = "ENTER ACCOUNT INFORMATION";
        String ActualEnterAccountInformationText = signupPage.getEnterAccountInformationText();
        Assert.assertTrue(ActualEnterAccountInformationText.contains(ExpectedEnterAccountInformationText),
                ActualEnterAccountInformationText + " Should Have Contained " + ExpectedEnterAccountInformationText);
        
        // Fill details: Title, Name, Email, Password, Date of birth
        if(TestUser.title.equals("Mr.")){
            signupPage.selectSignupRadioButtonTitleMr();
        }
        else{
            signupPage.selectSignupRadioButtonTitleMrs();
        }
        signupPage.sendName(TestUser.name);
        signupPage.scrollToAddress2();
        signupPage.sendPassword(TestUser.password);
        String DOB = TestUser.dateOfBirth ;
        // Parse into LocalDate
        LocalDate date = LocalDate.parse(DOB, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        // Extract values
        String day = String.valueOf(date.getDayOfMonth()); 
        String monthName = date.getMonth().getDisplayName(java.time.format.TextStyle.FULL, Locale.ENGLISH); 
        String year = String.valueOf(date.getYear()); 
        signupPage.selectDateOfBirthDay(day);
        signupPage.selectDateOfBirthMonth(monthName);
        signupPage.selectDateOfBirthYear(year);

        // Select checkbox 'Sign up for our newsletter!'
        signupPage.checkSignupForNewsletterCheckBox();
        
        // Select checkbox 'Receive special offers from our partners!'
        signupPage.checkReceiveSpecialOffersCheckBox();
        
        // Fill details: First name, Last name, Company, Address, Address2, Country, State, City, Zipcode, Mobile Number
        signupPage.sendFirstname(TestUser.firstName);
        signupPage.sendLastName(TestUser.lastName);
        signupPage.scrollToCreateAccountButton();
        signupPage.sendCompany(TestUser.company);
        signupPage.sendAddress(TestUser.address);
        signupPage.sendAddress2(TestUser.address2);
        signupPage.selectCountry(TestUser.country);
        signupPage.sendState(TestUser.state);
        signupPage.sendCity(TestUser.city);
        signupPage.scrollToSubscriptionAdText();
        signupPage.sendZipcode(TestUser.zipcode);
        signupPage.sendMobileNumber(TestUser.mobileNumber);
        
        
        // Click 'Create Account button'
        signupPage.clickCreateAccountButton();
        
        // Verify that 'ACCOUNT CREATED!' is visible
        String ExpectedAccountCreatedText = "ACCOUNT CREATED!";
        String ActualAccountCreatedText = signupPage.getAccountCreatedText();
        Assert.assertTrue(ActualAccountCreatedText.contains(ExpectedAccountCreatedText),
                ActualAccountCreatedText + " Should Have Contained " + ExpectedAccountCreatedText);
        
        // Click 'Continue' button
        signupPage.clickContinueButton();
        
        // Verify that 'Logged in as username' is visible
        String ExpectedLoggedInAsText = "Logged in as ";
        String ActualLoggedInAsText = homePage.getLoggedInText();
        Assert.assertTrue(ActualLoggedInAsText.contains(ExpectedLoggedInAsText),
                ActualLoggedInAsText + " Should Have Contained " + ExpectedLoggedInAsText);
       
        // Click 'Delete Account' button
        homePage.clickDeleteAccountButton();
        
        // Verify that 'ACCOUNT DELETED!' is visible 
        String ExpectedAccountDeletedText = "ACCOUNT DELETED!";
        String ActualAccountDeletedText = homePage.getDeletedAccountText();
        Assert.assertTrue(ActualAccountDeletedText.contains(ExpectedAccountDeletedText),
                ActualAccountDeletedText + " Should Have Contained " + ExpectedAccountDeletedText);
       
        // click 'Continue' button
        homePage.clickContinueButton();
    }
    
    // Test Case 5
    @Test(dataProvider = "registeredUserData")
    @Story("Register with existing email")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that registering with an already registered email shows an error message.")
    public void registerUserWithExistingEmail_TC5(RegisteredUser TestUser) {
       // verify in home page
        String ActualTitle =  homePage.getHomePageTitle();
        Assert.assertTrue(ActualTitle.contains("Automation Exercise"),
                "User is not in home page");
        
       // Click on 'Signup / Login' button
       homePage.clickSignupLoginButton();
       
       // Verify 'New User Signup!' is visible
       String ExpectedNewUserText = signupLoginPage.getNewUserSignupText();
       String ActualNewUserText = "New User Signup!";
       Assert.assertEquals(ActualNewUserText, ExpectedNewUserText, "Not in Signup/Login Page");

       // Enter name 
       signupLoginPage.sendSignupName(TestUser.name);
       
       // Enter already registered email address
       signupLoginPage.sendSignupEmail(TestUser.email);
       
       // Click 'Signup' button
       signupLoginPage.clickSignupButton();
       
       // Verify error 'Email Address already exist!' is visible
       String ExpectedErrorMessage = signupLoginPage.getSignupErrorMessageText();
       String ActualErrorMessage = "Email Address already exist!";
       Assert.assertTrue(ActualErrorMessage.contains(ExpectedErrorMessage),
                ActualErrorMessage + " Should Have Contained " + ExpectedErrorMessage);


    }
    
}
