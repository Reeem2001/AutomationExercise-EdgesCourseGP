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
public class OrderPlacedPage {
    // Locators
    private final By downloadInvoiceButtonLocator = By.cssSelector("a.check_out"); 
    private final By continueButtonLocator = By.cssSelector("a[data-qa=\"continue-button\"]");
    private final SelenuimFramework framework;
    
    
      public OrderPlacedPage(SelenuimFramework framework) {
         this.framework = framework;
      }
      
      // Actions
      public void clickContinueButton(){
          framework.click(continueButtonLocator);
      }
      public void clickDownloadInvoiceButton(){
          framework.click(downloadInvoiceButtonLocator);
      }
}
