/*******************************************************************************
 *  Copyright (c) 2010 - 2013 Ushahidi Inc
 *  All rights reserved
 *  Contact: team@ushahidi.com
 *  Website: http://www.ushahidi.com
 *  GNU Lesser General Public License Usage
 *  This file may be used under the terms of the GNU Lesser
 *  General Public License version 3 as published by the Free Software
 *  Foundation and appearing in the file LICENSE.LGPL included in the
 *  packaging of this file. Please review the following information to
 *  ensure the GNU Lesser General Public License version 3 requirements
 *  will be met: http://www.gnu.org/licenses/lgpl.html.
 *
 * If you have questions regarding the use of this file, please contact
 * Ushahidi developers at team@ushahidi.com.
 ******************************************************************************/

package ly.cel.nucleus.receivers;

import ly.cel.nucleus.Prefs;
import ly.cel.nucleus.R;
import ly.cel.nucleus.services.CheckTaskService;
import ly.cel.nucleus.services.SyncPendingMessagesService;
import ly.cel.nucleus.util.Util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * The manifest Receiver is used to detect changes in battery state. When the system broadcasts a
 * "Battery Low" warning we turn off we stop all enabled services. When the system broadcasts
 * "Battery OK" to indicate the battery has returned to an okay state, we start all enabled
 * services
 */

public class PowerStateChangedReceiver extends BroadcastReceiver {

    private boolean batteryLow;

    private Intent smsSyncAutoSyncServiceIntent;

    private Intent smsSyncTaskCheckServiceIntent;

    @Override
    public void onReceive(Context context, Intent intent) {
        batteryLow = intent.getAction().equals(Intent.ACTION_BATTERY_LOW);
        boolean batteryOkay = intent.getAction().equals(Intent.ACTION_BATTERY_OKAY);
        // load current settings
        Prefs.loadPreferences(context);
        if (batteryLow) {
            // is smssync enabled
            Util.logActivities(context, context.getString(R.string.battery_low));
            if (Prefs.enabled) {

                // clear all notifications
                Util.clearNotify(context);

                // Stop the service that pushes pending messages
                if (Prefs.enableAutoSync) {
                    smsSyncAutoSyncServiceIntent = new Intent(context,
                            SyncPendingMessagesService.class);
                    context.stopService(smsSyncAutoSyncServiceIntent);
                }

                // Stop the service that checks for tasks
                if (Prefs.enableTaskCheck) {
                    smsSyncTaskCheckServiceIntent = new Intent(context,
                            CheckTaskService.class);
                    context.stopService(smsSyncTaskCheckServiceIntent);
                }
            }
        }

        if (batteryOkay) {
            Util.logActivities(context, context.getString(R.string.battery_okay));
            // is smssync enabled
            if (Prefs.enabled) {

                // clear all notifications
                Util.clearNotify(context);

                // Stop the service that pushes pending messages
                if (Prefs.enableAutoSync) {
                    smsSyncAutoSyncServiceIntent = new Intent(context,
                            SyncPendingMessagesService.class);
                    context.startService(smsSyncAutoSyncServiceIntent);
                }

                // Stop the service that checks for tasks
                if (Prefs.enableTaskCheck) {
                    smsSyncTaskCheckServiceIntent = new Intent(context,
                            CheckTaskService.class);
                    context.startService(smsSyncTaskCheckServiceIntent);
                }
            }
        }

    }
}