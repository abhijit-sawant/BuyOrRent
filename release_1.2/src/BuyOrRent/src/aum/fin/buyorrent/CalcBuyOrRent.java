package aum.fin.buyorrent;

import android.content.SharedPreferences;

public class CalcBuyOrRent {
	
	public interface OnDataChangedListener {
		public void onDataChanged();
		public void onResetToDefault();
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
	
	private double mdTaxBracket;
	
	private double mdBuyProfit;
	private double mdTotalRent;
	
	public CalcBuyOrRent(SharedPreferences pref) {	
		
		resetToDefault();
		
		//loan
		miHousePrice     = pref.getInt("HousePrice", miHousePrice);
		miDownPay        = pref.getInt("DownPay", miDownPay);
		mdLoanIntRate    = pref.getFloat("IntRate", (float) mdLoanIntRate);
		miLoanTenr       = pref.getInt("LoanTenr", miLoanTenr);
		miHoldingPeriod  = pref.getInt("HoldingPeriod", miHoldingPeriod);
		miYearlyTax      = pref.getInt("YearlyTax", miYearlyTax);
		miYearlyMaintain = pref.getInt("YearlyMaintain", miYearlyMaintain);
		miYearlyPropIns  = pref.getInt("YearlyPropIns", miYearlyPropIns);
		mdMortIns        = pref.getFloat("MortIns", (float) mdMortIns);
		mdClosingCost    = pref.getFloat("ClosingCost", (float) mdClosingCost);
		mdHomeApprRate   = pref.getFloat("ApprRate", (float) mdHomeApprRate);
		
		//rent
		miRent             = pref.getInt("Rent", miRent);
		mdRentIncreaseRate = pref.getFloat("RentIncreaseRate", (float) mdRentIncreaseRate);
		miRentIns          = pref.getInt("RentIns", miRentIns);
		miUtility          = pref.getInt("Utility", miUtility);
		
		//tax
		mdTaxBracket  = pref.getFloat("TaxBracket", (float) mdTaxBracket);	
		
		mdBuyProfit = 0;
		mdTotalRent = 0;
	}
	
