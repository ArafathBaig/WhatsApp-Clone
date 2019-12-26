package arafath.myappcom.whatsappclone;

import android.app.Application;

import com.parse.Parse;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId(getString(R.string.back4app_app_id))
                // if defined
                .clientKey("CG2HHiVGD6zxwJCPApsz0pJbN8Edo1KziAavpmYS")
                .server("jyePPrX8nBIvp5X2PhziJQjbHC3NVID4pROumSTV")
                .build()
        );
    }
}
