package com.swaglabchallenge;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class codingChallenge {

	public static void main(String[] args) {

		ArrayList<Double> prices = new ArrayList<Double>();
		String xpath = "//div[@class='inventory_list']//descendant::div[@class='inventory_item_price']";

		// driver initialization
		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.get("https://www.saucedemo.com/");

		driver.findElement(By.xpath("//input[@id='user-name']")).sendKeys("standard_user");
		driver.findElement(By.xpath("//input[@id='password']")).sendKeys("secret_sauce");
		driver.findElement(By.xpath("//input[@id='login-button']")).click();

		List<WebElement> pricelist = driver.findElements(By.xpath(xpath));
		// replacing '$' symbol with blank value for all the prices
		for (int i = 0; i <= pricelist.size() - 1; i++) {

			prices.add(Double.parseDouble(pricelist.get(i).getText().replaceAll("\\$", "")));

		}

		// sorting the prices in descending order
		Collections.sort(prices, Collections.reverseOrder());
		String maxprice = prices.get(0).toString();
		System.out.println("Max price:" + maxprice);

		// checking the text of all prices which matches the max price
		for (int j = 0; j <= pricelist.size() - 1; j++) {
			if (pricelist.get(j).getText().equalsIgnoreCase("$" + maxprice)) {
				driver.findElement(By.xpath(xpath + "[" + (j + 1) + "]//following-sibling::button")).click();
				break;
			}
		}
		driver.findElement(By.xpath("//a[@class='shopping_cart_link']")).click();
		driver.findElement(By.xpath("//div[@class='inventory_item_name']")).click();

		// checking if price in main page and price in the cart matches or not
		String cartprice = driver.findElement(By.xpath("//div[@class='inventory_details_price']")).getText();
		if (cartprice.equalsIgnoreCase("$" + maxprice)) {
			System.out.println("Both Prices are matching");
		} else
			System.out.println("Prices are not matching");

		driver.quit();
	}

}
