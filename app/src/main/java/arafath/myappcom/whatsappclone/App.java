package arafath.myappcom.whatsappclone;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseInstallation;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("hrHgfr8rYPPQyaQ4Zyx4BVIZLHYi2s9a8E7cCU4s")
                .clientKey("NyyaUvCBtBLzYILs81tsme0A4gxfpPIag7ltuHj7")
                .server("https://parseapi.back4app.com/")
                .build()
        );

        
    }
}
