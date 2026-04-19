/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/EmptyTestNGTest.java to edit this template
 */
package TestCases;

import Helper.HelperClass;
import Helper.FullUserData;
import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import pages.CartPage;
import pages.CheckoutPage;
import pages.HomePage;
import pages.OrderPlacedPage;
import pages.PaymentPage;
import pages.SignupLoginPage;
import pages.SignupPage;
import selenuim.SelenuimFramework;
import io.qameta.allure.*;

/**
 *
 * @author Reem
 */
@Epic("Automation Exercise Website")
@Feature("Checkout & Order Placement")
@Owner("Reem Adel")
public class CheckoutPageTest {
    
    SelenuimFramework framework;
    HomePage homePage;
    CartPage cartPage;
    OrderPlacedPage orderPlacedPage;
    SignupLoginPage signupLoginPage;
    SignupPage signupPage;
    CheckoutPage checkoutPage;
    PaymentPage paymentPage;
    static FullUserData[] ListOfUsers;
    int quantityOfEachProduct = 1 ;
    String firstProductName = "Blue Top";
    String secondProductName = "Men Tshirt";
   
   @BeforeClass
    public void beforeClass() throws FileNotFoundException {
        ListOfUsers = HelperClass.ReadFullUser("FullUserData.json");
        System.out.println("Number of users loaded: " + ListOfUsers.length);
    }
    @DataProvider(name = "fullUserData")
    public FullUserData[] checkoutUserDataProvider() {
        return ListOfUsers;
    }
    @BeforeMethod
    public void beforeMethod() {
        framework = new SelenuimFramework();
        framework.initializeBrowser();
        framework.navigateToURL("https://automationexercise.com/");
        homePage = new HomePage(framework);
        cartPage = new CartPage(framework);
        signupPage = new SignupPage(framework);
        signupLoginPage = new SignupLoginPage(framework);
        checkoutPage = new CheckoutPage(framework);
        paymentPage = new PaymentPage(framework);
        orderPlacedPage = new OrderPlacedPage(framework);

    }

    @AfterMethod
    public void afterMethod() {
        framework.closeBrowser();
    }
    
    
     // Helper Test Case
    @Test(dataProvider = "fullUserData", priority =1)
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
        
