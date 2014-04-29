package ly.cel.nucleus.controllers;

import ly.cel.nucleus.models.PhoneStatusInfo;
import ly.cel.nucleus.views.ILogView;

/**
 * Log controller
 */
public class LogController {

    private ILogView mView;

    public void setView(ILogView view) {
        mView = view;
    }

    protected ILogView getView() {
        return mView;
    }

    public void setPhoneStatusInfo(PhoneStatusInfo info) {
        getView().setPhoneStatus(info);
    }
}
