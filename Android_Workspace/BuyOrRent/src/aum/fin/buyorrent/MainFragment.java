package aum.fin.buyorrent;

import java.text.NumberFormat;

import com.echo.holographlibrary.Bar;
import com.echo.holographlibrary.BarGraph;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextSwitcher;
import android.widget.TextView;
import aum.fin.buyorrent.CalcBuyOrRent.OnDataChangedListener;

public class MainFragment extends Activity {
	
	public static class TabListener<T extends Fragment> implements ActionBar.TabListener {
	    private Fragment mFragment;
	    private final Activity mActivity;
	    private final String mTag;
	    private final Class<T> mClass;

	    /** Constructor used each time a new tab is created.
	      * @param activity  The host Activity, used to instantiate the fragment
	      * @param tag  The identifier tag for the fragment
	      * @param clz  The fragment's Class, used to instantiate the fragment
	      */
	    public TabListener(Activity activity, String tag, Class<T> clz) {
	        mActivity = activity;
	        mTag = tag;
	        mClass = clz;
	    }

	    /* The following are each of the ActionBar.TabListener callbacks */
	    
		@Override
		public void onTabReselected(Tab tab, android.app.FragmentTransaction ft) {		
			
		}

		@Override
		public void onTabSelected(Tab tab, android.app.FragmentTransaction ft) {
			mFragment = mActivity.getFragmentManager().findFragmentByTag(mTag);
			
			 // Check if the fragment is already initialized
	        if (mFragment == null) {
	            // If not, instantiate and add it to the activity
	            mFragment = Fragment.instantiate(mActivity, mClass.getName());
	            ft.add(android.R.id.content, mFragment, mTag);
	        } else {
	            // If it exists, simply attach it in order to show it
	            ft.attach(mFragment);
	        }
			
		}

		@Override
		public void onTabUnselected(Tab tab, android.app.FragmentTransaction ft) {
	        if (mFragment != null) {
	            // Detach the fragment, because another one is being attached
	            ft.detach(mFragment);
	        }
			
		}
	}
	
    private TextSwitcher mTextSwitchDecision;
    private TextView     mTextDecisionBuy;
    private TextView     mTextDecisionRent;
    private BarGraph     mGraphResult;
    
    private boolean mbUpdateResult = false;
    
    public  static final NumberFormat mFormatCurrancy = NumberFormat.getCurrencyInstance(); 
 
    protected void onStart() {
    	super.onStart();
    	
    	setUpdateResult(true);
    	calcBuyOrRent();
    }
    
    protected void onPause() {
    	super.onPause();
    	    	
		SharedPreferences.Editor editor = getPreferences(Context.MODE_PRIVATE).edit();
    	editor.putInt("TabIndex", getActionBar().getSelectedNavigationIndex());
    	editor.commit();
    	
    	CalcBuyOrRent.getInstance().onPause();
    }
    
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        //setup action bar for tabs
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setDisplayShowTitleEnabled(false);

        Tab tab = actionBar.newTab()
                           .setText(R.string.title_activity_buy)
                           .setTag("TabBuy")
                           .setTabListener(new TabListener<BuyFragment>(this, "TabBuy", BuyFragment.class));
        
        actionBar.addTab(tab);

        tab = actionBar.newTab()
                       .setText(R.string.title_activity_rent)
                       .setTag("TabRent")
                       .setTabListener(new TabListener<RentFragment>(this, "TabRent", RentFragment.class));
        actionBar.addTab(tab);
        
        tab = actionBar.newTab()
                       .setText(R.string.title_activity_tax)
                       .setTag("TabTax")
                       .setTabListener(new TabListener<TaxFragment>(this, "TabTax", TaxFragment.class));
        actionBar.addTab(tab);   
        
        int iTabIndex = getPreferences(Context.MODE_PRIVATE).getInt("TabIndex", 0);
        actionBar.selectTab(actionBar.getTabAt(iTabIndex));
        
        mTextSwitchDecision = (TextSwitcher) findViewById(R.id.actMain_textSwitcher1);        
        
        mTextDecisionBuy = new TextView(this);
        mTextDecisionBuy.setText(R.string.decisionbuy);
        mTextDecisionBuy.setTextColor(Color.rgb(65, 105, 225));
        mTextDecisionBuy.setTextSize(20);
        mTextDecisionBuy.setTypeface(Typeface.SERIF);    
        
        mTextDecisionRent = new TextView(this);
        mTextDecisionRent.setText(R.string.decisionrent);
        mTextDecisionRent.setTextColor(Color.rgb(255, 140, 0));
        mTextDecisionRent.setTextSize(20);
        mTextDecisionRent.setTypeface(Typeface.SERIF);
        
      	/*mTextSwitchDecision.addView(mTextDecisionBuy);
        mTextSwitchDecision.addView(mTextDecisionRent);
        
        mTextSwitchDecision.setInAnimation(this,  android.R.anim.fade_in);
        mTextSwitchDecision.setOutAnimation(this, android.R.anim.fade_out);*/
        
