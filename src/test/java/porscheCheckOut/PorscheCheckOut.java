package porscheCheckOut;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class PorscheCheckOut {
	static WebDriver driver;
	static long miamiColor_long2;
	static long carreraWheel;
	static long equip;
	
	public static long priceConverted(String str) {
		char[] chars = str.toCharArray();
		String priceString ="";
		
		A:
		for (int i = 0; i < chars.length; i++) {
			if(chars[i] == '.') {
				break A;
			}
			
			if(Character.isDigit(chars[i])) {
				priceString += chars[i];
			}
		}
		long priceNumber = Long.parseLong(priceString);
		
		return priceNumber;
	}
	
	

	public static void main(String[] args) throws InterruptedException {
		
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		
		driver.get("https://www.porsche.com/usa/modelstart/");
		
		driver.findElement(By.xpath("//img[@title='Porsche - 718']")).click();
		
		String caymanPrice = driver.findElement(By.xpath("//div[@class='m-14-model-price'][1]")).getText();
		long price1 = priceConverted(caymanPrice);
		
		System.out.println("Price of 718 Cayman is " + price1);
		
//		driver.findElement(By.linkText("Build & Price")).click();
//		Thread.sleep(2000);
		
		driver.get("https://cc.porsche.com/icc_pcna/ccCall.do?rt=1530235089&screen=1366x768&userID=USM&lang=us&PARAM=parameter_internet_us&ORDERTYPE=982120&CNR=C02&MODELYEAR=2019&hookURL=https%3a%2f%2fwww.porsche.com%2fusa%2fmodelstart%2fall%2f");
		
		//switch to pop up window
		String parent = driver.getWindowHandle();
		Set<String> windows = driver.getWindowHandles();
		for (String child : windows) {
			if(!parent.equals(child)) {
				driver.switchTo().window(child);
				break;
			}
		}
		
		String basePrice = driver.findElement(By.xpath("//*[@id=\"s_price\"]/div[1]/div[1]/div[2]")).getText();
		System.out.println(basePrice);
		
		long price2 = priceConverted(basePrice);
		
		if(price1 == price2) {
			System.out.println("The base price is equal with the price of Step 4");
		}else {
			System.out.println("The base price is NOT equal with that of Step 4");
		}
		
		// 7. Verify that Price for Equipment is 0
//		String EqPrice = driver.findElement(By.xpath("//*[@id=\"s_price\"]/div[1]/div[2]/div[2]")).getText().replace("$","");
//		int equip = Integer.parseInt(EqPrice);
//		if(equip == 0) {
//			System.out.println("Price for Equipment is 0");
//		}else {
//			System.out.println("Price for Equipment is not 0");
//		}
		equipmentPrice();
		
//		8. Verify that total price is the sum of base price + Delivery, Processing and Handling Fee
		verifyTotalPrice_1();
		
//		9. Select color “Miami Blue”
		driver.findElement(By.xpath("//span[@style='background-color: rgb(0, 120, 138);']")).click();
			
//		10. Verify that Price for Equipment is Equal to Miami Blue price
		colorVerifier();
		
//		11. Verify that total price is the sum of base price + Price for Equipment + Delivery, Processing and Handling Fee
		
		totalPriceAfterChanged();
		
//		12. Select 20" Carrera Sport Wheels
		
		wheelPart();
		
//		13. Verify that Price for Equipment is the sum of Miami Blue price + 20" Carrera Sport Wheels	
		long p = priceConverted(     (driver.findElement(By.xpath("//*[@id=\"s_price\"]/div[1]/div[2]/div[2]")).getText())        );
		if(carreraWheel + miamiColor_long2 == p
				 	 
		
				) {
			System.out.println("Price for Equipment is the sum of Miami Blue price + 20\" Carrera Sport Wheels");
		}else {
			System.out.println("Price for Equipment is NOT the sum of Miami Blue price + 20\" Carrera Sport Wheels");
		}
		
	
		
		
	}
	
	public static void verifyTotalPrice_1() {
		
		String delProcessHandling = driver.findElement(By.xpath("//*[@id=\"s_price\"]/div[1]/div[3]/div[2]")).getText();
		long delProcesHandling_long = priceConverted(delProcessHandling);
		
		String basePriceString = driver.findElement(By.xpath("//*[@id=\"s_price\"]/div[1]/div[1]/div[2]")).getText();
		long basePrice_long = priceConverted(basePriceString);
		
		String totalPriceString = driver.findElement(By.xpath("//*[@id=\"s_price\"]/div[1]/div[4]/div[2]")).getText();
		long totalPrice_long = priceConverted(totalPriceString);
		
		if((delProcesHandling_long + basePrice_long) == totalPrice_long ) {
			System.out.println("Total Price is correct");
		}else {
			System.out.println("Total Price incorrect");
		}
	}
	public static void colorVerifier() {
		String PriceForEquipColor = driver.findElement(By.xpath("//*[@id=\"s_price\"]/div[1]/div[2]/div[2]")).getText();
		long miamiColor_long = priceConverted(PriceForEquipColor);
		
		String miamiColorBlue = driver.findElement(By.xpath("//*[@id=\"s_exterieur_x_IAF\"]/div[2]/div[1]/div/div[2]")).getText();
	     miamiColor_long2 = priceConverted(miamiColorBlue);
		
		if(miamiColor_long == miamiColor_long2) {
			System.out.println("Price for Equipment is Equal to Miami Blue price");
		}else {
			System.out.println("Price for Equipment is NOT Equal to Miami Blue price");
		}
	}
	
	public static void totalPriceAfterChanged() {
		String basePriceString = driver.findElement(By.xpath("//*[@id=\"s_price\"]/div[1]/div[1]/div[2]")).getText();
		long basePrice_long = priceConverted(basePriceString);
		
		String PriceForEquipColor = driver.findElement(By.xpath("//*[@id=\"s_price\"]/div[1]/div[2]/div[2]")).getText();
		long miamiColor_long = priceConverted(PriceForEquipColor);
		
		String delProcessHandling = driver.findElement(By.xpath("//*[@id=\"s_price\"]/div[1]/div[3]/div[2]")).getText();
		long delProcesHandling_long = priceConverted(delProcessHandling);
		
		String totalPriceString = driver.findElement(By.xpath("//*[@id=\"s_price\"]/div[1]/div[4]/div[2]")).getText();
		long totalPrice_long = priceConverted(totalPriceString);
		
		if (totalPriceChecking(basePrice_long, miamiColor_long2,delProcesHandling_long, totalPrice_long)){
			System.out.println("total price is the sum of base price + Price for Equipment + Delivery, Processing and Handling Fee");
			
		}else {
			System.out.println("total price is NOT the sum of base price + Price for Equipment + Delivery, Processing and Handling Fee");
			}
		
		}
		
		
		public static boolean totalPriceChecking(long l1, long l2, long l3, long sum) {
		
			return l1 + l2 + l3 == sum;
		}
			
		public static void  wheelPart() throws InterruptedException {
			driver.findElement(By.xpath("//*[@id=\"s_conf_submenu\"]/div/div")).click();
			Thread.sleep(3000);
			driver.findElement(By.id("submenu_exterieur_x_AA_submenu_x_IRA")).click();
			Thread.sleep(3000);
			driver.findElement(By.xpath("//*[@id=\"s_exterieur_x_M433\"]/span/span")).click();
			Thread.sleep(3000);
			String wheel = driver.findElement(By.xpath("//*[@id=\"s_exterieur_x_IRA\"]/div[2]/div[1]/div/div[2]")).getText();
			carreraWheel = priceConverted(wheel);
			
		}
		
		public static void equipmentPrice() {
			String EqPrice = driver.findElement(By.xpath("//*[@id=\"s_price\"]/div[1]/div[2]/div[2]")).getText().replace("$","");
			equip = Long.parseLong(EqPrice);
			if(equip == 0) {
				System.out.println("Price for Equipment is 0");
			}else {
				System.out.println("Price for Equipment is not 0");
			}
		}
		
}