	public void resetToDefault() {
		//loan
		miHousePrice     = 300000;
		miDownPay        = 100000;
		mdLoanIntRate    = 2;
		miLoanTenr       = 30;
		miHoldingPeriod  = 15;
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
		mdTaxBracket  = 0;		
		
		mdBuyProfit = 0;
		mdTotalRent = 0;
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
	
	public double getTaxBracket() {
		return mdTaxBracket;
	}
	
	public void setTaxBracket(double dVal) {
		mdTaxBracket = dVal;
	}
	
	public double getBuyProfit() {
		return mdBuyProfit;
	}
	
	public double getTotalRent() {
		return -1 * mdTotalRent;
	}
	
	public double getNetProfit() {
		return (getBuyProfit() - getTotalRent());
	}
	
	public void calculate() {
		int iLoanAmount = miHousePrice - miDownPay;
		double dIntRatePerMont = (mdLoanIntRate*0.01)/12;
		int iNumLoanPay = miLoanTenr * 12;
		
		double dMonthlyPayment = iLoanAmount * dIntRatePerMont;
		dMonthlyPayment = dMonthlyPayment / (1 - (1/Math.pow(1 + dIntRatePerMont, iNumLoanPay)));
		
		double dMortInsPerMonth = (mdMortIns * 0.01 * iLoanAmount)/12;
		
		int iHoldingPeriodMnths = miHoldingPeriod * 12;
		double dLoanBalance = iLoanAmount;
		dLoanBalance = dLoanBalance * (1 - Math.pow(1 + dIntRatePerMont, iHoldingPeriodMnths - iNumLoanPay));
		dLoanBalance = dLoanBalance / (1 - Math.pow(1 + dIntRatePerMont, -1 * iNumLoanPay));
		
		double dMortPaid = dMonthlyPayment * iHoldingPeriodMnths;
		double dPrincipalPaid = iLoanAmount - dLoanBalance;
		double dIntPaid = dMortPaid - dPrincipalPaid;
		
		//house sell price
		double dHouseSellPrice = futureValueOnAnnual(miHousePrice, mdHomeApprRate, miHoldingPeriod);
		
		double dTotalMortIns = dMortInsPerMonth * iHoldingPeriodMnths;
		
		double dTaxInsMaintain = ((double) (miYearlyTax + miYearlyPropIns + miYearlyMaintain) / 12) * iHoldingPeriodMnths;
		double dClosingCost = mdClosingCost * 0.01 * dHouseSellPrice;
		
		mdBuyProfit = dHouseSellPrice - (miHousePrice + dIntPaid + dClosingCost + dTotalMortIns + dTaxInsMaintain);
		mdBuyProfit = mdBuyProfit + taxSavings(dMonthlyPayment, dIntRatePerMont);
		
		mdTotalRent = futureValueOnMonthly((miRent), mdRentIncreaseRate, miHoldingPeriod) + (miHoldingPeriod*12*miUtility);
	}
	
    private double futureValueOnAnnual(double dCurVal, double dApprRate, int iPeriod) {
		double dFutVal = dCurVal;
		for(int i = 1; i <= iPeriod; i++)
			dFutVal += dFutVal * (dApprRate * 0.01);
		return dFutVal;
	}
	
	private double futureValueOnMonthly(double dCurVal, double dApprRate, int iPeriod) {
		double dFutVal = 0;
		double dMonthly = dCurVal;
		for(int i = 1; i <= iPeriod; i++)
		{
			dFutVal += 12 * dMonthly;
			dMonthly += dMonthly * (dApprRate * 0.01);
		}
		return dFutVal;
	}
	
	private double taxSavings(double dMonthlyPay, double dIntRatePerMont) {
		double dTaxSaving = 0;
		int iLoanAmount = miHousePrice - miDownPay;
		double dOldAmtOwed = iLoanAmount;
		for(int i = 1; i <= miHoldingPeriod; i++) {
			int n = i*12;
			double dAmtOwed = (Math.pow((1 + dIntRatePerMont), n)) * iLoanAmount;
			dAmtOwed = dAmtOwed - (((Math.pow((1 + dIntRatePerMont), n) - 1) / dIntRatePerMont) * dMonthlyPay);
			
			double dTotalAmtPayed  = 12 * dMonthlyPay;
			double dPrincipalPayed = dOldAmtOwed - dAmtOwed;
			double dIntPayed       = dTotalAmtPayed - dPrincipalPayed;
			dTaxSaving = dTaxSaving + (dIntPayed * mdTaxBracket) / 100;
			dOldAmtOwed = dAmtOwed;
		}
		
		return dTaxSaving;
	}
	
	public void onPause(SharedPreferences pref) {
		SharedPreferences.Editor editor = pref.edit();
    	
    	editor.putInt("HousePrice",     miHousePrice);
    	editor.putInt("DownPay",        miDownPay);
    	editor.putFloat("IntRate",      (float) mdLoanIntRate);
    	editor.putInt("LoanTenr",       miLoanTenr);
    	editor.putInt("HoldingPeriod",  miHoldingPeriod);
    	editor.putInt("YearlyTax",      miYearlyTax);
    	editor.putInt("YearlyMaintain", miYearlyMaintain);
    	editor.putInt("YearlyPropIns",  miYearlyPropIns);
    	editor.putFloat("MortIns",      (float) mdMortIns);
    	editor.putFloat("ClosingCost",  (float) mdClosingCost);
    	editor.putFloat("ApprRate",     (float) mdHomeApprRate);
    	
    	editor.putInt("Rent",               miRent);
    	editor.putFloat("RentIncreaseRate", (float) mdRentIncreaseRate);
    	editor.putInt("RentIns",            miRentIns);
    	editor.putInt("Utility",            miUtility);
    	
    	editor.putFloat("TaxBracket", (float) mdTaxBracket);
    	
    	editor.commit();
	}	
	
}
