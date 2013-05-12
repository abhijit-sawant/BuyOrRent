package aum.fin.buyorrent;

import android.R.drawable;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import aum.fin.buyorrent.CalcBuyOrRent.OnDataChangedListener;

public class BuyFragment  extends Fragment implements OnDataChangedListener {

    private EditText mTextHousePrice;
    private SeekBar  mSeekHousePrice;
    private EditTextSeekBarLinker mHousePriceLnk;
    
    private EditText mTextDownPay;
    private SeekBar  mSeekDownPay;
    private EditTextSeekBarLinker mDownPayLnk;
    
    private EditText mTextIntRate;
    private SeekBar  mSeekIntRate;
    private EditTextSeekBarLinker mIntRateLnk;
    
    private EditText mTextLoanTenr;
    private SeekBar  mSeekLoanTenr;
    private EditTextSeekBarLinker mLoanTenrLnk;
    
    private EditText mTextHoldingPeriod;
    private SeekBar  mSeekHoldingPeriod;
    private EditTextSeekBarLinker mHoldingPeriodLnk;
    
    private EditText mTextYearlyTax;
    private SeekBar  mSeekYearlyTax;
    private EditTextSeekBarLinker mYearlyTaxLnk;
    
    private EditText mTextYearlyMaintain;
    private SeekBar  mSeekYearlyMaintain; 
    private EditTextSeekBarLinker mYearlyMaintainLnk;
    
    private EditText mTextYearlyPropIns;
    private SeekBar  mSeekYearlyPropIns;    
    private EditTextSeekBarLinker mYearlyPropInsLnk;
    
    private EditText mTextMortIns;
    private SeekBar  mSeekMortIns;
    private EditTextSeekBarLinker mMortInsLnk;
    
    private EditText mTextClosingCost;
    private SeekBar  mSeekClosingCost;
    private EditTextSeekBarLinker mClosingCostLnk;
    
    private EditText mTextApprRate;
    private SeekBar  mSeekApprRate;
    private EditTextSeekBarLinker mApprRateLnk;
    
    private RelativeLayout mLoYearlyContent;
    private ImageView      mImgYearlyArrow;
    private RelativeLayout mLoPeriContent;
	private ImageView      mImgPeriArrow;
    
    private SharedPreferences mPrefrences;
    
    private Boolean mbIsCreated = false;
    
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	
        if (container == null) { //if fragment's container frame does not exist in layout just return null
            return null;
        }
        
        mbIsCreated = false;
        
		final ScrollView viewBuy = (ScrollView)inflater.inflate(R.layout.activity_buy, container, false);
		mTextHousePrice = (EditText) viewBuy.findViewById(R.id.actBuy_editText1);
		mSeekHousePrice = (SeekBar) viewBuy.findViewById(R.id.actBuy_seekBar1);
		 
		mTextDownPay = (EditText) viewBuy.findViewById(R.id.actBuy_editText2);
		mSeekDownPay = (SeekBar) viewBuy.findViewById(R.id.actBuy_seekBar2);     
		 
		mTextIntRate = (EditText) viewBuy.findViewById(R.id.actBuy_editText3);
		mSeekIntRate = (SeekBar) viewBuy.findViewById(R.id.actBuy_seekBar3);
		 
		mTextLoanTenr = (EditText) viewBuy.findViewById(R.id.actBuy_editText4);
		mSeekLoanTenr = (SeekBar) viewBuy.findViewById(R.id.actBuy_seekBar4);
		 
		mTextHoldingPeriod = (EditText) viewBuy.findViewById(R.id.actBuy_editText5);
		mSeekHoldingPeriod = (SeekBar) viewBuy.findViewById(R.id.actBuy_seekBar5);
		 
		mTextYearlyTax = (EditText) viewBuy.findViewById(R.id.actBuy_editText6);
		mSeekYearlyTax = (SeekBar) viewBuy.findViewById(R.id.actBuy_seekBar6);   
		 
		mTextYearlyMaintain = (EditText) viewBuy.findViewById(R.id.actBuy_editText7);
		mSeekYearlyMaintain = (SeekBar) viewBuy.findViewById(R.id.actBuy_seekBar7); 
		 
		mTextYearlyPropIns = (EditText) viewBuy.findViewById(R.id.actBuy_editText8);
		mSeekYearlyPropIns = (SeekBar) viewBuy.findViewById(R.id.actBuy_seekBar8);
		 
		mTextMortIns = (EditText) viewBuy.findViewById(R.id.actBuy_editText9);
		mSeekMortIns = (SeekBar) viewBuy.findViewById(R.id.actBuy_seekBar9);    
		 
		mTextClosingCost = (EditText) viewBuy.findViewById(R.id.actBuy_editText10);
		mSeekClosingCost = (SeekBar) viewBuy.findViewById(R.id.actBuy_seekBar10); 
		 
