package org.ourproject.kune.blogs.client.cnt.reader.ui;

import org.ourproject.kune.blogs.client.cnt.reader.BlogReaderControlView;
import org.ourproject.kune.blogs.client.cnt.reader.BlogReaderListener;
import org.ourproject.kune.platf.client.services.Kune;
import org.ourproject.kune.platf.client.ui.CustomPushButton;
import org.ourproject.kune.workspace.client.sitebar.Site;

import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Widget;

public class BlogReaderControlPanel extends HorizontalPanel implements BlogReaderControlView {
    private final CustomPushButton editBtn;
    private final CustomPushButton deleteBtn;
    private final CustomPushButton translateBtn;

    public BlogReaderControlPanel(final BlogReaderListener listener) {
        editBtn = new CustomPushButton(Kune.I18N.tWithNT("Edit", "used in button"), new ClickListener() {
            public void onClick(final Widget sender) {
                listener.onEdit();
                editBtn.removeStyleDependentName("up-hovering");
            }
        });

        deleteBtn = new CustomPushButton(Kune.I18N.tWithNT("Delete", "used in button"), new ClickListener() {
            public void onClick(final Widget sender) {
                listener.onDelete();
                deleteBtn.removeStyleDependentName("up-hovering");
            }
        });

        translateBtn = new CustomPushButton(Kune.I18N.tWithNT("Translate", "used in button"), new ClickListener() {
            public void onClick(final Widget sender) {
                listener.onTranslate();
                translateBtn.removeStyleDependentName("up-hovering");
                Site.showAlertMessage(Kune.I18N.t("Sorry, this functionality is currently in development"));
            }
        });

        add(editBtn);
        add(deleteBtn);
        add(translateBtn);
        deleteBtn.addStyleName("kune-Button-Small-lSpace");
        translateBtn.addStyleName("kune-Button-Small-lSpace");
        setEditEnabled(false);
        setDeleteEnabled(false);
        setTranslateEnabled(false);
    }

    public void setEditEnabled(final boolean isEnabled) {
        editBtn.setVisible(isEnabled);
    }

    public void setDeleteEnabled(final boolean isEnabled) {
        deleteBtn.setVisible(isEnabled);
    }

    public void setTranslateEnabled(final boolean isEnabled) {
        translateBtn.setVisible(isEnabled);
    }

}
