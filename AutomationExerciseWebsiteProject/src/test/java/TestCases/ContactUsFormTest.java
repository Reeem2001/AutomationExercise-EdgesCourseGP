/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/EmptyTestNGTest.java to edit this template
 */
package TestCases;

import Helper.ContactUsForm;
import Helper.HelperClass;
import java.io.FileNotFoundException;
import java.nio.file.Paths;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import pages.ContactUsPage;
import pages.HomePage;
import selenuim.SelenuimFramework;
import io.qameta.allure.*;

/**
 *
 * @author Reem
 */
@Epic("Automation Exercise Website")
@Feature("Contact Us Form")
@Owner("Reem Adel")
public class ContactUsFormTest {
    
     
    static ContactUsForm[] ListOfContactUsData;
    SelenuimFramework framework;
    HomePage homePage;
    ContactUsPage contactUsPage;
    String filePath = Paths.get(System.getProperty("user.dir"), "..", "Data", "testfile.txt")
                       .normalize()
                       .toAbsolutePath()
                       .toString();

    @BeforeClass
    public void beforeClass() throws FileNotFoundException {
        ListOfContactUsData = HelperClass.ReadContactUsFormData("ContactUsForm.json");
        System.out.println("Number of contact us form data loaded: " + ListOfContactUsData.length);
        
    }

    @BeforeMethod
    public void beforeMethod() {
        framework = new SelenuimFramework();
        framework.initializeBrowser();
        framework.navigateToURL("https://automationexercise.com/");
        homePage = new HomePage(framework);
        contactUsPage = new ContactUsPage(framework);
    }

    @AfterMethod
    public void afterMethod() {
        framework.closeBrowser();
    }

    @DataProvider(name = "contactUsFormData")
    public ContactUsForm[] contactUsFormDataProvider() {
        return ListOfContactUsData;
    }
      
    // Test Case 6
    @Test(dataProvider = "contactUsFormData")
    @Story("Submit Contact Us Form")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that a user can submit the 'Contact Us' form with valid data, upload a file, and receive a success message.")
    public void contactUsForm_TC6(ContactUsForm Form) {
        // verify in home page
        String ActualTitle =  homePage.getHomePageTitle();
        Assert.assertTrue(ActualTitle.contains("Automation Exercise"),
                "User is not in home page");
        
        // Click on 'Contact Us' button
        homePage.clickContactUsButton();
        
        // Verify 'GET IN TOUCH' is visible
        String ActualGetInTouchText = contactUsPage.getGetInTouchText();
        String ExpectedGetInTouchText = "GET IN TOUCH";
        Assert.assertTrue(ActualGetInTouchText.contains(ExpectedGetInTouchText),
                ActualGetInTouchText + " Should Have Contained " + ExpectedGetInTouchText);
    
        // Enter name, email, subject and message
        contactUsPage.sendContactUsName(Form.name);
        contactUsPage.sendContactUsEmail(Form.email);
        contactUsPage.sendContactUsSubject(Form.subject);
        contactUsPage.sendContactUsMessage(Form.message);
        
        contactUsPage.scrollToFooter();
        
        // Upload file
        contactUsPage.uploadFile(filePath);
        
        // Click 'Submit' button
        contactUsPage.clickSubmitButton();
        
        // Click OK button
        contactUsPage.clickOkButton();
        
        // Verify success message 'Success! Your details have been submitted successfully.' is visible
        String ActualSuccessMessageText = contactUsPage.getSuccessMessageText();
        String ExpecteduccessMessageText = "Success! Your details have been submitted successfully.";
        Assert.assertTrue(ActualSuccessMessageText.contains(ExpecteduccessMessageText),
                ActualSuccessMessageText + " Should Have Contained " + ExpecteduccessMessageText);
    
        // Click 'Home' button and verify that landed to home page successfully
        contactUsPage.clickHomeButton();
        String ActualTitleAfter =  homePage.getHomePageTitle();
        Assert.assertTrue(ActualTitleAfter.contains("Automation Exercise"),
                "User is not in home page");
    }
    
    
}
