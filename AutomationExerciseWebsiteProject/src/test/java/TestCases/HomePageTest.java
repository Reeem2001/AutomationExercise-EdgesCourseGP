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
import pages.CategoryPage;
import pages.HomePage;
import selenuim.SelenuimFramework;
import io.qameta.allure.*;


/**
 *
 * @author Reem
 */
@Epic("Automation Exercise Website")
@Feature("Home Page & Navigation")
@Owner("Reem Adel")
public class HomePageTest {
    
    SelenuimFramework framework;
    HomePage homePage;
    CategoryPage categoryPage;
    String subscriptionEmail = "reemadel@gmail.com";
    
    @BeforeMethod
    public void beforeMethod() {
        framework = new SelenuimFramework();
        framework.initializeBrowser();
        framework.navigateToURL("https://automationexercise.com/");
        homePage = new HomePage(framework);
        categoryPage = new CategoryPage(framework);
    }

    @AfterMethod
    public void afterMethod() {
        framework.closeBrowser();
    }
    
    // Test Case 25
    @Test
    @Story("Scroll Up Using Arrow Button")
    @Severity(SeverityLevel.MINOR)
    @Description("Verify that a user can scroll down and then use the up arrow button to return to the top of the page.")
    public void scrollWithArrow_TC25(){
         // verify in home page
        String ActualTitle =  homePage.getHomePageTitle();
        Assert.assertTrue(ActualTitle.contains("Automation Exercise"),
                "User is not in home page");
        
        // scroll down to bottom
        homePage.scrollDown();
        
        // verify "subscription" is visible
        String ExpectedSubscriptionText = "SUBSCRIPTION";
        String ActualSubscriptionText = homePage.getSubscriptionText();
        Assert.assertTrue(ActualSubscriptionText.contains(ExpectedSubscriptionText),
                ActualSubscriptionText + " Should Have Contained " + ExpectedSubscriptionText);
    
        // click on up arrow
        homePage.clickUpArrowButton();
        
        /* Verify that page is scrolled up and 'Full-Fledged practice website for Automation Engineers'
        text is visible on screen*/
        String ExpectedPageTopText = "Full-Fledged practice website for Automation Engineers";
        String ActualPageTopText = homePage.getTextInPageTop();
        Assert.assertTrue(ActualPageTopText.contains(ExpectedPageTopText),
                ActualPageTopText + " Should Have Contained " + ExpectedPageTopText);
    
        
    }
    
    // Test Case 26
    @Test
    @Story("Scroll Up Without Arrow Button")
    @Severity(SeverityLevel.MINOR)
    @Description("Verify that a user can scroll down and then scroll back up without using the arrow button.")
    public void scrollWithoutArrow_TC26(){
        // verify in home page
        String ActualTitle =  homePage.getHomePageTitle();
        Assert.assertTrue(ActualTitle.contains("Automation Exercise"),
                "User is not in home page");
        
        // scroll down to bottom
        homePage.scrollDown();
        
        // verify "subscription" is visible
        String ExpectedSubscriptionText = "SUBSCRIPTION";
        String ActualSubscriptionText = homePage.getSubscriptionText();
        Assert.assertTrue(ActualSubscriptionText.contains(ExpectedSubscriptionText),
                ActualSubscriptionText + " Should Have Contained " + ExpectedSubscriptionText);
    
        // scroll up page
        homePage.scrollUp();
        
        /* Verify that page is scrolled up and 'Full-Fledged practice website for Automation Engineers'
        text is visible on screen*/
        String ExpectedPageTopText = "Full-Fledged practice website for Automation Engineers";
        String ActualPageTopText = homePage.getTextInPageTop();
        Assert.assertTrue(ActualPageTopText.contains(ExpectedPageTopText),
                ActualPageTopText + " Should Have Contained " + ExpectedPageTopText);
    
        
    }
    
    // Test Case 10
    @Test
    @Story("Verify Subscription in Home Page")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify that a user can subscribe to the newsletter by entering an email and receiving a success message.")
    public void verifySubscriptionInHomePage_TC10(){
       // verify in home page
        String ActualTitle =  homePage.getHomePageTitle();
        Assert.assertTrue(ActualTitle.contains("Automation Exercise"),
                "User is not in home page");
        
        // scroll down to bottom
         homePage.scrollDown();

        // verify "subscription" is visible
        String ExpectedSubscriptionText = "SUBSCRIPTION";
        String ActualSubscriptionText = homePage.getSubscriptionText();
        Assert.assertTrue(ActualSubscriptionText.contains(ExpectedSubscriptionText),
              ActualSubscriptionText + " Should Have Contained " + ExpectedSubscriptionText);

        //Enter email address in input 
        homePage.sendSubscriptionEmail(subscriptionEmail);
        
        //click arrow button
        homePage.clickSubscriptionButton();
        
      //Verify success message 'You have been successfully subscribed!' is visible
      String ExpectedSuccessMessage = "You have been successfully subscribed!";
      String ActualSuccessMessage = homePage.getSubscriptionSuccessMessage();
      Assert.assertTrue(ActualSuccessMessage.contains(ExpectedSuccessMessage),
              ActualSuccessMessage + " Should Have Contained " + ExpectedSuccessMessage);
 
    }
    
    // Test Case 18
    @Test
    @Story("View Category Products")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that a user can view products by selecting categories and subcategories from the left sidebar.")
    public void viewCategoryProducts_TC18(){
        // Verify that categories are visible on left side bar
        Assert.assertTrue(homePage.isCategorySectionVisible(), "Category Section is not visible");
        
        homePage.scrollToViewCategorySection();

        // Click on 'Women' category
        homePage.clickWomenCategory();
        
        // Click on any category link under 'Women' category, for example: Dress
        homePage.clickDressCategory();
        
        // Verify that category page is displayed and confirm text 'WOMEN - TOPS PRODUCTS'
        String ActualPageTitleText = categoryPage.getPageTitle();
        String ExpectedPageTitleText = "Automation Exercise - Dress Products";
        Assert.assertTrue(ActualPageTitleText.contains(ExpectedPageTitleText),
                ActualPageTitleText + " Should Have Contained " + ExpectedPageTitleText);
        
        String ActualDressCategoryPageTitleText = categoryPage.getCategoryPageTitleText();
        String ExpectedDressCategoryPageTitleText = "WOMEN - DRESS PRODUCTS";
        Assert.assertTrue(ActualDressCategoryPageTitleText.contains(ExpectedDressCategoryPageTitleText),
                ActualDressCategoryPageTitleText + " Should Have Contained " + ExpectedDressCategoryPageTitleText);
        
        // On left side bar, click on any sub-category link of 'Men' category
        categoryPage.clickMenCategory();
        categoryPage.clickTshirtsCategory();
        
        // Verify that user is navigated to that category page
        String ActualTshirtsCategoryPageTitleText = categoryPage.getPageTitle();
        String ExpectedTshirtsCategoryPageTitleText = "Automation Exercise - Tshirts Products";
        Assert.assertTrue(ActualTshirtsCategoryPageTitleText.contains(ExpectedTshirtsCategoryPageTitleText),
                ActualTshirtsCategoryPageTitleText + " Should Have Contained " + ExpectedTshirtsCategoryPageTitleText);
        
    }


}
