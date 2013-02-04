package aum.fin.buyorrent;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class MainActivity extends TabActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
 
        TabHost tabHost = getTabHost();
 
        // Tab for Buy
        TabSpec buySpec = tabHost.newTabSpec("buy_tab");
        buySpec.setIndicator("Buy");
        Intent buyIntent = new Intent(this, BuyActivity.class);
        buySpec.setContent(buyIntent);
 
        // Tab for Rent
        TabSpec rentSpec = tabHost.newTabSpec("rent_tab");
        rentSpec.setIndicator("Rent");
        Intent rentIntent = new Intent(this, RentActivity.class);
        rentSpec.setContent(rentIntent);
 
        // Tab for Tax
        TabSpec taxSpec = tabHost.newTabSpec("tax_tab");
        //videospec.setIndicator("Videos", getResources().getDrawable(R.drawable.icon_videos_tab));
        taxSpec.setIndicator("Tax");
        Intent taxIntent = new Intent(this, TaxActivity.class);
        taxSpec.setContent(taxIntent);
 
        // Adding all TabSpecs to TabHost
        tabHost.addTab(buySpec);
        tabHost.addTab(rentSpec);
        tabHost.addTab(taxSpec);
    }
}
