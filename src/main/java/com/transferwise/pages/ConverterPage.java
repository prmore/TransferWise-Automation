package com.transferwise.pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.transferwise.base.TestBase;

public class ConverterPage extends TestBase{

	WebDriverWait wait;
	JavascriptExecutor js;
	Actions actions;

	//Page Factory - Object Repository
	@FindBy(id="tw-calculator-source")
	WebElement amountToSend;

	@FindBy(id="tw-calculator-target")
	WebElement amountToRecieve;

	@FindBy(xpath = "//li[1]//span[@class='tw-calculator-breakdown-item__left']")
	WebElement transferFee;

	@FindBy(xpath = "//li[1]//span[@class='tw-calculator-breakdown-item__right']")
	WebElement transferType;

	@FindBy(xpath = "//span[text()='Fast transfer']")
	WebElement fastTransfer;

	@FindBy(xpath = "//span[text()='Low cost transfer']")
	WebElement lowCostTransfer;

	@FindBy(xpath = "//span[text()='Easy transfer']")
	WebElement easyTransfer;

	@FindBy(xpath = "//li[2]//span[@class='tw-calculator-breakdown-item__left']")
	WebElement amountToConvert;

	@FindBy(xpath = "//li[3]//span[@class='tw-calculator-breakdown-item__left']")
	WebElement guaranteedRate;

	@FindBy(xpath = "//input[@id='tw-calculator-source']//following-sibling::span[contains(@class,'amount-currency-select-btn')]")
	WebElement sourceCurrency;

	@FindBy(xpath = "//input[@id='tw-calculator-target']//following-sibling::span[contains(@class,'amount-currency-select-btn')]")
	WebElement targetCurrency;
	
	@FindBy(xpath="//input[@placeholder='Type a currency / country']")
	WebElement currencyToSelect;

	public ConverterPage() {
		log.debug("Initializing PageFactory elements for ConverterPage");

		//Initialize Page factory elements
		PageFactory.initElements(driver, this);

		//Define wait object
		wait = new WebDriverWait(driver, 60);
		
		//Define actions class object - Added to click on target currency as simple click is not working on IE
		actions = new Actions(driver);
	}

	//Set source currency
	public void setSourceCurrency(String currency) {
		log.debug("Set source currency to: " + currency);
		wait.until(ExpectedConditions.visibilityOf(sourceCurrency));
		//sourceCurrency.click();
		actions.moveToElement(sourceCurrency).click().build().perform();
		
		currencyToSelect.sendKeys(currency);
		currencyToSelect.sendKeys(Keys.RETURN);
	}

	//Set target currency
	public void setTargetCurrency(String currency) {
		log.debug("Set target currency to: " + currency);
		wait.until(ExpectedConditions.visibilityOf(targetCurrency));
		//targetCurrency.click();
		actions.moveToElement(targetCurrency).click().build().perform();
		
		currencyToSelect.sendKeys(currency);
		currencyToSelect.sendKeys(Keys.RETURN);
	}

	//Set amount to transfer
	public void setAmountToConvert(String amount) {
		log.debug("Set amount to convert to: " + amount);
		wait.until(ExpectedConditions.visibilityOf(amountToSend));
		amountToSend.sendKeys(Keys.CONTROL + "a");
		amountToSend.sendKeys(Keys.DELETE);
		amountToSend.sendKeys(amount);
	}

	//select transfer type
	public void selectTransferType(String transType) {
		log.debug("Select transfer type as: " + transType);
		//Click on transfer type button
		wait.until(ExpectedConditions.visibilityOf(transferType));
		transferType.click();

		//Define JavaScript executor 
		js = (JavascriptExecutor)driver;
		
		if(transType.startsWith("Fast")) {
			js.executeScript("arguments[0].click();", fastTransfer);
		}else if(transType.startsWith("Low")) {
			js.executeScript("arguments[0].click();", lowCostTransfer);
		}else if(transType.startsWith("Easy")) {
			js.executeScript("arguments[0].click();", easyTransfer);
		}
	}

	public double getRecieveAmount() {
		return Double.parseDouble(amountToRecieve.getAttribute("value").replaceAll("[^0-9?!\\.]",""));
	}

	public double getTransferFee() {
		return Double.parseDouble(transferFee.getText().replaceAll("[^0-9?!\\.]",""));
	}

	public double getAmountToConvert() {
		return Double.parseDouble(amountToConvert.getText().replaceAll("[^0-9?!\\.]",""));
	}

	public double getGuaranteedRate() {
		return Double.parseDouble(guaranteedRate.getText());
	}

	public String getConverterPageTItile() {
		return driver.getTitle();
	}
}
