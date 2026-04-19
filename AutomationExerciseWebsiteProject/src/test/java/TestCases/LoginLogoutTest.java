/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/EmptyTestNGTest.java to edit this template
 */
package TestCases;
import io.qameta.allure.*;

import Helper.FullUserData;
import Helper.HelperClass;
import Helper.User;
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

/**
 *
 * @author Reem
 */
@Epic("Automation Exercise Website")
@Feature("Authentication")
@Owner("Reem Adel")
public class LoginLogoutTest {
    
    

    static User[] ListOfInvalidUsers;
    static FullUserData[] ListOfUsers;
    SelenuimFramework framework;
    SignupLoginPage signupLoginPage;
    HomePage homePage;
    SignupPage signupPage;

    @BeforeClass
    public void beforeClass() throws FileNotFoundException {
        ListOfInvalidUsers = HelperClass.ReadUsers("InvalidLoginCredentials.json");
        System.out.println("Number of invalid users loaded: " + ListOfInvalidUsers.length);
        ListOfUsers = HelperClass.ReadFullUser("FullUserData.json");
        System.out.println("Number of users loaded: " + ListOfUsers.length);
    }

    @BeforeMethod
    public void beforeMethod() {
        framework = new SelenuimFramework();
        framework.initializeBrowser();
        framework.navigateToURL("https://automationexercise.com/");
        signupLoginPage = new SignupLoginPage(framework);
        homePage = new HomePage(framework);
        signupPage = new SignupPage(framework);
    }

    @AfterMethod
    public void afterMethod() {
        framework.closeBrowser();
    }
    
     @DataProvider(name = "registerUserData")
    public FullUserData[] registerUserDataProvider() {
        return ListOfUsers;
    }

    
    @DataProvider(name = "invalidUsersData")
    public User[] invalidUserDataProvider() {
        return ListOfInvalidUsers;
    }
    
    // Helper Test Case
    @Test(dataProvider = "registerUserData")
    @Story("Helper: Register User")
    @Severity(SeverityLevel.BLOCKER)
    @Description("Helper test that registers a user.")
    public void registerUser(FullUserData TestUser) {
        
        //Click on 'Signup / Login' button
        homePage.clickSignupLoginButton();
       
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
        
        signupPage.waitHelper();
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
        
        // Click 'Continue' button
        signupPage.clickContinueButton();
        
        // Verify that 'Logged in as username' is visible
        String ExpectedLoggedInAsText = "Logged in as ";
        String ActualLoggedInAsText = homePage.getLoggedInText();
        Assert.assertTrue(ActualLoggedInAsText.contains(ExpectedLoggedInAsText),
                ActualLoggedInAsText + " Should Have Contained " + ExpectedLoggedInAsText);
       
        
    }
   
    // Test Case 3
    @Test(dataProvider = "invalidUsersData")
    @Story("Login with invalid credentials")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that login fails when incorrect credentials are entered.")
    public void incorrectLogin_TC3(User TestUser) {
        // verify in home page
        String ActualTitle =  homePage.getHomePageTitle();
        Assert.assertTrue(ActualTitle.contains("Automation Exercise"),
                "User is not in home page");

        // go to login page
        homePage.clickSignupLoginButton();

        // verify login text
        String ExpectedText = "Login to your account";
        String ActualText = signupLoginPage.getLoginToYourAccountText();
        Assert.assertEquals(ActualText, ExpectedText, "Not in Signup/Login Page");

        // perform incorrect login
        signupLoginPage.sendLoginEmail(TestUser.email);
        signupLoginPage.sendLoginPassword(TestUser.password);
        signupLoginPage.clickLoginButton();

        // verify error message
        String ExpectedMessage = "Your email or password is incorrect!";
        String ActualMessage = signupLoginPage.getIncorrectLoginText();
        Assert.assertTrue(ActualMessage.contains(ExpectedMessage),
                ActualMessage + " Should Have Contained " + ExpectedMessage);
    }
    
    // Test Case 2
    @Test(dataProvider = "registerUserData",priority = 1)
    @Story("Login with valid credentials")
    @Severity(SeverityLevel.BLOCKER)
    @Description("Verify that a registered user can log in with correct credentials.")
    public void correctLogin_TC2(FullUserData TestUser){
        // verify in home page
        String ActualTitle =  homePage.getHomePageTitle();
        Assert.assertTrue(ActualTitle.contains("Automation Exercise"),
                "User is not in home page");

        // go to login page
        homePage.clickSignupLoginButton();

        // verify login text
        String ExpectedText = "Login to your account";
        String ActualText = signupLoginPage.getLoginToYourAccountText();
        Assert.assertEquals(ActualText, ExpectedText, "Not in Login Page");

        // perform correct login
        signupLoginPage.sendLoginEmail(TestUser.email);
        signupLoginPage.sendLoginPassword(TestUser.password);
        signupLoginPage.clickLoginButton();
        
        // verify logged in success message
        String ExpectedLoggedInText = "Logged in as ";
        System.out.println(ExpectedLoggedInText);
        String ActualLoggedInText = homePage.getLoggedInText();
        System.out.println(ActualLoggedInText);
        Assert.assertTrue(ActualLoggedInText.contains(ExpectedLoggedInText),
                ActualLoggedInText + " Should Have Contained " + ExpectedLoggedInText);
       
        // delete account
        homePage.clickDeleteAccountButton();
        
        // verify delete account
        String ExpectedDeletedAccountText = "ACCOUNT DELETED!";
        String ActualDeletedAccountText = homePage.getDeletedAccountText();
        Assert.assertTrue(ActualDeletedAccountText.contains(ExpectedDeletedAccountText),
                ActualDeletedAccountText + " Should Have Contained " + ExpectedDeletedAccountText);
    
    }
    
    // Test Case 4
    @Test(dataProvider = "registerUserData",dependsOnMethods = "registerUser")
    @Story("Logout functionality")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify that a logged in user can log out successfully.")
    public void logout_TC4(FullUserData TestUser){
        // verify in home page
        String ActualTitle =  homePage.getHomePageTitle();
        Assert.assertTrue(ActualTitle.contains("Automation Exercise"),
                "User is not in home page");

        // go to login page
        homePage.clickSignupLoginButton();

        // verify login text
        String ExpectedText = "Login to your account";
        String ActualText = signupLoginPage.getLoginToYourAccountText();
        Assert.assertEquals(ActualText, ExpectedText, "Not in Login Page");

        // perform correct login
        signupLoginPage.sendLoginEmail(TestUser.email);
        signupLoginPage.sendLoginPassword(TestUser.password);
        signupLoginPage.clickLoginButton();
        String ExpectedLoggedInText = "Logged in as ";
        System.out.println(ExpectedLoggedInText);
        String ActualLoggedInText = homePage.getLoggedInText();
        System.out.println(ActualLoggedInText);
        Assert.assertTrue(ActualLoggedInText.contains(ExpectedLoggedInText),
                ActualLoggedInText + " Should Have Contained " + ExpectedLoggedInText);
       
        // click logout button
        homePage.clicklogoutButton();
        
        // verify user is navigated to login page
        String ExpectedLoginPageText = "Login to your account";
        String ActualLoginPageText = signupLoginPage.getLoginToYourAccountText();
        Assert.assertEquals(ActualLoginPageText, ExpectedLoginPageText, "Not in Login Page");
    
    }
    
    
}
