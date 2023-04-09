package automation.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import automation.genericfunctions.GenericMethods;


public class DashboardPage extends GenericMethods{

    private WebDriver driver;
    
    @FindBy(xpath="//h6[@class='oxd-text oxd-text--h6 oxd-topbar-header-breadcrumb-module']")
    WebElement pageTitle;
    
    @FindBy(xpath="//span[text()='PIM']")
    WebElement navMenuPIMLocator;

    public DashboardPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    
    public String getPageTitle() throws Exception {
        return getElementText(pageTitle);
    }
    
    public PIMPage clickPIM() throws Exception {
        click(navMenuPIMLocator);
        return new PIMPage(driver);
    }


}
