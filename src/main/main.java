package main;

import java.util.List;

import back.EastMoneyParser;
import back.MarginSecurity;

public class main {

	public static void main(String [] args) {
		EastMoneyParser parser = new EastMoneyParser();
		List<MarginSecurity> shanghai = parser.updateShanghai();
		for(MarginSecurity sh : shanghai) {
			System.out.println(sh.getCode() + " " + sh.getName() + " " + sh.getNetBuyingRatio());
		}
		System.out.println("end");
	}
	
}
