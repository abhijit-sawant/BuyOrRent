package aum.fin.buyorrent;

public class CalcBuyOrRent {
	
	public interface OnDataChangedListener {
		public void onDataChanged();
	}
	
	private int    miHousePrice;
	private int    miDownPay;
	private double mdLoanIntRate;
	private int    miLoanTenr;
	private int    miHoldingPeriod;
	private int    miYearlyTax;
	private int    miYearlyMaintain;
	private int    miYearlyPropIns;
	private double mdMortIns;
	private double mdClosingCost;
	private double mdHomeApprRate;
	
	private int    miRent;
	private double mdRentIncreaseRate;
	private int    miRentIns;
	private int    miUtility;
	
	private int    miGrossIncome;
	private int    miItemDeduct;
	private double mdTaxBracket;
	
	private double mdBuyProfit;
	
	public CalcBuyOrRent() {		
		//loan
		miHousePrice     = 300000;
		miDownPay        = 100000;
		mdLoanIntRate    = 2;
		miLoanTenr       = 15;
		miHoldingPeriod  = 4;
		miYearlyTax      = 2000;
		miYearlyMaintain = 1200;
		miYearlyPropIns  = 1200;
		mdMortIns        = 0.52;
		mdClosingCost    = 7;
		mdHomeApprRate   = 2;
		
		//rent
		miRent             = 800;
		mdRentIncreaseRate = 4;
		miRentIns          = 0;
		miUtility          = 50;
		
		//tax
		miGrossIncome = 80000;
		miItemDeduct  = 10000;
		mdTaxBracket  = 15;		
		
		mdBuyProfit = 0;
	}
	
	public int getHousePrice() {
		return miHousePrice;
	}
	
	public void setHousePrice(int iVal) {
		miHousePrice = iVal;
	}
	
	public int getDownPay() {
		return miDownPay;
	}
	
	public void setDownPay(int iVal) {
		miDownPay = iVal;
	}
	
	public double getLoanIntRate() {
		return mdLoanIntRate;
	}
	
	public void setLoanIntRate(double dVal) {
		mdLoanIntRate = dVal;
	}
	
	public int getLoanTenr() {
		return miLoanTenr;
	}
	
	public void setLoanTenr(int iVal) {
		miLoanTenr = iVal;
	}
	
	public int getHoldingPriod() {
		return miHoldingPeriod;
	}
	
	public void setHoldingPriod(int iVal) {
		miHoldingPeriod = iVal;
	}
		
	public int getYearlyTax() {
		return miYearlyTax;
	}
	
	public void setYearlyTax(int iVal) {
		miYearlyTax = iVal;
	}	
	
	public int getYearlyMaintain() {
		return miYearlyMaintain;
	}
	
	public void setYearlyMaintain(int iVal) {
		miYearlyMaintain = iVal;
	}
	
	public int getYearlyPropIns() {
		return miYearlyPropIns;
	}
	
	public void setYearlyPropIns(int iVal) {
		miYearlyPropIns = iVal;
	}
	
	public double getMortIns() {
		return mdMortIns;
	}
	
	public void setMortIns(double dVal) {
		mdMortIns = dVal;
	}	
	
	public double getClosingCost() {
		return mdClosingCost;
	}
	
	public void setClosingCost(double dVal) {
		mdClosingCost = dVal;
	}
	
	public double getHomeApprRate() {
		return mdHomeApprRate;
	}
	
	public void setHomeApprRate(double dVal) {
		mdHomeApprRate = dVal;
	}
	
	public int getRent() {
		return miRent;
	}
	
	public void setRent(int iVal) {
		miRent = iVal;
	}	
	
	public double getRentIncreaseRate() {
		return mdRentIncreaseRate;
	}
	
	public void setRentIncreaseRate(double dVal) {
		mdRentIncreaseRate = dVal;
	}
	
	public int getRentIns() {
		return miRentIns;
	}
	
	public void setRentIns(int iVal) {
		miRentIns = iVal;
	}
	
	public int getUtility() {
		return miUtility;
	}
	
	public void setUtility(int iVal) {
		miUtility = iVal;
	}
	
	public int getGrossIncome() {
		return miGrossIncome;
	}
	
	public void setGrossIncome(int iVal) {
		miGrossIncome = iVal;
	}
	
	public int getItmeDeduct() {
		return miItemDeduct;
	}
	
	public void setItemDeduct(int iVal) {
		miItemDeduct = iVal;
	}
	
	public double getTaxBracket() {
		return mdTaxBracket;
	}
	
	public void setTaxBracket(double dVal) {
		mdTaxBracket = dVal;
	}
	
	public double getBuyProfit() {
		return mdBuyProfit;
	}
	
	public void calculate() {
		int iLoanAmount = miHousePrice - miDownPay;
		double dIntRatePerMont = (mdLoanIntRate*0.01)/12;
		int iNumLoanPay = miLoanTenr * 12;
		
		double dMonthlyPayment = iLoanAmount * dIntRatePerMont;
		dMonthlyPayment = dMonthlyPayment / (1 - (1/Math.pow(1 + dIntRatePerMont, iNumLoanPay)));
		
		double dMortInsPerMonth = (mdMortIns * 0.01 * iLoanAmount)/12;
		double dTotalMonthlyPay = dMonthlyPayment + dMortInsPerMonth;
		
		int iHoldingPeriodMnths = miHoldingPeriod * 12;
		double dLoanBalance = iLoanAmount;
		dLoanBalance = dLoanBalance * (1 - Math.pow(1 + dIntRatePerMont, iHoldingPeriodMnths - iNumLoanPay));
		dLoanBalance = dLoanBalance / (1 - Math.pow(1 + dIntRatePerMont, -1 * iNumLoanPay));
		
		double dMortPaid = dMonthlyPayment * iHoldingPeriodMnths;
		double dPrincipalPaid = iLoanAmount - dLoanBalance;
		double dIntPaid = dMortPaid - dPrincipalPaid;
		
		//house sell price
		double dHouseSellPrice = miHousePrice;
		for(int i = 1; i <= miHoldingPeriod; i++)
			dHouseSellPrice += dHouseSellPrice * (mdHomeApprRate * 0.01);
		
		double dTaxNIntPerYear = ((dIntPaid * 12) / iHoldingPeriodMnths) + miYearlyTax;
		double dTotalMortIns = dMortInsPerMonth * iHoldingPeriodMnths;
		
		double dTaxInsMaintain = ((double) (miYearlyTax + miYearlyPropIns + miYearlyMaintain) / 12) * iHoldingPeriodMnths;
		double dClosingCost = mdClosingCost * 0.01 * dHouseSellPrice;
		
		mdBuyProfit = dHouseSellPrice - (miHousePrice + dIntPaid + dClosingCost + dTotalMortIns + dTaxInsMaintain);
	}
}
