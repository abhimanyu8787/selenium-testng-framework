package automation.tests;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.Test;

import automation.pages.DashboardPage;
import automation.pages.LoginPage;
import automation.utils.BaseTest;

public class TC01_Validate_Login_Functionality_Orange_HRM extends BaseTest{
    
    LoginPage loginPage;
    DashboardPage dashboardPage;
    
    @Test
    public void validateSuccessScenario() throws Exception {
        loginPage = new LoginPage(driver);
        loginPage.navigateToLoginPage();
        dashboardPage = loginPage.Login("Admin", "admin123");
        Assert.assertEquals(dashboardPage.getPageTitle(), "Dashboard");
    }
    
    @Test
    public void validateFailureScenarioWrongPassword() throws Exception {
        loginPage = new LoginPage(driver);
        loginPage.navigateToLoginPage();
        loginPage.enterUsername("Admin");
        loginPage.enterPassword("admin");
        loginPage.clickLoginButton();
        Assert.assertEquals(loginPage.getLoginErrorMessage(), "Invalid credentials");
    }

}
