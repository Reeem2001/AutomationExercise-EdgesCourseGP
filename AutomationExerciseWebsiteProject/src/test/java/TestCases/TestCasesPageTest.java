/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/EmptyTestNGTest.java to edit this template
 */
package TestCases;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import pages.HomePage;
import selenuim.SelenuimFramework;
import io.qameta.allure.*;


/**
 *
 * @author Reem
 */
@Epic("Automation Exercise Website")
@Feature("Test Cases Page Accessibility")
@Owner("Reem Adel")
public class TestCasesPageTest {
    
    
    SelenuimFramework framework;
    HomePage homePage;
    pages.TestCasesPage testCasesPage;
    @BeforeMethod
    public void beforeMethod() {
        framework = new SelenuimFramework();
        framework.initializeBrowser();
        framework.navigateToURL("https://automationexercise.com/");
        homePage = new HomePage(framework);
        testCasesPage = new pages.TestCasesPage(framework);
    }

    @AfterMethod
    public void afterMethod() {
        framework.closeBrowser();
    }
    
    // Test Case 7
    @Test
    @Story("Verify Test Cases Page is accessible")
    @Severity(SeverityLevel.MINOR)
    @Description("Validate that the Test Cases page loads correctly and user can access it.")
    public void verifyTestCasesPage_TC7(){
        // verify in home page
        String ActualTitle =  homePage.getHomePageTitle();
        Assert.assertTrue(ActualTitle.contains("Automation Exercise"),
                "User is not in home page");
        
        // click test cases button
        homePage.clickTestCasesButton();
        
        // verify in test cases page
        String ActualTestCasesText = testCasesPage.getTestCasesPageText();
        String ExpectedTestCasesText = "TEST CASES";
        Assert.assertTrue(ActualTestCasesText.contains(ExpectedTestCasesText),
                ActualTestCasesText + " Should Have Contained " + ExpectedTestCasesText);
    } 
    
}
