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

public class TaxFragment extends Fragment {
	
    private EditText mTextGrossIncome;
    private SeekBar  mSeekGrossIncome;
    
    private EditText mTextItemDeduct;
    private SeekBar  mSeekItemDeduct;
    
    private EditText mTextTaxBracket;
    private SeekBar  mSeekTaxBracket;    

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (container == null) {
            return null;
        }
       ScrollView viewTax = (ScrollView)inflater.inflate(R.layout.activity_tax, container, false);
       
       mTextGrossIncome = (EditText) viewTax.findViewById(R.id.actTax_editText1);
       mSeekGrossIncome = (SeekBar) viewTax.findViewById(R.id.actTax_seekBar1);
       
       mTextItemDeduct = (EditText) viewTax.findViewById(R.id.actTax_editText2);
       mSeekItemDeduct = (SeekBar) viewTax.findViewById(R.id.actTax_seekBar2);
       
       mTextTaxBracket = (EditText) viewTax.findViewById(R.id.actTax_editText3);
       mSeekTaxBracket = (SeekBar) viewTax.findViewById(R.id.actTax_seekBar3);       
        
      //set martial status choices
	  Spinner spinner = (Spinner) viewTax.findViewById(R.id.actTax_spinner1);
	 
	  ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.martialStatus_array,
	                                                                       android.R.layout.simple_spinner_item);
	  adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	  spinner.setAdapter(adapter);
	  
	  EditTextSeekBarLinker textNBarLinker = new EditTextSeekBarLinker();
		
	  textNBarLinker.linkEditTextNSeekBar(mTextGrossIncome, mSeekGrossIncome, "80000");
	  textNBarLinker.linkEditTextNSeekBar(mTextItemDeduct, mSeekItemDeduct, "10000");
	  textNBarLinker.linkEditTextNSeekBar(mTextTaxBracket, mSeekTaxBracket, "15.00");
	  
	  return viewTax;
    }
}
