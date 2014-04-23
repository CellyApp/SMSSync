package org.addhen.smssync.views;

import android.app.Activity;
import android.widget.Button;

import org.addhen.smssync.R;

/**
 * Created by ubiquill on 4/21/14.
 */
public class CellyManagerView extends View {

  @Widget(R.id.cellyToggle)
  public Button cellyToggle;

  @Widget(R.id.cellyGetApp)
  public Button cellyGetApp;

  public CellyManagerView(Activity activity) {
    super(activity);
  }
}
