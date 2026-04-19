/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pages;

import org.openqa.selenium.By;
import selenuim.SelenuimFramework;

/**
 *
 * @author Reem
 */
public class FirstProductPage {
    // Locators
    public final By writeYourReviewTextLocator = By.cssSelector("a[href=\"#reviews\"]");
    public final By nameBoxLocator = By.cssSelector("input#name");
    public final By emailBoxLocator = By.cssSelector("input#email");
    public final By reviewBoxLocator = By.cssSelector("textarea#review");
    public final By submitButtonLocator = By.cssSelector("button#button-review");
    public final By successMessageLocator = By.cssSelector("div#review-section div.alert-success");
    public final By productPriceLocator = By.cssSelector("div.product-information span span");
    public final By productNameLocator = By.cssSelector("div.product-information h2");
    public final By productBrandLocator = By.cssSelector("div.product-information p:nth-of-type(4) b");
    public final By productConditionLocator = By.cssSelector("div.product-information p:nth-of-type(3) b");
    public final By productCategoryLocator = By.cssSelector("div.product-information p:nth-of-type(1)");
    public final By productAvailabilityLocator = By.cssSelector("div.product-information p:nth-of-type(2) b");
    public final By addToCartButtonLocator = By.cssSelector("button.cart");
    private final By quantityLocator  = By.cssSelector("input#quantity");
    private final By viewCartButtonLocator = By.cssSelector("div.modal-body a[href=\"/view_cart\"]");
    private final SelenuimFramework framework;

    // Actions 
    public FirstProductPage(SelenuimFramework framework) {
        this.framework = framework;
    }
    public String getWriteYourReviewText(){
        return framework.getText(writeYourReviewTextLocator);
    }
    public void sendName(String name){
        framework.sendKeys(nameBoxLocator, name);
    }
    public void sendEmail(String email) {
        framework.sendKeys(emailBoxLocator, email);
    }
    public void sendReview(String review){
        framework.sendKeys(reviewBoxLocator, review);

    }
    public void clickSubmitButton(){
        framework.click(submitButtonLocator);
    }
    public String getSuccessMessage(){
        return framework.getText(successMessageLocator);
    }
    public boolean getProductName(){
        return framework.isDisplayed(productNameLocator);
    }
    public boolean getProductCategory(){
        return framework.isDisplayed(productCategoryLocator);
    }
    public boolean getProductAvailability(){
        return framework.isDisplayed(productAvailabilityLocator);
    }
    public boolean getProductPrice(){
        return framework.isDisplayed(productPriceLocator);
    }
    public boolean getProductBrand(){
        return framework.isDisplayed(productBrandLocator);
    }
    public boolean getProductCondition(){
        return framework.isDisplayed(productConditionLocator);
    }
    public void clickAddToCartButton(){
          framework.click(addToCartButtonLocator);
    }
    public void sendQuantity(String quantityValue ){
        framework.clearAndSendKeys(quantityLocator, quantityValue);
    }
    public void clickViewCartButton(){
        framework.click(viewCartButtonLocator);
    }
    
    
    
}
