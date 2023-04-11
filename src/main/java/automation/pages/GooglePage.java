package automation.pages;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import automation.genericfunctions.GenericMethods;
import automation.genericfunctions.Utility;

public class GooglePage extends GenericMethods{
    
    private WebDriver driver;
    
    @FindBy(xpath="//input[@name='q']")
    WebElement searchBox;
    
    @FindBy(xpath="//input[@name='btnK']")
    WebElement searchButton;
    
    @FindBy(xpath="//div[@id='result-stats']")
    WebElement numberOfResults;
    
    @FindBy(xpath="//a[text()='Gmail']")
    WebElement gmailLink;
    
    @FindBy(xpath="//a[text()='Images']")
    WebElement imagesLink;
    
    public GooglePage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    
    public void navigateToGoogle() throws IOException {
        navigateToUrl(Utility.getGlobalProperties("googleUrl"));
    }
    
    public void searchQuery(String query) throws Exception {
        enterText(searchBox, query);
    }
    
    public String numberOfSearchResults() throws Exception {
        String str = getElementText(numberOfResults);
        return str;
    }
    
    public boolean isGmailLinkPresent() {
        return isElementDisplayed(gmailLink);
    }
    
    public boolean isImageLinkPresent() {
        return isElementDisplayed(imagesLink);
    }

}
