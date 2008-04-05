package org.ourproject.kune.blogs.client.cnt;

import org.ourproject.kune.blogs.client.actions.BlogsEvents;
import org.ourproject.kune.blogs.client.cnt.folder.FolderEditor;
import org.ourproject.kune.blogs.client.cnt.folder.viewer.FolderViewer;
import org.ourproject.kune.blogs.client.cnt.reader.BlogReader;
import org.ourproject.kune.blogs.client.cnt.reader.BlogReaderControl;
import org.ourproject.kune.blogs.client.cnt.reader.BlogReaderListener;
import org.ourproject.kune.platf.client.PlatformEvents;
import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.dispatch.DefaultDispatcher;
import org.ourproject.kune.platf.client.dto.StateDTO;
import org.ourproject.kune.platf.client.extend.UIExtensionElement;
import org.ourproject.kune.platf.client.extend.UIExtensionPoint;
import org.ourproject.kune.platf.client.rpc.AsyncCallbackSimple;
import org.ourproject.kune.workspace.client.WorkspaceEvents;
import org.ourproject.kune.workspace.client.component.WorkspaceDeckView;
import org.ourproject.kune.workspace.client.editor.TextEditor;
import org.ourproject.kune.workspace.client.editor.TextEditorListener;

public class BlogContentPresenter implements BlogContent, BlogReaderListener, TextEditorListener {
    private final WorkspaceDeckView view;
    private final BlogComponents components;
    private StateDTO content;
    private final BlogContentListener listener;
    private final BlogReader reader;
    private final BlogReaderControl readerControl;

    public BlogContentPresenter(final BlogContentListener listener, final WorkspaceDeckView view) {
        this.listener = listener;
        this.view = view;
        this.components = new BlogComponents(this);
        reader = components.getDocumentReader();
        readerControl = components.getDocumentReaderControl();
    }

    public void setContent(final StateDTO content) {
        this.content = content;
        showContent();
    }

    public void onSaved() {
        components.getDocumentEditor().onSaved();
    }

    public void onSaveFailed() {
        components.getDocumentEditor().onSaveFailed();
    }

    public void onEdit() {
        DefaultDispatcher.getInstance().fire(WorkspaceEvents.ONLY_CHECK_USER_SESSION,
                new AsyncCallbackSimple<Object>() {
                    public void onSuccess(final Object result) {
                        if (content.hasDocument()) {
                            // Don't permit rate content while your are editing
                            DefaultDispatcher.getInstance().fire(WorkspaceEvents.DISABLE_RATEIT, null);
                            TextEditor editor = components.getDocumentEditor();
                            editor.setContent(content.getContent());
                            view.show(editor.getView());
                            DefaultDispatcher.getInstance().fire(PlatformEvents.CLEAR_EXT_POINT,
                                    UIExtensionPoint.CONTENT_TOOLBAR_LEFT);
                            DefaultDispatcher.getInstance().fire(PlatformEvents.ATTACH_TO_EXT_POINT,
                                    new UIExtensionElement(UIExtensionPoint.CONTENT_TOOLBAR_LEFT, editor.getToolBar()));
                        } else {
                            FolderEditor editor = components.getFolderEditor();
                            editor.setFolder(content.getFolder());
                            view.show(editor.getView());
                        }
                        listener.onEdit();
                    }
                });
    }

    public void onSave(final String text) {
        content.setContent(text);
        DefaultDispatcher.getInstance().fire(BlogsEvents.SAVE_DOCUMENT, content);
        // Re-enable rateIt widget
        DefaultDispatcher.getInstance().fire(WorkspaceEvents.ENABLE_RATEIT, null);
    }

    public void onCancel() {
        showContent();
        listener.onCancel();
        // Re-enable rateIt widget
        DefaultDispatcher.getInstance().fire(WorkspaceEvents.ENABLE_RATEIT, null);
    }

    public void onDelete() {
        DefaultDispatcher.getInstance().fire(BlogsEvents.DEL_CONTENT, content.getDocumentId());
    }

    public View getView() {
        return view;
    }

    public void attach() {
    }

    public void detach() {
    }

    public void onTranslate() {
    }

    private void showContent() {
        if (content.hasDocument()) {
            reader.showDocument(content.getContent());
            components.getDocumentEditor().reset();
            readerControl.setRights(content.getContentRights());
            DefaultDispatcher.getInstance().fire(PlatformEvents.CLEAR_EXT_POINT, UIExtensionPoint.CONTENT_TOOLBAR_LEFT);
            DefaultDispatcher.getInstance().fire(PlatformEvents.ATTACH_TO_EXT_POINT,
                    new UIExtensionElement(UIExtensionPoint.CONTENT_TOOLBAR_LEFT, readerControl.getView()));
            view.show(reader.getView());
        } else {
            FolderViewer viewer = components.getFolderViewer();
            viewer.setFolder(content.getFolder());
            DefaultDispatcher.getInstance().fire(PlatformEvents.CLEAR_EXT_POINT, UIExtensionPoint.CONTENT_TOOLBAR_LEFT);
            view.show(viewer.getView());
        }
    }
}
