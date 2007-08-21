package org.ourproject.kune.docs.client.ui.cnt.reader;

import org.ourproject.kune.platf.client.ui.CustomButton;

import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtext.client.widgets.Button;

public class DocumentReaderPanel extends VerticalPanel implements DocumentReaderView {
    private Button btnEdit;
    private final HTML content;

    public DocumentReaderPanel(final DocumentReaderListener listener) {
	add(createToolBar(listener));
	btnEdit.setVisible(false);
	content = new HTML("this is the content");
	add(content);
    }

    private Widget createToolBar(final DocumentReaderListener listener) {
	FlowPanel panel = new FlowPanel();
	btnEdit = new CustomButton("editar", new ClickListener() {
	    public void onClick(final Widget sender) {
		listener.onEdit();
	    }
	}).getButton();
	// TODO: Vicente
	panel.add(btnEdit);
	panel.add(new Label("this is the toolBar"));
	return panel;
    }

    public void setContent(final String text) {
	content.setHTML(text);
    }

    public void setEditEnabled(final boolean isEnabled) {
	btnEdit.setVisible(isEnabled);
    }

    public String getContent() {
	return content.getHTML();
    }

}
