/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pages;
//import org.openqa.selenium.By;
//import selenuim.SelenuimFramework;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import selenuim.SelenuimFramework;



/**
 *
 * @author Reem
 */
public class HomePage {
      // Locators
      private final By homePageButtonLocator = By.cssSelector("ul.nav a[href=\"/\"]");
      private final By signupLoginButtonLocator = By.cssSelector("a[href=\"/login\"]");
      private final By loggedInTextLocator = By.xpath("//*[@id=\"header\"]/div/div/div/div[2]/div/ul/li[10]/a");
      private final By deleteAccountButtonLocator = By.cssSelector("a[href = \"/delete_account\"]");
      private final By accountDeletedTextLocator =By.cssSelector("[data-qa=\"account-deleted\"]");
      private final By testCasesButtonLocator =By.cssSelector("ul.nav a[href=\"/test_cases\"]");
      private final By subscriptionTextLocator = By.cssSelector("div.single-widget h2");
      private final By upArrowButtonLocator = By.cssSelector("a#scrollUp");
      private final By textInPageTopLocator = By.cssSelector("div.col-sm-6 h2");
      private final By elementAtBottomLocator = By.cssSelector("p.pull-left");
      private final By productButtonLocator = By.cssSelector("a[href=\"/products\"]");
      private final By logoutButtonLocator = By.cssSelector("a[href=\"/logout\"]");
      private final By subscriptionEmailLocator = By.cssSelector("input#susbscribe_email");
      private final By subscriptionButtonLocator = By.cssSelector("button#subscribe");
      private final By SubscriptionSuccessMessageLocator= By.cssSelector("div.alert-success ");
      private final By cartButtonLocator= By.cssSelector("ul.nav a[href=\"/view_cart\"]");
      private final By continueButtonLocator = By.cssSelector("a[data-qa=\"continue-button\"]");
      private final By contactUsButtonLocator = By.cssSelector("a[href=\"/contact_us\"]");
      private final By viewProduct1ButtonLocator = By.cssSelector("a[href=\"/product_details/1\"]");
      private final By brandsTextLocator = By.cssSelector("div.brands_products h2");
      private final By continueShoppingButtonLocator = By.cssSelector("div.modal-dialog button.btn-success");
      private final By viewCartButtonLocator = By.cssSelector("div.modal-dialog a[href=\"/view_cart\"]");
      public final By brandSectionLocator = By.cssSelector("div.brands-name");
      private final By firstProducthoverLocator = By.cssSelector("img[src=\"/get_product_picture/1\"]");
      private final By secondProducthoverLocator = By.cssSelector("img[src=\"/get_product_picture/2\"]");
      private final By firstProductAddToCartButtonLocator = By.cssSelector("div.product-overlay a[data-product-id=\"1\"]");
      private final By secondProductAddToCartButtonLocator = By.cssSelector("div.product-overlay a[data-product-id=\"2\"]");
      private final By recommendedItemsTextLocator = By.cssSelector("div.recommended_items h2");
      private final By product5RecommendedProductAddToCartButtonLocator = By.cssSelector("div.recommended_items a[data-product-id=\"5\"]");
      private final By recommendedItemsSectionLocator = By.cssSelector("div.recommended_items");
      private final By productFourImgLocator = By.cssSelector("img[src=\"/get_product_picture/4\"]");
      private final By categorySectionLocator = By.cssSelector("div.category-products");
      private final By womenCategoryLocator = By.cssSelector("a[href=\"#Women\"]");
      private final By dressCategoryLocator = By.cssSelector("div#Women div.panel-body ul li a[href=\"/category_products/1\"]");
      private final By navLocator = By.cssSelector("header div.container");
      // trial
      // private final By firstProductAddToCartButtonLocator = By.xpath("/html/body/section[2]/div[1]/div/div[2]/div[1]/div[2]/div/div[1]/div[2]/div/a");
      //private final By secondProductAddToCartButtonLocator = By.xpath("/html/body/section[2]/div[1]/div/div[2]/div[1]/div[3]/div/div[1]/div[2]/div/a");
      //private final By firstProducthoverLocator = By.xpath("/html/body/section[2]/div[1]/div/div[2]/div[1]/div[2]/div/div[1]/div[1]/img");
      // private final By secondProducthoverLocator = By.xpath("/html/body/section[2]/div[1]/div/div[2]/div[1]/div[3]/div/div[1]/div[1]/img");
      private final SelenuimFramework framework;

