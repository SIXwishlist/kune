package org.ourproject.kune.workspace.client.socialnet;

import java.util.List;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.dispatch.DefaultDispatcher;
import org.ourproject.kune.platf.client.dto.AccessListsDTO;
import org.ourproject.kune.platf.client.dto.AccessRightsDTO;
import org.ourproject.kune.platf.client.dto.GroupDTO;
import org.ourproject.kune.platf.client.dto.SocialNetworkDTO;
import org.ourproject.kune.workspace.client.WorkspaceEvents;
import org.ourproject.kune.workspace.client.workspace.SocialNetworkComponent;

public class SocialNetworkPresenter implements SocialNetworkComponent {

    private SocialNetworkView view;

    public void setSocialNetwork(final SocialNetworkDTO socialNetwork, final AccessRightsDTO rights) {
	final AccessListsDTO accessLists = socialNetwork.getAccessLists();

	List adminsList = accessLists.getAdmins().getList();
	List collabList = accessLists.getEditors().getList();
	List pendingCollabsList = socialNetwork.getPendingCollaborators().getList();

	int numAdmins = adminsList.size();
	int numCollaborators = collabList.size();
	int numPendingCollabs = pendingCollabsList.size();

	boolean userIsAdmin = rights.isAdministrable();
	boolean userIsCollab = rights.isEditable();
	boolean userCanView = rights.isVisible();
	view.setDropDownContentVisible(false);
	view.clearGroups();

	view.setVisibleAddMemberLink(userIsAdmin);
	boolean isMember = isMember(userIsAdmin, userIsCollab);
	view.setVisibleJoinLink(!isMember);

	if (userCanView) {
	    if (numAdmins > 0) {
		view.addAdminsItems(numAdmins, adminsList, rights);
	    }
	    if (numCollaborators > 0) {
		view.addCollabItems(numCollaborators, collabList, rights);
	    }
	    if (isMember) {
		if (numPendingCollabs > 0) {
		    view.addPendingCollabsItems(numPendingCollabs, pendingCollabsList, rights);
		}
	    }
	}
	view.setDropDownContentVisible(true);
    }

    private boolean isMember(final boolean userIsAdmin, final boolean userIsCollab) {
	return userIsAdmin || userIsCollab;
    }

    public void init(final SocialNetworkView view) {
	this.view = view;
    }

    public View getView() {
	return view;
    }

    public void onJoin() {
	DefaultDispatcher.getInstance().fire(WorkspaceEvents.REQ_JOIN_GROUP, null, null);
    }

    public void onAddAdmin(final GroupDTO group) {
	DefaultDispatcher.getInstance().fire(WorkspaceEvents.ADD_ADMIN_MEMBER, group, this);
    }

    public void onAddCollab(final GroupDTO group) {
	DefaultDispatcher.getInstance().fire(WorkspaceEvents.ADD_COLLAB_MEMBER, group, this);
    }

    public void onAddViewer(final GroupDTO group) {

    }

    public void onAddMember() {
	// TODO Auto-generated method stub
    }

    public void doAction(final GroupDTO group, final String action) {
	DefaultDispatcher.getInstance().fire(action, group, this);

    }

}
