package network.base.com.basenetwork;

import android.app.Application;
import android.content.Context;

/**最后集成baseApplication
 * @author cuu
 */
public class GlobalApp extends Application {
    private static GlobalApp app;
    private Context context;

    @Override
    public void onCreate() {
        super.onCreate();

        app = this;
        context = getApplicationContext();
    }

    public static synchronized GlobalApp getInstance() {
        return app;
    }

    public Context getContext() {
        return context;
    }
}
