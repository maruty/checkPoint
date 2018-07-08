package com.blog.marublo;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class AbstractPointController implements PointInterface{

	protected WebDriver driver;

	public AbstractPointController(){

		driver = new FirefoxDriver();
	}

	@Override
	public void execute() throws InterruptedException, IOException {


	}

	public void quitDriver() {
		driver.quit();
	}





}
