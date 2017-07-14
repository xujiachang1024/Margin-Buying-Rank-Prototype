package back;

public class MarginSecurity {

	private String mCode;			// 证券代码
	private String mName;			// 证券简称
	private Double mLongBalance;	// 融资余额
	private Double mNetBuying;		// 融资净买入
	
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
	
	@SuppressWarnings("null")
	public double getNetBuyingRatio() {
		if (mLongBalance == 0) {
			return (Double)null;
		}
		return (mNetBuying / mLongBalance);
	}
}
