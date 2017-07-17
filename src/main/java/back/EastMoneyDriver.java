package back;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.safari.SafariDriver;

public class EastMoneyDriver {
	
	public static final String mShanghaiURL = "http://data.eastmoney.com/rzrq/sh.html";
	public static final String mShenzhenURL = "http://data.eastmoney.com/rzrq/sz.html";
	
	private WebDriver mDriver;
	private List<MarginSecurity> mShanghaiList;
	private List<MarginSecurity> mShenzhenList;
	private RatioComparator cmp;
	
	public EastMoneyDriver() {
		this.mDriver = null;
		this.mShanghaiList = new ArrayList<MarginSecurity>();
		this.mShenzhenList = new ArrayList<MarginSecurity>();
		this.cmp = new RatioComparator();
	}
	
	public List<MarginSecurity> updateShanghai() {
		this.mShanghaiList.clear();
		if (this.mDriver == null) {
			this.mDriver = new SafariDriver();
			this.mDriver.get(mShanghaiURL);
		}
		else {
			this.mDriver.navigate().to(mShanghaiURL);
		}
		WebElement table = mDriver.findElement(By.id("dt_1"));
		WebElement tbody = table.findElement(By.tagName("tbody"));
		List<WebElement> trs = tbody.findElements(By.tagName("tr"));
		for (WebElement tr : trs) {
			List<WebElement> tds = tr.findElements(By.tagName("td"));
			String inCode = tds.get(0).getText();
			String inName = tds.get(1).getText();
			String balanceStr = tds.get(3).getText();
			String netBuyingStr = tds.get(7).getText();
			double inLongBalance = toWanYuan(balanceStr);
			double inNetBuying = toWanYuan(netBuyingStr);
			MarginSecurity ms = new MarginSecurity(inCode, inName, inLongBalance, inNetBuying);
			mShanghaiList.add(ms);
		}
		Collections.sort(mShanghaiList, cmp);
		return mShanghaiList;
	}
	
	public List<MarginSecurity> updateShenzhen() {this.mShanghaiList.clear();
		this.mShenzhenList.clear();
		if (this.mDriver == null) {
			this.mDriver = new SafariDriver();
			this.mDriver.get(mShenzhenURL);
		}
		else {
			this.mDriver.navigate().to(mShenzhenURL);
		}
		WebElement table = mDriver.findElement(By.id("dt_1"));
		WebElement tbody = table.findElement(By.tagName("tbody"));
		List<WebElement> trs = tbody.findElements(By.tagName("tr"));
		for (WebElement tr : trs) {
			List<WebElement> tds = tr.findElements(By.tagName("td"));
			String inCode = tds.get(0).getText();
			String inName = tds.get(1).getText();
			String balanceStr = tds.get(3).getText();
			String netBuyingStr = tds.get(7).getText();
			double inLongBalance = toWanYuan(balanceStr);
			double inNetBuying = toWanYuan(netBuyingStr);
			MarginSecurity ms = new MarginSecurity(inCode, inName, inLongBalance, inNetBuying);
			mShenzhenList.add(ms);
		}
		Collections.sort(mShenzhenList, cmp);
		return mShenzhenList;
	}
	
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
	
	public void quit() {
		this.mDriver.quit();
	}
	
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
