package automation.pages;

import java.io.IOException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import automation.genericfunctions.GenericMethods;
import automation.genericfunctions.Utility;

public class LoginPage extends GenericMethods{
    
    private WebDriver driver;
    
    @FindBy(xpath="//input[@name='username']")
    WebElement usernamebox;
    
    @FindBy(xpath="//input[@name='password']")
    WebElement passwordbox;
    
    @FindBy(xpath="//button[@type='submit']")
    WebElement loginButton;
    
    @FindBy(xpath="//p[@class='oxd-text oxd-text--p oxd-alert-content-text']")
    WebElement loginErrorMessage;
    
    public LoginPage(WebDriver driver){
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    
    public void navigateToLoginPage() throws IOException {
        navigateToUrl(Utility.getGlobalProperties("applicationUrl"));
    }
    
    public void enterUsername(String username) throws Exception {
        enterText(usernamebox, username);
    }
    
    public void enterPassword(String password) throws Exception {
        enterText(passwordbox, password);
    }
    
    public void clickLoginButton() throws Exception {
        click(loginButton);
    }
    
    public String getLoginErrorMessage() throws Exception {
        return getElementText(loginErrorMessage);
    }
    
    public DashboardPage Login(String username, String password) throws Exception {
        enterText(usernamebox, username);
        enterText(passwordbox, password);
        click(loginButton);
        return new DashboardPage(driver);
    }

}
