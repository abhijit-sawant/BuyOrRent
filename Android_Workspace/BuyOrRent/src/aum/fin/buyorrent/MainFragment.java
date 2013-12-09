package aum.fin.buyorrent;

import java.text.NumberFormat;
import java.util.HashMap;
 
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
    
    private TextView mTextBuyProfit;
    private TextView mTextRentProfit;
    private TextView mTextNetProfit;
    
    private TextView mTextBuyProfitLabel;
    private TextView mTextRentProfitLabel;
    private TextView mTextNetProfitLabel;
    
    private TextView mTextDecisionBuy;
    private TextView mTextDecisionRent;
    private TextSwitcher mTextSwitchDecision;
    
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
        
        mTextBuyProfit      = (TextView) findViewById(R.id.actMain_textView2);
        mTextRentProfit     = (TextView) findViewById(R.id.actMain_textView4);
        mTextNetProfit      = (TextView) findViewById(R.id.actMain_textView6);
        
        mTextBuyProfitLabel  = (TextView) findViewById(R.id.actMain_textView1);
        mTextRentProfitLabel = (TextView) findViewById(R.id.actMain_textView3);
        mTextNetProfitLabel  = (TextView) findViewById(R.id.actMain_textView5);
        
        mTextSwitchDecision = (TextSwitcher) findViewById(R.id.actMain_textSwitcher1);        
        
        mTextDecisionBuy = new TextView(this);
        mTextDecisionBuy.setText("BUY");
        mTextDecisionBuy.setTextColor(Color.rgb(0, 128, 0));
        mTextDecisionBuy.setTextSize(30);
        mTextDecisionBuy.setTypeface(Typeface.SERIF);    
        
        mTextDecisionRent = new TextView(this);
        mTextDecisionRent.setText("RENT");
        mTextDecisionRent.setTextColor(Color.rgb(128, 0, 0));
        mTextDecisionRent.setTextSize(30);
        mTextDecisionRent.setTypeface(Typeface.SERIF);
        
      	mTextSwitchDecision.addView(mTextDecisionBuy);
        mTextSwitchDecision.addView(mTextDecisionRent);
        
        mTextSwitchDecision.setInAnimation(this,  android.R.anim.fade_in);
        mTextSwitchDecision.setOutAnimation(this, android.R.anim.fade_out);
        
        mTextNetProfit.setTextColor(miGreen);
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
    	
    	if(dBuyProfit > 0)
    		mTextBuyProfitLabel.setText("Buy Profit");
    	else
    		mTextBuyProfitLabel.setText("Buy Loss");
    	
    	if(dRentProfit > 0)
    		mTextRentProfitLabel.setText("Rent Profit");
    	else
    		mTextRentProfitLabel.setText("Rent Loss");    	
    	
    	if(dBuyNetProfit > 0)
    		mTextNetProfitLabel.setText("Net profit\nif you Buy");
    	else
    		mTextNetProfitLabel.setText("Net loss\nif you Buy");
    	
    	int iFactMult = 1;
    	if(dBuyProfit < 0)
    	{
    		iFactMult = -1;
    		mTextBuyProfit.setTextColor(miRed);
    	}
    	else
    		mTextBuyProfit.setTextColor(miGreen);
    	String strBuyProfit = mFormatCurrancy.format(iFactMult * dBuyProfit);
    	mTextBuyProfit.setText(strBuyProfit);
    	
    	iFactMult = 1;
    	if(dRentProfit < 0)
    	{
    		iFactMult = -1;
    		mTextRentProfit.setTextColor(miRed);
    	}
    	else
    		mTextRentProfit.setTextColor(miGreen);
    	String strRentProfit = mFormatCurrancy.format(iFactMult * dRentProfit);
    	mTextRentProfit.setText(strRentProfit);   	
    	
    	iFactMult = 1;
    	if(dBuyNetProfit < 0)
    	{
    		iFactMult = -1;
    		mTextNetProfit.setTextColor(miRed);
    	}
    	else
    		mTextNetProfit.setTextColor(miGreen); 
    	String strNetProfit = mFormatCurrancy.format(iFactMult * dBuyNetProfit);
    	mTextNetProfit.setText(strNetProfit);
  		    	
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
    }
 
}
