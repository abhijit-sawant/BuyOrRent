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
	private double mdHomeApprRate;
	private int    miBuyUtilities;
	private int    miYearlyTax;
	private int    miYearlyMaintain;
	private int    miYearlyPropIns;
	private double mdMortIns;
	private double mdClosingCost;
	private int    miMovingInCost;
	
	private int    miRent;
	private double mdRentIncreaseRate;
	private int    miRentIns;
	private int    miUtility;
	private double mdSavingReturnRate;
	
	private double mdTaxBracket;
	
	private double mdBuyProfit;
	private double mdRentProfit;
	private double mdNetBuyProfit;
	
	public CalcBuyOrRent(SharedPreferences pref) {	
		
		resetToDefault();
		
		//loan
		miHousePrice     = pref.getInt("HousePrice", miHousePrice);
		miDownPay        = pref.getInt("DownPay", miDownPay);
		mdLoanIntRate    = pref.getFloat("IntRate", (float) mdLoanIntRate);
		miLoanTenr       = pref.getInt("LoanTenr", miLoanTenr);
		miHoldingPeriod  = pref.getInt("HoldingPeriod", miHoldingPeriod);
		mdHomeApprRate   = pref.getFloat("ApprRate", (float) mdHomeApprRate);
		miBuyUtilities   = pref.getInt("BuyUtilities", miBuyUtilities);
		miYearlyTax      = pref.getInt("YearlyTax", miYearlyTax);
		miYearlyMaintain = pref.getInt("YearlyMaintain", miYearlyMaintain);
		miYearlyPropIns  = pref.getInt("YearlyPropIns", miYearlyPropIns);
		mdMortIns        = pref.getFloat("MortIns", (float) mdMortIns);
		mdClosingCost    = pref.getFloat("ClosingCost", (float) mdClosingCost);
		miMovingInCost   = pref.getInt("MovingInCost", miMovingInCost);
		//rent
		miRent             = pref.getInt("Rent", miRent);
		mdRentIncreaseRate = pref.getFloat("RentIncreaseRate", (float) mdRentIncreaseRate);
		miRentIns          = pref.getInt("RentIns", miRentIns);
		miUtility          = pref.getInt("Utility", miUtility);
		mdSavingReturnRate = pref.getFloat("SavingReturnRate", (float) mdSavingReturnRate);
		
		//tax
		mdTaxBracket  = pref.getFloat("TaxBracket", (float) mdTaxBracket);	
		
		mdBuyProfit    = 0;
		mdRentProfit   = 0;
		mdNetBuyProfit = 0;
	}
	
	public void resetToDefault() {
		//loan
		miHousePrice     = 300000;
		miDownPay        = 20000;
		mdLoanIntRate    = 4;
		miLoanTenr       = 30;
		miHoldingPeriod  = 20;
		mdHomeApprRate   = 3;
		miBuyUtilities   = 200;
		miYearlyTax      = 2000;
		miYearlyMaintain = 1200;
		miYearlyPropIns  = 1500;
		mdMortIns        = 0.52;
		mdClosingCost    = 3;
		miMovingInCost   = 0;
		
		//rent
		miRent             = 900;
		mdRentIncreaseRate = 4;
		miRentIns          = 0;
		miUtility          = 150;
		mdSavingReturnRate = 0;
		
		//tax
		mdTaxBracket  = 0;		
		
		mdBuyProfit    = 0;
		mdRentProfit   = 0;
		mdNetBuyProfit = 0;
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
	
	public int getMovingInCost() {
		return miMovingInCost;
	}
	
	public void setMovingInCost(int iVal) {
		miMovingInCost = iVal;
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
	
	public int getBuyUtilities() {
		return miBuyUtilities;
	}
	
	public void setBuyUtilities(int iVal) {
		miBuyUtilities = iVal;
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
	
	public double getSavingReturnRate() {
		return mdSavingReturnRate;
	}
	
	public void setSavingReturnRate(double dVal) {
		mdSavingReturnRate = dVal;
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
	
	public double getRentProfit() {
		return mdRentProfit;
	}
	
	public double getBuyNetProfit() {
		return mdNetBuyProfit;
	}
	
	public void calculate() {
		int    iLoanAmount     = miHousePrice - miDownPay;
		double dIntRatePerMont = (mdLoanIntRate*0.01)/12;
		int    iNumLoanPay     = miLoanTenr * 12;
		
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
		double dHouseSellPrice = futureValue(miHousePrice, mdHomeApprRate, miHoldingPeriod);
		
		double dTotalMortIns   = dMortInsPerMonth * iHoldingPeriodMnths;		
		double dTaxInsMaintain = ((double) (miYearlyTax + miYearlyPropIns + miYearlyMaintain)) * miHoldingPeriod;
		double dClosingCost    = mdClosingCost * 0.01 * dHouseSellPrice;
		double dTaxSavings     = (mdTaxBracket/100) * dIntPaid;
		
		double dBuyExpense  = (iHoldingPeriodMnths * dMonthlyPayment) + (iHoldingPeriodMnths * miBuyUtilities) +
		                      miMovingInCost + dTotalMortIns + dTaxInsMaintain + miDownPay - dTaxSavings;
		
		double dRentExpense = rentExpense();
		
		double dSavingMnthly = (dBuyExpense - dRentExpense - miDownPay - miMovingInCost) / (miHoldingPeriod * 12);
		double dOpptCost = futureValue((miDownPay + miMovingInCost), mdSavingReturnRate, miHoldingPeriod) +
				           futureValueCompound(dSavingMnthly, (mdSavingReturnRate/12), (miHoldingPeriod * 12));
				
		mdBuyProfit  = dHouseSellPrice - dBuyExpense - dLoanBalance - dClosingCost;
		mdRentProfit = dOpptCost - dRentExpense;
		
		mdNetBuyProfit = mdBuyProfit - mdRentProfit;
	}
	
    private double futureValue(double dCurVal, double dApprRate, int iPeriod) {
		double dFutVal = dCurVal;
		for(int i = 1; i <= iPeriod; i++)
			dFutVal += dFutVal * (dApprRate * 0.01);
		return dFutVal;
	}
    
    private double futureValueCompound(double dCurVal, double dApprRate, int iPeriod) {
		double dFutVal = dCurVal;
		for(int i = 1; i <= iPeriod; i++)
			dFutVal += dFutVal * (dApprRate * 0.01) + dCurVal;
		return dFutVal;
	}
	
	private double rentExpense() {
		double dRentExpense = 0;
		double dMonthly = miRent;
		for(int i = 1; i <= miHoldingPeriod; i++)
		{
			dRentExpense += 12 * (dMonthly);
			dMonthly += dMonthly * (mdRentIncreaseRate * 0.01);
		}
		dRentExpense += (miHoldingPeriod * 12 * miUtility) + (miHoldingPeriod * 12 * miRentIns);
		return dRentExpense;
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
    	editor.putFloat("ApprRate",     (float) mdHomeApprRate);
    	editor.putInt("BuyUtilities",   miBuyUtilities);
    	editor.putInt("YearlyTax",      miYearlyTax);
    	editor.putInt("YearlyMaintain", miYearlyMaintain);
    	editor.putInt("YearlyPropIns",  miYearlyPropIns);
    	editor.putFloat("MortIns",      (float) mdMortIns);
    	editor.putFloat("ClosingCost",  (float) mdClosingCost);
    	editor.putInt("MovingInCost",   miMovingInCost);
    	
    	editor.putInt("Rent",               miRent);
    	editor.putFloat("RentIncreaseRate", (float) mdRentIncreaseRate);
    	editor.putInt("RentIns",            miRentIns);
    	editor.putInt("Utility",            miUtility);
    	editor.putFloat("SavingReturnRate", (float) mdSavingReturnRate);
    	
    	editor.putFloat("TaxBracket", (float) mdTaxBracket);
    	
    	editor.commit();
	}	
	
}
