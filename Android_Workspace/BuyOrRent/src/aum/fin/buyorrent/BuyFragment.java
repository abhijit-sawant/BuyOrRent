package aum.fin.buyorrent;

import android.R.drawable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;

public class BuyFragment  extends Fragment {
    /** (non-Javadoc)
     * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
     */
    private EditText mTextHousePrice;
    private SeekBar  mSeekHousePrice;
    
    private EditText mTextDownPay;
    private SeekBar  mSeekDownPay;
    
    private EditText mTextIntRate;
    private SeekBar  mSeekIntRate;
    
    private EditText mTextLoanTenr;
    private SeekBar  mSeekLoanTenr;
    
    private EditText mTextHoldingPeriod;
    private SeekBar  mSeekHoldingPeriod;
    
    private EditText mTextYearlyTax;
    private SeekBar  mSeekYearlyTax;
    
    private EditText mTextYearlyMaintain;
    private SeekBar  mSeekYearlyMaintain; 
    
    private EditText mTextYearlyPropIns;
    private SeekBar  mSeekYearlyPropIns;    
    
    private EditText mTextMortIns;
    private SeekBar  mSeekMortIns;
    
    private EditText mTextClosingCost;
    private SeekBar  mSeekClosingCost;
    
    private EditText mTextApprRate;
    private SeekBar  mSeekApprRate;    
    
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	
        if (container == null) { //if fragment's container frame does not exist in layout just return null
            return null;
        }
        
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
		      
		//yearly expenses
		final RelativeLayout loYearlyTitle = (RelativeLayout) viewBuy.findViewById(R.id.actBuy_layoutYearlyTitle);
		final RelativeLayout loYearlyContent = (RelativeLayout) viewBuy.findViewById(R.id.actBuy_layoutYearlyContent);
		final ImageView imgYearlyArrow = (ImageView) viewBuy.findViewById(R.id.actBuy_imageYearlyArrow);
		loYearlyTitle.setOnClickListener(new View.OnClickListener() {
		    public void onClick(View v) {
		    	if (v == loYearlyTitle) {
		    		loYearlyContent.setVisibility(loYearlyContent.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
		    		if(loYearlyContent.getVisibility() == View.VISIBLE)
		    			imgYearlyArrow.setImageResource(drawable.arrow_up_float);
		    		else
		    			imgYearlyArrow.setImageResource(drawable.arrow_down_float);
		    	}
		    } 
		});
		    
		//peripheral expenses
		final RelativeLayout loPeriTitle = (RelativeLayout) viewBuy.findViewById(R.id.actBuy_layoutPeriTitle);
		final RelativeLayout loPeriContent = (RelativeLayout) viewBuy.findViewById(R.id.actBuy_layoutPeriContent);
		final ImageView imgPeriArrow = (ImageView) viewBuy.findViewById(R.id.actBuy_imagePeriArrow);
		loPeriTitle.setOnClickListener(new View.OnClickListener() {
		    public void onClick(View v) {
		    	if (v == loPeriTitle) {
		    		loPeriContent.setVisibility(loPeriContent.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
		    		if(loPeriContent.getVisibility() == View.VISIBLE)
		    			imgPeriArrow.setImageResource(drawable.arrow_up_float);
		    		else
		    			imgPeriArrow.setImageResource(drawable.arrow_down_float);
		    	}
		    } 
		});  	
		 
		linkEditTextNSeekBar(mTextHousePrice, mSeekHousePrice, "300000");
		linkEditTextNSeekBar(mTextDownPay, mSeekDownPay, "100000");
		linkEditTextNSeekBar(mTextIntRate, mSeekIntRate, "0.20");
		linkEditTextNSeekBar(mTextLoanTenr, mSeekLoanTenr, "15");
		linkEditTextNSeekBar(mTextHoldingPeriod, mSeekHoldingPeriod, "15");
		linkEditTextNSeekBar(mTextYearlyTax, mSeekYearlyTax, "2000");
		linkEditTextNSeekBar(mTextYearlyMaintain, mSeekYearlyMaintain, "1200");
		linkEditTextNSeekBar(mTextYearlyPropIns, mSeekYearlyPropIns, "1200");
		linkEditTextNSeekBar(mTextMortIns, mSeekMortIns, "0.52");
		linkEditTextNSeekBar(mTextClosingCost, mSeekClosingCost, "7.00");
		linkEditTextNSeekBar(mTextApprRate, mSeekApprRate, "2.00");

		return viewBuy;
    }
    
    class BuySeekBarListner implements SeekBar.OnSeekBarChangeListener {
    	private double mDblMin = 0;
    	private double mDblMax = 0;
    	private EditText mEditTextLinked;
   	 
    	public BuySeekBarListner(EditText editTextLinked) {
   		 	mEditTextLinked = editTextLinked;
   	 	}
   	 
		 public void setProgressMin(double dMin) { mDblMin = dMin; }
		 public void setProgressMax(double dMax) { mDblMax = dMax; }
		 public EditText getLinkedEditText() { return mEditTextLinked; }
   	 
	   	 public void onStartTrackingTouch(SeekBar seekBar) {}
	   	 public void onStopTrackingTouch(SeekBar seekBar) {}
	   	 public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
	   		 if(fromUser)
	   		 {
	    		 double dTemp = ((progress/100.0) * (mDblMax - mDblMin)) + mDblMin;
	    		 if(mEditTextLinked.getInputType() == (InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_NORMAL))
	    		 {
	    			 int iTemp = (int) dTemp;
	    			 mEditTextLinked.setText(String.valueOf(iTemp));
	    		 }
	    		 else
	    			 mEditTextLinked.setText(String.format("%.2f", dTemp));
	   		 }
	   	 }    	 
    };
    
