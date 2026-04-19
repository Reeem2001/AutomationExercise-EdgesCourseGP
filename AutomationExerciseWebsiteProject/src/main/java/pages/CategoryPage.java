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
public class CategoryPage {
    // Locators
    private final By categoryPageTitleTextLocator = By.cssSelector("div.features_items h2.title ");
    private final By tshirtsCategoryLocator = By.cssSelector("div#Men div.panel-body ul li a[href=\"/category_products/3\"]");
    private final By menCategoryLocator = By.cssSelector("a[href=\"#Men\"]");
    private final SelenuimFramework framework;

    // Actions 
    public CategoryPage(SelenuimFramework framework) {
        this.framework = framework;
    }
    public String getCategoryPageTitleText(){
        return framework.getText(categoryPageTitleTextLocator);
    }
    
    public String getPageTitle(){
        return framework.getPageTitle();
    }
    public void clickMenCategory(){
        framework.click(menCategoryLocator);
    }
    public void clickTshirtsCategory(){
        framework.click(tshirtsCategoryLocator);
    }
}
