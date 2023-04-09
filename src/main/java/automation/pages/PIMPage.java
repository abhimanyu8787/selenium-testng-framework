package automation.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import automation.genericfunctions.GenericMethods;

public class PIMPage extends GenericMethods{
    
    private WebDriver driver;
    
    @FindBy(xpath = "//div[@class='oxd-table-row oxd-table-row--with-border oxd-table-row--clickable']")
    List<WebElement> employeeTableElements;
    
    @FindBy(xpath="//div[@class='oxd-table-header-cell oxd-padding-cell oxd-table-th']")
    List<WebElement> employeeTableHeader;
    
    public PIMPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    
    public List<String> getPIMTableColumnNames() throws Exception{
        List<String> columnNames = new ArrayList<String>();
        for(WebElement element: employeeTableHeader)
            columnNames.add(getElementText(element));
        return columnNames;
    }
    
    public List<String> getPIMTableData() throws Exception{
        List<String> rowText = new ArrayList<String>();
        for(WebElement element:employeeTableElements) {
            rowText.add(getElementText(element));
        }
        return rowText;
    }

}
