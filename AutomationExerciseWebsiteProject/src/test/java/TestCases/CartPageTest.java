/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/EmptyTestNGTest.java to edit this template
 */
package TestCases;


import Helper.HelperClass;
import Helper.RegisteredUser;
import static TestCases.RegisterTest.ListOfRegisteredUser;
import java.util.List;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import pages.AllProductsPage;
import pages.CartPage;
import pages.FirstProductPage;
import pages.HomePage;
import pages.SignupLoginPage;
import selenuim.SelenuimFramework;
import io.qameta.allure.*;
import java.io.FileNotFoundException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;

/**
 *
 * @author Reem
 */
@Epic("Automation Exercise Website")
@Feature("Cart Page Accessibility")
@Owner("Reem Adel")
public class CartPageTest {
    
   SelenuimFramework framework;
    HomePage homePage;
    CartPage cartPage;
    AllProductsPage allProductsPage;
    FirstProductPage productPage ;
    SignupLoginPage signupLoginPage;
    String quantity = "4";
    int quantityOfEachProduct = 1 ;
    int numberOfItemsInCart = 2 ;
    String subscriptionEmail = "reemadel@gmail.com";
    String firstProductName = "Blue Top";
    String secondProductName = "Men Tshirt";
    String RecommendedProduct5Name = "Winter Top";
    String productToSearchFor_TC20 = "Blue Top";
    @BeforeClass
    public void beforeClass() throws FileNotFoundException {
        ListOfRegisteredUser = HelperClass.ReadRegisteredUsers("RegisteredUser.json");
        System.out.println("Number of registered users loaded: " + ListOfRegisteredUser.length);
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
        homePage = new HomePage(framework);
        cartPage = new CartPage(framework);
        allProductsPage = new AllProductsPage(framework);
        productPage = new FirstProductPage(framework);
        signupLoginPage = new SignupLoginPage(framework);
    }

    @AfterMethod
    public void afterMethod() {
        framework.closeBrowser();
    }
    
    // Test Case 11
    @Test 
    @Story("Cart Subscription")
    @Severity(SeverityLevel.MINOR)
    @Description("Verify that a user can subscribe to the newsletter from the Cart page and receive a success confirmation message.")
    public void verifySubscriptionInCartPage_TC11(){
       
        // verify in home page
        String ActualTitle =  homePage.getHomePageTitle();
        Assert.assertTrue(ActualTitle.contains("Automation Exercise"),
                "User is not in home page");
        
       // Click Cart button
       homePage.clickCartButton();
       
       // Scroll down to footer
       cartPage.scrollToFooter();
       
       //Verify text 'SUBSCRIPTION'
       String ActualSubscriptionText =  cartPage.getSubscriptionText();
       String ExpectedSubscriptionText = "SUBSCRIPTION";
       Assert.assertTrue(ActualSubscriptionText.contains(ExpectedSubscriptionText),
                ActualSubscriptionText + " Should Have Contained " + ExpectedSubscriptionText);
       
       //Enter email address in input 
       cartPage.sendSubscriptionEmail(subscriptionEmail);
       
       // click arrow button
       cartPage.clickSubscriptionButton();
       
       //Verify success message 'You have been successfully subscribed!' is visible
       String ExpectedSuccessMessage = "You have been successfully subscribed!";
      String ActualSuccessMessage = cartPage.getSubscriptionSuccessMessage();
      Assert.assertTrue(ActualSuccessMessage.contains(ExpectedSuccessMessage),
              ActualSuccessMessage + " Should Have Contained " + ExpectedSuccessMessage);
    
    }
    
    
    // Test Case 20
    @Test(dataProvider = "registeredUserData")
    @Story("Cart Persistence After Login")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that a searched product added to the cart before login remains in the cart after the user logs in.")
    public void searchProductsAndVerifyCartAfterLogin_TC20(RegisteredUser TestUser){
        
       // click products button
        homePage.clickProductsButton();

        // verify user navigated to all products page
        String ExpectedText = "ALL PRODUCTS";
        String ActualText = allProductsPage.getAllProductsText();
        Assert.assertTrue(ActualText.contains(ExpectedText),
                "User is not in all products page");

        // Enter product name in search input
        allProductsPage.sendSearchedProductName(productToSearchFor_TC20);

        // click search button
        allProductsPage.clickSearchButton();

        // Verify 'SEARCHED PRODUCTS' is visible
        Assert.assertTrue(allProductsPage.getSearchedProductsText().contains("SEARCHED PRODUCTS"),
                "'SEARCHED PRODUCTS' text is not visible");

        // Get actual results from page
        List<String> actualResults = allProductsPage.getSearchResultProducts();

        // Verify searched product exists in results
        Assert.assertTrue(
            actualResults.stream()
                    .anyMatch(actual -> actual.trim().toLowerCase().contains(productToSearchFor_TC20.toLowerCase())),
            "Expected product not found in search results: " + productToSearchFor_TC20 + 
            "\nActual results: " + actualResults
        );

        // ---------------- Add "Blue Top" to cart ----------------
        homePage.scrollToViewFirstProductAddToCartButton_Search();
        homePage.clickFirstProductAddToCartButton();
        homePage.clickContinueShoppingButton();

        // ---------------- Open cart and verify before login ----------------
        homePage.clickCartButton();
        Assert.assertTrue(
                cartPage.getCartProductNames().stream()
                        .anyMatch(name -> name.equalsIgnoreCase(productToSearchFor_TC20)),
                "Cart does not contain product before login: " + productToSearchFor_TC20
        );
        
        homePage.scrollToNav();

        // ---------------- Login ----------------
        cartPage.clickProceedToCheckoutButton();
        cartPage.clickSignupLoginPopUpButton();
        signupLoginPage.sendLoginEmail(TestUser.email); 
        signupLoginPage.sendLoginPassword(TestUser.password);
        signupLoginPage.clickLoginButton();

        // ---------------- Return to Cart ----------------
        homePage.clickCartButton();
        Assert.assertTrue(
                cartPage.getCartProductNames().stream()
                        .anyMatch(name -> name.equalsIgnoreCase(productToSearchFor_TC20)),
                "Cart does not contain product after login: " + productToSearchFor_TC20
        );
    }

