package aum.fin.buyorrent;

import android.support.v4.app.Fragment;
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
    
    private EditText mTextItemDeduct;
    private SeekBar  mSeekItemDeduct;
    
    private EditText mTextTaxBracket;
    private SeekBar  mSeekTaxBracket;    
    
    private Spinner mSpinMaritalStatus;
    
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
	 
	  ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.martialStatus_array,
	                                                                       android.R.layout.simple_spinner_item);
	  adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	  mSpinMaritalStatus.setAdapter(adapter);
	  
	  EditTextSeekBarLinker textNBarLinker = new EditTextSeekBarLinker();
	  CalcBuyOrRent calc = ((MainFragment) getActivity()).getCalcBuyOrRent();
		
	  textNBarLinker.linkEditTextNSeekBar(mTextGrossIncome, mSeekGrossIncome, String.valueOf(calc.getGrossIncome()));
	  textNBarLinker.linkEditTextNSeekBar(mTextItemDeduct, mSeekItemDeduct, String.valueOf(calc.getItmeDeduct()));
	  textNBarLinker.linkEditTextNSeekBar(mTextTaxBracket, mSeekTaxBracket, String.format("%.2f", calc.getTaxBracket()));
	  
	  mbIsCreated = true;
	  
	  return viewTax;
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
