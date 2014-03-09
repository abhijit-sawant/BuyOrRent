package aum.fin.buyorrent;

import android.R.drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.app.Fragment;
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
import aum.fin.buyorrent.CalcBuyOrRent.OnDataChangedListener;

public class BuyFragment  extends Fragment implements OnDataChangedListener {
	
	class BuyTextWatcher implements TextWatcher {
		
    	public void afterTextChanged(Editable s) {}	 		
		public void beforeTextChanged(CharSequence s, int start, int count, int after){}
		
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			onDataChanged();
		 }     
    };
    
    private EditText mTextHousePrice;
    private EditText mTextDownPay;
    private EditText mTextIntRate;
    private EditText mTextLoanTenr;
    private EditText mTextHoldingPeriod;
    private EditText mTextApprRate; 
    
    private EditText mTextUtilities;    
    private EditText mTextYearlyTax;
    private EditText mTextYearlyMaintain;
    private EditText mTextYearlyPropIns;
    
    private EditText mTextMortIns;
    private EditText mTextClosingCost;
    private EditText mTextMovingInCost;
   
    private RelativeLayout mLoYearlyContent;
    private ImageView      mImgYearlyArrow;
    private RelativeLayout mLoPeriContent;
	private ImageView      mImgPeriArrow;
    
    private SharedPreferences mPrefrences;
    
    private Boolean mbIsCreated = false;
    private boolean mbUpdate = true;
    
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	
        if (container == null) { //if fragment's container frame does not exist in layout just return null
            return null;
        }
        
        mbIsCreated = false;
        
		final ScrollView viewBuy = (ScrollView)inflater.inflate(R.layout.activity_buy, container, false);
		mTextHousePrice = (EditText) viewBuy.findViewById(R.id.actBuy_editText1);
		mTextDownPay = (EditText) viewBuy.findViewById(R.id.actBuy_editText2);
		mTextIntRate = (EditText) viewBuy.findViewById(R.id.actBuy_editText3);
		mTextLoanTenr = (EditText) viewBuy.findViewById(R.id.actBuy_editText4);
		mTextHoldingPeriod = (EditText) viewBuy.findViewById(R.id.actBuy_editText5);
		mTextApprRate = (EditText) viewBuy.findViewById(R.id.actBuy_editText11);		
		mTextUtilities = (EditText) viewBuy.findViewById(R.id.actBuy_editText13);
		
		mTextYearlyTax      = (EditText) viewBuy.findViewById(R.id.actBuy_editText6);
		mTextYearlyMaintain = (EditText) viewBuy.findViewById(R.id.actBuy_editText7);
		mTextYearlyPropIns  = (EditText) viewBuy.findViewById(R.id.actBuy_editText8);
		 
		mTextMortIns      = (EditText) viewBuy.findViewById(R.id.actBuy_editText9);
		mTextClosingCost  = (EditText) viewBuy.findViewById(R.id.actBuy_editText10);
		mTextMovingInCost = (EditText) viewBuy.findViewById(R.id.actBuy_editText12);

		mPrefrences = ((MainFragment) getActivity()).getPreferences(Context.MODE_PRIVATE);
		      
		//yearly expenses
		final RelativeLayout loYearlyBorder = (RelativeLayout) viewBuy.findViewById(R.id.actBuy_layoutYearlyBorder);
		mLoYearlyContent = (RelativeLayout) viewBuy.findViewById(R.id.actBuy_layoutYearlyContent);
		mImgYearlyArrow  = (ImageView) viewBuy.findViewById(R.id.actBuy_imageYearlyArrow);
		loYearlyBorder.setOnClickListener(new View.OnClickListener() {
		    public void onClick(View v) {
		    	if (v == loYearlyBorder) {
		    		mLoYearlyContent.setVisibility(mLoYearlyContent.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
		    		if(mLoYearlyContent.getVisibility() == View.VISIBLE)
		    			mImgYearlyArrow.setImageResource(drawable.arrow_up_float);
		    		else
		    			mImgYearlyArrow.setImageResource(drawable.arrow_down_float);
		    	}
		    } 
		});
		    
		//peripheral expenses
		final RelativeLayout loPeriBorder = (RelativeLayout) viewBuy.findViewById(R.id.actBuy_layoutPeriBorder);
		mLoPeriContent = (RelativeLayout) viewBuy.findViewById(R.id.actBuy_layoutPeriContent);
		mImgPeriArrow  = (ImageView) viewBuy.findViewById(R.id.actBuy_imagePeriArrow);
		loPeriBorder.setOnClickListener(new View.OnClickListener() {
		    public void onClick(View v) {
		    	if (v == loPeriBorder) {
		    		mLoPeriContent.setVisibility(mLoPeriContent.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
		    		if(mLoPeriContent.getVisibility() == View.VISIBLE)
		    			mImgPeriArrow.setImageResource(drawable.arrow_up_float);
		    		else
		    			mImgPeriArrow.setImageResource(drawable.arrow_down_float);
		    	}
		    } 
		});  
		
		mbIsCreated = true;
		return viewBuy;
    }
    
    public void onResetToDefault() {
    	mbUpdate = false;
    	
    	CalcBuyOrRent calc = CalcBuyOrRent.getInstance(); 
    	
    	mTextHousePrice.setText(String.valueOf(calc.getHousePrice()));
    	mTextDownPay.setText(String.valueOf(calc.getDownPay()));
    	mTextIntRate.setText(String.format("%.2f", calc.getLoanIntRate()));
    	mTextLoanTenr.setText(String.valueOf(calc.getLoanTenr()));
    	mTextHoldingPeriod.setText(String.valueOf(calc.getHoldingPriod()));
    	mTextApprRate.setText(String.format("%.2f", calc.getHomeApprRate()));    	
    	mTextUtilities.setText(String.valueOf(calc.getBuyUtilities()));
    	
    	mTextYearlyTax.setText(String.valueOf(calc.getYearlyTax()));
    	mTextYearlyMaintain.setText(String.valueOf(calc.getYearlyMaintain()));
    	mTextYearlyPropIns.setText( String.valueOf(calc.getYearlyPropIns()));
    	
    	mTextMortIns.setText(String.format("%.2f", calc.getMortIns()));
    	mTextClosingCost.setText( String.format("%.2f", calc.getClosingCost()));
    	mTextMovingInCost.setText(String.valueOf(calc.getMovingInCost()));
    	
    	mbUpdate = true;
    }
    
    public void onStart () {
    	super.onStart();
    	
    	CalcBuyOrRent calc = CalcBuyOrRent.getInstance();		
 		
    	mbUpdate = false;
    	
    	mTextHousePrice.setText(String.valueOf(calc.getHousePrice()));
    	mTextDownPay.setText(String.valueOf(calc.getDownPay()));
    	mTextIntRate.setText(String.format("%.2f", calc.getLoanIntRate()));
    	mTextLoanTenr.setText(String.valueOf(calc.getLoanTenr()));
    	mTextHoldingPeriod.setText(String.valueOf(calc.getHoldingPriod()));
    	mTextApprRate.setText(String.format("%.2f", calc.getHomeApprRate()));    	
 		mTextUtilities.setText(String.valueOf(calc.getBuyUtilities()));
    	
 		mTextYearlyTax.setText(String.valueOf(calc.getYearlyTax()));
    	mTextYearlyMaintain.setText(String.valueOf(calc.getYearlyMaintain()));
    	mTextYearlyPropIns.setText( String.valueOf(calc.getYearlyPropIns()));
 		
    	mTextMortIns.setText(String.format("%.2f", calc.getMortIns()));
    	mTextClosingCost.setText( String.format("%.2f", calc.getClosingCost()));
    	mTextMovingInCost.setText(String.valueOf(calc.getMovingInCost()));
    	
    	mTextHousePrice.addTextChangedListener(new BuyTextWatcher());
    	mTextDownPay.addTextChangedListener(new BuyTextWatcher());
    	mTextIntRate.addTextChangedListener(new BuyTextWatcher());
    	mTextLoanTenr.addTextChangedListener(new BuyTextWatcher());
    	mTextHoldingPeriod.addTextChangedListener(new BuyTextWatcher());
    	mTextApprRate.addTextChangedListener(new BuyTextWatcher());
    	mTextUtilities.addTextChangedListener(new BuyTextWatcher());
    	
    	mTextYearlyTax.addTextChangedListener(new BuyTextWatcher());		 
		mTextYearlyMaintain.addTextChangedListener(new BuyTextWatcher());		 
		mTextYearlyPropIns.addTextChangedListener(new BuyTextWatcher());
		 
		mTextMortIns.addTextChangedListener(new BuyTextWatcher());		 
		mTextClosingCost.addTextChangedListener(new BuyTextWatcher());		 
		mTextMovingInCost.addTextChangedListener(new BuyTextWatcher()); 
		
		mbUpdate = true;
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
		
		//set visibility state of peripheral expenses layout
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
    	
    	editor.commit();
    }
    
    public void onDataChanged() {
    	if(!mbIsCreated || !mbUpdate)
    		return;
    	
    	CalcBuyOrRent calc = CalcBuyOrRent.getInstance();
    	
    	try {
	    	calc.setHousePrice(Integer.valueOf(mTextHousePrice.getText().toString()));
	    	calc.setDownPay(Integer.valueOf(mTextDownPay.getText().toString()));
	    	calc.setLoanIntRate(Double.valueOf(mTextIntRate.getText().toString()));
	    	calc.setLoanTenr(Integer.valueOf(mTextLoanTenr.getText().toString()));
	    	calc.setHoldingPriod(Integer.valueOf(mTextHoldingPeriod.getText().toString()));
	    	calc.setHomeApprRate(Double.valueOf(mTextApprRate.getText().toString()));
	    	
	    	calc.setBuyUtilities(Integer.valueOf(mTextUtilities.getText().toString()));
	    	
	    	calc.setYearlyTax(Integer.valueOf(mTextYearlyTax.getText().toString()));
	    	calc.setYearlyMaintain(Integer.valueOf(mTextYearlyMaintain.getText().toString()));
	    	calc.setYearlyPropIns(Integer.valueOf(mTextYearlyPropIns.getText().toString()));
	    	calc.setMortIns(Double.valueOf(mTextMortIns.getText().toString()));
	    	calc.setClosingCost(Double.valueOf(mTextClosingCost.getText().toString()));
	    	calc.setMovingInCost(Integer.valueOf(mTextMovingInCost.getText().toString()));
    	} catch(NumberFormatException e) {
    		//do nothing
    	}
    }
    
}
