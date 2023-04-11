package automation.tests;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.Test;

import automation.pages.GooglePage;
import automation.utils.BaseTest;

public class TC03_Google_Test_Cases extends BaseTest{
    
    private GooglePage googlePage;
    
    @Test
    public void verifyGoogleImagesLinkDisplayed() throws IOException {
        googlePage = new GooglePage(driver);
        googlePage.navigateToGoogle();
        Assert.assertTrue(googlePage.isImageLinkPresent());
    }
    
    @Test
    public void verifyGmailLinkDisplayed() throws IOException {
        googlePage = new GooglePage(driver);
        googlePage.navigateToGoogle();
        Assert.assertTrue(googlePage.isGmailLinkPresent());
    }
    
    
    

}
