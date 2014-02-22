package aum.fin.buyorrent;

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
import android.widget.ScrollView;
import aum.fin.buyorrent.CalcBuyOrRent.OnDataChangedListener;

public class RentFragment extends Fragment implements OnDataChangedListener {
	
	class RentTextWatcher implements TextWatcher {
		public void afterTextChanged(Editable s) {}	 
		public void beforeTextChanged(CharSequence s, int start, int count, int after){}
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			onDataChanged();
		 }     
    };
	
    private EditText mTextRent;    
    private EditText mTextYrlyRentIncrease;
    private EditText mTextRentIns;
    private EditText mTextUtility;
    private EditText mTextSavingReturn;
    
    private SharedPreferences mPrefrences;
    
    private boolean mbIsCreated = false;
    private boolean mbUpdate = true;
    
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	    	
        if (container == null) {
            return null;
        }
        
        mbIsCreated = false;
    	
        final ScrollView viewRent = (ScrollView)inflater.inflate(R.layout.activity_rent, container, false);
        
        mTextRent = (EditText) viewRent.findViewById(R.id.actRent_editText1);
        mTextYrlyRentIncrease = (EditText) viewRent.findViewById(R.id.actRent_editText2);        
        mTextRentIns = (EditText) viewRent.findViewById(R.id.actRent_editText3);
        mTextUtility = (EditText) viewRent.findViewById(R.id.actRent_editText4);
        mTextSavingReturn = (EditText) viewRent.findViewById(R.id.actRent_editText5);       
        
        mPrefrences = ((MainFragment) getActivity()).getPreferences(Context.MODE_PRIVATE);
        
        mbIsCreated = true;		
        return viewRent;
    }
    
    public void onResetToDefault() {
    	mbUpdate = false;
    	
    	CalcBuyOrRent calc = CalcBuyOrRent.getInstance();
    	
		mTextRent.setText(String.valueOf(calc.getRent()));
		mTextYrlyRentIncrease.setText(String.format("%.2f", calc.getRentIncreaseRate()));
    	
    	mTextRentIns.setText(String.valueOf(calc.getRentIns()));
    	mTextUtility.setText(String.valueOf(calc.getUtility()));
		mTextSavingReturn.setText(String.format("%.2f", calc.getSavingReturnRate()));
		
		mbUpdate = true;
    }
    
    public void onStart () {
    	super.onStart();
    	
   		((MainFragment) getActivity()).setUpdateResult(false);
   		
   		CalcBuyOrRent calc = CalcBuyOrRent.getInstance();
		
		mTextRent.setText(String.valueOf(calc.getRent()));
		mTextRent.addTextChangedListener(new RentTextWatcher());
		
		mTextYrlyRentIncrease.setText(String.format("%.2f", calc.getRentIncreaseRate()));
		mTextYrlyRentIncrease.addTextChangedListener(new RentTextWatcher());
		
    	mTextRentIns.setText(String.valueOf(calc.getRentIns()));
    	mTextRentIns.addTextChangedListener(new RentTextWatcher());
    	
		mTextUtility.setText(String.valueOf(calc.getUtility()));
		mTextUtility.addTextChangedListener(new RentTextWatcher());
		
		mTextSavingReturn.setText(String.format("%.2f", calc.getSavingReturnRate()));
		mTextSavingReturn.addTextChangedListener(new RentTextWatcher());
   		
   		((MainFragment) getActivity()).setUpdateResult(true);
    }
    
    public void onDataChanged() {
    	if(!mbIsCreated || !mbUpdate)
    		return;
    	
    	CalcBuyOrRent calc = CalcBuyOrRent.getInstance();
    	
    	try {
	    	calc.setRent(Integer.valueOf(mTextRent.getText().toString()));
	    	calc.setRentIncreaseRate(Double.valueOf(mTextYrlyRentIncrease.getText().toString()));
	    	calc.setRentIns(Integer.valueOf(mTextRentIns.getText().toString()));
	    	calc.setUtility(Integer.valueOf(mTextUtility.getText().toString()));
	    	calc.setSavingReturnRate(Double.valueOf(mTextSavingReturn.getText().toString()));
    	} catch(NumberFormatException e) {
    		//do nothing
    	}
    }
}

