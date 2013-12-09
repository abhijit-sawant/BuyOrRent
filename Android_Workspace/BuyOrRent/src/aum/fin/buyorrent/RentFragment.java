package aum.fin.buyorrent;

import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.SeekBar;
import aum.fin.buyorrent.CalcBuyOrRent.OnDataChangedListener;

public class RentFragment extends Fragment implements OnDataChangedListener {
	
	class RentTextWatcher implements TextWatcher {
		public void afterTextChanged(Editable s) {}	 
		public void beforeTextChanged(CharSequence s, int start, int count, int after){}
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			 ((MainFragment) getActivity()).calcBuyOrRent();
		 }     
    };
	
    private EditText mTextRent = null;
    private SeekBar  mSeekRent;
    private EditTextSeekBarLinker mRentLnk;
    
    private EditText mTextYrlyRentIncrease;
    private SeekBar  mSeekYrlyRentIncrease;
    private EditTextSeekBarLinker mYrlyRentIncreaseLnk;
    
    private EditText mTextRentIns;
    private EditText mTextUtility;
    private EditText mTextSavingReturn;
    
    private SharedPreferences mPrefrences;
    
    private boolean mbIsCreated = false;
    
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	    	
        if (container == null) {
            return null;
        }
        
        mbIsCreated = false;
    	
        final ScrollView viewRent = (ScrollView)inflater.inflate(R.layout.activity_rent, container, false);
        
        mTextRent = (EditText) viewRent.findViewById(R.id.actRent_editText1);
        mSeekRent = (SeekBar) viewRent.findViewById(R.id.actRent_seekBar1);
        
        mTextYrlyRentIncrease = (EditText) viewRent.findViewById(R.id.actRent_editText2);
        mSeekYrlyRentIncrease = (SeekBar) viewRent.findViewById(R.id.actRent_seekBar2);
        
        mTextRentIns = (EditText) viewRent.findViewById(R.id.actRent_editText3);
        
        
        mTextUtility = (EditText) viewRent.findViewById(R.id.actRent_editText4);
        
        
        mTextSavingReturn = (EditText) viewRent.findViewById(R.id.actRent_editText5);
       
        
        mPrefrences = ((MainFragment) getActivity()).getPreferences(Context.MODE_PRIVATE);
        
        mbIsCreated = true;		
        return viewRent;
    }
    
    public void onResetToDefault() {
    	CalcBuyOrRent calc = ((MainFragment) getActivity()).getCalcBuyOrRent();
    	
    	mRentLnk.setValMinMax(mTextRent, calc.getRent(), "");
    	mYrlyRentIncreaseLnk.setValMinMax(mTextYrlyRentIncrease, calc.getRentIncreaseRate(), "");
    	
    	mTextRentIns.setText(String.valueOf((int) calc.getRentIns()));
    	mTextUtility.setText(String.valueOf((int) calc.getUtility()));
		mTextSavingReturn.setText(String.format("%.2f", calc.getSavingReturnRate()));
    }
    
    public void onStart () {
    	super.onStart();
    	
   		((MainFragment) getActivity()).setUpdateResult(false);
   		
   		CalcBuyOrRent calc = ((MainFragment) getActivity()).getCalcBuyOrRent();
       	mRentLnk = new EditTextSeekBarLinker(mTextRent, mSeekRent, calc.getRent(), "Rent");        
		mYrlyRentIncreaseLnk = new EditTextSeekBarLinker(mTextYrlyRentIncrease, mSeekYrlyRentIncrease, 
				                                        calc.getRentIncreaseRate(), "YrlyRentIncrease");
		
    	mTextRentIns.setText(String.valueOf((int) calc.getRentIns()));
    	mTextRentIns.addTextChangedListener(new RentTextWatcher());
    	
		mTextUtility.setText(String.valueOf((int) calc.getUtility()));
		mTextUtility.addTextChangedListener(new RentTextWatcher());
		
		mTextSavingReturn.setText(String.format("%.2f", calc.getSavingReturnRate()));
		mTextSavingReturn.addTextChangedListener(new RentTextWatcher());
   		
   		((MainFragment) getActivity()).setUpdateResult(true);
    }
    
    public void onPause() {
    	super.onPause();
    	
    	SharedPreferences.Editor editor = mPrefrences.edit();
    	editor.putFloat("RentMax",             (float) mRentLnk.getTextWatcher().getMax());
    	editor.putFloat("RentMin",             (float) mRentLnk.getTextWatcher().getMin());
    	editor.putFloat("YrlyRentIncreaseMax", (float) mYrlyRentIncreaseLnk.getTextWatcher().getMax());
    	editor.putFloat("YrlyRentIncreaseMin", (float) mYrlyRentIncreaseLnk.getTextWatcher().getMin());
   	
    	editor.commit();
    }
    
    public void onDataChanged() {
    	if(!mbIsCreated)
    		return;
    	
    	CalcBuyOrRent calc = ((MainFragment) getActivity()).getCalcBuyOrRent();
    	
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

