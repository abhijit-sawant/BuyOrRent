package aum.fin.buyorrent;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.Spinner;
import aum.fin.buyorrent.CalcBuyOrRent.OnDataChangedListener;

public class TaxFragment extends Fragment implements OnDataChangedListener {
	
    private EditText mTextGrossIncome;
    private SeekBar  mSeekGrossIncome;
    private EditTextSeekBarLinker mGrossIncomeLnk;
    
    private EditText mTextItemDeduct;
    private SeekBar  mSeekItemDeduct;
    private EditTextSeekBarLinker mItemDeductLnk;
    
    private EditText mTextTaxBracket;
    private SeekBar  mSeekTaxBracket; 
    private EditTextSeekBarLinker mTaxBracketLnk;
    
    private Spinner mSpinMaritalStatus;
    
    private SharedPreferences mPrefrences;
    
    private boolean mbIsCreated = false;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	
        if (container == null) {
            return null;
        }
        
        mbIsCreated = false;
       
        ScrollView viewTax = (ScrollView)inflater.inflate(R.layout.activity_tax, container, false);
       
       mTextGrossIncome = (EditText) viewTax.findViewById(R.id.actTax_editText1);
       mSeekGrossIncome = (SeekBar) viewTax.findViewById(R.id.actTax_seekBar1);
       
       mTextItemDeduct = (EditText) viewTax.findViewById(R.id.actTax_editText2);
       mSeekItemDeduct = (SeekBar) viewTax.findViewById(R.id.actTax_seekBar2);
       
       mTextTaxBracket = (EditText) viewTax.findViewById(R.id.actTax_editText3);
       mSeekTaxBracket = (SeekBar) viewTax.findViewById(R.id.actTax_seekBar3);       
        
      //set martial status choices
       mSpinMaritalStatus = (Spinner) viewTax.findViewById(R.id.actTax_spinner1);
       
       mPrefrences = ((MainFragment) getActivity()).getPreferences(Context.MODE_PRIVATE);
	 
	  ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.martialStatus_array,
	                                                                       android.R.layout.simple_spinner_item);
	  adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	  mSpinMaritalStatus.setAdapter(adapter);
	  
	  mbIsCreated = true;
	  
	  return viewTax;
    }
    
    public void onResetToDefault() {
    	CalcBuyOrRent calc = ((MainFragment) getActivity()).getCalcBuyOrRent();
    	
    	mGrossIncomeLnk.setValMinMax(mTextGrossIncome, calc.getGrossIncome(), "");
    	mItemDeductLnk.setValMinMax(mTextItemDeduct, calc.getItmeDeduct(), "");
   		mTaxBracketLnk.setValMinMax(mTextTaxBracket, calc.getTaxBracket(), "");    	
    }
    
    public void onStart () {
    	super.onStart();
    	
   		((MainFragment) getActivity()).setUpdateResult(false);
   		
   		CalcBuyOrRent calc = ((MainFragment) getActivity()).getCalcBuyOrRent();		
   		mGrossIncomeLnk = new EditTextSeekBarLinker(mTextGrossIncome, mSeekGrossIncome, calc.getGrossIncome(), "GrossIncome");
   		mItemDeductLnk = new EditTextSeekBarLinker(mTextItemDeduct, mSeekItemDeduct, calc.getItmeDeduct(), "ItemDeduct");
   		mTaxBracketLnk = new EditTextSeekBarLinker(mTextTaxBracket, mSeekTaxBracket, calc.getTaxBracket(), "TaxBracket");
   		
   		((MainFragment) getActivity()).setUpdateResult(true);
    }
    
    public void onPause() {
    	super.onPause();
    	
    	SharedPreferences.Editor editor = mPrefrences.edit();
    	
    	editor.putFloat("GrossIncomeMax", (float) mGrossIncomeLnk.getTextWatcher().getMax());
    	editor.putFloat("GrossIncomeMin", (float) mGrossIncomeLnk.getTextWatcher().getMin());
    	editor.putFloat("ItemDeductMax", (float) mItemDeductLnk.getTextWatcher().getMax());
    	editor.putFloat("ItemDeductMin", (float) mItemDeductLnk.getTextWatcher().getMin());
    	editor.putFloat("TaxBracketMax", (float) mTaxBracketLnk.getTextWatcher().getMax());
    	editor.putFloat("TaxBracketMin", (float) mTaxBracketLnk.getTextWatcher().getMin());
    	
    	editor.commit();
    }
    
    public void onDataChanged() {
    	if(!mbIsCreated)
    		return;
    	
    	CalcBuyOrRent calc = ((MainFragment) getActivity()).getCalcBuyOrRent();
    	calc.setGrossIncome(Integer.valueOf(mTextGrossIncome.getText().toString()));
    	calc.setItemDeduct(Integer.valueOf(mTextItemDeduct.getText().toString()));
    	calc.setTaxBracket(Double.valueOf(mTextTaxBracket.getText().toString()));
    }
}
