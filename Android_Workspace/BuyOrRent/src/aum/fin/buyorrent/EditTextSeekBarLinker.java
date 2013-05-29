package aum.fin.buyorrent;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.SeekBar;

public class EditTextSeekBarLinker {
	
	private BuySeekBarListner mBarListner  = null;
	private BuyTextWatcher    mTextWatcher = null;
	
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
	    		 setTextVal(mEditTextLinked, dTemp);
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
		
		public double getMax() {
			return mDblMax;
		}
		
		public double getMin() {
			return mDblMin;
		}
		
		public void setMax(double dVal) {
			mDblMax = dVal;
		}
		
		public void setMin(double dVal) {
			mDblMin = dVal;
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
			 
    		 MainFragment parentFragment = (MainFragment) mSeekBarLinked.getContext();
    		 parentFragment.calcBuyOrRent();
		 }     
    };
    
    public EditTextSeekBarLinker(EditText text, SeekBar bar, double dVal, String strPref) {
  	     mBarListner  = new BuySeekBarListner(text);
  	     mTextWatcher = new BuyTextWatcher(bar, mBarListner);
  	     
  	     text.addTextChangedListener(mTextWatcher);
  	     bar.setOnSeekBarChangeListener(mBarListner);
     
  	     setValMinMax(text, dVal, strPref);
  }
    
   public BuySeekBarListner getSeekBarListner() {
	   return mBarListner;
   }
   
   public BuyTextWatcher getTextWatcher() {
	   return mTextWatcher;
   }
   
   public void setValMinMax(EditText text, double dVal, String strPref) {
	   double dMin = dVal - dVal*0.5;
	   double dMax = dVal + dVal*0.5;	   
	   if(strPref != "")
	   {
		   SharedPreferences pref = ((MainFragment) text.getContext()).getPreferences(Context.MODE_PRIVATE);
		   dMin = (double) pref.getFloat(strPref + "Min", (float) (dMin));
		   dMax = (double) pref.getFloat(strPref + "Max", (float) (dMax));
	   }
	     
	   mBarListner.setProgressMin(dMin);
	   mBarListner.setProgressMax(dMax);	     
	   mTextWatcher.setMin(dMin);
	   mTextWatcher.setMax(dMax);
	     
	   setTextVal(text, dVal);
   }
   
   static public void setTextVal(EditText text, double dVal) {	   
	   if(text.getInputType() == (InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_NORMAL))
		   text.setText(String.valueOf((int) dVal));
	   else
		   text.setText(String.format("%.2f", dVal));
   }

}
