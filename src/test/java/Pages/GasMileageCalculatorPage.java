package Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class GasMileageCalculatorPage {

	public GasMileageCalculatorPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}
	
	 @FindBy(css="#uscodreading")
	  public WebElement CurrentOdometer;
	 
	 @FindBy(css="#uspodreading")
	 public WebElement PreviousOdometer;
	 
	 @FindBy(css="usgasputin")
	 public WebElement gas;
	 
	 @FindBy(css="#standard > tbody > tr:nth-child(5) > td > input[type=\"image\"]")
	 public WebElement Calculate;
	 
	 @FindBy(xpath="//b[contains(.,'miles per gallon')]")
	 public WebElement result;
}
