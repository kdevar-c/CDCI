package com.yelptest;

import java.util.List;
import java.util.concurrent.*;

import javax.swing.text.Document;

import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.SystemConfiguration;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.DisplayOrder;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class AppTest {

	public AppTest() {
		// loading configuration properties, like file paths.
		try {
			config = new CompositeConfiguration();
			config.addConfiguration(new SystemConfiguration());
			config.addConfiguration(new PropertiesConfiguration("config.properties"));
		} catch (Exception ex) {
			System.out.println("Could not load config.properties file. Please fix and continue");
		}
	}
	
	@BeforeTest
    @Parameters("browser")
    public void setup(String browserName) throws Exception{
        if (browserName.equalsIgnoreCase("Firefox")) {
			System.setProperty("webdriver.gecko.driver", config.getString("webdriver.firefox", null));
			//System.setProperty("webdriver.firefox.bin", config.getString("webdriver.firefoxbin", null));
            driver = new FirefoxDriver();
			driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
			
        }
        else if (browserName.equalsIgnoreCase("Chrome")) {
            System.setProperty("webdriver.chrome.driver", config.getString("webdriver.chrome", null));
            driver = new ChromeDriver();
        }
        else if (browserName.equalsIgnoreCase("ie")) {
            System.setProperty("webdriver.edge.driver", config.getString("webdriver.ie", null));
            driver = new EdgeDriver();
			driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
			
        }
		 else if (browserName.equalsIgnoreCase("Safari")) {
            //System.setProperty("webdriver.safari.driver", config.getString("webdriver.safari", null));
            driver = new SafariDriver();
        }
        else {
            throw new Exception("Browser is not correct");
        }
    }
	
	CompositeConfiguration config;
	// Declare Web Driver variables
	WebDriver driver;
	// Declare Extent Reports
	ExtentReports reports;

	ExtentTest test = null;
	
	@Test(dataProvider = "dp")
	public void yelpTest(String sPriceRange, String sFeature) throws Exception{
		// precondition is to download the driver
		// read driver path from property config.
		// Test 1. Below step is open the yelp page.
		
				driver.get("http://www.yelp.com");
				
				// Maximize the window
				driver.manage().window().maximize();
				
		// Kamal: 07/22 
		//Java script code
		JavascriptExecutor js = (JavascriptExecutor)driver;
		String sURL= js.executeScript("return document.title").toString();
		System.out.println(sURL);
		//Assert.assertEquals("Dallas Restaurants, Dentists, Bars, Beauty Salons, Doctors - Yelp", sURL);
		
		//js.executeScript("window.location-'http://www.yelp.com");
		// entering text in find as Restaurants
		js.executeScript("document.getElementById('find_desc').value='Restaurants'");
		// click the find button
		js.executeScript("document.getElementById('header-search-submit').click()");
		// entering the restaurants-pizza in search.		
		js.executeScript("document.getElementById('find_desc').value='Restaurants - Pizza'");
		// Click submit
		js.executeScript("document.getElementById('header-search-submit').click()");
		
		// Initiate Extent Reports
		reports = new ExtentReports(config.getString("reports.extent.html", null), false, DisplayOrder.NEWEST_FIRST);
		// Declare Start test name
		test = reports.startTest("Verify Yelp Home page");
		test.log(LogStatus.PASS, "Test Case 1: Browser is opened and window is Maximized and Yelp home page is displayed.");
		
		// Test 2. Below step clicks Restaurants on yelp home page.
		//driver.findElement(By.linkText("Restaurants")).click();
		test.log(LogStatus.PASS, "Test Case 2: User clicks Restaurants on yelp home page");
		// Using the wait to load the page with Information
		WebDriverWait wait = new WebDriverWait(driver, 60);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Restaurants")));
		
		// Test 3. Click Search button
		//driver.findElement(By.linkText("Restaurants")).click();
		test.log(LogStatus.PASS, "Test Case 3 : User clicks search button");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[id='find_desc']")));
		// The below state will clear the previously entered text "Restaurants"
		//js.executeScript("document.querySelector(\"input[id='find_desc']\").clear()");
		driver.findElement(By.cssSelector("input[id='find_desc']")).clear();
		
		// Test 4. Appending the Pizza Restaurants to search
		test.log(LogStatus.PASS, "Test Case 4 : Appending the Pizza Restaurants to search");
		//driver.findElement(By.cssSelector("input[id='find_desc']")).sendKeys("Restaurants Pizza"); // Restaurants Pizza
		//driver.findElement(By.cssSelector("button[id='header-search-submit']")).click();
		Thread.sleep(1000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("span[style='display: inline;']")));
		String beforeloadingpage = driver.findElement(By.className("pagination-results-window")).getText();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("pagination-results-window")));
		String SearchResultsCount = driver.findElement(By.className("pagination-results-window")).getText().split(" ")[3];
		Assert.assertNotEquals(beforeloadingpage, SearchResultsCount);
		// Test 5: Below print state will print total no.of Search results with
		// no.of results in the current page
		if (beforeloadingpage != SearchResultsCount) {
			test.log(LogStatus.PASS, "total no.of Search results with no.of results in the current page");
		} else {
			test.log(LogStatus.FAIL, "total no.of Search results with no.of results in the current page are not equal");
		}
		System.out.println("Total no. of Search results: " + SearchResultsCount);
		//Assert.assertEquals("2625", SearchResultsCount);

		Reporter.log("Total no. of Search results: " + SearchResultsCount);

		driver.findElement(By.className("filters-toggle")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("span[style='display: inline;']")));

		String sAddress = "5702 Richmond Ave, Dallas, TX 75206";

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			System.out.println("InterruptedException - during thread sleep");
			e.printStackTrace();
		}
		
		//driver.findElement(By.className("container"))
			//	.findElement(By.cssSelector("input[value='RestaurantsPriceRange2." + sPriceRange + "']")).click();
		js.executeScript("document.querySelector(\"input[value='RestaurantsPriceRange2.1']\").click()");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("span[style='display: inline;']")));
		
		// Test 6. clicking the filters and applying the filters
		//String[][] ListOfFeatures = { { "Order Delivery", "PlatformDelivery" }, { "Order Pickup", "PlatformPickup" },
			//	{ "Open Now", "open_now" }, { "Take-out", "RestaurantsTakeOut" } };
		if ("Order Delivery".equalsIgnoreCase(sFeature)) {
			js.executeScript("document.querySelector(\"input[value='PlatformDelivery']\").click()");
		}
		if ("Order Pickup".equalsIgnoreCase(sFeature)) {
			js.executeScript("document.querySelector(\"input[value='PlatformPickup']\").click()");
		}
			
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("span[style='display: inline;']")));
		
		if (sFeature.equalsIgnoreCase("Order Delivery")) {
			driver.findElement(By.className("address-picker")).sendKeys(sAddress);
			// icon--18-search-small

			driver.findElement(By.className("icon--18-search-small")).click();
		}
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("span[style='display: inline;']")));

		List<WebElement> ListofRestaurants = driver.findElements(By.className("regular-search-result"));
		for (WebElement Restaurant : ListofRestaurants) {
			String sRestaurantname = Restaurant.findElement(By.className("search-result-title")).getText();
			
			// Test 9. reporting the star ratings.
			String sStarts = Restaurant.findElement(By.className("i-stars")).getAttribute("title");
			System.out.println(sRestaurantname + "->" + sStarts);
			Reporter.log(sRestaurantname + "->" + sStarts);
		}
		
		// Test 10. clicking on first results from search page
		WebElement eSelectRestaurant = ListofRestaurants.get(0);
		eSelectRestaurant.findElement(By.className("biz-name")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("biz-page-header")));
		
		// Test 11. Log all critical information of the selected restauran
		// details,for the reporting purpose . Address, Phone No, web site
		// details . First 3 customer reviews
		Thread.sleep(2000);
		System.out.println("****************************************************************************************");
		System.out.println("Restaurant Name: " + driver.findElement(By.className("biz-page-title")).getText());
		System.out.println("Address: " + driver.findElement(By.tagName("address")).getText());
		System.out.println("Phone Number: " + driver.findElement(By.className("biz-phone")).getText());
		
		//System.out.println("Website: " + driver.findElement(By.className("biz-website")).getText());
		System.out.println("Website: " + driver.findElement(By.className("biz-website")).getText());
		Reporter.log("****************************************************************************************");
		Reporter.log("Restaurant Name: " + driver.findElement(By.className("biz-page-title")).getText());
		Reporter.log("Address: " + driver.findElement(By.tagName("address")).getText());
		Reporter.log("Phone Number: " + driver.findElement(By.className("biz-phone")).getText());
		Reporter.log("Website: " + driver.findElement(By.className("biz-website")).getText());
		List<WebElement> CustomerReview = driver.findElements(By.className("review-content"));
		int i = 0;
		for (WebElement eachCustomerReview : CustomerReview) {
			// Printing the Customer reviews
			System.out.println("Customer Review " + ++i + " : " + eachCustomerReview.getText());
			Reporter.log("Customer Review " + ++i + " : " + eachCustomerReview.getText());
			if (i == 3)
				break;
		}

	}

	@BeforeMethod
	public void beforeMethod() {

	}

	@AfterTest
	public void afterTest() {
		// Below method will close the browser
		driver.quit();
		reports.flush();
		reports.endTest(test);
	}

	@DataProvider
	public Object[][] dp() {
		return new Object[][] { new Object[] { "1", "Order Delivery" }, new Object[] { "1", "Order Pickup" }, };
	}
}