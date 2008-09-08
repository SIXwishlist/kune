package org.ourproject.kune.platf.client.actions;

import org.ourproject.kune.workspace.client.skel.WorkspaceSkeleton;

import com.calclab.suco.client.signal.Slot0;

public class ActionManager {

    private final WorkspaceSkeleton ws;

    public ActionManager(final WorkspaceSkeleton ws) {
	this.ws = ws;
    }

    public void doAction(final ActionDescriptor<?> action, final Object parameter) {
	if (action.isMustBeConfirmed()) {
	    ws.askConfirmation(action.getConfirmationTitle(), action.getConfirmationText(), new Slot0() {
		public void onEvent() {
		    action.fireOnPerformCall(parameter);
		}
	    }, new Slot0() {
		public void onEvent() {
		    action.fireOnNotConfirmed(parameter);
		}
	    });
	} else {
	    action.fireOnPerformCall(parameter);
	}
    }

}