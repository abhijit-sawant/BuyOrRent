package aum.fin.buyorrent;

import android.support.v4.app.Fragment;
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
	
    private EditText mTextRent;
    private SeekBar  mSeekRent;
    private EditTextSeekBarLinker mRentLnk;
    
    private EditText mTextYrlyRentIncrease;
    private SeekBar  mSeekYrlyRentIncrease;
    private EditTextSeekBarLinker mYrlyRentIncreaseLnk;
    
    private EditText mTextRentIns;
    private SeekBar  mSeekRentIns;
    private EditTextSeekBarLinker mRentInsLnk;
    
    private EditText mTextUtility;
    private SeekBar  mSeekUtility;
    private EditTextSeekBarLinker mUtilityLnk;
    
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
        mSeekRentIns = (SeekBar) viewRent.findViewById(R.id.actRent_seekBar3);
        
        mTextUtility = (EditText) viewRent.findViewById(R.id.actRent_editText4);
        mSeekUtility = (SeekBar) viewRent.findViewById(R.id.actRent_seekBar4);
        
        mPrefrences = ((MainFragment) getActivity()).getPreferences(Context.MODE_PRIVATE);
        
        CalcBuyOrRent calc = ((MainFragment) getActivity()).getCalcBuyOrRent();       
        
		mRentLnk = new EditTextSeekBarLinker(mTextRent, mSeekRent, calc.getRent(), "Rent");
		mYrlyRentIncreaseLnk = new EditTextSeekBarLinker(mTextYrlyRentIncrease, mSeekYrlyRentIncrease, 
				                                        calc.getRentIncreaseRate(), "YrlyRentIncrease");
		mRentInsLnk = new EditTextSeekBarLinker(mTextRentIns, mSeekRentIns, calc.getRentIns(), "RentIns");
		mUtilityLnk = new EditTextSeekBarLinker(mTextUtility, mSeekUtility, calc.getUtility(), "Utility");
        
		mbIsCreated = true;		
        return viewRent;
    }
    
    public void onPause() {
    	super.onPause();
    	
    	SharedPreferences.Editor editor = mPrefrences.edit();
    	editor.putFloat("RentMax",             (float) mRentLnk.getTextWatcher().getMax());
    	editor.putFloat("RentMin",             (float) mRentLnk.getTextWatcher().getMin());
    	editor.putFloat("YrlyRentIncreaseMax", (float) mYrlyRentIncreaseLnk.getTextWatcher().getMax());
    	editor.putFloat("YrlyRentIncreaseMin", (float) mYrlyRentIncreaseLnk.getTextWatcher().getMin());
    	editor.putFloat("RentInsMax",          (float) mRentInsLnk.getTextWatcher().getMax());
    	editor.putFloat("RentInsMin",          (float) mRentInsLnk.getTextWatcher().getMin());
    	editor.putFloat("UtilityMax",          (float) mUtilityLnk.getTextWatcher().getMax());
    	editor.putFloat("UtilityMin",          (float) mUtilityLnk.getTextWatcher().getMin());
    	
    	editor.commit();
    }
    
    public void onDataChanged() {
    	if(!mbIsCreated)
    		return;
    	
    	CalcBuyOrRent calc = ((MainFragment) getActivity()).getCalcBuyOrRent();
    	
    	calc.setRent(Integer.valueOf(mTextRent.getText().toString()));
    	calc.setRentIncreaseRate(Double.valueOf(mTextYrlyRentIncrease.getText().toString()));
    	calc.setRentIns(Integer.valueOf(mTextRentIns.getText().toString()));
    	calc.setUtility(Integer.valueOf(mTextUtility.getText().toString()));
    }
}