        mGraphResult = (BarGraph)findViewById(R.id.graph);        
    }
 
    protected void onSaveInstanceState(Bundle outState) {

		SharedPreferences.Editor editor = getPreferences(Context.MODE_PRIVATE).edit();
    	editor.putInt("TabIndex", getActionBar().getSelectedNavigationIndex());
    	editor.commit();
   	
        super.onSaveInstanceState(outState);
    }
 
    private void onResetToDefault() {
    	setUpdateResult(false);
    	
    	SharedPreferences pref =  getPreferences(Context.MODE_PRIVATE);
    	SharedPreferences.Editor edit = pref.edit();
    	edit.clear();
    	edit.commit();
    	
    	CalcBuyOrRent.getInstance().resetToDefault();
    	
    	Tab currentTab = getActionBar().getSelectedTab();
    	if(currentTab != null)
    	{
	    	Fragment fragmentCurrent = getFragmentManager().findFragmentByTag((String) currentTab.getTag());
	    	if(fragmentCurrent != null)
	    		((OnDataChangedListener) fragmentCurrent).onResetToDefault();
    	}
    	
    	setUpdateResult(true);
    	
    	//calcBuyOrRent();
    }
    
    public void setUpdateResult(boolean bVal) {
    	mbUpdateResult = bVal;
    }
    
    public boolean getUpdateResult() {
    	return mbUpdateResult;
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_reset_values:
            	onResetToDefault();
                return true;
            case R.id.item_result:
            	Intent intent = new Intent(this, ResultActiviy.class);
            	startActivity(intent);
            	return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    
    public void calcBuyOrRent() {
    	if(getUpdateResult() == false)
    		return;
    	
    	Tab currentTab = getActionBar().getSelectedTab();
    	if(currentTab == null)
    		return;    	
    	Fragment fragmentCurrent = getFragmentManager().findFragmentByTag((String) currentTab.getTag());
    	if(fragmentCurrent == null)
    		return;
    	
    	((OnDataChangedListener) fragmentCurrent).onDataChanged();
    	
    	/*getCalcBuyOrRent().calculate();
    	double dBuyProfit     = getCalcBuyOrRent().getBuyProfit();
    	double dRentProfit    = getCalcBuyOrRent().getRentProfit();
    	double dBuyNetProfit  = getCalcBuyOrRent().getBuyNetProfit();
    	
    	if(dBuyProfit > dRentProfit)
    	{
    		if(mTextSwitchDecision.getNextView() == mTextDecisionBuy)
    			mTextSwitchDecision.showNext();
    	}
    	else
    	{
    		if(mTextSwitchDecision.getNextView() == mTextDecisionRent)
    			mTextSwitchDecision.showNext();
    	}    	
    	
    	int iFactMult = 1;
    	if(dBuyProfit < 0)
    		iFactMult = -1;
    	String strBuyProfit = mFormatCurrancy.format(iFactMult * dBuyProfit);
 	
    	iFactMult = 1;
    	if(dRentProfit < 0)
    		iFactMult = -1;
    	String strRentProfit = mFormatCurrancy.format(iFactMult * dRentProfit);
    	
    	iFactMult = 1;
    	if(dBuyNetProfit < 0)
    		iFactMult = -1;
    	String strNetProfit = mFormatCurrancy.format(iFactMult * dBuyNetProfit);
  		    	
    	ArrayList<Bar> points = new ArrayList<Bar>();
    	Bar barBuy = new Bar();
    	barBuy.setColor(Color.rgb(101, 153, 255));
    	if(dBuyProfit > 0)
    		barBuy.setName(getString(R.string.buyprofit));
    	else
    		barBuy.setName(getString(R.string.buyloss));
    	barBuy.setValue((float) dBuyProfit);
    	barBuy.setValueString(strBuyProfit);
    	
    	Bar barRent = new Bar();
    	barRent.setColor(Color.rgb(255, 153, 0));
    	if(dRentProfit > 0)
    		barRent.setName(getString(R.string.rentprofit));
    	else
    		barRent.setName(getString(R.string.rentloss));
    	barRent.setValue((float) dRentProfit);
    	barRent.setValueString(strRentProfit);
    	
    	Bar barNet = new Bar();
  		barNet.setColor(Color.rgb(9, 112, 84));
  		if(dBuyNetProfit > 0)
  			barNet.setName(getString(R.string.netprofit));
  		else
  			barNet.setName(getString(R.string.netloss));
    	barNet.setValue((float) dBuyNetProfit);
    	barNet.setValueString(strNetProfit);
    	
    	points.add(barBuy);
    	points.add(barRent);   
    	points.add(barNet);*/

    	//mGraphResult.setBars(points);    	
    }
 
}
