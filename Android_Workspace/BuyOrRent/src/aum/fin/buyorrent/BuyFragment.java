package aum.fin.buyorrent;

import android.R.drawable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

public class BuyFragment  extends Fragment {
    /** (non-Javadoc)
     * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
     */
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        if (container == null) { //if fragment's container frame does not exist in layout just return null
            return null;
        }
     ScrollView viewBuy = (ScrollView)inflater.inflate(R.layout.activity_buy, container, false);
     
     //yearly expenses
     final RelativeLayout loYearlyTitle = (RelativeLayout) viewBuy.findViewById(R.id.actBuy_layoutYearlyTitle);
     final RelativeLayout loYearlyContent = (RelativeLayout) viewBuy.findViewById(R.id.actBuy_layoutYearlyContent);
     final ImageView imgYearlyArrow = (ImageView) viewBuy.findViewById(R.id.actBuy_imageYearlyArrow);
     loYearlyTitle.setOnClickListener(new View.OnClickListener() {
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
     final RelativeLayout loPeriTitle = (RelativeLayout) viewBuy.findViewById(R.id.actBuy_layoutPeriTitle);
     final RelativeLayout loPeriContent = (RelativeLayout) viewBuy.findViewById(R.id.actBuy_layoutPeriContent);
     final ImageView imgPeriArrow = (ImageView) viewBuy.findViewById(R.id.actBuy_imagePeriArrow);
     loPeriTitle.setOnClickListener(new View.OnClickListener() {
         public void onClick(View v) {
     if (v == loPeriTitle) {
    	 loPeriContent.setVisibility(loPeriContent.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
    	 if(loPeriContent.getVisibility() == View.VISIBLE)
    		 imgPeriArrow.setImageResource(drawable.arrow_up_float);
    	 else
    		 imgPeriArrow.setImageResource(drawable.arrow_down_float);
     	}
     } });
        
     return viewBuy;
    }
}
