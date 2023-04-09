package automation.genericfunctions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;


public class Utility {
    
    public static Map<String, By> mapFromExcel = new HashMap();
    public static String objectLocatorFile = "src\\test\\resources\\objectLocators\\ObjectLocators.xlsx";
    
    public static String getGlobalProperties(String key) throws IOException {
        FileReader reader = new FileReader("GlobalSettings.properties");
        Properties globalProperties = new Properties();
        globalProperties.load(reader);
        return globalProperties.getProperty(key);
    }
    
    public static void fetchLocator() {
        try {
            File testFile = new File(objectLocatorFile);
            FileInputStream ios = new FileInputStream(testFile);
            XSSFWorkbook workbook = new XSSFWorkbook(ios);
            for(int i = 0; i< workbook.getNumberOfSheets() - 2; i++) {
                XSSFSheet sheet = workbook.getSheetAt(i);
                String locator = null;
                String value = null;
                String param = null;
                
                Iterator<Row> rowIterator = sheet.iterator();
                while(rowIterator.hasNext()) {
                    Row row = (Row) rowIterator.next();
                    param = row.getCell(0).getStringCellValue();
                    locator = row.getCell(1).getStringCellValue();
                    value = row.getCell(2).getStringCellValue();
                    mapFromExcel.put(param, get_element_object(locator, value));
                }
            }
            workbook.close();
            ios.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static WebElement readFromExcel(String param, WebDriver driver) throws Exception {
        try {
            return driver.findElement((By) mapFromExcel.get(param));
        } catch (Exception e) {
            throw new Exception(param + " not found");
        }
    }
    
    public static List<WebElement> readListFromExcel(String param, WebDriver driver) throws Exception{
        try {
            return driver.findElements((By) mapFromExcel.get(param));
        } catch (Exception e) {
            throw new Exception(param + " not found");
        }
    }
    
    public static By get_element_object(String locName, String locValue) {
        if(locName.equalsIgnoreCase("XPATH")) {
            return By.xpath(locValue);
        }
        if(locName.equalsIgnoreCase("ID")) {
            return By.id(locValue);
        }
        if(locName.equalsIgnoreCase("CLASSNAME")) {
            return By.className(locValue);
        }
        if(locName.equalsIgnoreCase("NAME")) {
            return By.name(locValue);
        }
        if(locName.equalsIgnoreCase("CSS")) {
            return By.cssSelector(locValue);
        }
        if(locName.equalsIgnoreCase("LINK")) {
            return By.linkText(locValue);
        }
        if(locName.equalsIgnoreCase("PARTIALLINK")) {
            return By.partialLinkText(locValue);
        }
        if(!locValue.isEmpty()) {
            By.cssSelector("*[" + locName + "='" + locValue + "']");
        }
        return null;
    }
    

    public List<HashMap<String, String>> getJsonDataToMap(String filePath) throws IOException {
        String jsonContent = FileUtils.readFileToString(new File(filePath), StandardCharsets.UTF_8);

        ObjectMapper mapper = new ObjectMapper();
        List<HashMap<String, String>> data = mapper.readValue(jsonContent,
                new TypeReference<List<HashMap<String, String>>>() {
                });
        return data;
    }

}
