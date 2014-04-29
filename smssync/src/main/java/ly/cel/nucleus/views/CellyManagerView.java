package ly.cel.nucleus.views;

import android.app.Activity;
import android.widget.Button;
import android.widget.TextView;

import ly.cel.nucleus.R;

/**
 * Created by ubiquill on 4/21/14.
 */
public class CellyManagerView extends View {

  @Widget(R.id.cellyToggle)
  public Button cellyToggle;

  @Widget(R.id.cellyGetApp)
  public Button cellyGetApp;

  @Widget(R.id.cellyHelpText)
  public TextView cellyHelpText;

  public CellyManagerView(Activity activity) {
    super(activity);
  }
}
