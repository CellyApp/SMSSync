package org.addhen.smssync.navdrawer;

import com.actionbarsherlock.app.SherlockFragmentActivity;

import org.addhen.smssync.fragments.CellyFragment;

/**
 * Created by ubiquill on 4/18/14.
 */
public class CellyNavDrawerItem extends BaseNavDrawerItem {

  private static final String TAG = "celly";

  /**
   * @param title
   * @param iconRes
   */
  public CellyNavDrawerItem(String title, int iconRes,
                              SherlockFragmentActivity activity) {
    super(title, iconRes, activity);
  }

  @Override
  protected void onSelectItem() {
    fragment = new CellyFragment();
    showFragment(TAG);
  }

  @Override
  public void setCounter() {
  }
}
