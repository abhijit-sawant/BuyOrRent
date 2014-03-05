package aum.fin.buyorrent;

import java.text.NumberFormat;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import aum.fin.buyorrent.CalcBuyOrRent.OnDataChangedListener;

public class MainFragment extends Activity {
	
    public static final NumberFormat mFormatCurrancy = NumberFormat.getCurrencyInstance(); 
	
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
	
    protected void onStart() {
    	super.onStart();
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
    }
 
    protected void onSaveInstanceState(Bundle outState) {

		SharedPreferences.Editor editor = getPreferences(Context.MODE_PRIVATE).edit();
    	editor.putInt("TabIndex", getActionBar().getSelectedNavigationIndex());
    	editor.commit();
   	
        super.onSaveInstanceState(outState);
    }
 
    private void onResetToDefault() {
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
            case R.id.item_report:
            	Intent intent_report = new Intent(this, ReportActivity.class);
            	startActivity(intent_report);
            	return true;                
            case R.id.item_result:
            	Intent intent_result = new Intent(this, ResultActiviy.class);
            	startActivity(intent_result);
            	return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    
}
