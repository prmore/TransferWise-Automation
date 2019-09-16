package com.transferwise.tests;

import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.transferwise.base.TestBase;
import com.transferwise.pages.ConverterPage;
import com.transferwise.util.TestUtil;

public class ConverterPageTest extends TestBase{

	ConverterPage convereterPage;
	static int converterTestCounter;

	@BeforeTest
	@Parameters("browser")
	public void strtup(@Optional("Chrome") String browser) {
		initialization(browser);
		convereterPage = new ConverterPage();
	}

	@Test(priority=1)
	public void verifyConverterPageTitle() {
		log.info("************Performing converter page title verification************");
		String actual = convereterPage.getConverterPageTItile();
		Assert.assertEquals(actual, "Transfer Money Online | Send Money Abroad with TransferWise");
		log.info("CONVERETER PAGE TITLE VERIFICATION IS SUCCESSFUL!!!");
	}

	@Test(priority=2, dataProvider="getTestData")
	public void validateConverterTest(String srNo, String amount, String source, String target, String transType) throws InterruptedException {

		converterTestCounter++;
		log.info("************Performing converter data verification for Test "+ converterTestCounter + "************");

		//Set source currency
		convereterPage.setSourceCurrency(source);

		//Set amount to convert
		convereterPage.setAmountToConvert(amount);

		//Select transfer type
		convereterPage.selectTransferType(transType);

		//Set target currency
		Thread.sleep(3000);
		convereterPage.setTargetCurrency(target);

		Thread.sleep(3000);
		
		//Get amount to be converted 
		double amountToConvert = convereterPage.getAmountToConvert();

		//Get guaranteed rate 
		double transferRate = convereterPage.getGuaranteedRate();

		//Get expected amount
		double expectedAmount = TestUtil.TransferWiseConverter(amountToConvert, transferRate);

		//Perform validations
		double actualAmount = convereterPage.getRecieveAmount();
		Assert.assertEquals(actualAmount, expectedAmount);
		log.info("CONVERETER VERIFICATION FOR TEST " + converterTestCounter + " IS SUCCESSFUL!!!");
	}

	@AfterTest
	public void doCleanUpStuff() throws InterruptedException {
		Thread.sleep(3000);
		driver.quit();
	}

	@AfterSuite
	public void endOfExecution() {
		log.info("************!!!END OF RESULTS!!!************");
	}

	@DataProvider
	public Object[][] getTestData(){

		Object[][] data = 
				TestUtil.getExcelData(projectPath + prop.getProperty("inputDataExcelPath"), prop.getProperty("ConverterPageInputDataSheet"));

		return data;
	}

}
