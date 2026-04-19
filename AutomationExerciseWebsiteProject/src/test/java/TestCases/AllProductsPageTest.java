/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/EmptyTestNGTest.java to edit this template
 */
package TestCases;

import Helper.HelperClass;
import Helper.Product;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import pages.AllProductsPage;
import pages.BrandPage;
import pages.HomePage;
import selenuim.SelenuimFramework;
import io.qameta.allure.*;


/**
 *
 * @author Reem
 */
@Epic("Automation Exercise Website")
@Feature("Products Page Functionality")
@Owner("Reem Adel")
public class AllProductsPageTest {
    
    SelenuimFramework framework;
    HomePage homePage;
    AllProductsPage allProductsPage;
    BrandPage brandPage;
    private Product[] products;  
    @BeforeClass
    public void beforeClass() throws FileNotFoundException {
        products = HelperClass.ReadProducts("Products.json");
    }
    @DataProvider(name = "productData")
    public Object[][] provideProductData() {
        // Convert Product[] -> Object[][] with product names
        return Arrays.stream(products)
                .map(p -> new Object[]{p.getName()})
                .toArray(Object[][]::new);
    }

    @BeforeMethod
    public void beforeMethod() {
        framework = new SelenuimFramework();
        framework.initializeBrowser();
        framework.navigateToURL("https://automationexercise.com/");
        homePage = new HomePage(framework);
        allProductsPage = new AllProductsPage(framework);
        brandPage = new BrandPage(framework);
    }

    @AfterMethod
    public void afterMethod() {
        framework.closeBrowser();
    }
    
    // Test Case 9
    @Story("Search Product")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify that a user can search for a specific product.")
    @Test(dataProvider = "productData")
    public void searchProduct_TC9(String productToSearchFor){
         // verify in home page
        String ActualTitle = homePage.getHomePageTitle();
        Assert.assertTrue(ActualTitle.contains("Automation Exercise"),
                "User is not in home page");

        // click products button
        homePage.clickProductsButton();

        // verify user navigated to all products page
        String ExpectedText = "ALL PRODUCTS";
        String ActualText = allProductsPage.getAllProductsText();
        Assert.assertTrue(ActualText.contains(ExpectedText),
                "User is not in all products page");

        // Enter product name in search input
        allProductsPage.sendSearchedProductName(productToSearchFor);

        // click search button
        allProductsPage.clickSearchButton();

        // Verify 'SEARCHED PRODUCTS' is visible
        Assert.assertTrue(allProductsPage.getSearchedProductsText().contains("SEARCHED PRODUCTS"),
                "'SEARCHED PRODUCTS' text is not visible");

        // Get actual results from page
        List<String> actualResults = allProductsPage.getSearchResultProducts();

        // Verify searched product exists in results
        Assert.assertTrue(
                actualResults.stream().anyMatch(actual -> actual.equalsIgnoreCase(productToSearchFor)),
                "Expected product not found in search results: " + productToSearchFor
        );
    
    }
    
    
    // Test Case 19
    @Story("Different Brands Accessibility")
    @Severity(SeverityLevel.MINOR)
    @Description("Verify that a user can access different brands as a filter from products page and any brand page.")
    @Test
    public void ViewAndCartBrandProducts_TC19(){
        // Click on 'Products' button
        homePage.clickProductsButton();
        
        allProductsPage.scrollToViewBrandsSection();
        
        // Verify that Brands are visible on left side bar
        Assert.assertTrue(allProductsPage.isBrandsSectionVisible(), "Brands Section is not visible");
        
        // Click on any brand name
        allProductsPage.clickBrandPOLOButton();
        
        
        // Verify that user is navigated to brand page and brand products are displayed
        String ActualFirstBrandPageTitleText = brandPage.getBrandPageTitleTextLocator();
        String ExpectedFirstBrandPageTitleText = "BRAND - POLO PRODUCTS";
        Assert.assertTrue(ActualFirstBrandPageTitleText.contains(ExpectedFirstBrandPageTitleText),
                ActualFirstBrandPageTitleText + " Should Have Contained " + ExpectedFirstBrandPageTitleText);
        Assert.assertTrue(brandPage.isBrandProductsSectionVisible(), "Brand products Section is not visible");
        
        allProductsPage.scrollToViewBrandsSection();
        
        // On left side bar, click on any other brand link
        brandPage.clickBrandHAndMButton();
        
        // Verify that user is navigated to that brand page and can see products
        String ActualSecondBrandPageTitleText = brandPage.getBrandPageTitleTextLocator();
        String ExpectedSecondBrandPageTitleText = "BRAND - H&M PRODUCTS";
        Assert.assertTrue(ActualSecondBrandPageTitleText.contains(ExpectedSecondBrandPageTitleText),
                ActualSecondBrandPageTitleText + " Should Have Contained " + ExpectedSecondBrandPageTitleText);
        Assert.assertTrue(brandPage.isBrandProductsSectionVisible(), "Brand products Section is not visible");
        
    } 
}
