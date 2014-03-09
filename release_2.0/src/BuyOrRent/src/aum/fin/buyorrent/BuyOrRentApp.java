package aum.fin.buyorrent;

import android.app.Application;

public class BuyOrRentApp extends Application {
	private static BuyOrRentApp instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static BuyOrRentApp getInstance() {
        return instance;
    }
}