		mTextApprRate = (EditText) viewBuy.findViewById(R.id.actBuy_editText11);
		mSeekApprRate = (SeekBar) viewBuy.findViewById(R.id.actBuy_seekBar11);  
		
		mPrefrences = ((MainFragment) getActivity()).getPreferences(Context.MODE_PRIVATE);
		      
		//yearly expenses
		final RelativeLayout loYearlyTitle = (RelativeLayout) viewBuy.findViewById(R.id.actBuy_layoutYearlyTitle);
		mLoYearlyContent = (RelativeLayout) viewBuy.findViewById(R.id.actBuy_layoutYearlyContent);
		mImgYearlyArrow  = (ImageView) viewBuy.findViewById(R.id.actBuy_imageYearlyArrow);
		loYearlyTitle.setOnClickListener(new View.OnClickListener() {
		    public void onClick(View v) {
		    	if (v == loYearlyTitle) {
		    		mLoYearlyContent.setVisibility(mLoYearlyContent.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
		    		if(mLoYearlyContent.getVisibility() == View.VISIBLE)
		    			mImgYearlyArrow.setImageResource(drawable.arrow_up_float);
		    		else
		    			mImgYearlyArrow.setImageResource(drawable.arrow_down_float);
		    	}
		    } 
		});
		    
		//peripheral expenses
		final RelativeLayout loPeriTitle = (RelativeLayout) viewBuy.findViewById(R.id.actBuy_layoutPeriTitle);
		mLoPeriContent = (RelativeLayout) viewBuy.findViewById(R.id.actBuy_layoutPeriContent);
		mImgPeriArrow  = (ImageView) viewBuy.findViewById(R.id.actBuy_imagePeriArrow);
		loPeriTitle.setOnClickListener(new View.OnClickListener() {
		    public void onClick(View v) {
		    	if (v == loPeriTitle) {
		    		mLoPeriContent.setVisibility(mLoPeriContent.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
		    		if(mLoPeriContent.getVisibility() == View.VISIBLE)
		    			mImgPeriArrow.setImageResource(drawable.arrow_up_float);
		    		else
		    			mImgPeriArrow.setImageResource(drawable.arrow_down_float);
		    	}
		    } 
		});  
		
		
		
        CalcBuyOrRent calc = ((MainFragment) getActivity()).getCalcBuyOrRent();
		
		mHousePriceLnk     = new EditTextSeekBarLinker(mTextHousePrice, mSeekHousePrice, calc.getHousePrice(), "HousePrice");
		mDownPayLnk        = new EditTextSeekBarLinker(mTextDownPay, mSeekDownPay, calc.getDownPay(), "DownPay");
		mIntRateLnk        = new EditTextSeekBarLinker(mTextIntRate, mSeekIntRate, calc.getLoanIntRate(), "IntRate");
		mLoanTenrLnk       = new EditTextSeekBarLinker(mTextLoanTenr, mSeekLoanTenr, calc.getLoanTenr(), "LoanTenr");
		mHoldingPeriodLnk  = new EditTextSeekBarLinker(mTextHoldingPeriod, mSeekHoldingPeriod, calc.getHoldingPriod(), "HoldingPeriod");
		mYearlyTaxLnk      = new EditTextSeekBarLinker(mTextYearlyTax, mSeekYearlyTax, calc.getYearlyTax(), "YearlyTax");
		mYearlyMaintainLnk = new EditTextSeekBarLinker(mTextYearlyMaintain, mSeekYearlyMaintain, calc.getYearlyMaintain(), "YearlyMaintain");
		mYearlyPropInsLnk  = new EditTextSeekBarLinker(mTextYearlyPropIns, mSeekYearlyPropIns, calc.getYearlyPropIns(), "YearlyPropIns");
		mMortInsLnk        = new EditTextSeekBarLinker(mTextMortIns, mSeekMortIns, calc.getMortIns(), "MortIns");
		mClosingCostLnk    = new EditTextSeekBarLinker(mTextClosingCost, mSeekClosingCost, calc.getClosingCost(), "ClosingCost");
		mApprRateLnk       = new EditTextSeekBarLinker(mTextApprRate, mSeekApprRate, calc.getHomeApprRate(), "ApprRate");
		
		mbIsCreated = true;
		return viewBuy;
    }
    
    public void onResume() {
    	super.onResume();
    	
    	//set visibility state of yearly expenses layout
		int iLoYearlyState = mPrefrences.getInt("LoYearlyState", View.INVISIBLE);
		mLoYearlyContent.setVisibility(iLoYearlyState == View.VISIBLE ? View.VISIBLE : View.GONE);
		if(iLoYearlyState == View.VISIBLE)
			mImgYearlyArrow.setImageResource(drawable.arrow_up_float);
		else
			mImgYearlyArrow.setImageResource(drawable.arrow_down_float);
		
		//set visibility state of yearly expenses layout
		int iLoPeriState = mPrefrences.getInt("LoPeriState", View.INVISIBLE);
		mLoPeriContent.setVisibility(iLoPeriState == View.VISIBLE ? View.VISIBLE : View.GONE);
		if(iLoPeriState == View.VISIBLE)
			mImgPeriArrow.setImageResource(drawable.arrow_up_float);
		else
			mImgPeriArrow.setImageResource(drawable.arrow_down_float);
    }
    
    public void onPause() {
    	super.onPause();
    	
    	SharedPreferences.Editor editor = mPrefrences.edit();
    	editor.putInt("LoYearlyState", mLoYearlyContent.getVisibility());
    	editor.putInt("LoPeriState",   mLoPeriContent.getVisibility());
    	
    	editor.putFloat("HousePriceMax",     (float) mHousePriceLnk.getTextWatcher().getMax());
    	editor.putFloat("HousePriceMin",     (float) mHousePriceLnk.getTextWatcher().getMin());
    	editor.putFloat("DownPayMax",        (float) mDownPayLnk.getTextWatcher().getMax());
    	editor.putFloat("DownPayMin",        (float) mDownPayLnk.getTextWatcher().getMin());
    	editor.putFloat("IntRateMax",        (float) mIntRateLnk.getTextWatcher().getMax());
    	editor.putFloat("IntRateMin",        (float) mIntRateLnk.getTextWatcher().getMin());
    	editor.putFloat("LoanTenrMax",       (float) mLoanTenrLnk.getTextWatcher().getMax());
    	editor.putFloat("LoanTenrMin",       (float) mLoanTenrLnk.getTextWatcher().getMin());
    	editor.putFloat("HoldingPeriodMax",  (float) mHoldingPeriodLnk.getTextWatcher().getMax());
    	editor.putFloat("HoldingPeriodMin",  (float) mHoldingPeriodLnk.getTextWatcher().getMin());
    	editor.putFloat("YearlyTaxMax",      (float) mYearlyTaxLnk.getTextWatcher().getMax());
    	editor.putFloat("YearlyTaxMin",      (float) mYearlyTaxLnk.getTextWatcher().getMin());
    	editor.putFloat("YearlyMaintainMax", (float) mYearlyMaintainLnk.getTextWatcher().getMax());
    	editor.putFloat("YearlyMaintainMin", (float) mYearlyMaintainLnk.getTextWatcher().getMin());
    	editor.putFloat("YearlyPropInsMax",  (float) mYearlyPropInsLnk.getTextWatcher().getMax());
    	editor.putFloat("YearlyPropInsMin",  (float) mYearlyPropInsLnk.getTextWatcher().getMin());
    	editor.putFloat("MortInsMax",        (float) mMortInsLnk.getTextWatcher().getMax());
    	editor.putFloat("MortInsMin",        (float) mMortInsLnk.getTextWatcher().getMin());
    	editor.putFloat("ClosingCostMax",    (float) mClosingCostLnk.getTextWatcher().getMax());
    	editor.putFloat("ClosingCostMin",    (float) mClosingCostLnk.getTextWatcher().getMin());
    	editor.putFloat("ApprRateMax",       (float) mApprRateLnk.getTextWatcher().getMax());
    	editor.putFloat("ApprRateMin",       (float) mApprRateLnk.getTextWatcher().getMin());
    	
    	editor.commit();
    }
    
    public void onDataChanged() {
    	if(!mbIsCreated)
    		return;
    	
    	CalcBuyOrRent calc = ((MainFragment) getActivity()).getCalcBuyOrRent();
    	
    	calc.setHousePrice(Integer.valueOf(mTextHousePrice.getText().toString()));
    	calc.setDownPay(Integer.valueOf(mTextDownPay.getText().toString()));
    	calc.setLoanIntRate(Double.valueOf(mTextIntRate.getText().toString()));
    	calc.setLoanTenr(Integer.valueOf(mTextLoanTenr.getText().toString()));
    	calc.setHoldingPriod(Integer.valueOf(mTextHoldingPeriod.getText().toString()));
    	calc.setYearlyTax(Integer.valueOf(mTextYearlyTax.getText().toString()));
    	calc.setYearlyMaintain(Integer.valueOf(mTextYearlyMaintain.getText().toString()));
    	calc.setYearlyPropIns(Integer.valueOf(mTextYearlyPropIns.getText().toString()));
    	calc.setMortIns(Double.valueOf(mTextMortIns.getText().toString()));
    	calc.setClosingCost(Double.valueOf(mTextClosingCost.getText().toString()));
    	calc.setHomeApprRate(Double.valueOf(mTextApprRate.getText().toString()));
    }
    
}
