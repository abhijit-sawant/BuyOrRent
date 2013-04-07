package aum.fin.buyorrent;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ScrollView;
import android.widget.Spinner;

public class TaxFragment extends Fragment {
	/** (non-Javadoc)
     * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
     */
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        if (container == null) {
            return null;
        }
       ScrollView viewTax = (ScrollView)inflater.inflate(R.layout.activity_tax, container, false);
        
      //set martial status choices
	  Spinner spinner = (Spinner) viewTax.findViewById(R.id.actTax_spinner1);
	 
	  ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.martialStatus_array,
	                                                                       android.R.layout.simple_spinner_item);
	 
	  adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	  spinner.setAdapter(adapter);     
	  
	  return viewTax;
    }
}
