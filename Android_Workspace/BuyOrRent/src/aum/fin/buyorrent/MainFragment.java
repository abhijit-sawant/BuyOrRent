package aum.fin.buyorrent;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;

import com.echo.holographlibrary.Bar;
import com.echo.holographlibrary.BarGraph;
 
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TabHost;
import android.widget.TabHost.TabContentFactory;
import android.widget.TextSwitcher;
import android.widget.TextView;
import aum.fin.buyorrent.CalcBuyOrRent.OnDataChangedListener;

public class MainFragment extends FragmentActivity implements TabHost.OnTabChangeListener {
	
	class TabFactory implements TabContentFactory {
		 
        private final Context mContext;
 
        public TabFactory(Context context) {
            mContext = context;
        }
 
        public View createTabContent(String tag) {
            View v = new View(mContext);
            v.setMinimumWidth(0);
            v.setMinimumHeight(0);
            return v;
        }
 
    }
 
    private TabHost mTabHost;
    private HashMap mapTabInfo = new HashMap();
    private TabInfo mLastTab   = null;
    private CalcBuyOrRent mCalcBuyOrRent = null;
    
    private TextSwitcher mTextSwitchDecision;
    private TextView     mTextDecisionBuy;
    private TextView     mTextDecisionRent;
    private BarGraph     mGraphResult;
    
    private boolean mbUpdateResult = false;
    
    private final int miRed   = Color.rgb(183, 0,   0);
    private final int miGreen = Color.rgb(0,   153, 51);
    public  static final NumberFormat mFormatCurrancy = NumberFormat.getCurrencyInstance(); 
 
    private class TabInfo {
         private String tag;
         private Class clss;
         private Bundle args;
         private Fragment fragment;
         TabInfo(String tag, Class clazz, Bundle args) {
             this.tag  = tag;
             this.clss = clazz;
             this.args = args;
         }
 
    }
 
    protected void onStart() {
    	super.onStart();
    	
    	setUpdateResult(true);
    	calcBuyOrRent();
    }
    
    protected void onPause() {
    	super.onPause();
    	
    	SharedPreferences.Editor editor = getPreferences(Context.MODE_PRIVATE).edit();
    	editor.putString("TabTag", mTabHost.getCurrentTabTag());
    	editor.commit();
    	
    	getCalcBuyOrRent().onPause(getPreferences(Context.MODE_PRIVATE));
    }
    
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Step 1: Inflate layout
        setContentView(R.layout.activity_main);
        
        // Step 2: Setup TabHost
        initialiseTabHost(savedInstanceState);
        /*if (savedInstanceState != null) {
            mTabHost.setCurrentTabByTag(savedInstanceState.getString("tab")); //set the tab as per the saved state
        }*/
        String strTabTag = getPreferences(Context.MODE_PRIVATE).getString("TabTag", "");
        if(!strTabTag.equals("")) {
        	mTabHost.setCurrentTabByTag(strTabTag);
        }
        
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
        
      	mTextSwitchDecision.addView(mTextDecisionBuy);
        mTextSwitchDecision.addView(mTextDecisionRent);
        
        mTextSwitchDecision.setInAnimation(this,  android.R.anim.fade_in);
        mTextSwitchDecision.setOutAnimation(this, android.R.anim.fade_out);
        
