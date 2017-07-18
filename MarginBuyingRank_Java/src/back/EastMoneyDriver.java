package back;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;

public class EastMoneyDriver {
	
//	public static final String mShanghaiURL = "http://data.eastmoney.com/rzrq/sh.html";
//	public static final String mShenzhenURL = "http://data.eastmoney.com/rzrq/sz.html";
	
//	private WebDriver mDriver;
	private List<MarginSecurity> mShanghaiList;
//	private List<MarginSecurity> mShenzhenList;
	private RatioComparator cmp;
	
	public EastMoneyDriver() {
//		this.mDriver = null;
		this.mShanghaiList = new ArrayList<MarginSecurity>();
//		this.mShenzhenList = new ArrayList<MarginSecurity>();
		this.cmp = new RatioComparator();
	}
	
	public List<MarginSecurity> updateShanghai(File inFile) {
		this.mShanghaiList.clear();
//		if (this.mDriver == null) {
//			System.setProperty("webdriver.gecko.driver", "C:\\GeckoDriver.exe");
//			this.mDriver = new FirefoxDriver();
//			this.mDriver.get(mShanghaiURL);
//		}
//		else {
//			this.mDriver.navigate().to(mShanghaiURL);
//		}
//		WebElement table = mDriver.findElement(By.id("dt_1"));
//		WebElement tbody = table.findElement(By.tagName("tbody"));
//		List<WebElement> trs = tbody.findElements(By.tagName("tr"));
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(inFile.getAbsolutePath()));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String line = "";
		try {
			while ((line = br.readLine()) != null) {
				String[] data = line.split(",");
				if (data[0] == "代码");
				String inCode = data[0];
				String inName = data[1];
				String balanceStr = data[3];
				String netBuyingStr = data[7];
				double inLongBalance = toWanYuan(balanceStr);
				double inNetBuying = toWanYuan(netBuyingStr);
				MarginSecurity ms = new MarginSecurity(inCode, inName, inLongBalance, inNetBuying);
				mShanghaiList.add(ms);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		for (WebElement tr : trs) {
//			List<WebElement> tds = tr.findElements(By.tagName("td"));
//			String inCode = tds.get(0).getText();
//			String inName = tds.get(1).getText();
//			String balanceStr = tds.get(3).getText();
//			String netBuyingStr = tds.get(7).getText();
//			double inLongBalance = toWanYuan(balanceStr);
//			double inNetBuying = toWanYuan(netBuyingStr);
//			MarginSecurity ms = new MarginSecurity(inCode, inName, inLongBalance, inNetBuying);
//			mShanghaiList.add(ms);
//		}
		Collections.sort(mShanghaiList, cmp);
		return mShanghaiList;
	}
	
//	public List<MarginSecurity> updateShenzhen(File inFile) {
//		this.mShenzhenList.clear();
//		if (this.mDriver == null) {
//			System.setProperty("webdriver.gecko.driver", "C:\\GeckoDriver.exe");
//			this.mDriver = new FirefoxDriver();
//			this.mDriver.get(mShenzhenURL);
//		}
//		else {
//			this.mDriver.navigate().to(mShenzhenURL);
//		}
//		WebElement table = mDriver.findElement(By.id("dt_1"));
//		WebElement tbody = table.findElement(By.tagName("tbody"));
//		List<WebElement> trs = tbody.findElements(By.tagName("tr"));
//		for (WebElement tr : trs) {
//			List<WebElement> tds = tr.findElements(By.tagName("td"));
//			String inCode = tds.get(0).getText();
//			String inName = tds.get(1).getText();
//			String balanceStr = tds.get(3).getText();
//			String netBuyingStr = tds.get(7).getText();
//			double inLongBalance = toWanYuan(balanceStr);
//			double inNetBuying = toWanYuan(netBuyingStr);
//			MarginSecurity ms = new MarginSecurity(inCode, inName, inLongBalance, inNetBuying);
//			mShenzhenList.add(ms);
//		}
//		Collections.sort(mShenzhenList, cmp);
//		return mShenzhenList;
//	}
	
	private double toWanYuan(String str) {
		double toWanYuan = 0.00;
		if (str.indexOf("亿") != -1) {
			toWanYuan = Double.parseDouble(str.substring(0, str.indexOf("亿"))) * 10000.00;
		}
		if (str.indexOf("万") != -1) {
			toWanYuan = Double.parseDouble(str.substring(0, str.indexOf("万")));
		}
		return toWanYuan;
	}
	
//	public void quit() {
//		if (this.mDriver != null) {
//			this.mDriver.quit();
//		}
//	}
	
	private class RatioComparator implements Comparator<MarginSecurity> {

		public int compare(MarginSecurity o1, MarginSecurity o2) {
			if (o2.getNetBuyingRatio() > o1.getNetBuyingRatio()) {
				return 1;
			}
			if (o2.getNetBuyingRatio() < o1.getNetBuyingRatio()) {
				return -1;
			}
			return 0;
		}
		
	}
}
