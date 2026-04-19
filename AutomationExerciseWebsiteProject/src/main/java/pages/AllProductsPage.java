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
public class AllProductsPage {
    public final By allProductsTextLocator=By.cssSelector("h2.title");
    public final By viewProductButtonLocator=By.cssSelector("a[href=\"/product_details/1\"]");
    public final By scrollElementLocator=By.cssSelector("a[href=\"/brand_products/Biba\"]");
    public final By searchInputLocator=By.cssSelector("input#search_product");
    public final By searchButtonLocator=By.cssSelector("button#submit_search");
    public final By searchedProductsTextLocator=By.cssSelector("h2.title");
    public final By SearchResultLocator  = By.cssSelector("div.productinfo p");
    public final By productsListLocator  = By.cssSelector("div.productinfo");
    public final By brandsSectionLocator = By.cssSelector("div.brands_products");
    public final By brandPOLOButtonLocator = By.cssSelector("a[href=\"/brand_products/Polo\"]");
    private final By bibaBrandTextLocator = By.cssSelector("a[href=\"/brand_products/Biba\"]");
    public final By Locator = By.cssSelector("");
//    public final By Locator = By.cssSelector("");
    
    
//    public final By addToCartButtonLocator  = By.cssSelector("");
    private final SelenuimFramework framework;

    public AllProductsPage(SelenuimFramework framework) {
        this.framework = framework;
    }
    public void sendSearchedProductName(String productName){
        framework.sendKeys(searchInputLocator, productName);
    }
    public void clickSearchButton(){
        framework.click(searchButtonLocator);
    }
    public String getSearchedProductsText(){
        return framework.getText(searchedProductsTextLocator);
    }
    public String getAllProductsText(){
        return framework.getText(allProductsTextLocator);
    }
    public void scrollToView(){
        framework.scrollToElement(scrollElementLocator);
    }
    public void clickViewProductButton(){
//        framework.click(viewProductButtonLocator);
         WebElement e = framework.findElement(viewProductButtonLocator);
         System.out.println(e.getText());
         e.click();
    }
    public List<String> getSearchResultProducts() {
        List<WebElement> elements = framework.findElements(SearchResultLocator);
        return elements.stream()
                       .map(WebElement::getText)
                       .collect(Collectors.toList());
    }
    public List<WebElement> getAllProductElements() {
        return framework.findElements(productsListLocator); 
    }
    
    public void scrollToViewBrandsSection(){
        framework.scrollToElement(bibaBrandTextLocator);
    }
    
    public boolean isBrandsSectionVisible(){
        return framework.isDisplayed(brandsSectionLocator);
    }
    public void clickBrandPOLOButton(){
        framework.click(brandPOLOButtonLocator);
    }
    

    
}
