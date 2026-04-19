/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pages;

import java.util.List;
import java.util.stream.Collectors;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import selenuim.SelenuimFramework;

/**
 *
 * @author Reem
 */
public class CartPage {
    // Locators
    private final By subscriptionTextLocator = By.cssSelector("div.single-widget h2");
    private final By subscriptionEmailLocator = By.cssSelector("input#susbscribe_email");
    private final By subscriptionButtonLocator = By.cssSelector("button#subscribe");
    private final By subscriptionSuccessMessageLocator = By.cssSelector("div.alert-success ");
    private final By footerLocator = By.cssSelector("footer#footer div.footer-widget");
    private final By productQuantityLocator =By.cssSelector("button.disabled");
    private final By numberOfItemsInCartLocator = By.cssSelector("tbody tr");
    private final By pricesOfItemsInCartLocator = By.cssSelector("tbody tr td.cart_price p");
    private final By quantitiesOfItemsInCartLocator = By.cssSelector("tbody tr td.cart_quantity button");
    private final By totalsOfItemsInCartLocator = By.cssSelector("tbody tr td.cart_total p");
    private final By shoppingCartTextLocator = By.cssSelector("div.breadcrumbs li:nth-of-type(2)");
    private final By proceedToCheckoutButtonLocator = By.cssSelector("a.check_out");
    private final By xButtonForFirstProductLocator = By.cssSelector("a.cart_quantity_delete[data-product-id=\"1\"]");
    private final By namesOfItemsInCartLocator = By.cssSelector("tbody tr td.cart_description a");
    private final By firstProductLocator = By.cssSelector("a[href=\"/product_details/1\"]");
    private final By signupLoginPopUpButtonLocator = By.cssSelector("div#checkoutModal a[href=\"/login\"]");
    private final SelenuimFramework framework;

    // Actions 
    public CartPage(SelenuimFramework framework) {
        this.framework = framework;
    }
    
    public void sendSubscriptionEmail(String email){
          framework.sendKeys(subscriptionEmailLocator, email);
      }
      
    public void clickSubscriptionButton(){
          framework.click(subscriptionButtonLocator);
      }
      
    public String getSubscriptionSuccessMessage(){
        return framework.getText(subscriptionSuccessMessageLocator);
    }
    
    public void scrollToFooter(){
        framework.scrollToElement(footerLocator);
    }
    
    public String getSubscriptionText(){
        return framework.getText(subscriptionTextLocator);
    }
    
    public String getProductQuantity(){
        return framework.getText(productQuantityLocator);
    }
    
    public int getNumberOfItemsInCart(){
        return framework.findElements(numberOfItemsInCartLocator).size();
    }
    
    

    public List<Double> getProductPrices() {
    return framework.findElements(pricesOfItemsInCartLocator)
                   .stream()
                   .map(WebElement::getText)        // "Rs. 500"
                   .map(text -> text.replaceAll("[^0-9]", "")) // keep only digits -> "500"
                   .map(Double::parseDouble)        // convert to 500.0
                   .collect(Collectors.toList());
    }

    public List<Integer> getProductQuantities() {
        return framework.findElements(quantitiesOfItemsInCartLocator)
                    .stream()
                    .map(WebElement::getText)
                    .map(Integer::parseInt)
                    .toList();
    }

    public List<Double> getProductTotals() {
        return framework.findElements(totalsOfItemsInCartLocator)
                   .stream()
                   .map(WebElement::getText)        // "Rs. 500"
                   .map(text -> text.replaceAll("[^0-9]", "")) // keep only digits -> "500"
                   .map(Double::parseDouble)        // convert to 500.0
                   .collect(Collectors.toList());
    }
    
    
    public String getShoppingCartText(){
        return framework.getText(shoppingCartTextLocator);
    }
    
    public void clickProceedToCheckoutButton(){
        framework.click(proceedToCheckoutButtonLocator);
    }
    
     public void clickXButtonForFirstProductButton(){
        framework.click(xButtonForFirstProductLocator);
    }
     
    public List<String> getCartProductNames() {
        List<WebElement> elements = framework.findElements(namesOfItemsInCartLocator);
        return elements.stream()
                       .map(WebElement::getText)
                       .collect(Collectors.toList());
    }
    
     public void waitForProductToBeRemoved(){
          framework.waitForElementToBeInvesible(firstProductLocator);
      }
     
     public void clickSignupLoginPopUpButton(){
         framework.click(signupLoginPopUpButtonLocator);
     }

    
    
    
    
    
}
