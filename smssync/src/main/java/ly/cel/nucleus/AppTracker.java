package ly.cel.nucleus;

import android.app.Activity;
import android.content.Context;

/**
 * Analytics tracker
 */
public interface AppTracker {

    public void setContext(Context context);

    public void activityStart(Activity activity);

    public void activityStop(Activity stop);

}
