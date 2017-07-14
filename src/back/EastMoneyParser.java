package back;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class EastMoneyParser {

	private static String mShanghaiURL = "http://data.eastmoney.com/rzrq/sh.html";
	private static String mShenzhenURL = "http://data.eastmoney.com/rzrq/sz.html";
	private Document mShanghaiDoc;
	private Document mShenzhenDoc;
	private List<MarginSecurity> mShanghaiList;
	private List<MarginSecurity> mShenzhenList;
	private RatioComparator cmp;
	
	public EastMoneyParser() {
		this.mShanghaiList = new ArrayList<MarginSecurity>();
		this.mShenzhenList = new ArrayList<MarginSecurity>();
		this.cmp = new RatioComparator();
	}
	
	private boolean connect() {
		try {
			this.mShanghaiDoc = Jsoup.connect(mShanghaiURL).get();
		} catch (IOException e) {
			return false;
		}
		try {
			this.mShenzhenDoc = Jsoup.connect(mShenzhenURL).get();
		} catch (IOException e) {
			return false;
		}
		return true;
	}
	
	public List<MarginSecurity> updateShanghai() {
		if (mShanghaiDoc != null) {
			mShanghaiList.clear();
			Elements tables = mShanghaiDoc.getElementsByTag("table");
			Elements tableRows = tables.get(1).getElementsByTag("tr");
			for (Element tableRow : tableRows) {
				Elements tableDatas = tableRow.getElementsByTag("td");
				Element codeCell = tableDatas.get(0);
				Element nameCell = tableDatas.get(1);
				Element balanceCell = tableDatas.get(3);
				Element netBuyingCell = tableDatas.get(7);
				String inCode = codeCell.text();
				String inName = nameCell.text();
				String balanceStr = balanceCell.text();
				String netBuyingStr = netBuyingCell.text();
				double inLongBalance = toWanYuan(balanceStr);
				double inNetBuying = toWanYuan(netBuyingStr);
				mShanghaiList.add(new MarginSecurity(inCode, inName, inLongBalance, inNetBuying));
			}
			Collections.sort(mShanghaiList, cmp);
			return mShanghaiList;
		}
		return null;
	}
	
	public List<MarginSecurity> updateShenzhen() {
		if (mShenzhenDoc != null) {
			mShenzhenList.clear();
			Elements tables = mShenzhenDoc.getElementsByTag("table");
			Elements tableRows = tables.get(1).getElementsByTag("tr");
			for (Element tableRow : tableRows) {
				Elements tableDatas = tableRow.getElementsByTag("td");
				Element codeCell = tableDatas.get(0);
				Element nameCell = tableDatas.get(1);
				Element balanceCell = tableDatas.get(3);
				Element netBuyingCell = tableDatas.get(7);
				String inCode = codeCell.text();
				String inName = nameCell.text();
				String balanceStr = balanceCell.text();
				String netBuyingStr = netBuyingCell.text();
				double inLongBalance = toWanYuan(balanceStr);
				double inNetBuying = toWanYuan(netBuyingStr);
				mShenzhenList.add(new MarginSecurity(inCode, inName, inLongBalance, inNetBuying));
			}
			Collections.sort(mShenzhenList, cmp);
			return mShenzhenList;
		}
		return null;
	}
	
	private double toWanYuan(String inString) {
		double toWanYuan = 0.00;
		if (inString.lastIndexOf("万") != -1 || inString.lastIndexOf("亿") != -1) {
			if (inString.lastIndexOf("万") != -1) {
				int lastIndex = inString.lastIndexOf("万");
				String subStr = inString.substring(0, lastIndex);
				toWanYuan = Double.parseDouble(subStr);
			}
			else if (inString.lastIndexOf("亿") != -1) {
				int lastIndex = inString.lastIndexOf("亿");
				String subStr = inString.substring(0, lastIndex);
				toWanYuan = Double.parseDouble(subStr);
			}
		}
		return toWanYuan;
	}
	
	private class RatioComparator implements Comparator<MarginSecurity> {

		@Override
		public int compare(MarginSecurity o1, MarginSecurity o2) {
			if (o2.getNetBuyingRatio() - o1.getNetBuyingRatio() > 0) {
				return 1;
			}
			else if (o2.getNetBuyingRatio() - o1.getNetBuyingRatio() < 0) {
				return -1;
			}
			else {
				return 0;
			}
		}
		
	}
}
