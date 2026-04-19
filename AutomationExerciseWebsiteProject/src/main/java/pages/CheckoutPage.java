/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import selenuim.SelenuimFramework;

/**
 *
 * @author Reem
 */
public class CheckoutPage {
    // Locators
    private final By placeOrderButtonLocator = By.cssSelector("a[href=\"/payment\"]");
    private final By commentsSectionLocator = By.cssSelector("textarea[name=\"message\"]");
    private final By deliveryFullNameLocator = By.cssSelector("ul#address_delivery li.address_firstname ");
    private final By deliveryAddress1Locator = By.cssSelector("ul#address_delivery li:nth-of-type(4)");
    private final By deliveryAddress2Locator = By.cssSelector("ul#address_delivery li:nth-of-type(5)");
    private final By deliveryCityStateZipcodeLocator = By.cssSelector("ul#address_delivery li.address_city ");
    private final By deliveryCountryLocator = By.cssSelector("ul#address_delivery li.address_country_name ");
    private final By deliveryMobileNumberLocator = By.cssSelector("ul#address_delivery li.address_phone");
    private final By billingFullNameLocator = By.cssSelector("ul#address_invoice li.address_firstname");
    private final By billingAddress1Locator = By.cssSelector("ul#address_invoice li:nth-of-type(4)");
    private final By billingAddress2Locator = By.cssSelector("ul#address_invoice li:nth-of-type(5)");
    private final By billingCityStateZipcodeLocator = By.cssSelector("ul#address_invoice li.address_city ");
    private final By billingCountryLocator = By.cssSelector("ul#address_invoice li.address_country_name");
    private final By billingMobileNumberLocator = By.cssSelector("ul#address_invoice li.address_phone");
    private final By subscriptionTextLocator = By.cssSelector("div.single-widget h2");
        
    private final By cartTotalLocator = By.cssSelector("tr:nth-of-type(3) td p.cart_total_price");

    
    
    
    
    
    private final SelenuimFramework framework;
    
    // Actions 
    public CheckoutPage(SelenuimFramework framework) {
        this.framework = framework;
    }
    public void clickPlaceOrderButton(){
        framework.click(placeOrderButtonLocator);
    }
    
    public void sendComments(String comments){
        framework.sendKeys(commentsSectionLocator, comments);
    }
    
    public String getDeliveryFullName(){
       return framework.getText(deliveryFullNameLocator);
    }
    
    public String getDeliveryAddress1(){
       return framework.getText(deliveryAddress1Locator);
    }
    
    public String getDeliveryAddress2(){
       return framework.getText(deliveryAddress2Locator);
    }
    
    public String getDeliveryCityStateZipcode(){
       return framework.getText(deliveryCityStateZipcodeLocator);
    }
    
    public String getDeliveryCountry(){
       return framework.getText(deliveryCountryLocator);
    }
    
    public String getDeliveryMobileNumber(){
       return framework.getText(deliveryMobileNumberLocator);
    }
    
    public String getBillingFullName(){
       return framework.getText(billingFullNameLocator);
    }
    public String getBillingAddress1(){
       return framework.getText(billingAddress1Locator);
    }
    public String getBillingAddress2(){
       return framework.getText(billingAddress2Locator);
    }
    public String getBillingCityStateZipcode(){
       return framework.getText(billingCityStateZipcodeLocator);
    }
    public String getBillingCountry(){
       return framework.getText(billingCountryLocator);
    }
    
    public String getBillingMobileNumber(){
       return framework.getText(billingMobileNumberLocator);
    }

    public double getCartTotal(){
        String text = framework.findElement(cartTotalLocator).getText(); //  "Rs. 500"
        String numeric = text.replaceAll("[^0-9]", ""); // -> "500"
        return Double.parseDouble(numeric);
    }
    public void scrollToPlaceOrderButton(){
        framework.scrollToElement(commentsSectionLocator);
    }
    
    public void scrollToSubscriptionText(){
        framework.scrollToElement(subscriptionTextLocator);
    }
    
    public void waitHelper(){
        framework.implicitWait(15);
    }
    
    
    
    
    
}
