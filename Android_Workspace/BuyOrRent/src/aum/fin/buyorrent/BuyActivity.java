package aum.fin.buyorrent;

import android.R.drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.app.Activity;

public class BuyActivity extends Activity {
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy);
        
        //yearly expenses
        final RelativeLayout loYearlyTitle   = (RelativeLayout) findViewById(R.id.layoutYearlyTitle);
    	final RelativeLayout loYearlyContent = (RelativeLayout) findViewById(R.id.layoutYearlyContent);
    	final ImageView      imgYearlyArrow  = (ImageView) findViewById(R.id.imageYearlyArrow);
        loYearlyTitle.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
    	    if (v == loYearlyTitle) {
    	    	loYearlyContent.setVisibility(loYearlyContent.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
    	    	if(loYearlyContent.getVisibility() == View.VISIBLE)
    	    		imgYearlyArrow.setImageResource(drawable.arrow_up_float);
    	    	else
    	    		imgYearlyArrow.setImageResource(drawable.arrow_down_float);
    	    }
    	} });
        
        //peripheral expenses
        final RelativeLayout loPeriTitle   = (RelativeLayout) findViewById(R.id.layoutPeriTitle);
    	final RelativeLayout loPeriContent = (RelativeLayout) findViewById(R.id.layoutPeriContent);
    	final ImageView      imgPeriArrow  = (ImageView) findViewById(R.id.imagePeriArrow);
        loPeriTitle.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
    	    if (v == loPeriTitle) {
    	    	loPeriContent.setVisibility(loPeriContent.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
    	    	if(loPeriContent.getVisibility() == View.VISIBLE)
    	    		imgPeriArrow.setImageResource(drawable.arrow_up_float);
    	    	else
    	    		imgPeriArrow.setImageResource(drawable.arrow_down_float);
    	    }
    	} });
        
    }

}
