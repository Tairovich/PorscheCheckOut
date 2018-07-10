package porscheCheckOut;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Porsche {
	//all static members
	static WebDriver driver;
	static long basePrice;
	static long miamiBlue;
	static long carreraWheel;
	static long powerSportSeat;
	static long interiorCarbonFiber;
	static long Doppelkupplung;
	static long ceramicCompositeBrake;
	static long priceForEquipment;
	static long deliveryProcessHandling;
	static long totalPrice;
	
	//This will convert String to long price
	public static long convertPriceDigit(String str) {
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
		WebDriverManager.firefoxdriver().setup();
//		1.Open browser
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		
//		2.Go to url “https://www.porsche.com/usa/modelstart/”
		driver.get("https://www.porsche.com/usa/modelstart/");
		
//		3.Select model 718		
		driver.findElement(By.xpath("//img[@title='Porsche - 718']")).click();
		
//		4.Remember the price of 718Cayman
		String mainPriceString = driver.findElement(By.xpath("//div[@class='m-14-model-price'][1]")).getText();
		long mainPrice = convertPriceDigit(mainPriceString);
		System.out.println("Price of 718 Cayman is " + mainPrice);
		
//		5.Click on Build & Price under 718Cayman
		driver.findElement(By.linkText("Build & Price")).click();
		Thread.sleep(2000);
		
		//Switch to pop up window
		String parent = driver.getWindowHandle();
		Set<String> windows = driver.getWindowHandles();
		for (String child : windows) {
			if(!parent.equals(child)) {
				driver.switchTo().window(child);
				break;
			}
		}
		
//		6.Verify that Base price displayed on the page is same as the price from step 4
		String basePriceString = driver.findElement(By.xpath("//*[@id=\"s_price\"]/div[1]/div[1]/div[2]")).getText();
		basePrice = convertPriceDigit(basePriceString);
		System.out.println("Base Price of Cayman 718 is "+basePrice);
		
		if(mainPrice == basePrice) {
			System.out.println("The base price is equal with the price of Step 4");
		}else {
			System.out.println("The base price is NOT equal with that of Step 4");
		}
		
//		7.Verify that Price for Equipment is 0		
										//static method
		long initialPriceOfEquipment = priceForEquipment();
		if(initialPriceOfEquipment == 0) {
			System.out.println("Price for Equipment is 0");
		}else {
			System.out.println("Price for Equipment is NOT 0");
		}
		
//		8.Verify that total price is the sum of base price + Delivery, Processing and Handling Fee		
		verifyTotalPrice_1();
		
//		9.Select color “Miami Blue”
		driver.findElement(By.xpath("//span[@style='background-color: rgb(0, 120, 138);']")).click();
		String miamiColorString = driver.findElement(By.xpath("//*[@id=\"s_price\"]/div[1]/div[2]/div[2]")).getText();
		miamiBlue = convertPriceDigit(miamiColorString);
		Thread.sleep(4000);
		
//   	10.Verify that Price for Equipment is Equal to Miami Blue price
		long nextPriceOfEquipment = priceForEquipment();
		if(miamiBlue == nextPriceOfEquipment) {
			System.out.println("Price for Equipment is Equal to Miami Blue price");
		}else {
			System.out.println("Price for Equipment is NOT Equal to Miami Blue price");
		}
		
//		11.Verify that total price is the sum of base price + Price for Equipment + Delivery, Processing and Handling Fee		
		verifyTotalPrice_1();
		
//		12.Select 20" Carrera Sport Wheels
		wheelPart();
		
//		13.Verify that Price for Equipment is the sum of Miami Blue price + 20" Carrera Sport Wheels
		if(carreraWheel + miamiBlue == priceForEquipment()) {
			System.out.println("Price for Equipment is the sum of Miami Blue price + 20 Carrera Sport Wheels"); 
		}else {
			System.out.println(" Price for Equipment is NOT the sum of Miami Blue price + 20\" Carrera Sport Wheels");
		}
		
//		14.Verify that total price is the sum of base price + Price for Equipment + Delivery, Processing and Handling Fee		
		verifyTotalPrice_1();
		
//		15.Select seats ‘Power Sport Seats (14-way) with Memory Package’
		seatsPart();
		
//		16.Verify that Price for Equipment is the sum of Miami Blue price + 20" Carrera Sport Wheels + Power Sport Seats (14-way) with Memory Package	
		if(carreraWheel + miamiBlue + powerSportSeat == priceForEquipment()) {
			System.out.println("Price for Equipment is the sum of Miami Blue price + 20 Carrera Sport Wheels + Power Sport Seats (14-way) with Memory Package");
			System.out.println("price for Equipment so far is " + priceForEquipment);
		}else {
			System.out.println(" Price for Equipment is NOT the sum of Miami Blue price + 20 Carrera Sport Wheels + Power Sport Seats (14-way) with Memory Package");
		}
//		17.Verify that total price is the sum of base price + Price for Equipment + Delivery, Processing and Handling Fee
		verifyTotalPrice_1();
		
//	    18.Click on Interior Carbon Fiber
//		19.Select Interior Trim in Carbon Fiber i.c.w. Standard Interior
		interiourCarbonFiberPart();

//		20. Verify that Price for Equipment is the sum of Miami Blue price + 20" Carrera Sport Wheels + 
//			Power Sport Seats (14-way) with Memory Package + Interior Trim in Carbon Fiber i.c.w. Standard Interior
		if(carreraWheel + miamiBlue + powerSportSeat + interiorCarbonFiber == priceForEquipment()) {
			System.out.println("Price for Equipment is the sum of Miami Blue price + 20 Carrera Sport Wheels + Power Sport Seats (14-way) with Memory Package "+
		"Interior Trim in Carbon Fiber i.c.w. Standard Interior");
			System.out.println("price for Equipment so far is " + priceForEquipment);
		}else {
			System.out.println(" Price for Equipment is NOT the sum of Miami Blue price + 20 Carrera Sport Wheels + Power Sport Seats (14-way) with Memory Package "+
		"Interior Trim in Carbon Fiber i.c.w. Standard Interior");
		}

//		21.Verify that total price is the sum of base price + Price for Equipment + Delivery, Processing and Handling Fee
		verifyTotalPrice_1();

//		22.Click on Performance
//		23.Select 7-speed Porsche Doppelkupplung (PDK)
//		24.Select Porsche Ceramic Composite Brakes (PCCB)		
		performancePart();
		
//		25.Verify that Price for Equipment is the sum of Miami Blue price + 20" Carrera Sport Wheels + 
//		Power Sport Seats (14-way) with Memory Package + Interior Trim in Carbon Fiber i.c.w. Standard Interior + 
//		7-speed Porsche Doppelkupplung (PDK) + Porsche Ceramic Composite Brakes (PCCB)
		if(carreraWheel + miamiBlue + powerSportSeat + interiorCarbonFiber + Doppelkupplung + ceramicCompositeBrake == priceForEquipment()) {
			System.out.println("Price for Equipment is the sum of Miami Blue price + 20 Carrera Sport Wheels + Power Sport Seats (14-way) with Memory Package "+
		"Interior Trim in Carbon Fiber i.c.w. Standard Interior +7-speed Porsche Doppelkupplung (PDK) + Porsche Ceramic Composite Brakes (PCCB)");
			System.out.println("price for Equipment so far is " + priceForEquipment);
		}else {
			System.out.println(" Price for Equipment is NOT the sum of Miami Blue price + 20 Carrera Sport Wheels + Power Sport Seats (14-way) with Memory Package "+
		"Interior Trim in Carbon Fiber i.c.w. Standard Interior + 7-speed Porsche Doppelkupplung (PDK) + Porsche Ceramic Composite Brakes (PCCB)");
		}
		
//		26.Verify that total price is the sum of base price + Price for Equipment + Delivery, Processing and Handling Fee		
		verifyTotalPrice_1();
		
	}
	
	//This method will update everytime actio is taken
	public static void verifyTotalPrice_1() {
		
		String DeliveryProcessHandlingString = driver.findElement(By.xpath("//*[@id=\"s_price\"]/div[1]/div[3]/div[2]")).getText();
		deliveryProcessHandling = convertPriceDigit(DeliveryProcessHandlingString);
		long totalPriceForEquipment = priceForEquipment();
		String totalPriceString = driver.findElement(By.xpath("//*[@id=\"s_price\"]/div[1]/div[4]/div[2]")).getText();
		totalPrice = convertPriceDigit(totalPriceString);
		
		if((deliveryProcessHandling + basePrice + totalPriceForEquipment) == totalPrice ) {
			System.out.println("Total Price is correct: " + totalPrice);
		
		}else {
			System.out.println("Total Price incorrect");
		}	
	}
	
	//This method will update everytime actio is taken
	public static long priceForEquipment() {
		String priceForEquipmentString = driver.findElement(By.xpath("//*[@id=\"s_price\"]/div[1]/div[2]/div[2]")).getText().replace("$","");
		priceForEquipment = convertPriceDigit(priceForEquipmentString);
		return priceForEquipment;
	}
	
	public static void  wheelPart() throws InterruptedException {
		driver.findElement(By.xpath("//*[@id=\"s_conf_submenu\"]/div/div")).click();
		Thread.sleep(3000);
		driver.findElement(By.id("submenu_exterieur_x_AA_submenu_x_IRA")).click();
		Thread.sleep(3000);
		driver.findElement(By.xpath("//*[@id=\"s_exterieur_x_M433\"]/span/span")).click();
		Thread.sleep(3000);
		String wheel = driver.findElement(By.xpath("//*[@id=\"s_exterieur_x_IRA\"]/div[2]/div[1]/div/div[2]")).getText();
		carreraWheel = convertPriceDigit(wheel);
	}
	
	public static void seatsPart() throws InterruptedException {
		driver.findElement(By.xpath("//*[@id=\"s_conf_submenu\"]/div/div")).click();
		Thread.sleep(3000);
		driver.findElement(By.xpath("//*[@id=\"submenu_interieur_x_AI_submenu_x_submenu_parent\"]")).click();
		Thread.sleep(3000);
		driver.findElement(By.xpath("//*[@id=\"submenu_interieur_x_AI_submenu_x_submenu_seats\"]")).click();
		Thread.sleep(3000);
		driver.findElement(By.xpath("//*[@id=\"seats_73\"]/div[2]")).click();
		String seat = driver.findElement(By.xpath("//*[@id=\"seats_73\"]/div[2]/div[1]/div[3]/div")).getText();
		powerSportSeat = convertPriceDigit(seat);
		
	}
	
	public static void interiourCarbonFiberPart() throws InterruptedException {
		driver.findElement(By.xpath("//*[@id=\"s_conf_submenu\"]/div/div")).click();
		Thread.sleep(3000);
		driver.findElement(By.xpath("//*[@id=\"submenu_individualization_x_individual_submenu_x_submenu_parent\"]")).click();
		Thread.sleep(3000);
		driver.findElement(By.xpath("//*[@id=\"submenu_individualization_x_individual_submenu_x_IIC\"]")).click();
		Thread.sleep(3000);
		driver.findElement(By.xpath("//*[@id=\"vs_table_IIC_x_PEKH_x_c01_PEKH\"]")).click();
		String interiorCarbonFiberString = driver.findElement(By.xpath("//*[@id=\"vs_table_IIC_x_PEKH\"]/div[1]/div[2]/div")).getText();
		interiorCarbonFiber = convertPriceDigit(interiorCarbonFiberString);
	}
	
	public static void performancePart() throws InterruptedException {
		driver.findElement(By.xpath("//*[@id=\"s_conf_submenu\"]/div/div")).click();
		Thread.sleep(3000);
		driver.findElement(By.xpath("//*[@id=\"submenu_individualization_x_individual_submenu_x_submenu_parent\"]")).click();
		Thread.sleep(3000);
		driver.findElement(By.xpath("//*[@id=\"submenu_individualization_x_individual_submenu_x_IMG\"]")).click();
		Thread.sleep(3000);
		driver.findElement(By.xpath("//*[@id=\"vs_table_IMG_x_M250_x_c11_M250\"]")).click();
		Thread.sleep(4000);
		String DoppelkupplungString = driver.findElement(By.xpath("//*[@id=\"vs_table_IMG_x_M250\"]/div[1]/div[2]/div")).getText();
		Doppelkupplung = convertPriceDigit(DoppelkupplungString);
		//This will scroll down the poge so ceramic brake can be chosen
		((JavascriptExecutor) driver).executeScript("window.scrollBy(0,600)");
		Thread.sleep(4000);
		driver.findElement(By.xpath("//*[@id=\"vs_table_IMG_x_M450_x_c94_M450_x_shorttext\"]")).click();
		String CeramicCompositeBrakeString = driver.findElement(By.xpath("//*[@id=\"vs_table_IMG_x_M450\"]/div[1]/div[2]/div")).getText();
		ceramicCompositeBrake = convertPriceDigit(CeramicCompositeBrakeString);
		
	}
}
