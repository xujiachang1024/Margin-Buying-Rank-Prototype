package back;

import java.util.List;

import com.jaunt.Element;
import com.jaunt.Elements;
import com.jaunt.NotFound;
import com.jaunt.ResponseException;
import com.jaunt.UserAgent;
import com.jaunt.component.Table;

public class EastMoneyParserJaunt implements Runnable {

	public static void main(String [] args) {
		UserAgent agent = new UserAgent();
		try {
			agent.visit("http://data.eastmoney.com/rzrq/sh.html");
			System.out.println("连接成功：上证融资融券");
			try {
				agent.wait(6000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			Element tableElement = agent.doc.findEvery("<table id=dt_1>").toList().get(0);
			Element tbodyElement = tableElement.findEvery("<tbody>").toList().get(0);
			System.out.println("找到表格");
			try {
				Table table = new Table(tbodyElement);
				System.out.println("生成表格");
				Elements tds = table.getRow(1);
				for (Element td : tds) {
					System.out.println(td.getText());
				}
			} catch (NotFound e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			/**
			Element tbody = table.findEvery("<tbody>").toList().get(0);
			System.out.println("找到数据");
			Elements trs = tbody.findEvery("<tr>");
			System.out.println("找到每行");
			for (Element tr : trs) {
				System.out.println("进入第1个loop");
				List<Element> tds = tr.findEvery("<td>").toList();
				for (Element td : tds) {
					System.out.println("进入第2个loop");
					System.out.println(td.getText());
				}
			}
			 */
		} catch (ResponseException e) {
			// TODO Auto-generated catch block
			System.out.println("连接失败");
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		UserAgent agent = new UserAgent();
		try {
			agent.visit("http://data.eastmoney.com/rzrq/sh.html");
			System.out.println("连接成功：上证融资融券");
			Element tableElement = agent.doc.findEvery("<table id=dt_1>").toList().get(0);
			Element tbodyElement = tableElement.findEvery("<tbody>").toList().get(0);
			System.out.println("找到表格");
			try {
				Table table = new Table(tbodyElement);
				System.out.println("生成表格");
				Elements tds = table.getRow(1);
				for (Element td : tds) {
					System.out.println(td.getText());
				}
			} catch (NotFound e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			/**
			Element tbody = table.findEvery("<tbody>").toList().get(0);
			System.out.println("找到数据");
			Elements trs = tbody.findEvery("<tr>");
			System.out.println("找到每行");
			for (Element tr : trs) {
				System.out.println("进入第1个loop");
				List<Element> tds = tr.findEvery("<td>").toList();
				for (Element td : tds) {
					System.out.println("进入第2个loop");
					System.out.println(td.getText());
				}
			}
			 */
		} catch (ResponseException e) {
			// TODO Auto-generated catch block
			System.out.println("连接失败");
			e.printStackTrace();
		}
	}
	
}
