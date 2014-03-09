package aum.fin.buyorrent;

import java.text.NumberFormat;
import java.util.ArrayList;

import com.echo.holographlibrary.Bar;
import com.echo.holographlibrary.BarGraph;
import com.echo.holographlibrary.PieGraph;
import com.echo.holographlibrary.PieSlice;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Color;
import android.view.MenuItem;
import android.widget.TextView;
import android.support.v4.app.NavUtils;

public class ReportActivity extends Activity {
	
    private TextView mTextTitleResult;
    private BarGraph mGraphResult;	
    
    private TextView mTextTitleBuyExp;
    private PieGraph mGraphBuyExp;
    
    private TextView mTextEmi;
    private TextView mTextTax;
    private TextView mTextInsu;
    private TextView mTextMortInsu;
    private TextView mTextUtilMaint;
    
    private int miColorEmi       = Color.parseColor("#99CC00");
    private int miColorTax       = Color.parseColor("#FFBB33");
    private int miColorInsu      = Color.parseColor("#AA66CC");
    private int miColorMortInsu  = Color.parseColor("#33B5E5");
    private int miColorMaintUtil = Color.parseColor("#FF4444");
    
    private static final NumberFormat mFormatCurrancy = NumberFormat.getCurrencyInstance();    

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_report);
		setupActionBar();
		
		mTextTitleResult  = (TextView)findViewById(R.id.actRep_textViewBarTitle);
		mGraphResult = (BarGraph)findViewById(R.id.rep_bar_graph);
		
		mTextTitleBuyExp  = (TextView)findViewById(R.id.actRep_textViewPieTitle);
		mGraphBuyExp = (PieGraph)findViewById(R.id.rep_pie_graph);
		mGraphBuyExp.setThickness(100);
		
		mTextEmi       = (TextView)findViewById(R.id.actRep_textViewEmi);
		mTextTax       = (TextView)findViewById(R.id.actRep_textViewTax);
		mTextInsu      = (TextView)findViewById(R.id.actRep_textViewInsu);
		mTextMortInsu  = (TextView)findViewById(R.id.actRep_textViewMortInsu);
		mTextUtilMaint = (TextView)findViewById(R.id.actRep_textViewMaint);
		
		findViewById(R.id.actRep_ViewEmi).setBackgroundColor(miColorEmi);
		findViewById(R.id.actRep_ViewTax).setBackgroundColor(miColorTax);
		findViewById(R.id.actRep_ViewInsu).setBackgroundColor(miColorInsu);
		findViewById(R.id.actRep_ViewMortInsu).setBackgroundColor(miColorMortInsu);
		findViewById(R.id.actRep_ViewMaint).setBackgroundColor(miColorMaintUtil);
	}

	private void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled(true);

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
    public void onStart () {
    	super.onStart();
    	
    	updateResults();
    }
	
    private void updateResults() {
    	drawBarGraph();
    	drawPieGraph();
    }	
    
    private void drawBarGraph() {
    	
    	CalcBuyOrRent calc = CalcBuyOrRent.getInstance();
    	calc.calculate();
    	
    	double dBuyProfit    = calc.getBuyProfit();
    	double dRentProfit   = calc.getRentProfit();
    	double dBuyNetProfit = calc.getBuyNetProfit();
    	
    	if(dBuyProfit > dRentProfit)
    	{
    		mTextTitleResult.setText(R.string.decisionbuy);
    		mTextTitleResult.setTextColor(Color.parseColor("#0099CC"));
    	}
    	else
    	{
    		mTextTitleResult.setText(R.string.decisionrent);
    		mTextTitleResult.setTextColor(Color.parseColor("#FF8800"));
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
    
    void drawPieGraph() {
    	
    	CalcBuyOrRent calc = CalcBuyOrRent.getInstance();
    	
    	double dEmi       = calc.getMonthlyPayment();
    	double dTax       = calc.getMonthlyTax();
    	double dInsu      = calc.getMonthlyInsu();
    	double dMortInsu  = calc.getMonthlyMortInsu();
    	double dUtilMaint = calc.getMonthlyUtilMaint();
    	
    	double dTotalExp = dEmi + dTax + dInsu + dMortInsu + dUtilMaint;
    	
    	mTextTitleBuyExp.setText(getString(R.string.monthlyBuyExp) + " " + mFormatCurrancy.format(dTotalExp));
    	
    	mGraphBuyExp.removeSlices();
    	
    	if(dEmi > 0)
    	{
	    	PieSlice slice = new PieSlice();
	    	slice.setColor(miColorEmi);
	    	slice.setValue((float) dEmi);
	    	mGraphBuyExp.addSlice(slice);
    	}
    	mTextEmi.setText(mFormatCurrancy.format(dEmi));
    	
    	if(dTax > 0)
    	{
    		PieSlice slice = new PieSlice();
	    	slice.setColor(miColorTax);
	    	slice.setValue((float) (dTax));
	    	mGraphBuyExp.addSlice(slice);
    	}
    	mTextTax.setText(mFormatCurrancy.format(dTax));
    	
    	if(dInsu > 0)
    	{
    		PieSlice slice = new PieSlice();
	    	slice.setColor(miColorInsu);
	    	slice.setValue((float) (dInsu));
	    	mGraphBuyExp.addSlice(slice);  
    	}
    	mTextInsu.setText(mFormatCurrancy.format(dInsu));
    	
    	if(dMortInsu > 0)
    	{
    		PieSlice slice = new PieSlice();
	    	slice.setColor(miColorMortInsu);
	    	slice.setValue((float) (dMortInsu));
	    	mGraphBuyExp.addSlice(slice);
    	}
    	mTextMortInsu.setText(mFormatCurrancy.format(dMortInsu));
    	
    	if(dUtilMaint > 0)
    	{
	    	PieSlice slice = new PieSlice();
	    	slice.setColor(miColorMaintUtil);
	    	slice.setValue((float) (dUtilMaint));
	    	mGraphBuyExp.addSlice(slice);    
    	}	
    	mTextUtilMaint.setText(mFormatCurrancy.format(dUtilMaint));

    }

}
