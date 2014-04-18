package org.addhen.smssync.fragments;

import android.content.ComponentName;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import org.addhen.smssync.Prefs;
import org.addhen.smssync.R;
import org.addhen.smssync.adapters.SyncUrlAdapter;
import org.addhen.smssync.listeners.SyncUrlActionModeListener;
import org.addhen.smssync.models.CellyManager;
import org.addhen.smssync.models.SyncUrl;
import org.addhen.smssync.receivers.SmsReceiver;
import org.addhen.smssync.views.SyncUrlView;

/**
 * Created by ubiquill on 4/18/14.
 */
public class CellyFragment extends BaseFragment implements View.OnClickListener {

  private CellyManager model;
  private PackageManager pm;
  private ComponentName smsReceiverComponent;

  public CellyFragment() {
    super(R.menu.sync_url_menu);
    model = new CellyManager();
  }

  @Override
  public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    setHasOptionsMenu(true);

    Prefs.loadPreferences(getActivity());
    pm = getActivity().getPackageManager();
  }

  @Override
  public void onResume() {
    super.onResume();
    //loadSyncUrlInBackground();
  }

  @Override
  public void onStart() {
    super.onStart();
  }

  @Override
  public void onPause() {
    super.onPause();
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
  }

  @Override
  public void onClick(View v) {

  }
}
