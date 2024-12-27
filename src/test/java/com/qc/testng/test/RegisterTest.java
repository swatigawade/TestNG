package com.qc.testng.test;

import java.io.IOException;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

public class RegisterTest extends BaseIntegration {
	
	@BeforeSuite
	public void setup() throws IOException {
		doSetup();
	}
	@BeforeMethod
	public void LocateRegisterElement() {
		checkCurrentPage(); 
		rName = driver.findElement(By.id("name"));
		rName.clear();
		rMobile = driver.findElement(By.id("mobile"));
		rMobile.clear();
		rEmail = driver.findElement(By.id("email"));
		rEmail.clear();
		rPass = driver.findElement(By.id("password"));
		rPass.clear();
		rSubmit = driver.findElement(By.tagName("button"));
		
	}
	@Test(dataProvider = "registerData")
	public void doRegister(String testName,String uName,String uMobile,String uEmail,String uPass) {
	    tName = testName;
		rName.sendKeys(uName);
		rMobile.sendKeys(uMobile);
		rEmail.sendKeys(uEmail);
		rPass.sendKeys(uPass);
		rSubmit.click();
	}
	
	@AfterMethod
	public void doAssertion() throws InterruptedException {
		Thread.sleep(1000);
		String actResult,expResult;
		if(tName.equals("All are valid")) {
			actResult = handleAlert();
			expResult ="User registered successfully.";
		}else {
			actResult = driver.getTitle();
			expResult = "Queue Codes | Registration Page";
			
			}
		Assert.assertEquals(expResult, actResult);
	}
	
	public void checkCurrentPage() {
		String actTitle = driver.getTitle();
		if(actTitle.equals("Queue Codes | Log in")) {
			rRegisterPage = driver.findElement(By.linkText("Register a new membership"));
			rRegisterPage.click();
		}
	}
	
	public String handleAlert() {
		Alert alt = driver.switchTo().alert();
		String altText = alt.getText();
		alt.accept();
		return altText;
	}
	@AfterSuite
	public void tearDown() {
		driver.close();
	}

}
