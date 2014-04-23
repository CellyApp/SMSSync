package org.addhen.smssync.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import org.addhen.smssync.models.CellyManager;

/**
 * Created by ubiquill on 4/21/14.
 */
public class CellyAdapter extends BaseListAdapter<CellyManager> {

  public CellyAdapter(Context context) {
    super(context);
  }

  @Override
  public View getView(int position, View view, ViewGroup viewGroup) {
    return null;
  }

  @Override
  public void refresh() {
  }
}