    class BuyTextWatcher implements TextWatcher {
		private double mDblMin = 0;
		private double mDblMax = 0;
		private SeekBar mSeekBarLinked;
		private BuySeekBarListner mSeekBarLinkedListner;
		 
		public BuyTextWatcher(SeekBar seekBarLinked, BuySeekBarListner seekBarLinkedListner) {
			mSeekBarLinked = seekBarLinked;
			mSeekBarLinkedListner = seekBarLinkedListner;
		}
		 
		public void afterTextChanged(Editable s) {}	 
		public void beforeTextChanged(CharSequence s, int start, int count, int after){}
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			 if(s.toString().length() <= 0)
			 {
				 mSeekBarLinked.setProgress(0);
				 return;
			 }
			 double dInput = Double.valueOf(s.toString());
			 if(dInput < mDblMin ||  dInput > mDblMax)
			 {
				 mDblMin = dInput - dInput*0.5;
				 if(mSeekBarLinkedListner.getLinkedEditText().getInputType() == 
						 (InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_NORMAL))
				 {
					 mDblMin = (int) mDblMin;
				 }
				 if(mDblMin < 0)
					 mDblMin = 0;
				 
				 mDblMax = dInput + dInput*0.5;
				 if(mSeekBarLinkedListner.getLinkedEditText().getInputType() == 
						 (InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_NORMAL))
				 {
					 mDblMax = (int) mDblMax;
				 }    			 
				 
				 mSeekBarLinkedListner.setProgressMin(mDblMin);
				 mSeekBarLinkedListner.setProgressMax(mDblMax);
			 }
			 double dTemp = (dInput - mDblMin)/(mDblMax - mDblMin) * 100;
			 int iPos = (int)(dTemp + 0.5);
			 mSeekBarLinked.setProgress(iPos);
		 }     
    };
    
    private void linkEditTextNSeekBar(EditText text, SeekBar bar, String strDefault) {
    	 BuySeekBarListner barListner = new BuySeekBarListner(text);
	     text.addTextChangedListener(new BuyTextWatcher(bar, barListner));
	     text.setText(strDefault);     
	     bar.setOnSeekBarChangeListener(barListner);     	 
    }
}
