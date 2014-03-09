package aum.fin.buyorrent;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;

import com.echo.holographlibrary.Bar;
import com.echo.holographlibrary.BarGraph;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.MenuItem;
import android.widget.SeekBar;
import android.widget.TextView;
import android.support.v4.app.NavUtils;

public class TradeOffActiviy extends Activity {
	
	public enum DataType {
		DOUBLE, INTEGER
	}
	
	public enum Format {
		NONE, CURRENCY, PERCENT, YEARS
	}
	
	class ResultSeekListner implements SeekBar.OnSeekBarChangeListener {
		
    	private double   mDblMin = 0;
    	private double   mDblMax = 0;
    	private TextView mTextLinked;
    	private DataType mDataType = DataType.DOUBLE;
    	private Format   mFormat = Format.NONE;
    	
    	public ResultSeekListner(TextView textLinked, DataType type, Format format, double dVal) {
    		mTextLinked = textLinked;
    		mDataType   = type;
    		mFormat     = format;
    		setVal(dVal);
   	 	}
   	 
		 public void setProgressMin(double dMin) { mDblMin = dMin; }
		 public void setProgressMax(double dMax) { mDblMax = dMax; }
		 
		 public double getProgressMin() { return mDblMin; }
		 public double getProgressMax() { return mDblMax; }
   	 
	   	 public void onStartTrackingTouch(SeekBar seekBar) {}
	   	 public void onStopTrackingTouch(SeekBar seekBar) {}
	   	 public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
	   		 if(fromUser)
	   		 {
	    		 double dVal = ((progress/100.0) * (mDblMax - mDblMin)) + mDblMin;	    		 
	    		 setVal(dVal);
	   		 }
	   	 } 
	   	 
	   	 private void setVal(double dVal) {
	   		String strVal = "";
	   		 if(mDataType == DataType.DOUBLE && mFormat == Format.NONE) 
	   			 strVal = String.format("%.2f", dVal);
	   		 else if(mDataType == DataType.DOUBLE && mFormat == Format.PERCENT) 
	   			 strVal = String.format("%.2f", dVal) + " %";
	   		 else if(mDataType == DataType.DOUBLE && mFormat == Format.CURRENCY) 
	   			 strVal = mFormatCurrancy.format(dVal);	
	   		 else if(mDataType == DataType.INTEGER && mFormat == Format.NONE)
	   			 strVal = String.valueOf((int) dVal);
	   		 else if(mDataType == DataType.INTEGER && mFormat == Format.PERCENT)
	   			 strVal = String.valueOf((int) dVal) + " %";	
	   		 else if(mDataType == DataType.INTEGER && mFormat == Format.CURRENCY)
	   			 strVal = mFormatCurrancy.format((int)dVal);	
	   		 
	   		 else if((mDataType == DataType.DOUBLE ||  mDataType == DataType.INTEGER) &&
	   			      mFormat == Format.YEARS) 
	   			 strVal = String.valueOf((int)dVal) + " Years";	   		 
	   		 
	   		 mTextLinked.setText(strVal);
	   		 
	   		try {
				onDataChanged();
			} catch (ParseException e) {
				e.printStackTrace();
			}
	   	 }
	   	 
