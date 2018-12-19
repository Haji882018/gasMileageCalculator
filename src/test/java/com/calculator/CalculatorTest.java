package com.calculator;

import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import Pages.GasMileageCalculatorPage;
import io.github.bonigarcia.wdm.WebDriverManager;


public class CalculatorTest {

	Xls_Reader xl = new Xls_Reader("C:\\Users\\Gulnur Aslan\\Desktop\\selenium\\testing-maven\\Framework\\src\\test\\resources\\testData.xlsx");
	
	
	
	WebDriver driver = null;
	WebDriverWait wait = null;
	
	
	
	
	@BeforeTest
	public void setUp()  {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.get("https://www.calculator.net/gas-mileage-calculator.html");
		
	}

	@AfterTest
	public void closeUp() {
		driver.quit();
 }
	
	@Test
	public void calculateTest() {
		
		GasMileageCalculatorPage  calculatePage = new GasMileageCalculatorPage(driver);
		
//		double currentOr  = 3456;
//		double previousOr = 2345;
//		double gas=30;
		
		int rowcount = xl.getRowCount("data");
		
		for(int i=2; i<=rowcount;i++) {
			
			String run = xl.getCellData("Data", "Execute", i);
		      if(!run.equalsIgnoreCase("Y")) {
		        xl.setCellData("Data", "Status", i, "Skip Requested");
		        continue;
		      }
			
			String currentOr = xl.getCellData("data", "CurrentOR", i);
			String previousOr = xl.getCellData("data", "PreviousOR", i);
			String gas = xl.getCellData("data", "Gas", i);
			
			
			
		
		
		calculatePage.CurrentOdometer.clear();
		calculatePage.CurrentOdometer.sendKeys(currentOr);
		calculatePage.PreviousOdometer.clear();
		calculatePage.PreviousOdometer.sendKeys(previousOr);
		calculatePage.gas.clear();
		calculatePage.gas.sendKeys(gas);
		calculatePage.Calculate.click();
		
		String [] actualResult = calculatePage.result.getText().split(" ");
		xl.setCellData("data", "Actual", i, actualResult[0]);
		System.out.println(actualResult[0]);
		
		double co = Double.parseDouble(currentOr);
		double po = Double.parseDouble(previousOr);
		double gs = Double.parseDouble(gas);
		
		
		double expectedResult = (co-po)/gs;
		System.out.println(expectedResult);
		DecimalFormat df = new DecimalFormat("0.00");
		String expectedResult2 = String.valueOf(df.format(expectedResult));
		
		if(actualResult[0].equals(expectedResult2)) {
			xl.setCellData("data", "Status", i, "Pass");
		}else {
			xl.setCellData("data", "Status", i, "Fail");
		}
		xl.setCellData("Data", "Time", i, LocalDateTime.now().toString());
	}
	}
	
}

















