package back;

public class MarginSecurity {

	private String mCode;			// 证券代码
	private String mName;			// 证券简称
	private Double mLongBalance;	// 融资余额（单位：万元）
	private Double mNetBuying;		// 融资净买入（单位：万元）
	
	public MarginSecurity(String inCode, String inName, double inLongBalance, double inNetBuying) {
		this.mCode = inCode;
		this.mName = inName;
		this.mLongBalance = inLongBalance;
		this.mNetBuying = inNetBuying;
	}
	
	public String getCode() {
		return mCode;
	}
	
	public String getName() {
		return mName;
	}
	
	public double getLongBalance() {
		return mLongBalance;
	}
	
	public double getNetBuying() {
		return mNetBuying;
	}
	
	public double getNetBuyingRatio() {
		if (mLongBalance == 0) {
			return 0.00;
		}
		return (mNetBuying / mLongBalance) * 100.00;
	}
}
