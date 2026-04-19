/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/EmptyTestNGTest.java to edit this template
 */
package TestCases;

import Helper.HelperClass;
import Helper.Reviews;
import java.io.FileNotFoundException;
import java.util.List;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import pages.AllProductsPage;
import pages.FirstProductPage;
import pages.HomePage;
import selenuim.SelenuimFramework;
import io.qameta.allure.*;


/**
 *
 * @author Reem
 */
@Epic("Automation Exercise Website")
@Feature("Product Page Data & Reviews")
@Owner("Reem Adel")
public class ProductPageTest {
    
    static Reviews[] ListOfReviews;
    SelenuimFramework framework;
    HomePage homePage;
    AllProductsPage allProductsPage;
    FirstProductPage firstProductPage;

    @BeforeMethod
    public void beforeMethod() {
        framework = new SelenuimFramework();
        framework.initializeBrowser();
        framework.navigateToURL("https://automationexercise.com/");
        homePage = new HomePage(framework);
        allProductsPage = new AllProductsPage(framework);
        firstProductPage = new FirstProductPage(framework);
    }

    @AfterMethod
    public void afterMethod() {
        framework.closeBrowser();
    }
    
   
   

    @BeforeClass
    public void beforeClass() throws FileNotFoundException {
        ListOfReviews = HelperClass.ReadReviews("Review.json");
        System.out.println("Number of reviews loaded: " + ListOfReviews.length);
        
    }

  
    
    @DataProvider(name = "reviewsData")
    public Reviews[] reviewsDataProvider() {
        return ListOfReviews;
    }
    
    // Test Case 21
    @Test(dataProvider = "reviewsData")
    @Story("Adding Product Review")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify that a user can add review on a product.")
    public void addReviewOnProduct_TC21(Reviews Review) {
        // click products button
        homePage.clickProductsButton();
        
        // verify user navigated to all products page
        String ExpectedText = "ALL PRODUCTS";
        String ActualText = allProductsPage.getAllProductsText();
        Assert.assertTrue(ActualText.contains(ExpectedText),
                "User is not in all products page");
        
        // scroll to view the button
        allProductsPage.scrollToView();
        
        // click on view product button
        allProductsPage.clickViewProductButton();
        
        // verify write your review text
        String ExpectedWriteReviewText = "WRITE YOUR REVIEW";
        String ActualWriteReviewText = firstProductPage.getWriteYourReviewText();
        Assert.assertTrue(ActualWriteReviewText.contains(ExpectedWriteReviewText),
                ActualWriteReviewText + " Should Have Contained " + ExpectedWriteReviewText);
        
        // send name,email and review
        firstProductPage.sendName(Review.name);
        firstProductPage.sendEmail(Review.email);
        firstProductPage.sendReview(Review.review);
        
        // click submit button
        firstProductPage.clickSubmitButton();
        
        // verify success message 
        String ExpectedSuccessMessage = "Thank you for your review.";
        String ActualSuccessMessage = firstProductPage.getSuccessMessage();
        Assert.assertTrue(ActualSuccessMessage.contains(ExpectedSuccessMessage),
                ActualSuccessMessage + " Should Have Contained " + ExpectedSuccessMessage);
        
    }
    
    // Test Case 8
    @Test
    @Story("Product Page Details Verification")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that a user can view product details page with all details visible.")
    public void verifyProductsAndProductDetailPage_TC8(){
        // verify in home page
        String ActualTitle =  homePage.getHomePageTitle();
        Assert.assertTrue(ActualTitle.contains("Automation Exercise"),
                "User is not in home page");
        
       // click products button 
       homePage.clickProductsButton();
       
       // verify user navigated to all products page
        String ExpectedText = "ALL PRODUCTS";
        String ActualText = allProductsPage.getAllProductsText();
        Assert.assertTrue(ActualText.contains(ExpectedText),
                "User is not in all products page");
       
       //The products list is visible
       List<WebElement> products = allProductsPage.getAllProductElements();
       Assert.assertFalse(products.isEmpty(), "Products list is empty");

        // Check that each product is displayed
        for (WebElement product : products) {
            Assert.assertTrue(product.isDisplayed(), "Product is not visible: " + product.getText());
        }
      
       // scroll to view the button
        allProductsPage.scrollToView();
        
        // click on view product button
        allProductsPage.clickViewProductButton();
     
       //Verify that detail detail is visible: product name, category, price, availability, condition, brand
       Assert.assertTrue(firstProductPage.getProductName(),"Product name is not visible");
       Assert.assertTrue(firstProductPage.getProductPrice(),"Product price is not visible");
       Assert.assertTrue(firstProductPage.getProductCategory(),"Product category is not visible");
       Assert.assertTrue(firstProductPage.getProductBrand(),"Product brand is not visible");
       Assert.assertTrue(firstProductPage.getProductAvailability(),"Product availability is not visible");
       Assert.assertTrue(firstProductPage.getProductCondition(),"Product condition is not visible");
       
    }
 
    
}