    // Test Case 13
    @Test
    @Story("Product Quantity Management")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify that a user can set a specific quantity of a product and that the quantity is correctly reflected in the cart.")
    public void verifyProductQuantityInCart_TC13(){
        // verify in home page
        String ActualTitle =  homePage.getHomePageTitle();
        Assert.assertTrue(ActualTitle.contains("Automation Exercise"),
                "User is not in home page");
        
        // Click 'View Product' for any product on home page
        homePage.scrollToViewViewProductButton();
        homePage.clickViewProduct1Button();
        
        // Verify product detail is opened by checking categoryy and availabiluty is visible
        Assert.assertTrue(productPage.getProductCategory(),"Not in product detail page");
        Assert.assertTrue(productPage.getProductAvailability(),"Not in product detail page");
       
        // Increase quantity to 4
        productPage.sendQuantity(quantity);
        
        // Click 'Add to cart' button
        productPage.clickAddToCartButton();
        
        // Click 'View Cart' button
        productPage.clickViewCartButton();
        
        // Verify that product is displayed in cart page with exact quantity
        String ExpectedQuantity = quantity;
        String ActualQuantity = cartPage.getProductQuantity();
        Assert.assertEquals(ActualQuantity,ExpectedQuantity);
    }

    
    // Test Case 12
    @Test
    @Story("Add Multiple Products to Cart")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that a user can add multiple products to the cart and that their names, prices, quantities, and totals are displayed correctly.")
    public void addProductsInCart_TC12(){
        // verify in home page
        String ActualTitle =  homePage.getHomePageTitle();
        Assert.assertTrue(ActualTitle.contains("Automation Exercise"),
                "User is not in home page");   
        
        // Click 'Products' button
        homePage.clickProductsButton();
        
        // Hover over first product and click 'Add to cart'
        homePage.scrollToViewFirstProductAddToCartButton();
        homePage.clickFirstProductAddToCartButton();
        
        // Click 'Continue Shopping' button
        homePage.clickContinueShoppingButton();
        
        // Hover over second product and click 'Add to cart'
        homePage.clickSecondProductAddToCartButton();
        
        // Click 'View Cart' button
        homePage.clickViewCartButton();
        
        // Verify both products are added to Cart
        Assert.assertEquals(cartPage.getNumberOfItemsInCart(), numberOfItemsInCart,
                "Cart does not contain "+numberOfItemsInCart+" products");
        
        // Verify both products names
        List<String> names = cartPage.getCartProductNames();
        Assert.assertEquals(names.get(0), firstProductName, "Name mismatch for product 1");
        Assert.assertEquals(names.get(1), secondProductName, "Name mismatch for product 2");
        
        // Verify their prices, quantity and total price
        List<Double> prices = cartPage.getProductPrices();
        List<Integer> quantities = cartPage.getProductQuantities();
        List<Double> totals = cartPage.getProductTotals();
        
        Assert.assertEquals(prices.get(0),500.0 , "Price mismatch for product 1");
        Assert.assertEquals(prices.get(1),400.0, "Price mismatch for product 2");
        
        Assert.assertEquals(quantities.get(0), quantityOfEachProduct, "Quantity mismatch for product 1");
        Assert.assertEquals(quantities.get(1), quantityOfEachProduct, "Quantity mismatch for product 2");
        
        for (int i = 0; i < prices.size(); i++) {
            double expectedTotal = prices.get(i) * quantities.get(i);
            Assert.assertEquals(totals.get(i), expectedTotal, 0.01,
             "Total price mismatch for product " + (i + 1));
        }
    }
    
