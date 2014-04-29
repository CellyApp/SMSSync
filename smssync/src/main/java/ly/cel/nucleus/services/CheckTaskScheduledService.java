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

package ly.cel.nucleus.services;

import com.squareup.otto.Produce;

import ly.cel.nucleus.R;
import ly.cel.nucleus.messages.ProcessMessage;
import ly.cel.nucleus.models.SyncUrl;
import ly.cel.nucleus.util.ServicesConstants;
import ly.cel.nucleus.util.Util;

import android.content.Intent;

public class CheckTaskScheduledService extends SmsSyncServices {

    private static final String CLASS_TAG = CheckTaskScheduledService.class
            .getSimpleName();

    private SyncUrl model;

    public CheckTaskScheduledService() {
        super(CLASS_TAG);
        model = new SyncUrl();
    }

    @Override
    public void executeTask(Intent intent) {
        log("checking scheduled task services");
        Util.logActivities(this,getString(R.string.task_scheduler_running));
        // Perform a task
        for (SyncUrl syncUrl : model
                .loadByStatus(ServicesConstants.ACTIVE_SYNC_URL)) {
            new ProcessMessage(CheckTaskScheduledService.this).performTask(syncUrl);

        }
    }

    @Produce
    public boolean readLogs() {
        return true;
    }
}
