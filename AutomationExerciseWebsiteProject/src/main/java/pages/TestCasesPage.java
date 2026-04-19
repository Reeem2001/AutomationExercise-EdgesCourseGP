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
public class TestCasesPage {
    
    // Locators
    private final By testCasesTextLocator = By.cssSelector("h2.title");
    private final SelenuimFramework framework;

    public TestCasesPage(SelenuimFramework framework) {
        this.framework = framework;
    }
    
    
    // Actions
    public String getTestCasesPageText(){
        return framework.getText(testCasesTextLocator);
    }
}
