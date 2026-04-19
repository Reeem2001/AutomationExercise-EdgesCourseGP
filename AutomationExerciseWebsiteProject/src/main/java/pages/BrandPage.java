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
public class BrandPage {
    // Locators
    public final By brandHAndMButtonLocator = By.cssSelector("a[href=\"/brand_products/H&M\"]");
    public final By brandPageTitleTextLocator = By.cssSelector("div.features_items h2.title");
    public final By brandProductsSectionLocator = By.cssSelector("div.features_items");
    private final By bibaBrandTextLocator = By.cssSelector("a[href=\"/brand_products/Biba\"]");
    private final SelenuimFramework framework;

    // Actions 
    public BrandPage(SelenuimFramework framework) {
        this.framework = framework;
    }
    
    public void clickBrandHAndMButton(){
        framework.click(brandHAndMButtonLocator);
    }
    public String getBrandPageTitleTextLocator(){
        return framework.getText(brandPageTitleTextLocator);
    }
    
    public boolean isBrandProductsSectionVisible(){
        return framework.isDisplayed(brandProductsSectionLocator);
    }
     public void scrollToViewBrandsSection(){
        framework.scrollToElement(bibaBrandTextLocator);
    }
}
