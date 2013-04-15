package aum.fin.buyorrent;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.SeekBar;

public class RentFragment extends Fragment {
	
    private EditText mTextRent;
    private SeekBar  mSeekRent;
    
    private EditText mTextYrlyRentIncrease;
    private SeekBar  mSeekYrlyRentIncrease;
    
    private EditText mTextIns;
    private SeekBar  mSeekIns;
    
    private EditText mTextUtility;
    private SeekBar  mSeekUtility;
    
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	
        if (container == null) {
            return null;
        }
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
		
		textNBarLinker.linkEditTextNSeekBar(mTextRent, mSeekRent, "800");
		textNBarLinker.linkEditTextNSeekBar(mTextYrlyRentIncrease, mSeekYrlyRentIncrease, "4.00");
		textNBarLinker.linkEditTextNSeekBar(mTextIns, mSeekIns, "0");
		textNBarLinker.linkEditTextNSeekBar(mTextUtility, mSeekUtility, "50");
        
        return viewRent;
    }
}