        mGraphResult = (BarGraph)findViewById(R.id.graph);        
    }
 
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("tab", mTabHost.getCurrentTabTag()); //save the tab selected
        super.onSaveInstanceState(outState);
    }
 
    private void initialiseTabHost(Bundle args) {
        mTabHost = (TabHost)findViewById(android.R.id.tabhost);
        mTabHost.setup();
        TabInfo tabInfo = null;
        MainFragment.addTab(this, this.mTabHost, this.mTabHost.newTabSpec("TabBuy").setIndicator(getString(R.string.title_activity_buy)), 
        		            (tabInfo = new TabInfo("TabBuy", BuyFragment.class, args)));
        this.mapTabInfo.put(tabInfo.tag, tabInfo);
        MainFragment.addTab(this, this.mTabHost, this.mTabHost.newTabSpec("TabRent").setIndicator(getString(R.string.title_activity_rent)),
        		            (tabInfo = new TabInfo("TabRent", RentFragment.class, args)));
        this.mapTabInfo.put(tabInfo.tag, tabInfo);
        MainFragment.addTab(this, this.mTabHost, this.mTabHost.newTabSpec("TabTax").setIndicator(getString(R.string.title_activity_tax)),
		                    (tabInfo = new TabInfo("TabTax", TaxFragment.class, args)));
        this.mapTabInfo.put(tabInfo.tag, tabInfo);
        // Default to first tab
        this.onTabChanged("TabBuy");
        //
        mTabHost.setOnTabChangedListener(this);
    }
 
    private static void addTab(MainFragment activity, TabHost tabHost, TabHost.TabSpec tabSpec, TabInfo tabInfo) {
        // Attach a Tab view factory to the spec
        tabSpec.setContent(activity.new TabFactory(activity));
        String tag = tabSpec.getTag();
 
        // Check to see if we already have a fragment for this tab, probably
        // from a previously saved state.  If so, deactivate it, because our
        // initial state is that a tab isn't shown.
        tabInfo.fragment = activity.getSupportFragmentManager().findFragmentByTag(tag);
        if (tabInfo.fragment != null && !tabInfo.fragment.isDetached()) {
            FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
            ft.detach(tabInfo.fragment);
            ft.commit();
            activity.getSupportFragmentManager().executePendingTransactions();
        } 
        tabHost.addTab(tabSpec);
    }
    
    private void onResetToDefault() {
    	setUpdateResult(false);
    	
    	SharedPreferences pref =  getPreferences(Context.MODE_PRIVATE);
    	SharedPreferences.Editor edit = pref.edit();
    	edit.clear();
    	edit.commit();
    	
    	getCalcBuyOrRent().resetToDefault();
    	
    	Fragment fragmentCurrent = getSupportFragmentManager().findFragmentByTag(mTabHost.getCurrentTabTag());
    	if(fragmentCurrent != null)
    		((OnDataChangedListener) fragmentCurrent).onResetToDefault();
    	
    	setUpdateResult(true);
    	
    	mGraphResult.resetMaxVal();
    	calcBuyOrRent();
    }
    
    public void setUpdateResult(boolean bVal) {
    	mbUpdateResult = bVal;
    }
    
    public boolean getUpdateResult() {
    	return mbUpdateResult;
    }
    
    public void onTabChanged(String tag) {
        TabInfo newTab = (TabInfo) this.mapTabInfo.get(tag);
        if (mLastTab != newTab) {
            FragmentTransaction ft = this.getSupportFragmentManager().beginTransaction();
            if (mLastTab != null) {
                if (mLastTab.fragment != null) {
                    ft.detach(mLastTab.fragment);
                }
            }
            if (newTab != null) {
                if (newTab.fragment == null) {
                    newTab.fragment = Fragment.instantiate(this, newTab.clss.getName(), newTab.args);
                    ft.add(R.id.realtabcontent, newTab.fragment, newTab.tag);
                } else {
                    ft.attach(newTab.fragment);
                }
            }
 
            mLastTab = newTab;
            ft.commit();
            this.getSupportFragmentManager().executePendingTransactions();
        }
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_reset_values:
            	onResetToDefault();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    
    public CalcBuyOrRent getCalcBuyOrRent() {
    	if(mCalcBuyOrRent == null)
    		mCalcBuyOrRent = new CalcBuyOrRent(getPreferences(Context.MODE_PRIVATE));
    	return mCalcBuyOrRent;
    }
    
    public void calcBuyOrRent() {
    	if(getUpdateResult() == false)
    		return;
    	
    	Fragment fragmentCurrent = getSupportFragmentManager().findFragmentByTag(mTabHost.getCurrentTabTag());
    	if(fragmentCurrent == null)
    		return;
    	
    	((OnDataChangedListener) fragmentCurrent).onDataChanged();
    	
    	getCalcBuyOrRent().calculate();
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
    	points.add(barNet);

    	mGraphResult.setBars(points);    	
    }
 
}
