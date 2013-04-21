package aum.fin.buyorrent;

import java.util.HashMap;
 
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.TabHost;
import android.widget.TabHost.TabContentFactory;
import aum.fin.buyorrent.CalcBuyOrRent.OnDataChangedListener;
 
public class MainFragment extends FragmentActivity implements TabHost.OnTabChangeListener {
 
    private TabHost mTabHost;
    private HashMap mapTabInfo = new HashMap();
    private TabInfo mLastTab   = null;
    private CalcBuyOrRent mCalcBuyOrRent = null;
    
    private boolean bAppLoading = true;
 
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
    
    protected void onStart() {
    	super.onStart();
    	bAppLoading = false;
    	calcBuyOrRent();
    }
    
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Step 1: Inflate layout
        setContentView(R.layout.activity_main);
        // Step 2: Setup TabHost
        initialiseTabHost(savedInstanceState);
        if (savedInstanceState != null) {
            mTabHost.setCurrentTabByTag(savedInstanceState.getString("tab")); //set the tab as per the saved state
        }      
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
    
    public CalcBuyOrRent getCalcBuyOrRent() {
    	if(mCalcBuyOrRent == null)
    		mCalcBuyOrRent = new CalcBuyOrRent();
    	return mCalcBuyOrRent;
    }
    
    public void calcBuyOrRent() {
    	if(bAppLoading)
    		return;
    	
    	Fragment fragmentCurrent = getSupportFragmentManager().findFragmentByTag(mTabHost.getCurrentTabTag());
    	if(fragmentCurrent == null)
    		return;
    	
    	((OnDataChangedListener) fragmentCurrent).onDataChanged();
    }
 
}
