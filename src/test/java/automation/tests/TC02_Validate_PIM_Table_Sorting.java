package automation.tests;

import java.util.Arrays;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import automation.pages.DashboardPage;
import automation.pages.LoginPage;
import automation.pages.PIMPage;
import automation.utils.BaseTest;

public class TC02_Validate_PIM_Table_Sorting extends BaseTest{
    
    LoginPage loginPage;
    DashboardPage dashboardPage;
    PIMPage pimPage;
    
    @Test
    public void verifyEmployeeTableColumnNames() throws Exception {
        loginPage = new LoginPage(driver);
        loginPage.navigateToLoginPage();
        dashboardPage = loginPage.Login("Admin", "admin123");
        pimPage = dashboardPage.clickPIM();
        List<String> columnNames = pimPage.getPIMTableColumnNames();
        columnNames.remove(0);
        String[] expectedColumnNames = {"Id","First (& Middle) Name","Last Name","Job Title","Employment Status","Sub Unit","Supervisor","Actions"};
        for(int i = 0; i < expectedColumnNames.length; i++) {
            Assert.assertEquals(columnNames.get(i).trim(),expectedColumnNames[i],"Column Names Not As Expected");
        }
    }
    
    @Test
    public void verifySortingOnLastNameColumn() throws Exception {
        loginPage = new LoginPage(driver);
        loginPage.navigateToLoginPage();
        dashboardPage = loginPage.Login("Admin", "admin123");
        pimPage = dashboardPage.clickPIM();
        List<String> tableRowsText = pimPage.getPIMTableData();
    }

}
