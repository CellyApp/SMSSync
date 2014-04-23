package org.addhen.smssync.fragments;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import org.addhen.smssync.Prefs;
import org.addhen.smssync.R;
import org.addhen.smssync.adapters.CellyAdapter;
import org.addhen.smssync.adapters.SyncUrlAdapter;
import org.addhen.smssync.listeners.SyncUrlActionModeListener;
import org.addhen.smssync.models.CellyManager;
import org.addhen.smssync.models.SyncUrl;
import org.addhen.smssync.receivers.SmsReceiver;
import org.addhen.smssync.services.CheckTaskScheduledService;
import org.addhen.smssync.services.CheckTaskService;
import org.addhen.smssync.tasks.ProgressTask;
import org.addhen.smssync.util.RunServicesUtil;
import org.addhen.smssync.util.Util;
import org.addhen.smssync.views.AddSyncUrl;
import org.addhen.smssync.views.CellyManagerView;
import org.addhen.smssync.views.SyncUrlView;

import java.util.List;

/**
 * Created by ubiquill on 4/18/14.
 */
public class CellyFragment extends BaseListFragment<CellyManagerView, CellyManager, CellyAdapter> implements View.OnClickListener {
  final static int NUCLEUS_CODE = 457;
  private CellyManager model;
  private PackageManager pm;
  private ComponentName smsReceiverComponent;
  private SyncUrl syncUrl;

  public CellyFragment() {
    super(CellyManagerView.class, CellyAdapter.class, R.layout.celly_manager, R.menu.sync_url_menu, android.R.id.list);
    model = new CellyManager();
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (resultCode == NUCLEUS_CODE) {
      Context context = getActivity().getApplicationContext();
      model.setSecret(data.getExtras().getString("apiKey"));
      model.setStatus(1);
      model.update();
      syncUrl = model.getSyncUrl();
      toggleSync();
    }
  }

  @Override
  public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    setHasOptionsMenu(true);

    Prefs.loadPreferences(getActivity());
    pm = getActivity().getPackageManager();
    smsReceiverComponent = new ComponentName(getActivity(),
        SmsReceiver.class);

    view.cellyGetApp.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent LaunchIntent = getActivity().getPackageManager().getLaunchIntentForPackage("com.p2.android.celly");
        if (LaunchIntent != null) {
          startActivity(LaunchIntent);
        } else {
          Uri uri = Uri.parse("http://play.google.com/store/apps/details?id=com.p2.android.celly");
          Intent intent = new Intent(Intent.ACTION_VIEW, uri);
          intent.setData(uri);
          startActivity(intent);
        }
      }
    });

    toggleButton();

    view.cellyToggle.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (Prefs.enabled) {
          toggleSync();
        } else {
          Intent nucleusIntent = new Intent("com.p2.android.celly.action.NUCLEUS");
          nucleusIntent.putExtra("nucleus", true);
          Uri marketUri = Uri.parse("market://search?q=pname:com.p2.android.celly");
          Intent marketIntent = new Intent(Intent.ACTION_VIEW).setData(marketUri);

          PackageManager pm = getActivity().getPackageManager();
          for (ResolveInfo ri : pm.queryIntentActivities(nucleusIntent, 0)) {
          }
          startActivityForResult(pm.queryIntentActivities(nucleusIntent, 0).size() != 0 ?
              nucleusIntent : marketIntent, NUCLEUS_CODE);
        }
      }
    });
  }

  public void toggleButton() {
    if (Prefs.enabled) {
      view.cellyToggle.setBackgroundColor(getResources().getColor(R.color.pending_color));
      view.cellyToggle.setText(getResources().getString(R.string.celly_stop));
    } else {
      view.cellyToggle.setBackgroundColor(getResources().getColor(R.color.celly_green));
      view.cellyToggle.setText(getResources().getString(R.string.celly_start));
    }
  }

  @Override
  public android.view.View onCreateView(LayoutInflater inflater,
                                        ViewGroup container, Bundle savedInstanceState) {
    android.view.View root = null;
    if (layout != 0) {
      root = inflater.inflate(layout, container, false);
    }
    return root;
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

  public void loadByStatus() {
    LoadingTask loadingStatusTask = new LoadingTask(getActivity());
    loadingStatusTask.loadSyncUrlByStatus = true;
    loadingStatusTask.execute((String) null);
  }

  public void toggleSync() {

      loadByStatus();
      if (syncUrl != null) {
        if (!Prefs.enabled) {

          if (Util.isDefaultSmsApp(this.getActivity())) {
            // start sms receiver
            pm.setComponentEnabledSetting(smsReceiverComponent,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);

            Prefs.enabled = true;
            //view.enableSmsSync.setChecked(true);
            // because the services to be run depends on the state of the service so save the
            // changes first
            Prefs.savePreferences(getActivity());
            // run auto sync service
            RunServicesUtil.runAutoSyncService(getActivity());

            // run check task service
            RunServicesUtil.runCheckTaskService(getActivity());

            // show notification
            Util.showNotification(getActivity());
          } else {
            //view.enableSmsSync.setChecked(false);
            Prefs.enabled = false;
            Util.makeDefaultSmsApp(this.getActivity());
          }

        } else {
          // stop sms receiver
          pm.setComponentEnabledSetting(smsReceiverComponent,
              PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
              PackageManager.DONT_KILL_APP);

          RunServicesUtil.stopCheckTaskService(getActivity());
          RunServicesUtil.stopAutoSyncService(getActivity());

          // stop check task schedule
          getActivity().stopService(
              new Intent(getActivity(),
                  CheckTaskScheduledService.class));
          getActivity().stopService(
              new Intent(getActivity(), CheckTaskService.class));

          Util.clearNotify(getActivity());
          Prefs.enabled = false;
          //view.enableSmsSync.setChecked(false);
        }
      } else {
        toastLong(R.string.no_enabled_sync_url);
        Prefs.enabled = false;
        //view.enableSmsSync.setChecked(false);
      }

    Prefs.savePreferences(getActivity());
    toggleButton();
  }

  private class LoadingTask extends ProgressTask {

    protected boolean loadSyncUrlByStatus = false;

    public LoadingTask(Activity activity) {
      super(activity);
    }

    @Override
    protected void onPreExecute() {
      super.onPreExecute();
      dialog.cancel();
      //view.emptyView.setVisibility(android.view.View.GONE);
    }

    @Override
    protected Boolean doInBackground(String... args) {
      if (loadSyncUrlByStatus) {
        List<SyncUrl> syncUrlList = model.loadByStatus(1);
        if (syncUrlList.size() > 0) {
          syncUrl = syncUrlList.get(0);
        }
        return true;
      } else {
        return model.load();
      }
    }

    @Override
    protected void onPostExecute(Boolean success) {
      super.onPostExecute(success);
      if (success) {
        listView.setAdapter(adapter);
      }
    }
  }

}