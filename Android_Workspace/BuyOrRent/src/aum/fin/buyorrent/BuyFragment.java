package aum.fin.buyorrent;

import android.R.drawable;
import android.support.v4.app.Fragment;
import android.text.Editable;
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
    private double dHousePriceMax = 600000;
    private double dHousePriceMin = 50000;
    
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        if (container == null) { //if fragment's container frame does not exist in layout just return null
            return null;
        }
     final ScrollView viewBuy = (ScrollView)inflater.inflate(R.layout.activity_buy, container, false);
     
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
     
     EditText textHousePrice = (EditText) viewBuy.findViewById(R.id.actBuy_editText1);
     textHousePrice.addTextChangedListener(new TextWatcher() {
    	 public void afterTextChanged(Editable s) {}	 
    	 public void beforeTextChanged(CharSequence s, int start, int count, int after){}
    	 public void onTextChanged(CharSequence s, int start, int before, int count) {
    		 SeekBar seekHousePrice = (SeekBar) viewBuy.findViewById(R.id.actBuy_seekBar1);
    		 seekHousePrice.setMax(1000);
    		 if(s.toString().length() <= 0)
    		 {
    			 seekHousePrice.setProgress(0);
    			 return;
    		 }
    		 double dInput = Double.valueOf(s.toString()).doubleValue();
    		 double t1 = dInput - dHousePriceMin;
    		 double t2 = dHousePriceMax - dHousePriceMin;
    		 double t3 = (t1/t2) * 1000;
    		 int iPos = (int)(t3 + 0.5);
    		 seekHousePrice.setProgress(iPos);
    	 }
     }); 
     textHousePrice.setText("");
     
     return viewBuy;
    }
}
