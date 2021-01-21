import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;

import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

public class automateApplication {
	public static void main(String args[]) throws InterruptedException, IOException {
		//Runtime.getRuntime().exec("taskkill /F /IM chrome.exe");
		//Runtime.getRuntime().exec("taskkill /F /IM chromedriver.exe");
		String filePath=System.getProperty("user.dir") + "\\src\\drivers\\chromedriver.exe";
		System.out.println(filePath);
		System.setProperty("webdriver.chrome.driver",filePath);
		WebDriver driver=new ChromeDriver();
		driver.manage().window().maximize();
		//To launch URL
		driver.get("http://automationpractice.com/index.php");
		System.out.println("Successfully launched application URL");
		//Login to application by entering username and password
		WebElement signIn=driver.findElement(By.xpath("//a[@class='login']"));
		signIn.click();
		driver.findElement(By.id("email")).sendKeys("usharanip5431@gmail.com");
		driver.findElement(By.id("passwd")).sendKeys("Usharani");
		driver.findElement(By.id("SubmitLogin")).click();
		System.out.println("Logged in with my credentials");
		//Navigate to 'My Addresses' and add a new address
		driver.findElement(By.xpath("//span[text()='My addresses']")).click();
		driver.findElement(By.xpath("//span[text()='Add a new address']")).click();
		System.out.println("Adding New Address");
		//Fill all the information
		driver.findElement(By.id("company")).sendKeys("ATC");
		driver.findElement(By.xpath("//input[@id='address1']")).sendKeys("ParkStreet");
		driver.findElement(By.xpath("//input[@id='address2']")).sendKeys("SouthBay");
		driver.findElement(By.id("city")).sendKeys("New York");
		Select state=new Select(driver.findElement(By.id("id_state")));
		state.selectByVisibleText("Alaska");
		driver.findElement(By.id("postcode")).sendKeys("24235");
		Select country=new Select(driver.findElement(By.id("id_country")));
		country.selectByValue("21");
		driver.findElement(By.id("phone")).sendKeys("2324573647");
		driver.findElement(By.id("phone_mobile")).sendKeys("2324545647");
		driver.findElement(By.id("other")).sendKeys("Near Central Perk");
		driver.findElement(By.id("alias")).clear();
		driver.findElement(By.id("alias")).sendKeys("Home");
		System.out.println("Filled all the information");
		//Click Save
		driver.findElement(By.id("submitAddress")).click();
		System.out.println("Clicked on save address");
		Thread.sleep(5000);
		try {
		//Verifiying whether address is saved or not
		String SavedAddress=driver.findElement(By.xpath("(//*[@class='page-subheading'])[2]")).getText();
		if(SavedAddress.equalsIgnoreCase("Home"))
			System.out.println("Successfully saved new address");
		else
			System.out.println("Please check whether new address is added or not");
		}catch(Exception e) {
			System.out.println("Address with alias name Home is already there");
		}
		//Click on women then summer dresses
		WebElement women=driver.findElement(By.xpath("//*[@id='block_top_menu']/ul/li[1]/a"));
		JavascriptExecutor ex=(JavascriptExecutor)driver;
		women.click();
		WebElement summerdresses=driver.findElement(By.xpath("//*[@id='block_top_menu']/ul/li[1]/ul/li[2]/ul/li[3]/a"));
		ex.executeScript("arguments[0].click();", summerdresses);
		//Changing items to list view
		driver.findElement(By.xpath("//*[@id='list']")).click();
		for(int i=1;i<4;i++) {
			//Click on the item to view
			String productPath="(//div[@class='product-image-container'])"+"["+i+"]/a[2]/span";
			WebElement product=driver.findElement(By.xpath(productPath));
			ex.executeScript("arguments[0].click();", product);
			//Increase quantity to 5
			driver.findElement(By.id("quantity_wanted")).clear();
			driver.findElement(By.id("quantity_wanted")).sendKeys("5");
			//Change size to L
			Select size=new Select(driver.findElement(By.xpath("//*[@name='group_1']")));
			size.selectByVisibleText("L");
			//Select any color
			if(i==1)
				driver.findElement(By.name("Blue")).click();
			else if(i==2)
				driver.findElement(By.name("Yellow")).click();
			else
				driver.findElement(By.name("Green")).click();
			//Add to cart
			driver.findElement(By.xpath("//span[text()='Add to cart']")).click();
			WebElement ele;
			//click on continue shopping for 2 times
			if(i!=3) {
				Thread.sleep(5000);
				ele=driver.findElement(By.xpath("//span[@title='Continue shopping']"));
				ex.executeScript("arguments[0].click();",ele);
				Thread.sleep(5000);
				driver.findElement(By.xpath("(//*[@title='Summer Dresses'])[3]")).click();
			}else {
				//click on proceed to checkout after adding 2 products
				ele=driver.findElement(By.xpath("//a[@title='Proceed to checkout']"));
				ex.executeScript("arguments[0].click();",ele);
			}
			Thread.sleep(3000);
		}
		//Click on proceed to checkout
		driver.findElement(By.xpath("//*[@class='cart_navigation clearfix']/a[@title='Proceed to checkout']")).click();
		//Click on proceed to checkout in address bar
		driver.findElement(By.xpath("//*[@name='processAddress']")).click();
		//Agree for terms and conditions
		driver.findElement(By.id("cgv")).click();
		//Click on proceed to checkout in carrier bar
		driver.findElement(By.name("processCarrier")).click();
		//Select payment method as cheque
		driver.findElement(By.xpath("//a[@class='cheque']")).click();
		//Click on confirm order
		driver.findElement(By.xpath("//button[@type=\"submit\"]//span[text()='I confirm my order']")).click();
		//Move to my profile
		driver.findElement(By.xpath("//*[@class='account']")).click();
		//Click on order history and details
		driver.findElement(By.xpath("//span[text()='Order history and details']")).click();
		//Taking screenshot of order history
		String screenshotFolder=System.getProperty("user.dir") + "\\src\\Screenshots\\Order_screenshot.png";
		ChromeOptions options=new ChromeOptions();
		options.addArguments("start-maximized");
		options.addArguments("disable-infobars");
		options.addArguments("--disable-extension");
		Screenshot myScreenshot=new AShot().shootingStrategy(ShootingStrategies.viewportPasting(100)).takeScreenshot(driver);
		ImageIO.write(myScreenshot.getImage(), "PNG", new File(screenshotFolder));
		System.out.println("Successfully took Screenshot");
		// Sign out from app
		driver.findElement(By.xpath("//a[@class='logout']")).click();
		//driver.quit();\
		driver.close();
		System.out.println("Execution Completed");
	}

}
