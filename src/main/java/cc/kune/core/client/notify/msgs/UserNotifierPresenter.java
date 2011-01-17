package cc.kune.core.client.notify.msgs;

import cc.kune.core.client.notify.msgs.UserNotifierPresenter.UserNotifierProxy;
import cc.kune.core.client.notify.msgs.UserNotifierPresenter.UserNotifierView;

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.PopupView;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.annotations.ProxyEvent;
import com.gwtplatform.mvp.client.proxy.Proxy;
import com.gwtplatform.mvp.client.proxy.RevealRootPopupContentEvent;

public class UserNotifierPresenter extends Presenter<UserNotifierView, UserNotifierProxy> {
    @ProxyCodeSplit
    public interface UserNotifierProxy extends Proxy<UserNotifierPresenter> {
    }

    public interface UserNotifierView extends PopupView {
        public void notify(UserNotifyEvent event);
    }

    @Inject
    public UserNotifierPresenter(final EventBus eventBus, final UserNotifierView view, final UserNotifierProxy proxy) {
        super(eventBus, view, proxy);
    }

    @ProxyEvent
    public void onUserNotify(UserNotifyEvent event) {
        getView().notify(event);
    }

    @Override
    protected void revealInParent() {
        RevealRootPopupContentEvent.fire(this, this);
    }

}