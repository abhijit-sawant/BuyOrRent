package aum.fin.buyorrent;

import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.SeekBar;

public class EditTextSeekBarLinker {
	
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
			 
    		 MainFragment parentFragment = (MainFragment) mSeekBarLinked.getContext();
    		 parentFragment.calcBuyOrRent();
		 }     
    };
    
    public void linkEditTextNSeekBar(EditText text, SeekBar bar, String strDefault) {
   	     BuySeekBarListner barListner = new BuySeekBarListner(text);
	     text.addTextChangedListener(new BuyTextWatcher(bar, barListner));
	     text.setText(strDefault);     
	     bar.setOnSeekBarChangeListener(barListner);     	 
   }

}
