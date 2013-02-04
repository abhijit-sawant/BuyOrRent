package aum.fin.buyorrent;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.app.Activity;

public class TaxActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tax);
        
        //set marital status choices
        Spinner spinner = (Spinner) findViewById(R.id.spinner1);
     
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                                             R.array.maritalStatus_array, 
                                             android.R.layout.simple_spinner_item);
     
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

}
