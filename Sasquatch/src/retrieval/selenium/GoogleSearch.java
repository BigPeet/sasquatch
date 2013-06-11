package retrieval.selenium;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.gargoylesoftware.htmlunit.BrowserVersion;

/**
 * Search Google example.
 *
 * @author Rahul
 */
public class GoogleSearch {
    static WebDriver driver;
    static Wait<WebDriver> wait;
    static int TABLE_SIZE = 25;
    private static String baseURL = "http://sourceforge.net";
    private static String firstMail = "//div[@id='doc4']/form/table[2]/tbody/tr/td/a";
    private static String PATH = "res/selenium/test.html";

    public static void main(String[] args) {
        driver = new HtmlUnitDriver(BrowserVersion.FIREFOX_17);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.get(baseURL + "/mailarchive/forum.php?forum_name=dom4j-dev");
        //String[] pages = getPagesViaTime("dom4j-dev", 2001, 2012);
        driver.get(buildMonthLink("dom4j-dev", 2001, 10));
        String htmlContent = driver.getPageSource();
        driver.close();
        System.out.println(htmlContent);
//        File f = new File(PATH);
//        try {
//			BufferedWriter writer = new BufferedWriter(new FileWriter(f));
//			writer.write(htmlContent);
//			writer.close();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
        
    }
    
    @Deprecated
    private static String[] getPagesViaTable() {
    	ArrayList<String> pages = new ArrayList<String>();
    	driver.findElement(By.xpath(firstMail)).click();
    	for (int y = 1; y < 13; y++) {
    		for (int x = 1; x < 13; x++) {
    			String selector = buildTableString(y, x);
    			System.out.println(selector);
				driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
				WebElement e = null;
    			try {
    				e = driver.findElement(By.xpath(selector));
    			} catch (NoSuchElementException ex) {
    				e = null;
    			}
    			if (e != null) {
    				e.click();
    				pages.add(driver.getPageSource());
    			}
    		}
    	}
    	return pages.toArray(new String[pages.size()]);
    }
    
    private static String[] getPagesViaTime(String name, int start, int end) {
    	//Uses the links
    	ArrayList<String> pages = new ArrayList<String>();
    	for (int year = start; year <= end; year++) {
    		for (int month = 1; month < 13; month++) {
    			String link = buildMonthLink(name, year, month);
    			driver.get(link);
    			pages.add(driver.getPageSource());
    		}
    	}
    	
    	return pages.toArray(new String[pages.size()]);
    }
    
    private static String buildMonthLink(String name, int year, int month) {
    	String dMonth = String.format("%02d", month);
    	return baseURL + "/mailarchive/forum.php?forum_name=" + name + "&max_rows=100&style=nested&viewmonth=" + year + dMonth;
    }
    
    //Does not work with a HTMLUnitDriver!
    private static String buildTableString(int row, int col) {
    	StringBuilder builder = new StringBuilder();
		builder.append("//div[@id='doc4']/table/tbody/tr/td/table/tbody/tr");
		if (row != 1) {
			builder.append("[" + row + "]");
		}
		builder.append("/td");
		if (col != 1) {
			builder.append("[" + col + "]");
		}
		builder.append("/a");
		return builder.toString();
    }
}