	   	 private double getVal() {
	   		 if(mFormat == Format.CURRENCY)
	   		 {
				try {
					return mFormatCurrancy.parse(mTextLinked.getText().toString()).doubleValue();
				} catch (ParseException e) {
					e.printStackTrace();
				}
	   		 }
	   		 
	   		 if(mFormat == Format.PERCENT)
	   		 {
	   			String s = mTextLinked.getText().toString();
	   			return Double.valueOf(s.replace(" %", ""));
	   		 }
	   		 
	   		 if(mFormat == Format.YEARS)
	   		 {
	   			String s = mTextLinked.getText().toString();
	   			return Double.valueOf(s.replace(" Years", ""));
	   		 }	   		 
	   		 
	   		 if(mFormat == Format.NONE)
	   			 return Double.valueOf(mTextLinked.getText().toString());
	   			 
	   		 return 0;
	   	 }
    };
    
    private TextView mTextResult;
    private BarGraph mGraphResult;
	
    private TextView mTextHousePrice;
    private SeekBar  mSeekHousePrice;
    private ResultSeekListner mSeekListenerHousePrice;
    
    private TextView mTextDownPay;
    private SeekBar  mSeekDownPay;
    private ResultSeekListner mSeekListenerDownPay;
    
    private TextView mTextIntRate;
    private SeekBar  mSeekIntRate;
    private ResultSeekListner mSeekListenerIntRate;
    
    private TextView mTextLoanTenr;
    private SeekBar  mSeekLoanTenr;
    private ResultSeekListner mSeekListenerLoanTenr;
    
    private TextView mTextHoldingPeriod;
    private SeekBar  mSeekHoldingPeriod;
    private ResultSeekListner mSeekListenerHoldingPeriod;
    
    private TextView mTextApprRate;
    private SeekBar  mSeekApprRate;
    private ResultSeekListner mSeekListenerApprRate;
    
    private TextView mTextRent;
    private SeekBar  mSeekRent;
    private ResultSeekListner mSeekListenerRent;
    
    private TextView mTextRentIncrease;
    private SeekBar  mSeekRentIncrease;
    private ResultSeekListner mSeekListenerRentIncrease;    
    
    private static final NumberFormat mFormatCurrancy = NumberFormat.getCurrencyInstance();
    private boolean mbUpdate = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_trade_off);
		
		// Show the Up button in the action bar.
		setupActionBar();
		
		mTextResult  = (TextView) findViewById(R.id.actTof_textViewTitle);
		mGraphResult = (BarGraph)findViewById(R.id.res_bar_graph);
		mGraphResult.setDynamic(true);
		
		mTextHousePrice = (TextView) findViewById(R.id.actTof_home_price_value);
		mSeekHousePrice = (SeekBar) findViewById(R.id.actTof_home_price_seek);
		 
		mTextDownPay = (TextView) findViewById(R.id.actTof_down_pay_value);
		mSeekDownPay = (SeekBar) findViewById(R.id.actTof_down_pay_seek);     
		 
		mTextIntRate = (TextView) findViewById(R.id.actTof_int_rate_value);
		mSeekIntRate = (SeekBar) findViewById(R.id.actTof_int_rate_seek);
		 
		mTextLoanTenr = (TextView) findViewById(R.id.actTof_tenure_value);
		mSeekLoanTenr = (SeekBar) findViewById(R.id.actTof_tenure_seek);
		 
		mTextHoldingPeriod = (TextView) findViewById(R.id.actTof_holdPeriod_value);
		mSeekHoldingPeriod = (SeekBar) findViewById(R.id.actTof_holdPeriod_seek);
		
		mTextApprRate = (TextView) findViewById(R.id.actTof_apprRate_value);
		mSeekApprRate = (SeekBar) findViewById(R.id.actTof_apprRate_seek); 
		
		mTextRent = (TextView) findViewById(R.id.actTof_rent_value);
		mSeekRent = (SeekBar) findViewById(R.id.actTof_rent_seek);
		
		mTextRentIncrease = (TextView) findViewById(R.id.actTof_rent_increase_value);
		mSeekRentIncrease = (SeekBar) findViewById(R.id.actTof_rent_increase_seek);
	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled(true);

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
    public void onStart () {
    	super.onStart();
    	
    	setUpdate(false);
    	
    	CalcBuyOrRent calc = CalcBuyOrRent.getInstance();
    	    	
    	double dVal = (double)calc.getHousePrice();
    	mSeekListenerHousePrice = new ResultSeekListner(mTextHousePrice, DataType.INTEGER, Format.CURRENCY, dVal);
    	setUpSeekBar(dVal, "HousePrice", mSeekHousePrice, mSeekListenerHousePrice);
    	
    	dVal = (double)calc.getDownPay();
    	mSeekListenerDownPay = new ResultSeekListner(mTextDownPay, DataType.INTEGER, Format.CURRENCY, dVal);
    	setUpSeekBar(dVal, "DownPay", mSeekDownPay, mSeekListenerDownPay);
    	
    	dVal = (double)calc.getLoanIntRate();
    	mSeekListenerIntRate = new ResultSeekListner(mTextIntRate, DataType.DOUBLE, Format.PERCENT, dVal);
    	setUpSeekBar(dVal, "IntRate", mSeekIntRate, mSeekListenerIntRate);
    	
    	dVal = (double)calc.getLoanTenr();
    	mSeekListenerLoanTenr = new ResultSeekListner(mTextLoanTenr, DataType.INTEGER, Format.YEARS, dVal);
    	setUpSeekBar(dVal, "LoanTenr", mSeekLoanTenr, mSeekListenerLoanTenr);    
    	
    	dVal = (double)calc.getHoldingPriod();
    	mSeekListenerHoldingPeriod = new ResultSeekListner(mTextHoldingPeriod, DataType.INTEGER, Format.YEARS, dVal);
    	setUpSeekBar(dVal, "HoldingPeriod", mSeekHoldingPeriod, mSeekListenerHoldingPeriod); 
    	
    	dVal = (double)calc.getHomeApprRate();
    	mSeekListenerApprRate = new ResultSeekListner(mTextApprRate, DataType.DOUBLE, Format.PERCENT, dVal);
    	setUpSeekBar(dVal, "ApprRate", mSeekApprRate, mSeekListenerApprRate);
    	
    	dVal = (double) calc.getRent();
    	mSeekListenerRent = new ResultSeekListner(mTextRent, DataType.DOUBLE, Format.CURRENCY, dVal);
    	setUpSeekBar(dVal, "Rent", mSeekRent, mSeekListenerRent);
    	
    	dVal = calc.getRentIncreaseRate();
    	mSeekListenerRentIncrease = new ResultSeekListner(mTextRentIncrease, DataType.DOUBLE, Format.PERCENT, dVal);
    	setUpSeekBar(dVal, "RentIncreaseRate", mSeekRentIncrease, mSeekListenerRentIncrease); 
    	
    	setUpdate(true);
    	
    	SharedPreferences pref = BuyOrRentApp.getInstance().getSharedPreferences(MainFragment.class.getSimpleName(), 
                                                                                 Context.MODE_PRIVATE);
        double dGraphMax = (double) pref.getFloat("GraphProfitLossMaxVal", 0);
        mGraphResult.setMaxVal(dGraphMax);
    	
    	try {
			onDataChanged();
		} catch (ParseException e) {
			e.printStackTrace();
		}
    }
    
    public void onPause() {
    	super.onPause();
    	
    	SharedPreferences pref = BuyOrRentApp.getInstance().getSharedPreferences(MainFragment.class.getSimpleName(), 
                                                                                 Context.MODE_PRIVATE);
    	SharedPreferences.Editor editor = pref.edit();
    	
    	editor.putFloat("HousePriceMax", (float) mSeekListenerHousePrice.getProgressMax());
    	editor.putFloat("HousePriceMin", (float) mSeekListenerHousePrice.getProgressMin());
    	
    	editor.putFloat("DownPayMax", (float) mSeekListenerDownPay.getProgressMax());
    	editor.putFloat("DownPayMin", (float) mSeekListenerDownPay.getProgressMin());
    	
    	editor.putFloat("IntRateMax", (float) mSeekListenerIntRate.getProgressMax());
    	editor.putFloat("IntRateMin", (float) mSeekListenerIntRate.getProgressMin()); 
    	
    	editor.putFloat("LoanTenrMax", (float) mSeekListenerLoanTenr.getProgressMax());
    	editor.putFloat("LoanTenrMin", (float) mSeekListenerLoanTenr.getProgressMin());  
    	
    	editor.putFloat("HoldingPeriodMax", (float) mSeekListenerHoldingPeriod.getProgressMax());
    	editor.putFloat("HoldingPeriodMin", (float) mSeekListenerHoldingPeriod.getProgressMin());
    	
    	editor.putFloat("ApprRateMax", (float) mSeekListenerApprRate.getProgressMax());
    	editor.putFloat("ApprRateMin", (float) mSeekListenerApprRate.getProgressMin());
    	
    	editor.putFloat("RentMax", (float) mSeekListenerRent.getProgressMax());
    	editor.putFloat("RentMin", (float) mSeekListenerRent.getProgressMin());
    	
    	editor.putFloat("YrlyRentIncreaseMax", (float) mSeekListenerRentIncrease.getProgressMax());
    	editor.putFloat("YrlyRentIncreaseMin", (float) mSeekListenerRentIncrease.getProgressMin());
    	
    	editor.putFloat("GraphProfitLossMaxVal", (float) mGraphResult.getMaxVal());
    	
    	editor.commit();
    }
    
    private void setUpSeekBar(double dVal, String strPref, SeekBar seekBar, ResultSeekListner seekListener) {
 	   double dMin = dVal - dVal*0.5;
 	   double dMax = dVal + dVal*0.5;	
 	   
 	   if(strPref != "")
 	   {
 		   SharedPreferences pref = BuyOrRentApp.getInstance().getSharedPreferences(MainFragment.class.getSimpleName(), 
                                                                                    Context.MODE_PRIVATE);
 		  
 		   double dPrefMin = (double) pref.getFloat(strPref + "Min", (float) (dMin));
 		   double dPrefMax = (double) pref.getFloat(strPref + "Max", (float) (dMax));
 		  
		   if(dVal <= dPrefMax && dVal >= dPrefMin)
		   {
			   dMin = dPrefMin;
			   dMax = dPrefMax;
		   }
 	   }
 	     
 	   seekListener.setProgressMin(dMin);
 	   seekListener.setProgressMax(dMax);
 	   
 	   seekBar.setOnSeekBarChangeListener(seekListener);
 	   
 	   double dTemp = (dVal - dMin)/(dMax - dMin) * 100;
 	   int iPos = (int)(dTemp + 0.5);
 	   seekBar.setProgress(iPos);
    }    
    
    private void onDataChanged() throws ParseException {
    	
    	if(!getUpdate())
    		return;
    	
    	CalcBuyOrRent calc = CalcBuyOrRent.getInstance();
    	
    	int iVal = 0;
		iVal = (int) mSeekListenerHousePrice.getVal();
    	calc.setHousePrice(iVal);
    	
		iVal = (int) mSeekListenerDownPay.getVal();
    	calc.setDownPay(iVal);
    	
    	double dVal = mSeekListenerIntRate.getVal();
    	calc.setLoanIntRate(dVal);
    	
    	iVal = (int) mSeekListenerLoanTenr.getVal();
    	calc.setLoanTenr(iVal);
    	
    	iVal = (int) mSeekListenerHoldingPeriod.getVal();
    	calc.setHoldingPriod(iVal);
    	
    	dVal = mSeekListenerApprRate.getVal();
    	calc.setHomeApprRate(dVal);
    	
    	iVal = (int) mSeekListenerRent.getVal();
    	calc.setRent(iVal);
    	
    	dVal = mSeekListenerRentIncrease.getVal();
    	calc.setRentIncreaseRate(dVal);  	
    	
    	calc.calculate();    	
    	updateResults();
    }
    
    private void updateResults() {
    	
    	CalcBuyOrRent calc = CalcBuyOrRent.getInstance();
    	
    	double dBuyProfit    = calc.getBuyProfit();
    	double dRentProfit   = calc.getRentProfit();
    	double dBuyNetProfit = calc.getBuyNetProfit();
    	
    	if(dBuyProfit > dRentProfit)
    	{
            mTextResult.setText(R.string.decisionbuy);
            mTextResult.setTextColor(Color.parseColor("#0099CC"));
    	}
    	else
    	{
    		mTextResult.setText(R.string.decisionrent);
    		mTextResult.setTextColor(Color.parseColor("#FF8800"));
    	}   
    	
    	int iFactMult = 1;
    	if(dBuyProfit < 0)
    		iFactMult = -1;
    	String strBuyProfit = mFormatCurrancy.format(iFactMult * dBuyProfit);
 	
    	iFactMult = 1;
    	if(dRentProfit < 0)
    		iFactMult = -1;
    	String strRentProfit = mFormatCurrancy.format(iFactMult * dRentProfit);
    	
    	iFactMult = 1;
    	if(dBuyNetProfit < 0)
    		iFactMult = -1;
    	String strNetProfit = mFormatCurrancy.format(iFactMult * dBuyNetProfit);
    	
    	ArrayList<Bar> points = new ArrayList<Bar>();
    	Bar barBuy = new Bar();
    	barBuy.setColor(Color.parseColor("#0099CC"));
    	if(dBuyProfit > 0)
    		barBuy.setName(getString(R.string.buyprofit));
    	else
    		barBuy.setName(getString(R.string.buyloss));
    	barBuy.setValue((float) dBuyProfit);
    	barBuy.setValueString(strBuyProfit);
    	
    	Bar barRent = new Bar();
    	barRent.setColor(Color.parseColor("#FF8800"));
    	if(dRentProfit > 0)
    		barRent.setName(getString(R.string.rentprofit));
    	else
    		barRent.setName(getString(R.string.rentloss));
    	barRent.setValue((float) dRentProfit);
    	barRent.setValueString(strRentProfit);
    	
    	Bar barNet = new Bar();
  		barNet.setColor(Color.parseColor("#669900"));
  		if(dBuyNetProfit > 0)
  			barNet.setName(getString(R.string.netprofit));
  		else
  			barNet.setName(getString(R.string.netloss));
    	barNet.setValue((float) dBuyNetProfit);
    	barNet.setValueString(strNetProfit);
    	
    	points.add(barBuy);
    	points.add(barRent);   
    	points.add(barNet);

    	mGraphResult.setBars(points);    	
    }
    
    private void setUpdate(boolean bVal) 
    {
    	mbUpdate = bVal;
    }
    
    private boolean getUpdate() 
    {
    	return mbUpdate;
    }

}
