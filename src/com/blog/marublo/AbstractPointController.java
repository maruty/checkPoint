package com.blog.marublo;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class AbstractPointController implements PointInterface{

	WebDriver driver;

	public AbstractPointController(){
		driver = new FirefoxDriver();
	}

	@Override
	public void execute() throws InterruptedException {


	}



}