        checkoutPage.waitHelper();
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
    
    
    // Test Case 16
    @Test(dataProvider = "fullUserData", priority =1, dependsOnMethods = "registerUser")
    @Story("Place Order - Login Before Checkout")
    @Severity(SeverityLevel.BLOCKER)
    @Description("Verify that a registered user can log in before checkout, add products to the cart, proceed to checkout, " +
                 "verify delivery and billing addresses, review the order, complete payment, and place the order successfully.")
    public void placeOrderLoginBeforeCheckout_TC16(FullUserData TestUser){
        // verify in home page
        String ActualTitle =  homePage.getHomePageTitle();
        Assert.assertTrue(ActualTitle.contains("Automation Exercise"),
                "User is not in home page");
        
        //Click on 'Signup / Login' button
        homePage.clickSignupLoginButton();
        
        // perform correct login
        signupLoginPage.sendLoginEmail(TestUser.email);
        signupLoginPage.sendLoginPassword(TestUser.password);
        signupLoginPage.clickLoginButton();
        
        // Verify that 'Logged in as username' is visible
        String ExpectedLoggedInAsText = "Logged in as ";
        String ActualLoggedInAsText = homePage.getLoggedInText();
        Assert.assertTrue(ActualLoggedInAsText.contains(ExpectedLoggedInAsText),
                ActualLoggedInAsText + " Should Have Contained " + ExpectedLoggedInAsText);
        
        /* ---------  Add products to cart  --------------------- */
        // Hover over first product and click 'Add to cart'
        homePage.scrollToViewFirstProductAddToCartButton();
        homePage.clickFirstProductAddToCartButton();
        
        // Click 'Continue Shopping' button
        homePage.clickContinueShoppingButton();
        
        // Hover over second product and click 'Add to cart'
        homePage.clickSecondProductAddToCartButton();
        
        // Click 'View Cart' button
        homePage.clickViewCartButton();
        
        /* --------------------------------------------------------------- */
        
        // Verify that cart page is displayed
        String ExpectedShoppinCartText = "Shopping Cart";
        String ActualShoppinCartText = cartPage.getShoppingCartText();
        Assert.assertTrue(ActualShoppinCartText.contains(ExpectedShoppinCartText),
                ActualShoppinCartText + " Should Have Contained " + ExpectedShoppinCartText);
       
        // Click Proceed To Checkout
        cartPage.clickProceedToCheckoutButton();
        
        //--------- Verify Address Details --------------  
        String ExpectedFullName = TestUser.title +" "+ TestUser.firstName +" "+ TestUser.lastName;
        String ExpectedCityStateZipcode = TestUser.city+" "+TestUser.state+" "+ TestUser.zipcode;
        
        //           Delivery Address  
        Assert.assertTrue(checkoutPage.getDeliveryFullName().contains(ExpectedFullName)," Name in delivery address in incorrect ");
        Assert.assertTrue(checkoutPage.getDeliveryAddress1().contains(TestUser.address)," Address in delivery address in incorrect " );
        Assert.assertTrue(checkoutPage.getDeliveryAddress2().contains(TestUser.address2)," Address2 in delivery address in incorrect ");
        Assert.assertTrue(checkoutPage.getDeliveryCityStateZipcode().contains(ExpectedCityStateZipcode)," City State Zipcode in delivery address in incorrect ");
        Assert.assertTrue(checkoutPage.getDeliveryCountry().contains(TestUser.country)," Country in delivery address in incorrect ");
        Assert.assertTrue(checkoutPage.getDeliveryMobileNumber().contains(TestUser.mobileNumber)," Mobile number in delivery address in incorrect ");

        //           Billing Address  
        Assert.assertTrue(checkoutPage.getBillingFullName().contains(ExpectedFullName)," Name in billing address in incorrect ");
        Assert.assertTrue(checkoutPage.getBillingAddress1().contains(TestUser.address)," Address in billing address in incorrect " );
        Assert.assertTrue(checkoutPage.getBillingAddress2().contains(TestUser.address2)," Address2 in billing address in incorrect ");
        Assert.assertTrue(checkoutPage.getBillingCityStateZipcode().contains(ExpectedCityStateZipcode)," City State Zipcode in billing address in incorrect ");
        Assert.assertTrue(checkoutPage.getBillingCountry().contains(TestUser.country)," Country in billing address in incorrect ");
        Assert.assertTrue(checkoutPage.getBillingMobileNumber().contains(TestUser.mobileNumber)," Mobile number in billing address in incorrect ");
        
        checkoutPage.scrollToPlaceOrderButton();
        //--------- Review Your Order -------------------
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
        
        Double ExpectedCartTotal = 0.0;
        for (int i = 0; i < prices.size(); i++) {
             ExpectedCartTotal += prices.get(i);
        }
        Assert.assertEquals(checkoutPage.getCartTotal(), ExpectedCartTotal, "Cart total is incorrect");

        
        // Enter description in comment text area 
        checkoutPage.sendComments(TestUser.comments);
        
        checkoutPage.scrollToSubscriptionText();
        
        // click 'Place Order'
        checkoutPage.clickPlaceOrderButton();
        
        paymentPage.scrollToFooter();
        
        // Enter payment details: Name on Card, Card Number, CVC, Expiration date
        paymentPage.sendNameOnCard(TestUser.cardName);
        paymentPage.sendCardNumber(TestUser.cardNumber);
        paymentPage.sendCVC(TestUser.cvc);
        paymentPage.sendExpMonth(TestUser.expMonth);
        paymentPage.sendExpYear(TestUser.expYear);
        
        // Click 'Pay and Confirm Order' button
        paymentPage.clickPayAndConfirmButton();
        
        // Verify order placed
        String ExpectedOrderPlacedText = "ORDER PLACED!";
        String ActualOrderPlacedText = paymentPage.getOrderPlacedText();
        Assert.assertTrue(ActualOrderPlacedText.contains(ExpectedOrderPlacedText),
                ActualOrderPlacedText + " Should Have Contained " + ExpectedOrderPlacedText);
        
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
    
    // Test Case 14
    @Test(dataProvider = "fullUserData")
    @Story("Place Order - Register While Checkout")
    @Severity(SeverityLevel.BLOCKER)
    @Description("Verify that a new user can register during checkout, add products to the cart, verify address details, " +
                 "review the order, complete payment, and place the order successfully.")
    public void placeOrderRegisterWhileCheckout_TC14(FullUserData TestUser){
        // verify in home page
        String ActualTitle =  homePage.getHomePageTitle();
        Assert.assertTrue(ActualTitle.contains("Automation Exercise"),
                "User is not in home page");
        
        
       /* ---------  Add products to cart  --------------------- */
        // Hover over first product and click 'Add to cart'
        homePage.scrollToViewFirstProductAddToCartButton();
        homePage.clickFirstProductAddToCartButton();
        
        // Click 'Continue Shopping' button
        homePage.clickContinueShoppingButton();
        
        // Hover over second product and click 'Add to cart'
        homePage.clickSecondProductAddToCartButton();
        
        // Click 'View Cart' button
        homePage.clickViewCartButton();
        
        /* --------------------------------------------------------------- */
        
        // Verify that cart page is displayed
        String ExpectedShoppinCartText = "Shopping Cart";
        String ActualShoppinCartText = cartPage.getShoppingCartText();
        Assert.assertTrue(ActualShoppinCartText.contains(ExpectedShoppinCartText),
                ActualShoppinCartText + " Should Have Contained " + ExpectedShoppinCartText);
                
        //Click on 'Signup / Login' button
        homePage.clickSignupLoginButton();
        
        // Fill all details in Signup and create account
        /* ------------------ Sign UP Steps --------------------------*/
        // Enter name 
        signupLoginPage.sendSignupName(TestUser.name);
       
        // Enter email address
        signupLoginPage.sendSignupEmail(TestUser.email);

        // Click 'Signup' button
        signupLoginPage.clickSignupButton();
        
        checkoutPage.waitHelper();
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
        /* -------------------------------------------------------------------*/
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
        
        // Click 'Cart' button
        homePage.clickCartButton();
        
        // Click Proceed To Checkout
        cartPage.clickProceedToCheckoutButton();
        
        //--------- Verify Address Details --------------  
        String ExpectedFullName = TestUser.title +" "+ TestUser.firstName +" "+ TestUser.lastName;
        String ExpectedCityStateZipcode = TestUser.city+" "+TestUser.state+" "+ TestUser.zipcode;
        
        //           Delivery Address  
        Assert.assertTrue(checkoutPage.getDeliveryFullName().contains(ExpectedFullName)," Name in delivery address in incorrect ");
        Assert.assertTrue(checkoutPage.getDeliveryAddress1().contains(TestUser.address)," Address in delivery address in incorrect " );
        Assert.assertTrue(checkoutPage.getDeliveryAddress2().contains(TestUser.address2)," Address2 in delivery address in incorrect ");
        Assert.assertTrue(checkoutPage.getDeliveryCityStateZipcode().contains(ExpectedCityStateZipcode)," City State Zipcode in delivery address in incorrect ");
        Assert.assertTrue(checkoutPage.getDeliveryCountry().contains(TestUser.country)," Country in delivery address in incorrect ");
        Assert.assertTrue(checkoutPage.getDeliveryMobileNumber().contains(TestUser.mobileNumber)," Mobile number in delivery address in incorrect ");

        //           Billing Address  
        Assert.assertTrue(checkoutPage.getBillingFullName().contains(ExpectedFullName)," Name in billing address in incorrect ");
        Assert.assertTrue(checkoutPage.getBillingAddress1().contains(TestUser.address)," Address in billing address in incorrect " );
        Assert.assertTrue(checkoutPage.getBillingAddress2().contains(TestUser.address2)," Address2 in billing address in incorrect ");
        Assert.assertTrue(checkoutPage.getBillingCityStateZipcode().contains(ExpectedCityStateZipcode)," City State Zipcode in billing address in incorrect ");
        Assert.assertTrue(checkoutPage.getBillingCountry().contains(TestUser.country)," Country in billing address in incorrect ");
        Assert.assertTrue(checkoutPage.getBillingMobileNumber().contains(TestUser.mobileNumber)," Mobile number in billing address in incorrect ");
        
        checkoutPage.scrollToPlaceOrderButton();
        //--------- Review Your Order -------------------
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
        
        Double ExpectedCartTotal = 0.0;
        for (int i = 0; i < prices.size(); i++) {
             ExpectedCartTotal += prices.get(i);
        }
        Assert.assertEquals(checkoutPage.getCartTotal(), ExpectedCartTotal, "Cart total is incorrect");

        
        // Enter description in comment text area 
        checkoutPage.sendComments(TestUser.comments);
        
        checkoutPage.scrollToSubscriptionText();
        
        // click 'Place Order'
        checkoutPage.clickPlaceOrderButton();
        
        paymentPage.scrollToFooter();
        
        // Enter payment details: Name on Card, Card Number, CVC, Expiration date
        paymentPage.sendNameOnCard(TestUser.cardName);
        paymentPage.sendCardNumber(TestUser.cardNumber);
        paymentPage.sendCVC(TestUser.cvc);
        paymentPage.sendExpMonth(TestUser.expMonth);
        paymentPage.sendExpYear(TestUser.expYear);
        
        // Click 'Pay and Confirm Order' button
        paymentPage.clickPayAndConfirmButton();
        
        // Verify order placed
        String ExpectedOrderPlacedText = "ORDER PLACED!";
        String ActualOrderPlacedText = paymentPage.getOrderPlacedText();
        Assert.assertTrue(ActualOrderPlacedText.contains(ExpectedOrderPlacedText),
                ActualOrderPlacedText + " Should Have Contained " + ExpectedOrderPlacedText);
        
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
    
    // Test Case 15
    @Test(dataProvider = "fullUserData")
    @Story("Place Order after Registration")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that a user can register, add products to cart, and successfully place an order before checkout.")
    public void placeOrderRegisterBeforeCheckout_TC15(FullUserData TestUser){

        // verify in home page
        String ActualTitle =  homePage.getHomePageTitle();
        Assert.assertTrue(ActualTitle.contains("Automation Exercise"),
                "User is not in home page");
        
        //Click on 'Signup / Login' button
        homePage.clickSignupLoginButton();
        
        // Fill all details in Signup and create account
        /* ------------------ Sign UP Steps --------------------------*/
        // Enter name 
        signupLoginPage.sendSignupName(TestUser.name);
       
        // Enter email address
        signupLoginPage.sendSignupEmail(TestUser.email);

        // Click 'Signup' button
        signupLoginPage.clickSignupButton();
        
        checkoutPage.waitHelper();
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
        /* -------------------------------------------------------------------*/
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
        
       
       /* ---------  Add products to cart  --------------------- */
        // Hover over first product and click 'Add to cart'
        homePage.scrollToViewFirstProductAddToCartButton();
        homePage.clickFirstProductAddToCartButton();
        
        // Click 'Continue Shopping' button
        homePage.clickContinueShoppingButton();
        
        // Hover over second product and click 'Add to cart'
        homePage.clickSecondProductAddToCartButton();
        
        // Click 'View Cart' button
        homePage.clickViewCartButton();
        
        /* --------------------------------------------------------------- */
        
        // Verify that cart page is displayed
        String ExpectedShoppinCartText = "Shopping Cart";
        String ActualShoppinCartText = cartPage.getShoppingCartText();
        Assert.assertTrue(ActualShoppinCartText.contains(ExpectedShoppinCartText),
                ActualShoppinCartText + " Should Have Contained " + ExpectedShoppinCartText);
        
        // Click Proceed To Checkout
        cartPage.clickProceedToCheckoutButton();
        
        //--------- Verify Address Details --------------  
        String ExpectedFullName = TestUser.title +" "+ TestUser.firstName +" "+ TestUser.lastName;
        String ExpectedCityStateZipcode = TestUser.city+" "+TestUser.state+" "+ TestUser.zipcode;
        
        //           Delivery Address  
        Assert.assertTrue(checkoutPage.getDeliveryFullName().contains(ExpectedFullName)," Name in delivery address in incorrect ");
        Assert.assertTrue(checkoutPage.getDeliveryAddress1().contains(TestUser.address)," Address in delivery address in incorrect " );
        Assert.assertTrue(checkoutPage.getDeliveryAddress2().contains(TestUser.address2)," Address2 in delivery address in incorrect ");
        Assert.assertTrue(checkoutPage.getDeliveryCityStateZipcode().contains(ExpectedCityStateZipcode)," City State Zipcode in delivery address in incorrect ");
        Assert.assertTrue(checkoutPage.getDeliveryCountry().contains(TestUser.country)," Country in delivery address in incorrect ");
        Assert.assertTrue(checkoutPage.getDeliveryMobileNumber().contains(TestUser.mobileNumber)," Mobile number in delivery address in incorrect ");

        //           Billing Address  
        Assert.assertTrue(checkoutPage.getBillingFullName().contains(ExpectedFullName)," Name in billing address in incorrect ");
        Assert.assertTrue(checkoutPage.getBillingAddress1().contains(TestUser.address)," Address in billing address in incorrect " );
        Assert.assertTrue(checkoutPage.getBillingAddress2().contains(TestUser.address2)," Address2 in billing address in incorrect ");
        Assert.assertTrue(checkoutPage.getBillingCityStateZipcode().contains(ExpectedCityStateZipcode)," City State Zipcode in billing address in incorrect ");
        Assert.assertTrue(checkoutPage.getBillingCountry().contains(TestUser.country)," Country in billing address in incorrect ");
        Assert.assertTrue(checkoutPage.getBillingMobileNumber().contains(TestUser.mobileNumber)," Mobile number in billing address in incorrect ");
        
        checkoutPage.scrollToPlaceOrderButton();
        //--------- Review Your Order -------------------
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
        
        Double ExpectedCartTotal = 0.0;
        for (int i = 0; i < prices.size(); i++) {
             ExpectedCartTotal += prices.get(i);
        }
        Assert.assertEquals(checkoutPage.getCartTotal(), ExpectedCartTotal, "Cart total is incorrect");

        
        // Enter description in comment text area 
        checkoutPage.sendComments(TestUser.comments);
        
        checkoutPage.scrollToSubscriptionText();
        
        // click 'Place Order'
        checkoutPage.clickPlaceOrderButton();
        
        paymentPage.scrollToFooter();
        
        // Enter payment details: Name on Card, Card Number, CVC, Expiration date
        paymentPage.sendNameOnCard(TestUser.cardName);
        paymentPage.sendCardNumber(TestUser.cardNumber);
        paymentPage.sendCVC(TestUser.cvc);
        paymentPage.sendExpMonth(TestUser.expMonth);
        paymentPage.sendExpYear(TestUser.expYear);
        
        // Click 'Pay and Confirm Order' button
        paymentPage.clickPayAndConfirmButton();
        
        // Verify order placed
        String ExpectedOrderPlacedText = "ORDER PLACED!";
        String ActualOrderPlacedText = paymentPage.getOrderPlacedText();
        Assert.assertTrue(ActualOrderPlacedText.contains(ExpectedOrderPlacedText),
                ActualOrderPlacedText + " Should Have Contained " + ExpectedOrderPlacedText);
        
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
    
    // Test Case 23
    @Test(dataProvider = "fullUserData")
    @Story("Verify Checkout Address Details")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify that delivery and billing address details are displayed correctly on the checkout page.")
    public void verifyAddressDetailsInCheckoutPage_TC23(FullUserData TestUser){
         // verify in home page
        String ActualTitle =  homePage.getHomePageTitle();
        Assert.assertTrue(ActualTitle.contains("Automation Exercise"),
                "User is not in home page");
        
        //Click on 'Signup / Login' button
        homePage.clickSignupLoginButton();
        
        // Fill all details in Signup and create account
        /* ------------------ Sign UP Steps --------------------------*/
        // Enter name 
        signupLoginPage.sendSignupName(TestUser.name);
       
        // Enter email address
        signupLoginPage.sendSignupEmail(TestUser.email);

        // Click 'Signup' button
        signupLoginPage.clickSignupButton();
        
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
        /* -------------------------------------------------------------------*/
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
        
       
       /* ---------  Add products to cart  --------------------- */
        // Hover over first product and click 'Add to cart'
        homePage.scrollToViewFirstProductAddToCartButton();
        homePage.clickFirstProductAddToCartButton();
        
        // Click 'Continue Shopping' button
        homePage.clickContinueShoppingButton();
        
        // Hover over second product and click 'Add to cart'
        homePage.clickSecondProductAddToCartButton();
        
        // Click 'View Cart' button
        homePage.clickViewCartButton();
        
        /* --------------------------------------------------------------- */
        
        // Verify that cart page is displayed
        String ExpectedShoppinCartText = "Shopping Cart";
        String ActualShoppinCartText = cartPage.getShoppingCartText();
        Assert.assertTrue(ActualShoppinCartText.contains(ExpectedShoppinCartText),
                ActualShoppinCartText + " Should Have Contained " + ExpectedShoppinCartText);
        
        // Click Proceed To Checkout
        cartPage.clickProceedToCheckoutButton();
        
        //--------- Verify Address Details --------------  
        String ExpectedFullName = TestUser.title +" "+ TestUser.firstName +" "+ TestUser.lastName;
        String ExpectedCityStateZipcode = TestUser.city+" "+TestUser.state+" "+ TestUser.zipcode;
        
        //           Delivery Address  
        Assert.assertTrue(checkoutPage.getDeliveryFullName().contains(ExpectedFullName)," Name in delivery address in incorrect ");
        Assert.assertTrue(checkoutPage.getDeliveryAddress1().contains(TestUser.address)," Address in delivery address in incorrect " );
        Assert.assertTrue(checkoutPage.getDeliveryAddress2().contains(TestUser.address2)," Address2 in delivery address in incorrect ");
        Assert.assertTrue(checkoutPage.getDeliveryCityStateZipcode().contains(ExpectedCityStateZipcode)," City State Zipcode in delivery address in incorrect ");
        Assert.assertTrue(checkoutPage.getDeliveryCountry().contains(TestUser.country)," Country in delivery address in incorrect ");
        Assert.assertTrue(checkoutPage.getDeliveryMobileNumber().contains(TestUser.mobileNumber)," Mobile number in delivery address in incorrect ");

        //           Billing Address  
        Assert.assertTrue(checkoutPage.getBillingFullName().contains(ExpectedFullName)," Name in billing address in incorrect ");
        Assert.assertTrue(checkoutPage.getBillingAddress1().contains(TestUser.address)," Address in billing address in incorrect " );
        Assert.assertTrue(checkoutPage.getBillingAddress2().contains(TestUser.address2)," Address2 in billing address in incorrect ");
        Assert.assertTrue(checkoutPage.getBillingCityStateZipcode().contains(ExpectedCityStateZipcode)," City State Zipcode in billing address in incorrect ");
        Assert.assertTrue(checkoutPage.getBillingCountry().contains(TestUser.country)," Country in billing address in incorrect ");
        Assert.assertTrue(checkoutPage.getBillingMobileNumber().contains(TestUser.mobileNumber)," Mobile number in billing address in incorrect ");
        
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

    // Test Case 24
    @Test(dataProvider = "fullUserData")
    @Story("Download Invoice after Purchase")
    @Severity(SeverityLevel.MINOR)
    @Description("Verify that a user can download the invoice after successfully placing an order.")
    public void downloadInvoiceAfterPurchaseOrder_TC24(FullUserData TestUser){
         // verify in home page
        String ActualTitle =  homePage.getHomePageTitle();
        Assert.assertTrue(ActualTitle.contains("Automation Exercise"),
                "User is not in home page");
        
        
       /* ---------  Add products to cart  --------------------- */
        // Hover over first product and click 'Add to cart'
        homePage.scrollToViewFirstProductAddToCartButton();
        homePage.clickFirstProductAddToCartButton();
        
        // Click 'Continue Shopping' button
        homePage.clickContinueShoppingButton();
        
        // Hover over second product and click 'Add to cart'
        homePage.clickSecondProductAddToCartButton();
        
        // Click 'View Cart' button
        homePage.clickViewCartButton();
        
        /* --------------------------------------------------------------- */
        
        // Verify that cart page is displayed
        String ExpectedShoppinCartText = "Shopping Cart";
        String ActualShoppinCartText = cartPage.getShoppingCartText();
        Assert.assertTrue(ActualShoppinCartText.contains(ExpectedShoppinCartText),
                ActualShoppinCartText + " Should Have Contained " + ExpectedShoppinCartText);
                
        // Click Proceed To Checkout
        cartPage.clickProceedToCheckoutButton();
        
        //Click on 'Signup / Login' button
        cartPage.clickSignupLoginPopUpButton();
        
        // Fill all details in Signup and create account
        /* ------------------ Sign UP Steps --------------------------*/
        // Enter name 
        signupLoginPage.sendSignupName(TestUser.name);
       
        // Enter email address
        signupLoginPage.sendSignupEmail(TestUser.email);

        // Click 'Signup' button
        signupLoginPage.clickSignupButton();
        
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
        /* -------------------------------------------------------------------*/
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
        
        // Click 'Cart' button
        homePage.clickCartButton();
        
        // Click Proceed To Checkout
        cartPage.clickProceedToCheckoutButton();
        
        //--------- Verify Address Details --------------  
        String ExpectedFullName = TestUser.title +" "+ TestUser.firstName +" "+ TestUser.lastName;
        String ExpectedCityStateZipcode = TestUser.city+" "+TestUser.state+" "+ TestUser.zipcode;
        
        //           Delivery Address  
        Assert.assertTrue(checkoutPage.getDeliveryFullName().contains(ExpectedFullName)," Name in delivery address in incorrect ");
        Assert.assertTrue(checkoutPage.getDeliveryAddress1().contains(TestUser.address)," Address in delivery address in incorrect " );
        Assert.assertTrue(checkoutPage.getDeliveryAddress2().contains(TestUser.address2)," Address2 in delivery address in incorrect ");
        Assert.assertTrue(checkoutPage.getDeliveryCityStateZipcode().contains(ExpectedCityStateZipcode)," City State Zipcode in delivery address in incorrect ");
        Assert.assertTrue(checkoutPage.getDeliveryCountry().contains(TestUser.country)," Country in delivery address in incorrect ");
        Assert.assertTrue(checkoutPage.getDeliveryMobileNumber().contains(TestUser.mobileNumber)," Mobile number in delivery address in incorrect ");

        //           Billing Address  
        Assert.assertTrue(checkoutPage.getBillingFullName().contains(ExpectedFullName)," Name in billing address in incorrect ");
        Assert.assertTrue(checkoutPage.getBillingAddress1().contains(TestUser.address)," Address in billing address in incorrect " );
        Assert.assertTrue(checkoutPage.getBillingAddress2().contains(TestUser.address2)," Address2 in billing address in incorrect ");
        Assert.assertTrue(checkoutPage.getBillingCityStateZipcode().contains(ExpectedCityStateZipcode)," City State Zipcode in billing address in incorrect ");
        Assert.assertTrue(checkoutPage.getBillingCountry().contains(TestUser.country)," Country in billing address in incorrect ");
        Assert.assertTrue(checkoutPage.getBillingMobileNumber().contains(TestUser.mobileNumber)," Mobile number in billing address in incorrect ");
        
        checkoutPage.scrollToPlaceOrderButton();
        //--------- Review Your Order -------------------
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
        
        Double ExpectedCartTotal = 0.0;
        for (int i = 0; i < prices.size(); i++) {
             ExpectedCartTotal += prices.get(i);
        }
        Assert.assertEquals(checkoutPage.getCartTotal(), ExpectedCartTotal, "Cart total is incorrect");

        
        // Enter description in comment text area 
        checkoutPage.sendComments(TestUser.comments);
        
        checkoutPage.scrollToSubscriptionText();
        
        // click 'Place Order'
        checkoutPage.clickPlaceOrderButton();
        
        paymentPage.scrollToFooter();
        
        // Enter payment details: Name on Card, Card Number, CVC, Expiration date
        paymentPage.sendNameOnCard(TestUser.cardName);
        paymentPage.sendCardNumber(TestUser.cardNumber);
        paymentPage.sendCVC(TestUser.cvc);
        paymentPage.sendExpMonth(TestUser.expMonth);
        paymentPage.sendExpYear(TestUser.expYear);
        
        // Click 'Pay and Confirm Order' button
        paymentPage.clickPayAndConfirmButton();
        
        // Verify order placed
        String ExpectedOrderPlacedText = "ORDER PLACED!";
        String ActualOrderPlacedText = paymentPage.getOrderPlacedText();
        Assert.assertTrue(ActualOrderPlacedText.contains(ExpectedOrderPlacedText),
                ActualOrderPlacedText + " Should Have Contained " + ExpectedOrderPlacedText);
        
        // Click 'Download Invoice' button 
        orderPlacedPage.clickDownloadInvoiceButton();
        
        // verify invoice is downloaded successfully
        String downloadDir = Paths.get(System.getProperty("user.dir"))
                          .getParent()  // go up from work to Project-1
                          .resolve("Downloads")
                          .toString();
        boolean fileExists = framework.waitForFile(downloadDir,".txt", 15);
        Assert.assertTrue(fileExists, "Invoice file was not downloaded!");
        
        //  Click 'Continue' button
        orderPlacedPage.clickContinueButton();
        
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

}
