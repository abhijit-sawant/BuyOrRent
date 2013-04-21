package aum.fin.buyorrent;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.SeekBar;
import aum.fin.buyorrent.CalcBuyOrRent.OnDataChangedListener;

public class RentFragment extends Fragment implements OnDataChangedListener {
	
    private EditText mTextRent;
    private SeekBar  mSeekRent;
    
    private EditText mTextYrlyRentIncrease;
    private SeekBar  mSeekYrlyRentIncrease;
    
    private EditText mTextIns;
    private SeekBar  mSeekIns;
    
    private EditText mTextUtility;
    private SeekBar  mSeekUtility;
    
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
        
        mTextIns = (EditText) viewRent.findViewById(R.id.actRent_editText3);
        mSeekIns = (SeekBar) viewRent.findViewById(R.id.actRent_seekBar3);
        
        mTextUtility = (EditText) viewRent.findViewById(R.id.actRent_editText4);
        mSeekUtility = (SeekBar) viewRent.findViewById(R.id.actRent_seekBar4);
        
        EditTextSeekBarLinker textNBarLinker = new EditTextSeekBarLinker();
        CalcBuyOrRent calc = ((MainFragment) getActivity()).getCalcBuyOrRent();
        
		textNBarLinker.linkEditTextNSeekBar(mTextRent, mSeekRent, String.valueOf(calc.getRent()));
		textNBarLinker.linkEditTextNSeekBar(mTextYrlyRentIncrease, mSeekYrlyRentIncrease,
				                            String.format("%.2f", calc.getRentIncreaseRate()));
		textNBarLinker.linkEditTextNSeekBar(mTextIns, mSeekIns, String.valueOf(calc.getRentIns()));
		textNBarLinker.linkEditTextNSeekBar(mTextUtility, mSeekUtility, String.valueOf(calc.getUtility()));
        
		mbIsCreated = true;
		
        return viewRent;
    }
    
    public void onDataChanged() {
    	if(!mbIsCreated)
    		return;
    	
    	CalcBuyOrRent calc = ((MainFragment) getActivity()).getCalcBuyOrRent();
    	
    	calc.setRent(Integer.valueOf(mTextRent.getText().toString()));
    	calc.setRentIncreaseRate(Double.valueOf(mTextYrlyRentIncrease.getText().toString()));
    	calc.setRentIns(Integer.valueOf(mTextIns.getText().toString()));
    	calc.setUtility(Integer.valueOf(mTextUtility.getText().toString()));
    }
    
}

