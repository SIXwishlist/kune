package org.ourproject.kune.workspace.client.socialnet.ui;

import org.ourproject.kune.platf.client.PlatformEvents;
import org.ourproject.kune.platf.client.services.Images;
import org.ourproject.kune.platf.client.services.Kune;
import org.ourproject.kune.platf.client.ui.UIConstants;
import org.ourproject.kune.platf.client.ui.stacks.StackSubItemAction;
import org.ourproject.kune.platf.client.ui.stacks.StackedDropDownPanel;
import org.ourproject.kune.workspace.client.WorkspaceEvents;
import org.ourproject.kune.workspace.client.socialnet.GroupMembersPresenter;
import org.ourproject.kune.workspace.client.socialnet.GroupMembersView;
import org.ourproject.kune.workspace.client.socialnet.MemberAction;

import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.gwtext.client.widgets.MessageBox;

public class GroupMembersPanel extends StackedDropDownPanel implements GroupMembersView {
    private static final boolean COUNTS_VISIBLE = true;
    private final Images img = Images.App.getInstance();
    private final GroupMembersPresenter presenter;

    public GroupMembersPanel(final GroupMembersPresenter initPresenter) {
        super(initPresenter, "#00D4AA", Kune.I18N.t("Group members"), Kune.I18N
                .t("People and groups collaborating in this group"), COUNTS_VISIBLE);
        presenter = initPresenter;
    }

    public void addJoinLink() {
        super.addBottomLink(img.addGreen(), Kune.I18N.t("Request to join"), Kune.I18N
                .t("Request to participate in this group"), WorkspaceEvents.REQ_JOIN_GROUP);
    }

    public void addUnjoinLink() {
        super.addBottomLink(img.del(), Kune.I18N.t("Unjoin this group"), Kune.I18N
                .t("Don't participate more as a member in this group"), WorkspaceEvents.UNJOIN_GROUP);
    }

    public void addAddMemberLink() {
        super.addBottomLink(img.addGreen(), Kune.I18N.t("Add member"), Kune.I18N
                .t("Add a group or a person as member of this group"), WorkspaceEvents.ADD_MEMBER_GROUPLIVESEARCH,
                presenter);
    }

    public void clear() {
        super.clear();
    }

    public void addComment(final String comment) {
        super.addComment(comment);
    }

    public void addCategory(final String name, final String title) {
        super.addStackItem(name, title, COUNTS_VISIBLE);
    }

    public void showCategory(final String name) {
        super.showStackItem(name);
    }

    public void addCategory(final String name, final String title, final String iconType) {
        super.addStackItem(name, title, getIcon(iconType), UIConstants.ICON_HORIZ_ALIGN_RIGHT, COUNTS_VISIBLE);
    }

    public void addCategoryMember(final String categoryName, final String name, final String title,
            final MemberAction[] memberActions) {
        StackSubItemAction[] subItems = new StackSubItemAction[memberActions.length];
        for (int i = 0; i < memberActions.length; i++) {
            subItems[i] = new StackSubItemAction(getIconFronEvent(memberActions[i].getAction()), memberActions[i]
                    .getText(), memberActions[i].getAction());
        }

        super.addStackSubItem(categoryName, img.groupDefIcon(), name, title, subItems);
    }

    public void show() {
        this.setVisible(true);
    }

    public void hide() {
        this.setVisible(false);
    }

    public void confirmAddCollab(final String groupShortName, final String groupLongName) {
        String groupName = groupLongName + " (" + groupShortName + ")";
        MessageBox.confirm(Kune.I18N.t("Confirm addition of member"), Kune.I18N.t("Add [%s] as member?", groupName),
                new MessageBox.ConfirmCallback() {
                    public void execute(final String btnID) {
                        if (btnID.equals("yes")) {
                            presenter.addCollab(groupShortName);
                        }
                    }
                });
    }

    private AbstractImagePrototype getIcon(final String event) {
        if (event == GroupMembersView.ICON_ALERT) {
            return img.alert();
        }
        throw new IndexOutOfBoundsException("Icon unknown in GroupMemebersPanel");
    }

    private AbstractImagePrototype getIconFronEvent(final String event) {
        if (event == WorkspaceEvents.ACCEPT_JOIN_GROUP) {
            return img.accept();
        }
        if (event == WorkspaceEvents.DENY_JOIN_GROUP) {
            return img.cancel();
        }
        if (event == WorkspaceEvents.DEL_MEMBER) {
            return img.del();
        }
        if (event == PlatformEvents.GOTO) {
            return img.groupHome();
        }
        if (event == WorkspaceEvents.SET_ADMIN_AS_COLLAB) {
            return img.arrowDownGreen();
        }
        if (event == WorkspaceEvents.SET_COLLAB_AS_ADMIN) {
            return img.arrowUpGreen();
        }
        throw new IndexOutOfBoundsException("Event unknown in GroupMembersPanel");
    }

}
