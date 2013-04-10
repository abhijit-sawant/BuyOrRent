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
    private int mIntHousePriceMax = 600000;
    private int mIntHousePriceMin = 50000;
    private int mIntHousePrice    = 300000;
    
    private EditText mTextHousePrice;
    private SeekBar  mSeekHousePrice;
    
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        if (container == null) { //if fragment's container frame does not exist in layout just return null
            return null;
        }
        
     final ScrollView viewBuy = (ScrollView)inflater.inflate(R.layout.activity_buy, container, false);
     mTextHousePrice = (EditText) viewBuy.findViewById(R.id.actBuy_editText1);
     mSeekHousePrice = (SeekBar) viewBuy.findViewById(R.id.actBuy_seekBar1);
          
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
     } });
        
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
     } });
     
     class BuyTextWatcher implements TextWatcher {
    	 private double mDblMin = 0;
    	 private double mDblMax = 0;
    	 private SeekBar mSeekBarLinked;
    	 
    	 public BuyTextWatcher(double dMin, double dMax, SeekBar seekBarLinked) {
    		 mDblMin = dMin;
    		 mDblMax = dMax;
    		 mSeekBarLinked = seekBarLinked;
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
    		 double dTemp = (dInput - mDblMin)/(mDblMax - mDblMin) * 100;
    		 int iPos = (int)(dTemp + 0.5);
    		 mSeekBarLinked.setProgress(iPos);
    	 }
     
     };
     
     class BuySeekBarListner implements SeekBar.OnSeekBarChangeListener {
    	 private double mDblMin = 0;
    	 private double mDblMax = 0;
    	 private EditText mEditTextLinked;
    	 
    	 public BuySeekBarListner(double dMin, double dMax, EditText editTextLinked) {
    		 mDblMin = dMin;
    		 mDblMax = dMax;
    		 mEditTextLinked = editTextLinked;
    	 }
    	 
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
	    			 mEditTextLinked.setText(String.valueOf(dTemp));
    		 }
    	 }    	 
     };
     
     mTextHousePrice.addTextChangedListener(new BuyTextWatcher(mIntHousePriceMin, mIntHousePriceMax, mSeekHousePrice));
     mTextHousePrice.setText("300000");
     
     mSeekHousePrice.setOnSeekBarChangeListener(new BuySeekBarListner(mIntHousePriceMin, mIntHousePriceMax, mTextHousePrice));
          
     return viewBuy;
    }
}
