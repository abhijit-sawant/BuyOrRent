package aum.fin.buyorrent;

import android.text.Editable;
import android.text.TextWatcher;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ScrollView;
import aum.fin.buyorrent.CalcBuyOrRent.OnDataChangedListener;

public class TaxFragment extends Fragment implements OnDataChangedListener {
	
	 class TaxTextWatcher implements TextWatcher {
     		public void afterTextChanged(Editable s) {}	 
			public void beforeTextChanged(CharSequence s, int start, int count, int after){}
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				onDataChanged();
			 }     
	    };
	
    private EditText mTextTaxBracket;
    private boolean mbIsCreated = false;
    private boolean mbUpdate = true;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	
        if (container == null) {
            return null;
        }
        
        mbIsCreated = false;
       
        ScrollView viewTax = (ScrollView)inflater.inflate(R.layout.activity_tax, container, false);
       
       mTextTaxBracket = (EditText) viewTax.findViewById(R.id.actTax_editText1);
              
	   mbIsCreated = true;
	  
	   return viewTax;
    }
    
    public void onResetToDefault() {
    	CalcBuyOrRent calc = CalcBuyOrRent.getInstance();
    	
    	mTextTaxBracket.setText(String.format("%.2f", calc.getTaxBracket()));
    }
    
    public void onStart () {
    	super.onStart();
    	
   		CalcBuyOrRent calc = CalcBuyOrRent.getInstance();
   		
   		mbUpdate = false;
   		
   		mTextTaxBracket.setText(String.format("%.2f", calc.getTaxBracket()));
   		mTextTaxBracket.addTextChangedListener(new TaxTextWatcher());
   		
   		mbUpdate = true;
    }
    
    public void onDataChanged() {
    	if(!mbIsCreated || !mbUpdate)
    		return;
    	
    	CalcBuyOrRent calc = CalcBuyOrRent.getInstance();
    	
    	double dVal = 0;
    	String strVal = mTextTaxBracket.getText().toString();
    	if(strVal.length() > 0)
    	{
    		try {
    			dVal = Double.valueOf(strVal);
    		} catch(NumberFormatException e) {
        		//do nothing
        	}
    	}
    		
    	calc.setTaxBracket(dVal);
    }
}