    // Test Case 17
    @Test
    @Story("Remove Products from Cart")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify that a user can remove a product from the cart and that the cart updates correctly.")
    public void removeProductsFromCart_TC17(){
        // verify in home page
        String ActualTitle =  homePage.getHomePageTitle();
        Assert.assertTrue(ActualTitle.contains("Automation Exercise"),
                "User is not in home page");   
        
        // --------  Add Products -----------------------
        // Hover over first product and click 'Add to cart'
        homePage.scrollToViewFirstProductAddToCartButton();
        homePage.clickFirstProductAddToCartButton();
        
        // Click 'Continue Shopping' button
        homePage.clickContinueShoppingButton();
        
        // Hover over second product and click 'Add to cart'
        homePage.clickSecondProductAddToCartButton();
        
        // Click 'Continue Shopping' button
        homePage.clickContinueShoppingButton();
        
        // ------------------------------------------------
        
        // Click 'Cart' button
        homePage.clickCartButton();
        
        // Verify that cart page is displayed
        String ExpectedShoppinCartText = "Shopping Cart";
        String ActualShoppinCartText = cartPage.getShoppingCartText();
        Assert.assertTrue(ActualShoppinCartText.contains(ExpectedShoppinCartText),
                ActualShoppinCartText + " Should Have Contained " + ExpectedShoppinCartText);
        
        // Click 'X' button for first product
        cartPage.clickXButtonForFirstProductButton();
        
        // wait for the DOM to refresh 
        cartPage.waitForProductToBeRemoved();
        
        // Verify that product is removed from the cart
        List<String> productNamesAfterRemoval = cartPage.getCartProductNames();
        Assert.assertFalse(productNamesAfterRemoval.contains(firstProductName),
            "Product " + firstProductName + " was not removed from the cart");
        
    }

    // Test Case 22
    @Test
    @Story("Recommended Items in Cart")
    @Severity(SeverityLevel.MINOR)
    @Description("Verify that a user can add a product from the 'Recommended Items' section to the cart and that it is displayed correctly.")
    public void AddToCartFromRecommendedItems_TC22(){
        // Scroll to bottom of page
        homePage.scrollToViewRecommendedItemsText();
        
        // Verify 'RECOMMENDED ITEMS' are visible
        String ExpectedRecommendedItemsText = "RECOMMENDED ITEMS";
        String ActualRecommendedItemsText = homePage.getRecommendedItemsText();
        Assert.assertTrue(ActualRecommendedItemsText.contains(ExpectedRecommendedItemsText),
                ActualRecommendedItemsText + " Should Have Contained " + ExpectedRecommendedItemsText);
        
        // scroll to avoid ad
        homePage.scrollToViewRecommendedItemsSection();
        
        // Click on 'Add To Cart' on Recommended product
        homePage.clickProduct5InRecommendedProductAddToCartButton();
        
        // Click on 'View Cart' button
        homePage.clickViewCartButton();
        
        // Verify that product is displayed in cart page
        List<String> names = cartPage.getCartProductNames();
        Assert.assertEquals(names.get(0), RecommendedProduct5Name, "Recommended item is not in cart");

    }   

}