      public HomePage(SelenuimFramework framework) {
         this.framework = framework; 
      }

      // Actions
      public String getHomePageTitle(){
          return framework.getPageTitle();
      }
      
      public void clickSignupLoginButton(){
          framework.click(signupLoginButtonLocator);
      }
      public String getLoggedInText(){
          framework.explicitWait(loggedInTextLocator,50);
          WebElement element = framework.findElement(loggedInTextLocator);
          String text = element.getText();
          System.out.println(text);
          return text ;
      }
      public void clickDeleteAccountButton(){
          framework.click(deleteAccountButtonLocator);
      }
      public String getDeletedAccountText(){
          return framework.getText(accountDeletedTextLocator);
      }
      public void clickTestCasesButton(){
          framework.click(testCasesButtonLocator);
      }
      
      public void clickUpArrowButton(){
          framework.click(upArrowButtonLocator);
      }
      
      public String getSubscriptionText(){
          return framework.getText(subscriptionTextLocator);
      }
      
      public String getTextInPageTop(){
          return framework.getText(textInPageTopLocator);
      }
      
      public void scrollDown(){
          framework.scrollToElement(elementAtBottomLocator);
      }
      
      public void scrollUp(){
          framework.scrollToElement(homePageButtonLocator);
      }
      
      public void clickProductsButton(){
          framework.click(productButtonLocator);
      }
      
      public void clicklogoutButton(){
          framework.click(logoutButtonLocator);
      }
      
      public void sendSubscriptionEmail(String email){
          framework.sendKeys(subscriptionEmailLocator, email);
      }
      
      public void clickSubscriptionButton(){
          framework.click(subscriptionButtonLocator);
      }
      
      public String getSubscriptionSuccessMessage(){
        return framework.getText(SubscriptionSuccessMessageLocator);
    }
      public void clickCartButton(){
          framework.click(cartButtonLocator);
      }
      public void clickContinueButton(){
          framework.click(continueButtonLocator);
      }
      
      public void clickContactUsButton(){
          framework.click(contactUsButtonLocator);
      }
      
      public void clickHomeButton(){
          framework.click(homePageButtonLocator);
      }
      
      public void clickViewProduct1Button(){
          framework.click(viewProduct1ButtonLocator);
      }
      
      public void scrollToViewViewProductButton(){
          framework.scrollToElement(brandsTextLocator);
      }
     
      public void clickContinueShoppingButton (){
          framework.click(continueShoppingButtonLocator);
      }
      public void clickViewCartButton (){
          framework.click(viewCartButtonLocator);
      }
      
      public void scrollToViewFirstProductAddToCartButton (){
          framework.scrollToElement(productFourImgLocator);
      }
      
      public void scrollToViewFirstProductAddToCartButton_Search (){
          framework.scrollToElement(brandSectionLocator);
      }
      
      public void clickFirstProductAddToCartButton (){
          framework.hoverOnElement(firstProducthoverLocator);
          framework.hoverOnElement(firstProductAddToCartButtonLocator);
          framework.click(firstProductAddToCartButtonLocator);
      }
      public void clickSecondProductAddToCartButton (){
          framework.hoverOnElement(secondProducthoverLocator);
          framework.hoverOnElement(secondProductAddToCartButtonLocator);
          framework.click(secondProductAddToCartButtonLocator);
      }
      
      public String getRecommendedItemsText(){
          return framework.getText(recommendedItemsTextLocator);
      }
      public void clickProduct5InRecommendedProductAddToCartButton(){
          framework.click(product5RecommendedProductAddToCartButtonLocator);
      }
      public void scrollToViewRecommendedItemsSection(){
          framework.scrollToElement(subscriptionTextLocator);
      }
      
      public void scrollToViewRecommendedItemsText(){
          framework.scrollToElement(recommendedItemsSectionLocator);
      }
      
      public void clickWomenCategory(){
          framework.click(womenCategoryLocator);
      }
      public void clickDressCategory(){
          framework.click(dressCategoryLocator);
      }
      public boolean isCategorySectionVisible(){
          return framework.isDisplayed(categorySectionLocator);
      }
      public void scrollToViewCategorySection(){
          framework.scrollToElement(viewProduct1ButtonLocator);
      }
      
      public void scrollToNav(){
          framework.scrollToElement(navLocator);
      }
       
  
}
