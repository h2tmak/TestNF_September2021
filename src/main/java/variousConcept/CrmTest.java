package variousConcept;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.TimeUnit;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class CrmTest {
	WebDriver driver;
	String browser;
	String url;
	@BeforeClass
	public void readConfig() {
		try {
			InputStream file=new FileInputStream("src\\main\\java\\config\\config.properties");
			Properties prop=new Properties();
			prop.load(file);
			browser=prop.getProperty("browser");
			System.out.println("Browser used :"+browser);
			url=prop.getProperty("url");
			
			
		}catch(IOException e) {
			e.printStackTrace();
		}
	}

	// Storing webElement with By class
	By userNameLocator = By.xpath("//input[@id='username']");
	By passwordLocator = By.xpath("//input[@id='password']");
	By loginLocator = By.xpath("//button[@name='login']");
	By dashboardLocator = By.xpath("//h2[text()=' Dashboard ']");
	By customersLocator = By.xpath("//span[text()='Customers']");
	By addCustomerLocator = By.xpath("//a[text()='Add Customer']");
	By addContactLocator = By.xpath("//div[@id=\"page-wrapper\"]/descendant::div[@class='ibox-title']/h5");
	By fullNameLocator = By.xpath("//input[@id='account']");
	By companyDropdownLocator = By.xpath("//select[@id='cid']");
	By emailLocator = By.xpath("//input[@id='email']");
	By phoneLocator = By.xpath("//input[@id='phone']");
	By addressLocator = By.xpath("//input[@id='address']");
	By cityLocator = By.xpath("//input[@id='city']");
	By stateLocator = By.xpath("//input[@id='state']");
	By zipLocator = By.xpath("//input[@id='zip']");
	By countryDropdownLocator = By.xpath("//select[@id='country']");
	By saveButtonLocator = By.xpath("//button[@id='submit']");
	By listCustomersLocator= By.xpath("//a[text()='List Customers']");
	By searchOptionLocator=By.xpath("//input[@id='foo_filter']");
	By colLocator=By.xpath("//div[@id='page-wrapper']/descendant::td[3]");
	

	// Login data
	String userName = "demo@techfios.com";
	String password = "abc123";

	// Test data
	String fullName = "Tech Mak";
	String companyName = "Techfios";
	String email = "qa@techfios.com";
	String phone = "402123";
	String address = "Dhanmondi";
	String city = "Dhaka";
	String state = "Nebraska";
	String zip = "24501";
	String countryName = "Switzerland";
	
	@BeforeMethod
	public void init() {
		// Launch Browser
		if(browser.equalsIgnoreCase("chrome")) {
			System.setProperty("webdriver.chrome.driver", "driver/chromedriver.exe");
			driver = new ChromeDriver();
		}else if(browser.equalsIgnoreCase("firefox")){
			System.setProperty("webdriver.gecko.driver", "driver\\geckodriver.exe");
			driver = new FirefoxDriver();
		}else {
			System.out.println("select chrome or firefox");
		}

	
		
		
		driver.get(url);
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

	}
	@Test
	public void crmTest() throws InterruptedException {
		driver.findElement(userNameLocator).sendKeys(userName);
		driver.findElement(passwordLocator).sendKeys(password);
		driver.findElement(loginLocator).click();

		// Validation of Dashboard page
		Thread.sleep(5000);
		String dashboardText = driver.findElement(dashboardLocator).getText();
		System.out.println(dashboardText);
		Assert.assertTrue( driver.findElement(dashboardLocator).isDisplayed(),"Wrong page");

		driver.findElement(customersLocator).click();
		driver.findElement(addCustomerLocator).click();

		// Validation of Add contact form
		//Thread.sleep(5000);
		
		waitForElement(driver,5,addContactLocator);
		String addContactText = driver.findElement(addContactLocator).getText();
		System.out.println(addContactText);
		Assert.assertEquals( "Add Contact", addContactText,"Wrong page!!!");
		
		//Generate random number
		Random rand=new Random();
		int extentionNumber=rand.nextInt(9999);

		driver.findElement(fullNameLocator).sendKeys(fullName+"_"+extentionNumber);

		// Company name drop down
		Select sel1 = new Select(driver.findElement(companyDropdownLocator));
		sel1.selectByVisibleText(companyName);

		driver.findElement(emailLocator).sendKeys(extentionNumber+"_"+email);
		driver.findElement(phoneLocator).sendKeys(phone+extentionNumber);
		driver.findElement(addressLocator).sendKeys(address);
		driver.findElement(cityLocator).sendKeys(city);
		driver.findElement(stateLocator).sendKeys(state);
		driver.findElement(zipLocator).sendKeys(zip);
		Thread.sleep(5000);

		// Country name drop down
		Select sel2 = new Select(driver.findElement(countryDropdownLocator));
		sel2.selectByVisibleText(countryName);
		
		
		driver.findElement(saveButtonLocator).click();
		
		Thread.sleep(5000);
		driver.findElement(listCustomersLocator).click();
		Thread.sleep(3000);
		String validationText=fullName+"_"+extentionNumber;
		driver.findElement(searchOptionLocator).sendKeys(validationText);
		String customerNameText=driver.findElement(colLocator).getText();
		Assert.assertEquals(validationText,customerNameText,"Account not created!!!");
		

	}
	private void waitForElement(WebDriver driver, int timeInSeconds, By locator) {
		WebDriverWait wait=new WebDriverWait(driver,timeInSeconds);
		wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		
	}

		
	
	


}
